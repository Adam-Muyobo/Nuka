import 'package:flutter/material.dart';
import 'package:nuka_pos/models/Category.dart';
import 'package:nuka_pos/models/Product.dart';
import '../../services/product_service.dart'; // Assuming ProductService is defined here

class ProductForm extends StatefulWidget {
  final int organizationId;
  final Product? existingProduct;
  final List<Category> categories;
  final bool isBarcodeEnabled; // New prop to check if barcode is enabled
  final VoidCallback onSave;
  final productService = ProductService();

  ProductForm({
    super.key,
    required this.organizationId,
    this.existingProduct,
    required this.categories,
    required this.isBarcodeEnabled, // Passing the barcode flag here
    required this.onSave,
  });

  @override
  State<ProductForm> createState() => _ProductFormState();
}

class _ProductFormState extends State<ProductForm> {
  final _formKey = GlobalKey<FormState>();
  late TextEditingController _nameController;
  late TextEditingController _priceController;
  late TextEditingController _descriptionController;
  late TextEditingController _costController;
  late TextEditingController _barcodeController;  // New controller for barcode
  late Category? _selectedCategory;
  bool _isActive = true;

  @override
  void initState() {
    super.initState();
    _nameController = TextEditingController(text: widget.existingProduct?.name ?? '');
    _priceController = TextEditingController(text: widget.existingProduct?.price.toString() ?? '');
    _descriptionController = TextEditingController(text: widget.existingProduct?.description ?? '');
    _costController = TextEditingController(text: widget.existingProduct?.cost.toString() ?? '');
    _barcodeController = TextEditingController(text: widget.existingProduct?.barcode ?? ''); // Initialize barcode controller

    // Set selected category based on existing product
    _selectedCategory = widget.categories.firstWhere(
            (category) => category.id == widget.existingProduct?.categoryId,
        orElse: () => widget.categories[0]);

    // Set isActive value
    _isActive = widget.existingProduct?.isActive ?? true;
  }

  // Save or update product using ProductService
  Future<void> _saveProduct() async {
    if (!_formKey.currentState!.validate()) return;

    final product = Product(
      id: widget.existingProduct?.id ?? 0,  // If product exists, use existing ID
      name: _nameController.text,
      price: double.parse(_priceController.text),
      description: _descriptionController.text,
      cost: double.parse(_costController.text),
      barcode: widget.isBarcodeEnabled ? _barcodeController.text : '', // Use barcode if enabled
      categoryId: _selectedCategory?.id ?? 0,
      isActive: _isActive, organizationId: widget.organizationId,
    );

    try {
      // Show a loading indicator if needed (optional)
      final response = widget.existingProduct == null
          ? await widget.productService.createProduct(product)
          : await widget.productService.updateProduct(product.id, product);

      // Notify user on success
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(widget.existingProduct == null ? 'Product created successfully' : 'Product updated successfully')),
      );

      widget.onSave(); // Trigger the onSave callback to refresh the parent

      // Close the form dialog after successful operation
      if (Navigator.canPop(context)) {
        Navigator.pop(context);
      }
    } catch (e) {
      // Handle failure
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Failed to save product: $e')),
      );
    }
  }

  // Form for adding a new product
  Widget _buildAddProductForm() {
    return Column(
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
          validator: (value) {
            if (value == null || value.isEmpty) {
              return 'Enter cost';
            }
            if (double.tryParse(value) == null) {
              return 'Enter a valid cost';
            }
            return null;
          },
        ),
        // Dropdown for Categories
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
        // Barcode field (conditionally displayed)
        if (widget.isBarcodeEnabled)
          TextFormField(
            controller: _barcodeController,
            decoration: const InputDecoration(labelText: 'Barcode'),
            validator: (value) => value == null || value.isEmpty ? 'Enter barcode' : null,
          ),
        // Checkbox for Is Active
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
    );
  }

  // Form for updating an existing product
  Widget _buildEditProductForm() {
    return Column(
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
          validator: (value) {
            if (value == null || value.isEmpty) {
              return 'Enter cost';
            }
            if (double.tryParse(value) == null) {
              return 'Enter a valid cost';
            }
            return null;
          },
        ),
        // Dropdown for Categories
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
        // Barcode field (conditionally displayed)
        if (widget.isBarcodeEnabled)
          TextFormField(
            controller: _barcodeController,
            decoration: const InputDecoration(labelText: 'Barcode'),
            validator: (value) => value == null || value.isEmpty ? 'Enter barcode' : null,
          ),
        // Checkbox for Is Active
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
    );
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text(widget.existingProduct == null ? 'Add Product' : 'Edit Product'),
      content: Form(
        key: _formKey,
        child: SingleChildScrollView(
          child: widget.existingProduct == null
              ? _buildAddProductForm()  // Use form for adding a new product
              : _buildEditProductForm(),  // Use form for updating an existing product
        ),
      ),
      actions: [
        TextButton(
          onPressed: () => Navigator.pop(context),
          child: const Text('Cancel'),
        ),
        ElevatedButton(
          onPressed: _saveProduct, // Directly call _saveProduct without passing BuildContext
          child: const Text('Save'),
        ),
      ],
    );
  }
}
