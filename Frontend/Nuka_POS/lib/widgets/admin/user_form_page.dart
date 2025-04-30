import 'package:flutter/material.dart';
import 'package:nuka_pos/models/user.dart';
import 'package:nuka_pos/services/user_service.dart';

class UserFormPage extends StatefulWidget {
  final User? user;

  const UserFormPage({super.key, this.user});

  @override
  UserFormPageState createState() => UserFormPageState();
}

class UserFormPageState extends State<UserFormPage> {
  final _formKey = GlobalKey<FormState>();
  late TextEditingController _usernameController;
  late TextEditingController _forenamesController;
  late TextEditingController _surnameController;
  late TextEditingController _emailController;
  late TextEditingController _phoneController;
  late bool _isActive;
  late String _role;

  late UserService _userService;

  @override
  void initState() {
    super.initState();
    _userService = UserService();
    _usernameController = TextEditingController(text: widget.user?.username ?? '');
    _forenamesController = TextEditingController(text: widget.user?.forenames ?? '');
    _surnameController = TextEditingController(text: widget.user?.surname ?? '');
    _emailController = TextEditingController(text: widget.user?.email ?? '');
    _phoneController = TextEditingController(text: widget.user?.phone ?? '');
    _isActive = widget.user?.isActive ?? true;
    _role = widget.user?.role ?? 'CASHIER';
  }

  Future<void> _submit() async {
    if (_formKey.currentState!.validate()) {
      final user = User(
        id: widget.user?.id,
        username: _usernameController.text,
        forenames: _forenamesController.text,
        surname: _surnameController.text,
        email: _emailController.text,
        phone: _phoneController.text,
        isActive: _isActive,
        role: _role,
        organizationId: 1, // Replace with actual organization ID
      );

      final success = widget.user == null
          ? await _userService.createUser(user)
          : await _userService.updateUser(user.id!, user);

      if (context.mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text(success ? 'User saved successfully' : 'Failed to save user')),
        );
        if (success) Navigator.pop(context);
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.user == null ? 'Create User' : 'Edit User'),
        backgroundColor: Colors.deepPurple,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              TextFormField(
                controller: _usernameController,
                decoration: const InputDecoration(labelText: 'Username'),
                validator: (value) => value!.isEmpty ? 'Please enter a username' : null,
              ),
              TextFormField(
                controller: _forenamesController,
                decoration: const InputDecoration(labelText: 'Forenames'),
                validator: (value) => value!.isEmpty ? 'Please enter forenames' : null,
              ),
              TextFormField(
                controller: _surnameController,
                decoration: const InputDecoration(labelText: 'Surname'),
                validator: (value) => value!.isEmpty ? 'Please enter surname' : null,
              ),
              TextFormField(
                controller: _emailController,
                decoration: const InputDecoration(labelText: 'Email'),
                validator: (value) =>
                value!.isEmpty ? 'Please enter a valid email' : null,
              ),
              TextFormField(
                controller: _phoneController,
                decoration: const InputDecoration(labelText: 'Phone'),
                validator: (value) => value!.isEmpty ? 'Please enter a phone number' : null,
              ),
              SwitchListTile(
                title: const Text('Active'),
                value: _isActive,
                onChanged: (value) {
                  setState(() {
                    _isActive = value;
                  });
                },
              ),
              DropdownButtonFormField<String>(
                value: _role == null ? _role: 'ADMIN',
                items: ['ROOT_ADMIN','ADMIN', 'CASHIER'].map((role) {
                  return DropdownMenuItem<String>(
                    value: role,
                    child: Text(role),
                  );
                }).toList(),
                onChanged: (value) {
                  setState(() {
                    _role = value!;
                  });
                },
                decoration: const InputDecoration(labelText: 'Role'),
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: _submit,
                style: ElevatedButton.styleFrom(backgroundColor: Colors.deepPurple),
                child: Text(widget.user == null ? 'Create User' : 'Save Changes'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
