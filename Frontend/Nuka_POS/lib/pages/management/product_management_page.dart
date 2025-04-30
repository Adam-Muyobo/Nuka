import 'package:flutter/material.dart';
import 'package:nuka_pos/models/Product.dart';
import 'package:nuka_pos/services/product_service.dart';
import 'package:nuka_pos/services/category_service.dart';
import '../../models/organization.dart';
import '../../services/organization_service.dart';
import '../../widgets/admin/add_product_form.dart';
import '../../widgets/admin/edit_product_form.dart';

class ProductManagementPage extends StatefulWidget {
  final int organizationId;
  final OrganizationService organizationService = OrganizationService();
  final CategoryService categoryService = CategoryService();

  ProductManagementPage({super.key, required this.organizationId});

  @override
  State<ProductManagementPage> createState() => _ProductManagementPageState();
}

class _ProductManagementPageState extends State<ProductManagementPage> {
  List<Product> products = [];
  bool isLoading = true;
  String? errorMessage;
  bool _isBarcodeEnabled = false;

  @override
  void initState() {
    super.initState();
    _initializeData();
  }

  Future<void> _initializeData() async {
    try {
      final productFuture = ProductService().getProductsByOrganization(widget.organizationId);
      final organizationFuture = widget.organizationService.getOrganizationById(widget.organizationId);

      final results = await Future.wait([productFuture, organizationFuture]);

      // Handle products
      final products = (results[0] as List<Product>)
        ..sort((a, b) => a.name.compareTo(b.name));

      // Handle organization - convert JSON to Organization object
      final organizationJson = results[1] as Map<String, dynamic>;
      final organization = Organization.fromJson(organizationJson);

      if (mounted) {
        setState(() {
          this.products = products;
          _isBarcodeEnabled = organization.barcodeEnabled;
          isLoading = false;
        });
      }
    } catch (e) {
      if (mounted) {
        setState(() {
          errorMessage = 'Failed to load data: ${e.toString()}';
          isLoading = false;
        });
      }
    }
  }

  Future<void> _fetchProducts() async {
    setState(() {
      isLoading = true;
      errorMessage = null;
    });
    try {
      final fetchedProducts = await ProductService().getProductsByOrganization(widget.organizationId);
      fetchedProducts.sort((a, b) => a.name.compareTo(b.name));
      setState(() {
        products = fetchedProducts.cast<Product>();
      });
    } catch (e) {
      setState(() {
        errorMessage = 'Failed to load products: ${e.toString()}';
      });
    } finally {
      setState(() {
        isLoading = false;
      });
    }
  }


  void _showAddProductDialog() async {
    try {
      final categories = await widget.categoryService.getCategoriesByOrganization(widget.organizationId);

      if (!mounted) return;

      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AddProductForm(
            organizationId: widget.organizationId,
            categories: categories,
            isBarcodeEnabled: _isBarcodeEnabled,
            onSave: () {
              Navigator.of(context).pop();
              _fetchProducts();
            },
          );
        },
      );
    } catch (e) {
      if (mounted) {
        setState(() {
          errorMessage = 'Failed to load categories: ${e.toString()}';
        });
      }
    }
  }

  void _showEditProductDialog(Product product) async {
    try {
      final categories = await widget.categoryService.getCategoriesByOrganization(widget.organizationId);

      if (!mounted) return;

      showDialog(
        context: context,
        builder: (BuildContext context) {
          return EditProductForm(
            organizationId: widget.organizationId,
            existingProduct: product,
            categories: categories,
            isBarcodeEnabled: _isBarcodeEnabled,
            onSave: () {
              Navigator.of(context).pop();
              _fetchProducts();
            },
          );
        },
      );
    } catch (e) {
      if (mounted) {
        setState(() {
          errorMessage = 'Failed to load categories: ${e.toString()}';
        });
      }
    }
  }

  Future<void> _deleteProduct(int productId) async {
    try {
      await ProductService().deleteProduct(productId);
      _fetchProducts();
    } catch (e) {
      if (mounted) {
        setState(() {
          errorMessage = 'Failed to delete product: ${e.toString()}';
        });
      }
    }
  }

  Future<void> _confirmDelete(Product product) async {
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Confirm Delete'),
        content: Text('Are you sure you want to delete ${product.name}?'),
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

    if (confirmed == true) {
      await _deleteProduct(product.id);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Manage Products'),
        backgroundColor: Colors.deepPurple,
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            ElevatedButton.icon(
              icon: const Icon(Icons.add),
              label: const Text('Add New Product'),
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.deepPurple,
                foregroundColor: Colors.white,
                minimumSize: const Size(double.infinity, 48),
              ),
              onPressed: _showAddProductDialog,
            ),
            const SizedBox(height: 12),
            if (errorMessage != null)
              Padding(
                padding: const EdgeInsets.symmetric(vertical: 8.0),
                child: Text(
                  errorMessage!,
                  style: const TextStyle(color: Colors.red),
                ),
              ),
            const SizedBox(height: 12),
            Expanded(
              child: products.isEmpty
                  ? const Center(
                child: Text('No products found'),
              )
                  : ListView.builder(
                itemCount: products.length,
                itemBuilder: (context, index) {
                  final product = products[index];
                  return Card(
                    margin: const EdgeInsets.symmetric(vertical: 4),
                    child: ListTile(
                      title: Text(product.name),
                      subtitle: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text('\$${product.price.toStringAsFixed(2)}'),
                          if (product.description.isNotEmpty)
                            Text(
                              product.description,
                              maxLines: 1,
                              overflow: TextOverflow.ellipsis,
                              style: Theme.of(context).textTheme.bodySmall,
                            ),
                        ],
                      ),
                      trailing: Row(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          IconButton(
                            icon: const Icon(Icons.edit, color: Colors.deepPurple),
                            onPressed: () => _showEditProductDialog(product),
                          ),
                          IconButton(
                            icon: const Icon(Icons.delete, color: Colors.red),
                            onPressed: () => _confirmDelete(product),
                          ),
                        ],
                      ),
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}