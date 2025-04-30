import 'dart:convert';
import 'package:http/http.dart' as http;

import '../models/Category.dart';

class CategoryService {
  final String apiUrl = 'http://127.0.0.1:8080/api/categories';

  Future<List<Category>> getCategoriesByOrganization(int organizationId) async {
    final response = await http.get(Uri.parse('$apiUrl/organization/$organizationId'));
    if (response.statusCode == 200) {
      List<dynamic> data = jsonDecode(response.body);
      return data.map((categoryJson) => Category.fromJson(categoryJson)).toList();
    } else {
      // Handle errors here
      throw Exception('Failed to load categories');
    }
  }


  // Function to create a new category
  Future<void> createCategory(Category category) async {
    final response = await http.post(
      Uri.parse('http://127.0.0.1:8080/api/categories'), // Replace with the correct URL
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(category.toJson()), // Convert category object to JSON
    );

    if (response.statusCode == 200) {
      // Successfully created the category
        print('Category created successfully');
    } else {
      // Handle error
        print('Failed to create category: ${response.statusCode}');
    }
  }

  Future<void> updateCategory(int id, Category category) async {
    final response = await http.put(
      Uri.parse('$apiUrl/$id'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(category.toJson()),
    );
    if (response.statusCode != 200) {
      // Handle error
    }
  }

  Future<void> deleteCategory(int id) async {
    final response = await http.delete(Uri.parse('$apiUrl/$id'));
    if (response.statusCode != 200) {
      // Handle error
    }
  }
}
