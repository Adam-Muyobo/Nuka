// lib/pages/service_management_page.dart

import 'package:flutter/material.dart';
import 'package:nuka_pos/models/Service.dart';
import 'package:nuka_pos/services/service_service.dart';
import 'package:nuka_pos/services/category_service.dart';
import 'package:nuka_pos/widgets/admin/add_service_form.dart';
import 'package:nuka_pos/widgets/admin/edit_service_form.dart';

class ServiceManagementPage extends StatefulWidget {
  final int organizationId;
  const ServiceManagementPage({Key? key, required this.organizationId}) : super(key: key);

  @override
  ServiceManagementPageState createState() => ServiceManagementPageState();
}

class ServiceManagementPageState extends State<ServiceManagementPage> {
  final ServiceService _serviceService = ServiceService();
  final CategoryService _categoryService = CategoryService();
  List<Service> _services = [];
  bool _isLoading = true;
  String? _error;

  @override
  void initState() {
    super.initState();
    _loadServices();
  }

  Future<void> _loadServices() async {
    setState(() => _isLoading = true);
    try {
      final svcs = await _serviceService.getServicesByOrganization(widget.organizationId);
      setState(() => _services = svcs);
    } catch (e) {
      setState(() => _error = 'Failed to load services: $e');
    } finally {
      setState(() => _isLoading = false);
    }
  }

  Future<void> _showAddDialog() async {
    final cats = await _categoryService.getCategoriesByOrganization(widget.organizationId);
    if (!mounted) return;
    showDialog(
      context: context,
      builder: (_) => AddServiceForm(
        organizationId: widget.organizationId,
        categories: cats,
        onSave: _loadServices,
      ),
    );
  }

  Future<void> _showEditDialog(Service svc) async {
    final cats = await _categoryService.getCategoriesByOrganization(widget.organizationId);
    if (!mounted) return;
    showDialog(
      context: context,
      builder: (_) => EditServiceForm(
        organizationId: widget.organizationId,
        existingService: svc,
        categories: cats,
        onSave: _loadServices,
      ),
    );
  }

  Future<void> _deleteService(int id) async {
    try {
      await _serviceService.deleteService(id);
      ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Deleted')));
      _loadServices();
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Delete failed: $e')));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Manage Services'),
        backgroundColor: Colors.deepPurple,
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            ElevatedButton.icon(
              icon: const Icon(Icons.add),
              label: const Text('Add New Service'),
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.deepPurple,
                foregroundColor: Colors.white,
              ),
              onPressed: _showAddDialog,
            ),
            if (_error != null) ...[
              const SizedBox(height: 8),
              Text(_error!, style: const TextStyle(color: Colors.red)),
            ],
            const SizedBox(height: 8),
            Expanded(
              child: ListView.builder(
                itemCount: _services.length,
                itemBuilder: (ctx, i) {
                  final svc = _services[i];
                  return Card(
                    margin: const EdgeInsets.symmetric(vertical: 4),
                    child: ListTile(
                      title: Text(svc.name),
                      subtitle: Text('Price: ${svc.price.toStringAsFixed(2)}\n${svc.description}'),
                      isThreeLine: true,
                      trailing: Row(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          IconButton(
                            icon: const Icon(Icons.edit, color: Colors.deepPurple),
                            onPressed: () => _showEditDialog(svc),
                          ),
                          IconButton(
                            icon: const Icon(Icons.delete, color: Colors.red),
                            onPressed: () => _deleteService(svc.id),
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
