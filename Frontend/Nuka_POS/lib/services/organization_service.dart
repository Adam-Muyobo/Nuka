import 'dart:convert';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;

class OrganizationService {
  final String baseUrl = 'http://127.0.0.1:8080/api/organizations';

  // Fetch organization as a map (compatible with EditOrganizationPage)
  Future<Map<String, dynamic>?> getOrganizationById(int orgId) async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/$orgId'));

      if (response.statusCode == 200) {
        return json.decode(response.body) as Map<String, dynamic>;
      } else {
        if (kDebugMode) {
          print('Failed to fetch organization: ${response.statusCode}');
        }
        return null;
      }
    } catch (e) {
      if (kDebugMode) {
        print('Error fetching organization: $e');
      }
      return null;
    }
  }

  // Update organization with a map
  Future<bool> updateOrganization(int orgId, Map<String, dynamic> updatedOrg) async {
    try {
      final response = await http.put(
        Uri.parse('$baseUrl/$orgId'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode(updatedOrg),
      );

      if (kDebugMode) {
        print('Update response: ${response.statusCode} ${response.body}');
      }

      return response.statusCode == 200;
    } catch (e) {
      if (kDebugMode) {
        print('Error updating organization: $e');
      }
      return false;
    }
  }

  Future<void> logAudit({
    required int userId,
    required String action,
    required String entity,
    required int entityId,
    String? details,
  }) async {
    try {
      final response = await http.post(
        Uri.parse('http://127.0.0.1:8080/api/audit-logs'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode({
          'userId': userId,
          'action': action,
          'entity': entity,
          'entityId': entityId,
          'metaData': details ?? '',
        }),
      );

      if (kDebugMode) {
        print('Audit log response: ${response.statusCode}');
      }
    } catch (e) {
      if (kDebugMode) {
        print('Audit log error: $e');
      }
    }
  }

}
