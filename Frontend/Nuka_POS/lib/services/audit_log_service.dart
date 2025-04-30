import 'dart:convert';
import 'package:http/http.dart' as http;

class AuditLogService {
  final String baseUrl = 'http://127.0.0.1:8080/api/audit-logs';

  Future<List<dynamic>> fetchLogsByOrganizationId(int organizationId) async {
    final response = await http.get(Uri.parse('$baseUrl/organization/$organizationId'));

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to load audit logs');
    }
  }
}
