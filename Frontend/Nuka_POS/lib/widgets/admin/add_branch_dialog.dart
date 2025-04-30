import 'package:flutter/material.dart';
import '../../models/branch.dart';
import '../../models/currency.dart';
import '../../services/branch_service.dart';
import '../../services/currency_service.dart';

class AddBranchDialog extends StatefulWidget {
  final int organizationId;
  final Branch? existingBranch;
  final VoidCallback onBranchAdded;

  const AddBranchDialog({
    super.key,
    required this.organizationId,
    this.existingBranch,
    required this.onBranchAdded,
  });

  @override
  State<AddBranchDialog> createState() => _AddBranchDialogState();
}

class _AddBranchDialogState extends State<AddBranchDialog> {
  final _formKey = GlobalKey<FormState>();
  final _branchService = BranchService();
  final _currencyService = CurrencyService();

  late TextEditingController _nameController;
  late TextEditingController _locationController;
  late TextEditingController _phoneController;
  late TextEditingController _emailController;
  bool isHeadOffice = false;
  List<Currency> _currencies = [];
  Currency? _selectedCurrency;
  bool _isLoadingCurrencies = true;

  @override
  void initState() {
    super.initState();
    final branch = widget.existingBranch;
    _nameController = TextEditingController(text: branch?.name);
    _locationController = TextEditingController(text: branch?.location);
    _phoneController = TextEditingController(text: branch?.phone);
    _emailController = TextEditingController(text: branch?.email);
    isHeadOffice = branch?.isHeadOffice ?? false;
    _loadCurrencies();
  }

  Future<void> _loadCurrencies() async {
    setState(() => _isLoadingCurrencies = true);
    try {
      final currencies = await _currencyService.getAllCurrencies();
      if (mounted) {
        setState(() {
          _currencies = currencies;
          if (widget.existingBranch != null) {
            _selectedCurrency = currencies.firstWhere(
                  (c) => c.id == widget.existingBranch!.currencyId,
              orElse: () => currencies.first,
            );
          }
        });
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Failed to load currencies: ${e.toString()}')),
        );
      }
    } finally {
      if (mounted) {
        setState(() => _isLoadingCurrencies = false);
      }
    }
  }

  Future<void> _submit() async {
    if (_formKey.currentState!.validate()) {
      if (_selectedCurrency == null) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Please select a currency')),
        );
        return;
      }

      final branch = Branch(
        id: widget.existingBranch?.id,
        name: _nameController.text.trim(),
        location: _locationController.text.trim(),
        phone: _phoneController.text.trim(),
        email: _emailController.text.trim(),
        isHeadOffice: isHeadOffice,
        organizationId: widget.organizationId,
        currencyId: _selectedCurrency!.id,
      );

      final success = widget.existingBranch == null
          ? await _branchService.createBranch(branch)
          : await _branchService.updateBranch(branch.id!, branch);

      if (context.mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text(success ? 'Saved successfully' : 'Failed to save')),
        );
        if (success) {
          Navigator.pop(context);
          widget.onBranchAdded();
        }
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text(widget.existingBranch == null ? 'Add Branch' : 'Edit Branch'),
      content: SingleChildScrollView(
        child: Form(
          key: _formKey,
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextFormField(
                controller: _nameController,
                decoration: const InputDecoration(labelText: 'Name'),
                validator: (val) => val == null || val.trim().isEmpty ? 'Required' : null,
              ),
              TextFormField(
                controller: _locationController,
                decoration: const InputDecoration(labelText: 'Location'),
              ),
              TextFormField(
                controller: _phoneController,
                decoration: const InputDecoration(labelText: 'Phone'),
              ),
              TextFormField(
                controller: _emailController,
                decoration: const InputDecoration(labelText: 'Email'),
              ),
              const SizedBox(height: 16),
              _isLoadingCurrencies
                  ? const CircularProgressIndicator()
                  : DropdownButtonFormField<Currency>(
                value: _selectedCurrency,
                decoration: const InputDecoration(labelText: 'Currency'),
                items: _currencies.map((currency) {
                  return DropdownMenuItem<Currency>(
                    value: currency,
                    child: Text('${currency.symbol} ${currency.name}'), // << Only Symbol and Name
                  );
                }).toList(),
                onChanged: (Currency? newValue) {
                  setState(() {
                    _selectedCurrency = newValue;
                  });
                },
                validator: (value) => value == null ? 'Required' : null,
              ),
              SwitchListTile(
                title: const Text('Is Head Office'),
                value: isHeadOffice,
                onChanged: (val) => setState(() => isHeadOffice = val),
              ),
            ],
          ),
        ),
      ),
      actions: [
        TextButton(
          onPressed: () => Navigator.pop(context),
          child: const Text('Cancel'),
        ),
        ElevatedButton(
          onPressed: _submit,
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.deepPurple,
            foregroundColor: Colors.white,
          ),
          child: const Text('Save'),
        ),
      ],
    );
  }
}
