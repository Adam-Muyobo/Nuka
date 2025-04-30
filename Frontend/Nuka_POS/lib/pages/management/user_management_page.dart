import 'package:flutter/material.dart';
import 'package:nuka_pos/services/user_service.dart';
import 'package:nuka_pos/models/user.dart';
import '../../widgets/admin/user_form_page.dart';

class UserManagementPage extends StatefulWidget {
  final int organizationId;

  const UserManagementPage({super.key, required this.organizationId});

  @override
  UserManagementPageState createState() => UserManagementPageState();
}

class UserManagementPageState extends State<UserManagementPage> {
  final UserService _userService = UserService();
  List<User> _users = [];
  bool _isLoading = true;
  String? _errorMessage;
  bool _isDeleting = false;

  @override
  void initState() {
    super.initState();
    _loadUsers();
  }

  Future<void> _loadUsers() async {
    setState(() {
      _isLoading = true;
      _errorMessage = null;
    });

    try {
      final users = await _userService.getUsersByOrganization(widget.organizationId);
      if (mounted) {
        setState(() {
          _users = users;
          _isLoading = false;
        });
      }
    } catch (e) {
      if (mounted) {
        setState(() {
          _errorMessage = 'Failed to load users: ${e.toString()}';
          _isLoading = false;
        });
      }
    }
  }

  Future<void> _deleteUser(User user) async {
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Confirm Delete'),
        content: Text('Are you sure you want to delete ${user.username}?'),
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

    if (confirmed != true) return;

    setState(() => _isDeleting = true);

    try {
      final success = await _userService.deleteUser(user.id!);
      if (success && mounted) {
        await _loadUsers();
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('User deleted successfully')),
        );
      } else if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Failed to delete user')),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Error: ${e.toString()}')),
        );
      }
    } finally {
      if (mounted) {
        setState(() => _isDeleting = false);
      }
    }
  }

  void _navigateToUserForm([User? user]) async {
    await Navigator.push(
      context,
      MaterialPageRoute(
        builder: (_) => UserFormPage(user: user),
      ),
    );
    await _loadUsers();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('User Management'),
        backgroundColor: Colors.deepPurple,
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: _loadUsers,
            tooltip: 'Refresh',
          ),
        ],
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : _errorMessage != null
          ? Center(child: Text(_errorMessage!))
          : _users.isEmpty
          ? const Center(child: Text('No users found'))
          : RefreshIndicator(
        onRefresh: _loadUsers,
        child: ListView.builder(
          padding: const EdgeInsets.all(8),
          itemCount: _users.length,
          itemBuilder: (context, index) {
            final user = _users[index];
            return Card(
              margin: const EdgeInsets.symmetric(vertical: 4),
              child: ListTile(
                leading: CircleAvatar(
                  child: Text(user.username[0].toUpperCase()),
                ),
                title: Text(
                  user.username,
                  style: const TextStyle(fontWeight: FontWeight.bold),
                ),
                subtitle: Text(user.role),
                trailing: _isDeleting
                    ? const CircularProgressIndicator()
                    : Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    IconButton(
                      icon: const Icon(Icons.edit, color: Colors.blue),
                      onPressed: () => _navigateToUserForm(user),
                    ),
                    IconButton(
                      icon: const Icon(Icons.delete, color: Colors.red),
                      onPressed: () => _deleteUser(user),
                    ),
                  ],
                ),
              ),
            );
          },
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _navigateToUserForm(),
        backgroundColor: Colors.deepPurple,
        child: const Icon(Icons.add, color: Colors.white),
      ),
    );
  }
}