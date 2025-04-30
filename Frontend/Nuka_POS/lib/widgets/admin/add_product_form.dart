import 'package:flutter/material.dart';
import 'package:nuka_pos/models/Category.dart';
import 'package:nuka_pos/models/Product.dart';

import '../../services/product_service.dart';

class AddProductForm extends StatefulWidget {
  final int organizationId;
  final List<Category> categories;
  final bool isBarcodeEnabled;
  final VoidCallback onSave;
  final productService = ProductService();

  AddProductForm({
    super.key,
    required this.organizationId,
    required this.categories,
    required this.isBarcodeEnabled,
    required this.onSave,
  });

  @override
  State<AddProductForm> createState() => _AddProductFormState();
}

class _AddProductFormState extends State<AddProductForm> {
  final _formKey = GlobalKey<FormState>();
  late final TextEditingController _nameController;
  late final TextEditingController _priceController;
  late final TextEditingController _descriptionController;
  late final TextEditingController _costController;
  late final TextEditingController _barcodeController;
  late Category _selectedCategory;
  bool _isActive = true;

  @override
  void initState() {
    super.initState();
    _nameController = TextEditingController();
    _priceController = TextEditingController();
    _descriptionController = TextEditingController();
    _costController = TextEditingController();
    _barcodeController = TextEditingController();
    _selectedCategory = widget.categories.first;
  }

  Future<void> _saveProduct() async {
    if (!_formKey.currentState!.validate()) return;

    final product = Product(
      id: 0,
      name: _nameController.text,
      price: double.parse(_priceController.text),
      description: _descriptionController.text,
      cost: double.parse(_costController.text),
      barcode: widget.isBarcodeEnabled ? _barcodeController.text : '',
      categoryId: _selectedCategory.id,
      isActive: _isActive,
      organizationId: widget.organizationId,
    );

    try {
      await widget.productService.createProduct(product);
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Product created successfully')),
      );
      widget.onSave();
      if (Navigator.canPop(context)) Navigator.pop(context);
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Failed to save product: $e')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text('Add Product'),
      content: Form(
        key: _formKey,
        child: SingleChildScrollView(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextFormField(
                controller: _nameController,
                decoration: const InputDecoration(labelText: 'Product Name'),
                validator: (value) => value == null || value.isEmpty ? 'Enter product name' : null,
              ),
              TextFormField(
                controller: _priceController,
                decoration: const InputDecoration(labelText: 'Price'),
                keyboardType: TextInputType.number,
                validator: (value) => value == null || value.isEmpty ? 'Enter price' : null,
              ),
              TextFormField(
                controller: _descriptionController,
                decoration: const InputDecoration(labelText: 'Description'),
              ),
              TextFormField(
                controller: _costController,
                decoration: const InputDecoration(labelText: 'Cost'),
                keyboardType: TextInputType.number,
                validator: (value) => value == null || value.isEmpty ? 'Enter cost' : null,
              ),
              DropdownButtonFormField<Category>(
                value: _selectedCategory,
                decoration: const InputDecoration(labelText: 'Category'),
                items: widget.categories.map((category) {
                  return DropdownMenuItem<Category>(
                    value: category,
                    child: Text(category.name),
                  );
                }).toList(),
                onChanged: (Category? newCategory) {
                  setState(() {
                    _selectedCategory = newCategory!;
                  });
                },
              ),
              if (widget.isBarcodeEnabled)
                TextFormField(
                  controller: _barcodeController,
                  decoration: const InputDecoration(labelText: 'Barcode'),
                  validator: (value) => value == null || value.isEmpty ? 'Enter barcode' : null,
                ),
              Row(
                children: [
                  Checkbox(
                    value: _isActive,
                    onChanged: (bool? value) {
                      setState(() {
                        _isActive = value ?? true;
                      });
                    },
                  ),
                  const Text('Is Active (Show in Sale Options)'),
                ],
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
          onPressed: _saveProduct,
          child: const Text('Save'),
        ),
      ],
    );
  }
}
