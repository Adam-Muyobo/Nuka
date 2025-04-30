// lib/pages/inventory_management_page.dart

import 'package:flutter/material.dart';

import '../../models/Category.dart';
import '../../models/Product.dart';
import '../../models/branch.dart';
import '../../models/inventory.dart';
import '../../services/category_service.dart';
import '../../services/inventory_service.dart';
import '../../services/product_service.dart';
import '../../widgets/admin/add_edit_inventory_dialogue.dart';
import '../../widgets/admin/branch_selection_dialogue.dart';
import '../../widgets/admin/inventory_list.dart';

class InventoryManagementPage extends StatefulWidget {
  final int organizationId;
  const InventoryManagementPage({Key? key, required this.organizationId}) : super(key: key);

  @override
  InventoryManagementPageState createState() => InventoryManagementPageState();
}

class InventoryManagementPageState extends State<InventoryManagementPage> {
  Branch? _selectedBranch;
  List<Inventory> _items = [];
  Map<int, Product> _productMap = {};
  Map<int, Category> _categoryMap = {};
  bool _loading = false;
  String? _error;

  @override
  void initState() {
    super.initState();
  }

  Future<void> _pickBranch() async {
    final branch = await showDialog<Branch>(
      context: context,
      builder: (ctx) => BranchSelectionDialog(organizationId: widget.organizationId),
    );
    if (branch == null) return;

    setState(() {
      _selectedBranch = branch;
      _loading = true;
      _error = null;
    });

    try {
      // load products & categories for name lookup
      final products = await ProductService().getProductsByOrganization(widget.organizationId);
      final categories = await CategoryService().getCategoriesByOrganization(widget.organizationId);
      _productMap = {for (var p in products) p.id: p};
      _categoryMap = {for (var c in categories) c.id: c};

      // load inventory for this branch
      final inv = await InventoryService().getByBranch(branch.id!);
      setState(() => _items = inv);
    } catch (e) {
      setState(() => _error = 'Failed to load inventory: $e');
    } finally {
      setState(() => _loading = false);
    }
  }

  Future<void> _openAddEdit([Inventory? existing]) async {
    if (_selectedBranch == null) return;
    final changed = await showDialog<bool>(
      context: context,
      builder: (_) => AddEditInventoryDialog(
        organizationId: widget.organizationId,
        branch: _selectedBranch!,
        productMap: _productMap,
        existing: existing,
      ),
    );
    if (changed == true) {
      await _pickBranch();
    }
  }

  Future<void> _deleteItem(int id) async {
    try {
      final ok = await InventoryService().deleteInventory(id);
      if (ok) {
        ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Deleted')));
        await _pickBranch();
      } else {
        throw 'Delete failed';
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Error: $e')));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Inventory Management'),
        backgroundColor: Colors.deepPurple,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            ElevatedButton.icon(
              icon: const Icon(Icons.store),
              label: Text(_selectedBranch == null
                  ? 'Select Branch'
                  : 'Branch: ${_selectedBranch!.name}'),
              onPressed: _pickBranch,
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.deepPurple,
                foregroundColor: Colors.white,
              ),
            ),
            const SizedBox(height: 12),
            if (_loading) const CircularProgressIndicator(),
            if (_error != null)
              Text(_error!, style: const TextStyle(color: Colors.red)),
            if (!_loading && _selectedBranch != null)
              Expanded(
                child: InventoryList(
                  items: _items,
                  productMap: _productMap,
                  categoryMap: _categoryMap,
                  onEdit: _openAddEdit,
                  onDelete: _deleteItem,
                ),
              ),
          ],
        ),
      ),
      floatingActionButton: _selectedBranch == null
          ? null
          : FloatingActionButton(
        backgroundColor: Colors.deepPurple,
        child: const Icon(Icons.add),
        onPressed: () => _openAddEdit(),
      ),
    );
  }
}
