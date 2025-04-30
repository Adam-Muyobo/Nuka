import 'package:flutter/material.dart';
import '../../services/organization_service.dart';

class EditOrganizationPage extends StatefulWidget {
  final int organizationId;
  final int userId;

  const EditOrganizationPage({super.key, required this.organizationId, required this.userId});

  @override
  State<EditOrganizationPage> createState() => _EditOrganizationPageState();
}

class _EditOrganizationPageState extends State<EditOrganizationPage> {
  final _formKey = GlobalKey<FormState>();
  final OrganizationService _organizationService = OrganizationService();

  Map<String, dynamic> organizationData = {};
  bool isLoading = true;

  final List<String> businessTypes = ['RETAIL', 'WHOLESALE', 'SERVICE'];
  final List<String> receiptModes = ['PRINT', 'EMAIL', 'BOTH'];

  @override
  void initState() {
    super.initState();
    _loadOrganization();
  }

  Future<void> _loadOrganization() async {
    final org = await _organizationService.getOrganizationById(widget.organizationId);
    if (org != null && mounted) {
      setState(() {
        organizationData = org;
        isLoading = false;
      });
    }
  }

  Future<void> _saveChanges() async {
    if (_formKey.currentState!.validate()) {
      final success = await _organizationService.updateOrganization(
        widget.organizationId,
        organizationData,
      );

      if (!mounted) return;

      if (success) {
        // Log audit
        await _organizationService.logAudit(
          userId: widget.userId,
          action: 'UPDATE',
          entity: 'Organization',
          entityId: widget.organizationId,
          details: 'Updated organization settings',
        );
      }

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(success
              ? 'Organization updated successfully'
              : 'Failed to update organization'),
        ),
      );

      if (success) Navigator.pop(context);
    }
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Edit Organization'),
        backgroundColor: Colors.deepPurple,
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: ListView(
            children: [
              TextFormField(
                initialValue: organizationData['name'] ?? '',
                decoration: const InputDecoration(labelText: 'Organization Name'),
                onChanged: (value) => organizationData['name'] = value,
                validator: (value) => value!.isEmpty ? 'Required' : null,
              ),
              TextFormField(
                initialValue: organizationData['email'] ?? '',
                decoration: const InputDecoration(labelText: 'Email'),
                onChanged: (value) => organizationData['email'] = value,
                validator: (value) => value!.isEmpty ? 'Required' : null,
              ),
              TextFormField(
                initialValue: organizationData['phone'] ?? '',
                decoration: const InputDecoration(labelText: 'Phone'),
                onChanged: (value) => organizationData['phone'] = value,
              ),
              DropdownButtonFormField<String>(
                value: organizationData['businessType'],
                decoration: const InputDecoration(labelText: 'Business Type'),
                items: businessTypes
                    .map((type) => DropdownMenuItem(value: type, child: Text(type)))
                    .toList(),
                onChanged: (value) => setState(() {
                  organizationData['businessType'] = value;
                }),
              ),
              DropdownButtonFormField<String>(
                value: organizationData['receiptMode'],
                decoration: const InputDecoration(labelText: 'Receipt Mode'),
                items: receiptModes
                    .map((mode) => DropdownMenuItem(value: mode, child: Text(mode)))
                    .toList(),
                onChanged: (value) => setState(() {
                  organizationData['receiptMode'] = value;
                }),
              ),
              TextFormField(
                initialValue: organizationData['taxNumber'] ?? '',
                decoration: const InputDecoration(labelText: 'Tax Number'),
                onChanged: (value) => organizationData['taxNumber'] = value,
              ),
              SwitchListTile(
                title: const Text('Smart Invoicing Enabled'),
                value: organizationData['smartInvoicingEnabled'] ?? false,
                onChanged: (value) => setState(() {
                  organizationData['smartInvoicingEnabled'] = value;
                }),
              ),
              SwitchListTile(
                title: const Text('Barcode Enabled'),
                value: organizationData['barcodeEnabled'] ?? false,
                onChanged: (value) => setState(() {
                  organizationData['barcodeEnabled'] = value;
                }),
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: _saveChanges,
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.deepPurple,
                  foregroundColor: Colors.white,
                ),
                child: const Text('Save Changes'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
