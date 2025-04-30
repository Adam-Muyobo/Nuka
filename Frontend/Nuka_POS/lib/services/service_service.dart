import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/Service.dart';

class ServiceService {
  static const String baseUrl = 'http://127.0.0.1:8080/api/services';

  Future<List<Service>> getServicesByOrganization(int organizationId) async {
    final response = await http.get(Uri.parse('$baseUrl/organization/$organizationId'));

    if (response.statusCode == 200) {
      final List<dynamic> data = jsonDecode(response.body);
      return data.map((item) => Service.fromJson(item)).toList();
    } else {
      throw Exception('Failed to load services for organization $organizationId');
    }
  }

  Future<void> createService(Service service) async {
    final response = await http.post(
      Uri.parse(baseUrl),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(service.toJson()),
    );

    if (response.statusCode != 201) {
      throw Exception('Failed to create service');
    }
  }

  Future<void> updateService(int id, Service service) async {
    final response = await http.put(
      Uri.parse('$baseUrl/$id'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(service.toJson()),
    );

    if (response.statusCode != 200) {
      throw Exception('Failed to update service');
    }
  }

  Future<void> deleteService(int id) async {
    final response = await http.delete(Uri.parse('$baseUrl/$id'));

    if (response.statusCode != 204) {
      throw Exception('Failed to delete service');
    }
  }
}
