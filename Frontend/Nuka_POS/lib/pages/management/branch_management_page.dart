import 'package:flutter/material.dart';
import '../../services/branch_service.dart';
import '../../models/branch.dart';
import '../../widgets/admin/add_branch_dialog.dart';

class BranchManagementPage extends StatefulWidget {
  final int organizationId;

  const BranchManagementPage({super.key, required this.organizationId});

  @override
  State<BranchManagementPage> createState() => _BranchManagementPageState();
}

class _BranchManagementPageState extends State<BranchManagementPage> {
  final BranchService _branchService = BranchService();
  List<Branch> _branches = [];
  bool _isLoading = true;
  bool _isDeleting = false;
  String? _errorMessage;

  @override
  void initState() {
    super.initState();
    _loadBranches();
  }

  Future<void> _loadBranches() async {
    setState(() {
      _isLoading = true;
      _errorMessage = null;
    });

    try {
      final branches = await _branchService.getBranchesByOrganizationId(widget.organizationId);
      if (mounted) {
        setState(() {
          _branches = branches;
          _isLoading = false;
        });
      }
    } catch (e) {
      if (mounted) {
        setState(() {
          _errorMessage = 'Failed to load branches: ${e.toString()}';
          _isLoading = false;
        });
      }
    }
  }

  Future<void> _confirmDeleteBranch(Branch branch) async {
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Confirm Delete'),
        content: Text('Are you sure you want to delete ${branch.name}?'),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(false),
            child: const Text('Cancel'),
          ),
          TextButton(
            onPressed: () => Navigator.of(context).pop(true),
            child: const Text('Delete', style: TextStyle(color: Colors.red)),
          ),
        ],
      ),
    );

    if (confirmed != true) return;

    setState(() => _isDeleting = true);

    try {
      final success = await _branchService.deleteBranch(branch.id!);
      if (mounted) {
        if (success) {
          await _loadBranches();
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text('Branch deleted successfully')),
          );
        } else {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text('Cannot delete branch with references')),
          );
        }
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Error: ${e.toString()}')),
        );
      }
    } finally {
      if (mounted) {
        setState(() => _isDeleting = false);
      }
    }
  }

  void _showEditBranchDialog(Branch branch) async {
    await showDialog(
      context: context,
      builder: (context) => AddBranchDialog(
        organizationId: widget.organizationId,
        existingBranch: branch,
        onBranchAdded: _loadBranches,
      ),
    );
    await _loadBranches();
  }

  void _showAddBranchDialog() async {
    await showDialog(
      context: context,
      builder: (context) => AddBranchDialog(
        organizationId: widget.organizationId,
        onBranchAdded: _loadBranches,
      ),
    );
    await _loadBranches();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Branch Management'),
        backgroundColor: Colors.deepPurple,
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: _loadBranches,
            tooltip: 'Refresh',
          ),
        ],
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : _errorMessage != null
          ? Center(child: Text(_errorMessage!))
          : _branches.isEmpty
          ? const Center(child: Text('No branches found'))
          : RefreshIndicator(
        onRefresh: _loadBranches,
        child: ListView.builder(
          padding: const EdgeInsets.all(8),
          itemCount: _branches.length,
          itemBuilder: (context, index) {
            final branch = _branches[index];
            return Card(
              margin: const EdgeInsets.symmetric(vertical: 4),
              child: ListTile(
                leading: const CircleAvatar(
                  child: Icon(Icons.store),
                ),
                title: Text(
                  branch.name,
                  style: const TextStyle(fontWeight: FontWeight.bold),
                ),
                subtitle: Text(branch.location),
                trailing: _isDeleting
                    ? const CircularProgressIndicator()
                    : Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    IconButton(
                      icon: const Icon(Icons.edit, color: Colors.blue),
                      onPressed: () => _showEditBranchDialog(branch),
                    ),
                    IconButton(
                      icon: const Icon(Icons.delete, color: Colors.red),
                      onPressed: () => _confirmDeleteBranch(branch),
                    ),
                  ],
                ),
              ),
            );
          },
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _showAddBranchDialog,
        backgroundColor: Colors.deepPurple,
        child: const Icon(Icons.add, color: Colors.white),
      ),
    );
  }
}