import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'login_page.dart';

class UserSignupPage extends StatefulWidget {
  final int organizationId;

  const UserSignupPage({super.key, required this.organizationId});

  @override
  State<UserSignupPage> createState() => _UserSignupPageState();
}

class _UserSignupPageState extends State<UserSignupPage> {
  final _formKey = GlobalKey<FormState>();

  final TextEditingController _username = TextEditingController();
  final TextEditingController _password = TextEditingController();
  final TextEditingController _forenames = TextEditingController();
  final TextEditingController _surname = TextEditingController();
  final TextEditingController _email = TextEditingController();
  final TextEditingController _phone = TextEditingController();

  Future<void> _createUser() async {
    if (!_formKey.currentState!.validate()) return;

    final response = await http.post(
      Uri.parse('http://127.0.0.1:8080/api/users'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        "username": _username.text,
        "password": _password.text,
        "forenames": _forenames.text,
        "surname": _surname.text,
        "email": _email.text,
        "phone": _phone.text,
        "profilePicture": null,
        "isActive": true,
        "organization": {"id": widget.organizationId},
        "role": "ROOT_ADMIN",
        "branch": null
      }),
    );

    if (response.statusCode == 201) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Signup complete. Please log in.")),
      );
      Navigator.pushReplacement(context, MaterialPageRoute(builder: (_) => const LoginPage()));
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text("User creation failed: ${response.body}")),
      );
      await http.delete(Uri.parse('http://127.0.0.1:8080/api/organizations/${widget.organizationId}'));
    }
  }

  @override
  Widget build(BuildContext context) {
    final bool isMobile = MediaQuery.of(context).size.width < 600;

    return Scaffold(
      body: Center(
        child: ConstrainedBox(
          constraints: const BoxConstraints(maxWidth: 900),
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: isMobile ? _buildMobileLayout() : _buildDesktopLayout(),
          ),
        ),
      ),
    );
  }

  Widget _buildMobileLayout() {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Image.asset('images/nuka_logo.png', width: 100, height: 100),
        const SizedBox(height: 20),
        Expanded(child: _buildFormWithScroll()),
      ],
    );
  }

  Widget _buildDesktopLayout() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Expanded(
          child: Image.asset('images/nuka_logo.png', width: 150, height: 150),
        ),
        const SizedBox(width: 30),
        Flexible(child: _buildFormWithScroll()),
      ],
    );
  }

  Widget _buildFormWithScroll() {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        SingleChildScrollView(
          child: _buildSignupForm(),
        ),
        const SizedBox(height: 10),
        ElevatedButton(
          onPressed: _createUser,
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.deepPurple,
            foregroundColor: Colors.white,
            minimumSize: const Size(140, 45),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(10),
            ),
          ),
          child: const Text("Complete Signup"),
        ),
      ],
    );
  }

  Widget _buildSignupForm() {
    return Container(
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
        color: Colors.black.withOpacity(0.7),
        borderRadius: BorderRadius.circular(12),
      ),
      child: Form(
        key: _formKey,
        child: Column(
          children: [
            const Text(
              'Create Admin User',
              style: TextStyle(
                fontSize: 22,
                fontWeight: FontWeight.bold,
                color: Colors.white,
              ),
            ),
            const SizedBox(height: 20),
            _buildTextField(_username, 'Username'),
            _buildTextField(_password, 'Password', obscure: true),
            _buildTextField(_forenames, 'Forenames'),
            _buildTextField(_surname, 'Surname'),
            _buildTextField(_email, 'Email'),
            _buildTextField(_phone, 'Phone'),
          ],
        ),
      ),
    );
  }

  Widget _buildTextField(TextEditingController controller, String label, {bool obscure = false}) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 16),
      child: TextFormField(
        controller: controller,
        obscureText: obscure,
        validator: (value) => value == null || value.isEmpty ? 'Enter $label' : null,
        style: const TextStyle(color: Colors.white),
        decoration: InputDecoration(
          labelText: label,
          labelStyle: const TextStyle(color: Colors.white),
          border: const OutlineInputBorder(),
          filled: true,
          fillColor: Colors.grey[850],
        ),
      ),
    );
  }
}
