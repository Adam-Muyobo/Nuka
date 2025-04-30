import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/user.dart';

class UserService {
  final String baseUrl = 'http://127.0.0.1:8080/api';

  // Get all users in an organization
  Future<List<User>> getUsersByOrganization(int organizationId) async {
    final response = await http.get(Uri.parse('$baseUrl/users/organization/$organizationId'));

    if (response.statusCode == 200) {
      final List<dynamic> jsonList = jsonDecode(response.body);
      return jsonList.map((json) => User.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load users');
    }
  }

  // Get a user by ID
  Future<User> getUserById(int userId) async {
    final response = await http.get(Uri.parse('$baseUrl/users/$userId'));

    if (response.statusCode == 200) {
      return User.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Failed to load user');
    }
  }

  // Create a new user
  Future<bool> createUser(User user) async {
    final response = await http.post(
      Uri.parse('$baseUrl/users'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(user.toJson()),
    );

    return response.statusCode == 201;
  }

  // Update an existing user
  Future<bool> updateUser(int userId, User user) async {
    final response = await http.put(
      Uri.parse('$baseUrl/users/$userId'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(user.toJson()),
    );

    return response.statusCode == 200;
  }

  // Delete a user by ID
  Future<bool> deleteUser(int userId) async {
    final response = await http.delete(Uri.parse('$baseUrl/users/$userId'));

    return response.statusCode == 200;
  }

  // Change user password
  Future<bool> changePassword(int userId, String newPassword) async {
    final response = await http.put(
      Uri.parse('$baseUrl/users/$userId/password'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'password': newPassword}),
    );

    return response.statusCode == 200;
  }


  Future<Map<String, dynamic>?> fetchUserByUsername(String username) async {
    final url = Uri.parse('$baseUrl/users/username/$username');
    final response = await http.get(url);

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      return null;
    }
  }
}
