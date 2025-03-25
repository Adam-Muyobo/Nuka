import 'package:flutter/material.dart';

import 'package:http/http.dart' as http;
import 'dart:convert';
import 'admin_dashboard.dart';
import 'cashier_dashboard.dart';

class LandingPage extends StatefulWidget {
  @override
  _LandingPageState createState() => _LandingPageState();
}

class _LandingPageState extends State<LandingPage> {
  bool isLogin = true; // Toggle between Login and Sign-Up
  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();
  final TextEditingController nameController = TextEditingController();

  Future<void> login() async {
    final url = Uri.parse('http://localhost:8080/api/users/email/${emailController.text}');
    final response = await http.get(url);

    if (response.statusCode == 200) {
      final user = jsonDecode(response.body);

      if (user['password'] == passwordController.text) { // Replace with hash comparison if needed
        String role = user['role'];

        if (role == 'ADMIN') {
          Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => AdminDashboard()));
        } else if (role == 'CASHIER') {
          Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => CashierDashboard()));
        } else {
          showError('Unknown role: $role');
        }
      } else {
        showError('Incorrect password');
      }
    } else {
      showError('User not found');
    }
  }

  Future<void> signUp() async {
    final url = Uri.parse('http://localhost:8080/api/users');
    final response = await http.post(
      url,
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'name': nameController.text,
        'email': emailController.text,
        'password': passwordController.text, // Store securely in real app
        'role': 'ADMIN', // Only Admins can be created via Sign-Up
      }),
    );

    if (response.statusCode == 201) {
      setState(() => isLogin = true); // Switch to login after successful sign-up
    } else {
      showError('Sign-up failed');
    }
  }

  void showError(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text(message), backgroundColor: Colors.red),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: LayoutBuilder(
        builder: (context, constraints) {
          bool isWideScreen = constraints.maxWidth > 800;

          return Center(
            child: Container(
              padding: EdgeInsets.all(20),
              width: isWideScreen ? 800 : double.infinity,
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text('Nuka POS System', style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold)),

                  SizedBox(height: 20),

                  isWideScreen
                      ? Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: [
                      _buildLoginContainer(),
                      _buildSignupContainer(),
                    ],
                  )
                      : Column(
                    children: [
                      _buildLoginContainer(),
                      SizedBox(height: 20),
                      _buildSignupContainer(),
                    ],
                  ),
                ],
              ),
            ),
          );
        },
      ),
    );
  }

  Widget _buildLoginContainer() {
    return Expanded(
      child: Card(
        elevation: 5,
        child: Padding(
          padding: EdgeInsets.all(20),
          child: Column(
            children: [
              Text('Login', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
              TextField(controller: emailController, decoration: InputDecoration(labelText: 'Email')),
              TextField(controller: passwordController, decoration: InputDecoration(labelText: 'Password'), obscureText: true),
              SizedBox(height: 10),
              ElevatedButton(onPressed: login, child: Text('Login')),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildSignupContainer() {
    return Expanded(
      child: Card(
        elevation: 5,
        child: Padding(
          padding: EdgeInsets.all(20),
          child: Column(
            children: [
              Text('Sign Up (Admins Only)', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
              TextField(controller: nameController, decoration: InputDecoration(labelText: 'Name')),
              TextField(controller: emailController, decoration: InputDecoration(labelText: 'Email')),
              TextField(controller: passwordController, decoration: InputDecoration(labelText: 'Password'), obscureText: true),
              SizedBox(height: 10),
              ElevatedButton(onPressed: signUp, child: Text('Sign Up')),
            ],
          ),
        ),
      ),
    );
  }
}

