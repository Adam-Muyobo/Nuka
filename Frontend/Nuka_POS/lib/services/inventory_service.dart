// lib/services/inventory_service.dart

import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/inventory.dart';

/// A service to call the Inventory REST API.
class InventoryService {
  static const _baseUrl = 'http://127.0.0.1:8080/api/inventory';

  /// GET /api/inventory
  Future<List<Inventory>> getAllInventory() async {
    final res = await http.get(Uri.parse(_baseUrl));
    if (res.statusCode == 200) {
      final List<dynamic> list = jsonDecode(res.body);
      return list.map((e) => Inventory.fromJson(e)).toList();
    }
    throw Exception('Failed to load all inventory');
  }

  /// GET /api/inventory/{id}
  Future<Inventory> getInventoryById(int id) async {
    final res = await http.get(Uri.parse('$_baseUrl/$id'));
    if (res.statusCode == 200) {
      return Inventory.fromJson(jsonDecode(res.body));
    }
    throw Exception('Failed to load inventory id=$id');
  }

  /// GET /api/inventory/organization/{organizationId}
  Future<List<Inventory>> getByOrganization(int organizationId) async {
    final res = await http.get(Uri.parse('$_baseUrl/organization/$organizationId'));
    if (res.statusCode == 200) {
      final List<dynamic> list = jsonDecode(res.body);
      return list.map((e) => Inventory.fromJson(e)).toList();
    }
    throw Exception('Failed to load inventory for organization $organizationId');
  }

  /// GET /api/inventory/branch/{branchId}
  Future<List<Inventory>> getByBranch(int branchId) async {
    final res = await http.get(Uri.parse('$_baseUrl/branch/$branchId'));
    if (res.statusCode == 200) {
      final List<dynamic> list = jsonDecode(res.body);
      return list.map((e) => Inventory.fromJson(e)).toList();
    }
    throw Exception('Failed to load inventory for branch $branchId');
  }

  /// GET /api/inventory/product/{productId}
  Future<List<Inventory>> getByProduct(int productId) async {
    final res = await http.get(Uri.parse('$_baseUrl/product/$productId'));
    if (res.statusCode == 200) {
      final List<dynamic> list = jsonDecode(res.body);
      return list.map((e) => Inventory.fromJson(e)).toList();
    }
    throw Exception('Failed to load inventory for product $productId');
  }

  /// GET /api/inventory/status/{status}
  Future<List<Inventory>> getByStatus(String status) async {
    final res = await http.get(Uri.parse('$_baseUrl/status/$status'));
    if (res.statusCode == 200) {
      final List<dynamic> list = jsonDecode(res.body);
      return list.map((e) => Inventory.fromJson(e)).toList();
    }
    throw Exception('Failed to load inventory with status $status');
  }

  /// POST /api/inventory
  Future<bool> createInventory(Inventory inv) async {
    final res = await http.post(
      Uri.parse(_baseUrl),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(inv.toJsonForRequest()),
    );
    return res.statusCode == 201;
  }

  /// PUT /api/inventory/{id}
  Future<bool> updateInventory(int id, Inventory inv) async {
    final res = await http.put(
      Uri.parse('$_baseUrl/$id'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(inv.toJsonForRequest()),
    );
    return res.statusCode == 200;
  }

  /// DELETE /api/inventory/{id}
  Future<bool> deleteInventory(int id) async {
    final res = await http.delete(Uri.parse('$_baseUrl/$id'));
    return res.statusCode == 204 || res.statusCode == 200;
  }
}
