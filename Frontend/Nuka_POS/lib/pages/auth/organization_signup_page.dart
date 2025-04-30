import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'user_signup_page.dart';

class OrganizationSignupPage extends StatefulWidget {
  const OrganizationSignupPage({super.key});

  @override
  State<OrganizationSignupPage> createState() => _OrganizationSignupPageState();
}

class _OrganizationSignupPageState extends State<OrganizationSignupPage> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _codeController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _phoneController = TextEditingController();
  final TextEditingController _countryController = TextEditingController();
  final TextEditingController _taxController = TextEditingController();

  String _selectedBusinessType = 'RETAIL';
  String _selectedReceiptMode = 'EMAIL';
  bool _smartInvoicing = false;
  bool _barcodeEnabled = false;

  Future<void> _submitOrganization() async {
    if (!_formKey.currentState!.validate()) return;

    final isZambia = _countryController.text.toLowerCase() == 'zambia';

    final orgPayload = {
      "name": _nameController.text,
      "code": _codeController.text,
      "email": _emailController.text,
      "phone": _phoneController.text,
      "country": _countryController.text,
      "businessType": _selectedBusinessType,
      "smartInvoicingEnabled": isZambia ? _smartInvoicing : false,
      "barcodeEnabled": _barcodeEnabled,
      "receiptMode": _selectedReceiptMode,
      "taxNumber": _taxController.text,
      "logoPath": null,
      "isVerified": false,
      "verificationCode": null,
    };

    final response = await http.post(
      Uri.parse('http://127.0.0.1:8080/api/organizations'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(orgPayload),
    );

    if (response.statusCode == 201 || response.statusCode == 200) {
      final fetchResponse = await http.get(
        Uri.parse('http://127.0.0.1:8080/api/organizations/code/${_codeController.text}'),
      );

      if (fetchResponse.statusCode == 200) {
        final org = jsonDecode(fetchResponse.body);
        final orgId = org['organizationId'];

        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => UserSignupPage(organizationId: orgId),
          ),
        );
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Organization created, but failed to retrieve ID.')),
        );
      }
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Failed to create organization: ${response.body}')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    final bool isMobile = MediaQuery.of(context).size.width < 600;

    return Scaffold(
      body: Center(
        child: ConstrainedBox(
          constraints: const BoxConstraints(maxWidth: 900),
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: isMobile ? _buildMobileLayout() : _buildDesktopLayout(),
          ),
        ),
      ),
    );
  }

  Widget _buildMobileLayout() {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Image.asset('images/nuka_logo.png', width: 100, height: 100),
        const SizedBox(height: 20),
        Expanded(child: _buildFormWithScroll()),
      ],
    );
  }

  Widget _buildDesktopLayout() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Expanded(
          child: Image.asset('images/nuka_logo.png', width: 150, height: 150),
        ),
        const SizedBox(width: 30),
        Flexible(child: _buildFormWithScroll()),
      ],
    );
  }

  Widget _buildFormWithScroll() {
    return Column(
      children: [
        Expanded(
          child: SingleChildScrollView(
            child: _buildSignupForm(),
          ),
        ),
        const SizedBox(height: 10),
        ElevatedButton(
          onPressed: _submitOrganization,
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.deepPurple,
            foregroundColor: Colors.white,
            minimumSize: const Size(140, 45),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(10),
            ),
          ),
          child: const Text("Next"),
        ),
      ],
    );
  }

  Widget _buildSignupForm() {
    return Container(
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
        color: Colors.black.withOpacity(0.7),
        borderRadius: BorderRadius.circular(12),
      ),
      child: Form(
        key: _formKey,
        child: Column(
          children: [
            const Text(
              'Register Organization',
              style: TextStyle(
                fontSize: 22,
                fontWeight: FontWeight.bold,
                color: Colors.white,
              ),
            ),
            const SizedBox(height: 20),
            _buildTextField(_nameController, 'Organization Name'),
            _buildTextField(_codeController, 'Code'),
            _buildTextField(_emailController, 'Email'),
            _buildTextField(_phoneController, 'Phone'),
            _buildTextField(_countryController, 'Country', onChanged: (value) {
              setState(() => _smartInvoicing = value.toLowerCase() == 'zambia');
            }),
            _buildDropdown(
              label: 'Business Type',
              value: _selectedBusinessType,
              items: ['RETAIL', 'SERVICE', 'WHOLESALE', 'MIXED'],
              onChanged: (val) => setState(() => _selectedBusinessType = val!),
            ),
            _buildDropdown(
              label: 'Receipt Mode',
              value: _selectedReceiptMode,
              items: ['EMAIL', 'PRINT', 'BOTH'],
              onChanged: (val) => setState(() => _selectedReceiptMode = val!),
            ),
            if (_countryController.text.toLowerCase() == 'zambia')
              SwitchListTile(
                value: _smartInvoicing,
                onChanged: (val) => setState(() => _smartInvoicing = val),
                title: const Text("Smart Invoicing", style: TextStyle(color: Colors.white)),
              ),
            CheckboxListTile(
              value: _barcodeEnabled,
              onChanged: (val) => setState(() => _barcodeEnabled = val!),
              title: const Text("Enable Barcode", style: TextStyle(color: Colors.white)),
            ),
            _buildTextField(_taxController, 'Tax Number'),
          ],
        ),
      ),
    );
  }

  Widget _buildTextField(TextEditingController controller, String label, {void Function(String)? onChanged}) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 16),
      child: TextFormField(
        controller: controller,
        onChanged: onChanged,
        validator: (value) => value == null || value.isEmpty ? 'Enter $label' : null,
        style: const TextStyle(color: Colors.white),
        decoration: InputDecoration(
          labelText: label,
          labelStyle: const TextStyle(color: Colors.white),
          border: const OutlineInputBorder(),
          filled: true,
          fillColor: Colors.grey[850],
        ),
      ),
    );
  }

  Widget _buildDropdown({
    required String label,
    required String value,
    required List<String> items,
    required void Function(String?) onChanged,
  }) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 16),
      child: DropdownButtonFormField<String>(
        value: value,
        decoration: InputDecoration(
          labelText: label,
          labelStyle: const TextStyle(color: Colors.white),
          border: const OutlineInputBorder(),
          filled: true,
          fillColor: Colors.grey[850],
        ),
        dropdownColor: Colors.grey[850],
        style: const TextStyle(color: Colors.white),
        iconEnabledColor: Colors.white,
        items: items.map((e) => DropdownMenuItem(value: e, child: Text(e))).toList(),
        onChanged: onChanged,
      ),
    );
  }
}
