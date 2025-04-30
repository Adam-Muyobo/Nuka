class Branch {
  final int? id;
  final String name;
  final String location;
  final String phone;
  final String email;
  final bool isHeadOffice;
  final int organizationId;
  final int currencyId;

  Branch({
    this.id,
    required this.name,
    required this.location,
    required this.phone,
    required this.email,
    required this.isHeadOffice,
    required this.organizationId,
    required this.currencyId,
  });

  factory Branch.fromJson(Map<String, dynamic> json) {
    return Branch(
      id: json['id'],
      name: json['name'],
      location: json['location'],
      phone: json['phone'],
      email: json['email'],
      isHeadOffice: json['isHeadOffice'],
      organizationId: json['organizationId'],
      currencyId: json['currencyId'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'location': location,
      'phone': phone,
      'email': email,
      'isHeadOffice': isHeadOffice,
      'organization': {'id':organizationId},
      'currency': {'id':currencyId},
    };
  }
}
