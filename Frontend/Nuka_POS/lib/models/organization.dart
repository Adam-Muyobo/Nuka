import 'dart:core';
class Organization {
  late final String name;
  final String code;
  late final String email;
  late final String phone;
  final String country;
  late final String businessType;
  late final bool smartInvoicingEnabled;
  late final bool barcodeEnabled;
  late final String receiptMode;
  late final String taxNumber;
  final String logoPath;
  final bool isVerified;
  final String verificationCode;
  final String createdAt;

  Organization({
    required this.name,
    required this.code,
    required this.email,
    required this.phone,
    required this.country,
    required this.businessType,
    required this.smartInvoicingEnabled,
    required this.barcodeEnabled,
    required this.receiptMode,
    required this.taxNumber,
    required this.logoPath,
    required this.isVerified,
    required this.verificationCode,
    required this.createdAt,
  });

  factory Organization.fromJson(Map<String, dynamic> json) {
    return Organization(
      name: json['name'],
      code: json['code'],
      email: json['email'],
      phone: json['phone'],
      country: json['country'],
      businessType: json['businessType'],
      smartInvoicingEnabled: json['smartInvoicingEnabled'],
      barcodeEnabled: json['barcodeEnabled'],
      receiptMode: json['receiptMode'],
      taxNumber: json['taxNumber'],
      logoPath: json['logoPath'],
      isVerified: json['isVerified'],
      verificationCode: json['verificationCode'],
      createdAt: json['createdAt'],
    );
  }


  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'code': code,
      'email': email,
      'phone': phone,
      'country': country,
      'businessType': businessType,
      'smartInvoicingEnabled': smartInvoicingEnabled,
      'barcodeEnabled': barcodeEnabled,
      'receiptMode': receiptMode,
      'taxNumber': taxNumber,
      'logoPath': logoPath,
      'isVerified': isVerified,
      'verificationCode': verificationCode,
      'createdAt': createdAt,
    };
  }
}
