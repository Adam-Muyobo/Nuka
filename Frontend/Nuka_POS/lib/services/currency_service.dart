import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/currency.dart';
import '../constants/api_constants.dart';

class CurrencyService {
  Future<List<Currency>> getAllCurrencies() async {
    final response = await http.get(Uri.parse('$baseUrl/currencies'));

    if (response.statusCode == 200) {
      final List<dynamic> jsonList = jsonDecode(response.body);
      return jsonList.map((json) => Currency.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load currencies');
    }
  }

  Future<List<Currency>> getCurrencyByCountry(String country) async {
    final response = await http.get(Uri.parse('$baseUrl/currencies/by-country/$country'));

    if (response.statusCode == 200) {
      final List<dynamic> jsonList = jsonDecode(response.body);
      return jsonList.map((json) => Currency.fromJson(json)).toList();
    } else {
      return [];
    }
  }
}
