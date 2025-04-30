// lib/widgets/admin/add_service_form.dart

import 'package:flutter/material.dart';
import 'package:nuka_pos/models/Category.dart';
import 'package:nuka_pos/models/Service.dart';
import 'package:nuka_pos/services/service_service.dart';

class AddServiceForm extends StatefulWidget {
  final int organizationId;
  final List<Category> categories;
  final VoidCallback onSave;

  const AddServiceForm({
    Key? key,
    required this.organizationId,
    required this.categories,
    required this.onSave,
  }) : super(key: key);

  @override
  AddServiceFormState createState() => AddServiceFormState();
}

class AddServiceFormState extends State<AddServiceForm> {
  final _formKey = GlobalKey<FormState>();
  final ServiceService _svc = ServiceService();

  late TextEditingController _nameCtl;
  late TextEditingController _priceCtl;
  late TextEditingController _descCtl;
  late Category _selectedCat;
  bool _isActive = true;
  bool _submitting = false;

  @override
  void initState() {
    super.initState();
    _nameCtl = TextEditingController();
    _priceCtl = TextEditingController();
    _descCtl = TextEditingController();
    _selectedCat = widget.categories.first;
  }

  Future<void> _submit() async {
    if (!_formKey.currentState!.validate()) return;
    final service = Service(
      id: 0,
      name: _nameCtl.text.trim(),
      description: _descCtl.text.trim(),
      price: double.tryParse(_priceCtl.text.trim()) ?? 0.0,
      isActive: _isActive,
      organizationId: widget.organizationId,
      categoryId: _selectedCat.id,
    );
    setState(() => _submitting = true);
    try {
      await _svc.createService(service);
      ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Created')));
      widget.onSave();
      Navigator.pop(context);
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Error: $e')));
    } finally {
      if (mounted) setState(() => _submitting = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text('Add Service'),
      content: Form(
        key: _formKey,
        child: SingleChildScrollView(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextFormField(
                controller: _nameCtl,
                decoration: const InputDecoration(labelText: 'Service Name'),
                validator: (v) => v == null || v.isEmpty ? 'Required' : null,
              ),
              const SizedBox(height: 8),
              TextFormField(
                controller: _priceCtl,
                decoration: const InputDecoration(labelText: 'Price'),
                keyboardType: const TextInputType.numberWithOptions(decimal: true),
                validator: (v) => v == null || v.isEmpty ? 'Required' : null,
              ),
              const SizedBox(height: 8),
              TextFormField(
                controller: _descCtl,
                decoration: const InputDecoration(labelText: 'Description'),
              ),
              const SizedBox(height: 8),
              DropdownButtonFormField<Category>(
                value: _selectedCat,
                decoration: const InputDecoration(labelText: 'Category'),
                items: widget.categories.map((c) => DropdownMenuItem(
                  value: c,
                  child: Text(c.name),
                )).toList(),
                onChanged: (c) => setState(() => _selectedCat = c!),
              ),
              const SizedBox(height: 8),
              SwitchListTile(
                title: const Text('Is Active'),
                value: _isActive,
                onChanged: (v) => setState(() => _isActive = v),
              ),
            ],
          ),
        ),
      ),
      actions: [
        TextButton(
          onPressed: _submitting ? null : () => Navigator.pop(context),
          child: const Text('Cancel'),
        ),
        ElevatedButton(
          onPressed: _submitting ? null : _submit,
          style: ElevatedButton.styleFrom(backgroundColor: Colors.deepPurple),
          child: _submitting
              ? const SizedBox(width: 20, height: 20, child: CircularProgressIndicator(color: Colors.white, strokeWidth: 2))
              : const Text('Save'),
        ),
      ],
    );
  }
}
