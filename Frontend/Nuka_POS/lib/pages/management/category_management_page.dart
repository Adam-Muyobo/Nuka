import 'package:flutter/material.dart';
import '../../models/Category.dart';
import '../../services/category_service.dart';

class CategoryManagementPage extends StatefulWidget {
  final int organizationId;

  const CategoryManagementPage({super.key, required this.organizationId});

  @override
  CategoryManagementPageState createState() => CategoryManagementPageState();
}

class CategoryManagementPageState extends State<CategoryManagementPage> {
  List<Category> categories = [];
  bool isLoading = true;
  bool isSubmitting = false;  // Track the form submission state
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _descriptionController = TextEditingController();

  @override
  void initState() {
    super.initState();
    _fetchCategories();
  }

  Future<void> _fetchCategories() async {
    setState(() => isLoading = true);
    final response = await CategoryService().getCategoriesByOrganization(widget.organizationId);
    if (mounted) {
      setState(() {
        categories = response;
        isLoading = false;
      });
    } else {
      setState(() => isLoading = false);
    }
  }

  Future<void> _createCategory() async {
    final name = _nameController.text;
    final description = _descriptionController.text;

    if (name.isEmpty || description.isEmpty) {
      _showErrorSnackBar("Please fill in all fields.");
      return;
    }

    setState(() => isSubmitting = true);  // Indicate form submission

    final newCategory = Category(
      id: 0, // New category, so ID is 0
      name: name,
      description: description,
      organizationId: widget.organizationId,
    );

    await CategoryService().createCategory(newCategory);

    _nameController.clear();
    _descriptionController.clear();
    _fetchCategories(); // Refresh the category list after creating

    setState(() => isSubmitting = false); // Stop loading
  }

  Future<void> _updateCategory(int id) async {
    final name = _nameController.text;
    final description = _descriptionController.text;

    if (name.isEmpty || description.isEmpty) {
      _showErrorSnackBar("Please fill in all fields.");
      return;
    }

    setState(() => isSubmitting = true);  // Indicate form submission

    final updatedCategory = Category(
      id: id,
      name: name,
      description: description,
      organizationId: widget.organizationId,
    );

    await CategoryService().updateCategory(id, updatedCategory);

    _nameController.clear();
    _descriptionController.clear();
    _fetchCategories(); // Refresh the category list after updating

    setState(() => isSubmitting = false); // Stop loading
  }

  Future<void> _deleteCategory(int id) async {
    await CategoryService().deleteCategory(id);
    _fetchCategories(); // Refresh the category list after deleting
  }

  // Show error SnackBar
  void _showErrorSnackBar(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text(message)),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Manage Categories'),
        backgroundColor: Colors.deepPurple,
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Category list
            Expanded(
              child: ListView.builder(
                itemCount: categories.length,
                itemBuilder: (context, index) {
                  final category = categories[index];
                  return ListTile(
                    title: Text(category.name),
                    subtitle: Text(category.description),
                    trailing: Row(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        IconButton(
                          icon: const Icon(Icons.edit),
                          onPressed: () {
                            _nameController.text = category.name;
                            _descriptionController.text = category.description;
                            _showCategoryDialog(
                              context,
                              category.id,
                              'Edit Category',
                                  (id) => _updateCategory(id!), // Ensuring we pass a valid `id`
                            );
                          },
                        ),
                        IconButton(
                          icon: const Icon(Icons.delete),
                          onPressed: () {
                            _deleteCategory(category.id);
                          },
                        ),
                      ],
                    ),
                  );
                },
              ),
            ),
            const SizedBox(height: 12),
            // Create new category button
            ElevatedButton.icon(
              icon: const Icon(Icons.add),
              label: const Text('Add New Category'),
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.deepPurple,
                foregroundColor: Colors.white,
              ),
              onPressed: () {
                _showCategoryDialog(
                  context,
                  null,
                  'Create Category',
                      (id) => _createCategory(), // Passing a dummy value
                );
              },
            ),
          ],
        ),
      ),
    );
  }

  // Show dialog to create or update category
  void _showCategoryDialog(
      BuildContext context, int? id, String title, Function(int?) onSubmit) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text(title),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                controller: _nameController,
                decoration: const InputDecoration(labelText: 'Category Name'),
              ),
              TextField(
                controller: _descriptionController,
                decoration: const InputDecoration(labelText: 'Category Description'),
              ),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: const Text('Cancel'),
            ),
            ElevatedButton(
              onPressed: isSubmitting
                  ? null // Disable button during form submission
                  : () {
                onSubmit(id); // Call the callback with the id
                Navigator.of(context).pop();
              },
              child: Text(id == null ? 'Create' : 'Update'),
            ),
          ],
        );
      },
    );
  }
}
