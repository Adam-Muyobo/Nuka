// lib/widgets/add_edit_inventory_dialog.dart
import 'package:flutter/material.dart';

import '../../models/Product.dart';
import '../../models/branch.dart';
import '../../models/inventory.dart';
import '../../services/inventory_service.dart';

class AddEditInventoryDialog extends StatefulWidget {
  final int organizationId;
  final Branch branch;
  final Map<int, Product> productMap;
  final Inventory? existing;

  const AddEditInventoryDialog({
    Key? key,
    required this.organizationId,
    required this.branch,
    required this.productMap,
    this.existing,
  }) : super(key: key);

  @override
  AddEditInventoryDialogState createState() => AddEditInventoryDialogState();
}

class AddEditInventoryDialogState extends State<AddEditInventoryDialog> {
  final _formKey = GlobalKey<FormState>();
  late Product _selectedProduct;
  late TextEditingController _availCtl;
  late TextEditingController _resCtl;
  late TextEditingController _soldCtl;
  late TextEditingController _costCtl;
  late String _status;
  bool _submitting = false;

  static const _statuses = ['AVAILABLE', 'RESERVED', 'SOLD'];

  @override
  void initState() {
    super.initState();
    final ex = widget.existing;
    _selectedProduct = ex != null
        ? widget.productMap[ex.productId]!
        : widget.productMap.values.first;
    _availCtl = TextEditingController(text: ex?.quantityAvailable.toString() ?? '0');
    _resCtl   = TextEditingController(text: ex?.quantityReserved.toString() ?? '0');
    _soldCtl  = TextEditingController(text: ex?.quantitySold.toString() ?? '0');
    _costCtl  = TextEditingController(text: ex?.cost.toString() ?? widget.productMap[_selectedProduct.id]!.cost.toString());
    _status   = ex?.inventoryStatus ?? 'AVAILABLE';
  }

  @override
  void dispose() {
    _availCtl.dispose();
    _resCtl.dispose();
    _soldCtl.dispose();
    _costCtl.dispose();
    super.dispose();
  }

  Future<void> _save() async {
    if (!_formKey.currentState!.validate()) return;
    setState(() => _submitting = true);

    final inv = Inventory(
      id: widget.existing?.id ?? 0,
      organizationId: widget.organizationId,
      productId: _selectedProduct.id,
      branchId: widget.branch.id!,
      quantityAvailable: int.parse(_availCtl.text),
      quantityReserved:  int.parse(_resCtl.text),
      quantitySold:      int.parse(_soldCtl.text),
      cost:               double.parse(_costCtl.text),
      inventoryStatus:   _status,
      createdAt:          widget.existing?.createdAt ?? DateTime.now(),
      updatedAt:          DateTime.now(),
    );

    bool ok;
    if (widget.existing == null) {
      ok = await InventoryService().createInventory(inv);
    } else {
      ok = await InventoryService().updateInventory(inv.id, inv);
    }

    if (mounted) {
      if (ok) {
        Navigator.pop(context, true);
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Save failed')),
        );
      }
      setState(() => _submitting = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text(widget.existing == null ? 'Add Inventory' : 'Edit Inventory'),
      content: Form(
        key: _formKey,
        child: SizedBox(
          width: 300,
          child: SingleChildScrollView(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                // Product dropdown
                DropdownButtonFormField<Product>(
                  value: _selectedProduct,
                  decoration: const InputDecoration(labelText: 'Product'),
                  items: widget.productMap.values.map((p) {
                    return DropdownMenuItem(value: p, child: Text(p.name));
                  }).toList(),
                  onChanged: (p) {
                    if (p == null) return;
                    setState(() {
                      _selectedProduct = p;
                      _costCtl.text = p.cost.toString();
                    });
                  },
                ),
                const SizedBox(height: 8),
                // Quantity fields
                TextFormField(
                  controller: _availCtl,
                  keyboardType: TextInputType.number,
                  decoration: const InputDecoration(labelText: 'Quantity Available'),
                  validator: (v) => (v == null || v.isEmpty) ? 'Required' : null,
                ),
                const SizedBox(height: 8),
                TextFormField(
                  controller: _resCtl,
                  keyboardType: TextInputType.number,
                  decoration: const InputDecoration(labelText: 'Quantity Reserved'),
                ),
                const SizedBox(height: 8),
                TextFormField(
                  controller: _soldCtl,
                  keyboardType: TextInputType.number,
                  decoration: const InputDecoration(labelText: 'Quantity Sold'),
                ),
                const SizedBox(height: 8),
                // Cost (read-only)
                TextFormField(
                  controller: _costCtl,
                  decoration: const InputDecoration(labelText: 'Cost'),
                  readOnly: true,
                ),
                const SizedBox(height: 8),
                // Status dropdown
                DropdownButtonFormField<String>(
                  value: _status,
                  decoration: const InputDecoration(labelText: 'Status'),
                  items: _statuses.map((s) => DropdownMenuItem(value: s, child: Text(s))).toList(),
                  onChanged: (s) => setState(() => _status = s!),
                ),
              ],
            ),
          ),
        ),
      ),
      actions: [
        TextButton(
          onPressed: _submitting ? null : () => Navigator.pop(context),
          child: const Text('Cancel'),
        ),
        ElevatedButton(
          onPressed: _submitting ? null : _save,
          style: ElevatedButton.styleFrom(backgroundColor: Colors.deepPurple),
          child: _submitting
              ? const SizedBox(width: 20, height: 20, child: CircularProgressIndicator(color: Colors.white, strokeWidth: 2))
              : const Text('Save'),
        ),
      ],
    );
  }
}
