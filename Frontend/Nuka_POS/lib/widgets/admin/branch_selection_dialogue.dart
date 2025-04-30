// lib/widgets/branch_selection_dialog.dart
import 'package:flutter/material.dart';

import '../../models/branch.dart';
import '../../services/branch_service.dart';


class BranchSelectionDialog extends StatefulWidget {
  final int organizationId;
  const BranchSelectionDialog({Key? key, required this.organizationId}) : super(key: key);

  @override
  BranchSelectionDialogState createState() => BranchSelectionDialogState();
}

class BranchSelectionDialogState extends State<BranchSelectionDialog> {
  final _service = BranchService();
  bool _loading = true;
  List<Branch> _branches = [];
  String? _error;

  @override
  void initState() {
    super.initState();
    _load();
  }

  Future<void> _load() async {
    try {
      final list = await _service.getBranchesByOrganizationId(widget.organizationId);
      setState(() {
        _branches = list;
        _loading = false;
      });
    } catch (e) {
      setState(() {
        _error = 'Failed to load branches';
        _loading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text('Select Branch'),
      content: SizedBox(
        width: double.maxFinite,
        child: _loading
            ? const Center(child: CircularProgressIndicator())
            : _error != null
            ? Text(_error!, style: const TextStyle(color: Colors.red))
            : ListView.builder(
          shrinkWrap: true,
          itemCount: _branches.length,
          itemBuilder: (ctx, i) {
            final b = _branches[i];
            return ListTile(
              title: Text(b.name),
              onTap: () => Navigator.pop(context, b),
            );
          },
        ),
      ),
      actions: [
        TextButton(onPressed: () => Navigator.pop(context), child: const Text('Cancel')),
      ],
    );
  }
}
