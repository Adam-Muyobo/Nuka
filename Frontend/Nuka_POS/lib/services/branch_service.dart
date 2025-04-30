import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/branch.dart';
import '../models/currency.dart';

class BranchService {
  final String baseUrl = 'http://127.0.0.1:8080/api';

  // Get currency ID by country (used when creating/updating branch)
  Future<int?> getCurrencyIdByCountry(String country) async {
    final response = await http.get(Uri.parse('$baseUrl/currencies/by-country/$country'));
    if (response.statusCode == 200) {
      final List jsonList = jsonDecode(response.body);
      if (jsonList.isNotEmpty) {
        final currency = Currency.fromJson(jsonList[0]);
        return currency.id;
      }
    }
    return null;
  }

  Future<List<Branch>> getBranchesByOrganizationId(int organizationId) async {
    final response = await http.get(
      Uri.parse('$baseUrl/branches/organization/$organizationId'),
    );

    if (response.statusCode == 200) {
      final List<dynamic> jsonList = jsonDecode(response.body);
      return jsonList.map((json) => Branch.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load branches for organization');
    }
  }


  // Create a new branch
  Future<bool> createBranch(Branch branch) async {
    final response = await http.post(
      Uri.parse('$baseUrl/branches'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(branch.toJson()),
    );
    if (response.statusCode == 200) {
      return true;
    } else {
      return false;
    }
  }


  // Update an existing branch by ID
  Future<bool> updateBranch(int id, Branch updatedBranch) async {
    final response = await http.put(
      Uri.parse('$baseUrl/branches/$id'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(updatedBranch.toJson()),
    );

    if (response.statusCode == 200) {
      return true;
    } else {
      return false;
    }
  }


  // Delete a branch by ID
  Future<bool> deleteBranch(int id) async {
    final response = await http.delete(Uri.parse('$baseUrl/branches/$id'));

    if (response.statusCode == 200) {
      return true;
    } else if (response.statusCode == 409) {
      // Assuming 409 is returned for "Can't delete due to dependencies"
      return false;
    } else {
      throw Exception('Failed to delete branch');
    }
  }

}
