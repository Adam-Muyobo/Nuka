import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/Product.dart';

class ProductService {
  final String baseUrl = 'http://127.0.0.1:8080/api/products';

  // Fetch products by organizationId
  Future<List<Product>> getProductsByOrganization(int organizationId) async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/organization/$organizationId'),
        headers: {'Content-Type': 'application/json'},
      );

      if (response.statusCode == 200) {
        final List<dynamic> productsJson = json.decode(response.body);
        return productsJson.map((json) => Product.fromJson(json)).toList();
      } else {
        throw Exception('Failed to load products. Status code: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Failed to load products: $e');
    }
  }

  // Fetch products by categoryId
  Future<List<Product>> getProductsByCategory(int categoryId) async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/category/$categoryId'),
        headers: {'Content-Type': 'application/json'},
      );

      if (response.statusCode == 200) {
        final List<dynamic> productsJson = json.decode(response.body);
        return productsJson.map((json) => Product.fromJson(json)).toList();
      } else {
        throw Exception('Failed to load products by category. Status code: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Failed to load products by category: $e');
    }
  }

  // Create a product
  Future<void> createProduct(Product product) async {
    try {
      final response = await http.post(
        Uri.parse(baseUrl),
        headers: {'Content-Type': 'application/json'},
        body: json.encode(product.toJson()),
      );

      if (response.statusCode == 201) {
        // Successfully created product
      } else {
        throw Exception('Failed to create product. Status code: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Failed to create product: $e');
    }
  }

  // Update product
  Future<void> updateProduct(int id, Product product) async {
    try {
      final response = await http.put(
        Uri.parse('$baseUrl/$id'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode(product.toJson()),
      );

      if (response.statusCode == 200) {
        // Successfully updated product
      } else {
        throw Exception('Failed to update product. Status code: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Failed to update product: $e');
    }
  }

  // Delete product
  Future<void> deleteProduct(int id) async {
    try {
      final response = await http.delete(
        Uri.parse('$baseUrl/$id'),
        headers: {'Content-Type': 'application/json'},
      );

      if (response.statusCode != 204) {
        throw Exception('Failed to delete product. Status code: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Failed to delete product: $e');
    }
  }
}
