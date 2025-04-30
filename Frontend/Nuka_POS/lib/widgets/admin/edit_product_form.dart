import 'package:flutter/material.dart';
import 'package:nuka_pos/models/Category.dart';
import 'package:nuka_pos/models/Product.dart';

import '../../services/product_service.dart';

class EditProductForm extends StatefulWidget {
  final Product existingProduct;
  final List<Category> categories;
  final bool isBarcodeEnabled;
  final VoidCallback onSave;
  final int organizationId;
  final productService = ProductService();

  EditProductForm({
    super.key,
    required this.existingProduct,
    required this.categories,
    required this.isBarcodeEnabled,
    required this.onSave, required this.organizationId,
  });

  @override
  State<EditProductForm> createState() => _EditProductFormState();
}

class _EditProductFormState extends State<EditProductForm> {
  final _formKey = GlobalKey<FormState>();
  late TextEditingController _nameController;
  late TextEditingController _priceController;
  late TextEditingController _descriptionController;
  late TextEditingController _costController;
  late TextEditingController _barcodeController;
  late Category? _selectedCategory;
  bool _isActive = true;

  @override
  void initState() {
    super.initState();
    _nameController = TextEditingController(text: widget.existingProduct.name);
    _priceController = TextEditingController(text: widget.existingProduct.price.toString());
    _descriptionController = TextEditingController(text: widget.existingProduct.description);
    _costController = TextEditingController(text: widget.existingProduct.cost.toString());
    _barcodeController = TextEditingController(text: widget.existingProduct.barcode);
    _selectedCategory = widget.categories.firstWhere((category) => category.id == widget.existingProduct.categoryId, orElse: () => widget.categories[0]);
    _isActive = widget.existingProduct.isActive;
  }

  Future<void> _saveProduct() async {
    if (!_formKey.currentState!.validate()) return;

    final product = Product(
      id: widget.existingProduct.id,
      name: _nameController.text,
      price: double.parse(_priceController.text),
      description: _descriptionController.text,
      cost: double.parse(_costController.text),
      barcode: widget.isBarcodeEnabled ? _barcodeController.text : '',
      categoryId: _selectedCategory!.id,
      isActive: _isActive,
      organizationId: widget.organizationId,
    );

    try {
      await widget.productService.updateProduct(product.id, product);
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Product updated successfully')),
      );
      widget.onSave();
      if (Navigator.canPop(context)) Navigator.pop(context);
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Failed to update product: $e')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text('Edit Product'),
      content: Form(
        key: _formKey,
        child: SingleChildScrollView(
          child: Column(
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
                    _selectedCategory = newCategory;
                  });
                },
                validator: (value) => value == null ? 'Select a category' : null,
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
