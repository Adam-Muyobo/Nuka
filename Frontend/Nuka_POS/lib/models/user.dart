class User {
  final int? id;
  final String username;
  final String forenames;
  final String surname;
  final String email;
  final String phone;
  final bool isActive;
  final String role;
  final int organizationId;
  final int? branchId;

  User({
    this.id,
    required this.username,
    required this.forenames,
    required this.surname,
    required this.email,
    required this.phone,
    required this.isActive,
    required this.role,
    required this.organizationId,
    this.branchId,
  });

  // Deserialize JSON
  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'],
      username: json['username'],
      forenames: json['forenames'],
      surname: json['surname'],
      email: json['email'],
      phone: json['phone'],
      isActive: json['isActive'],
      role: json['role'],
      organizationId: json['organizationId'],
      branchId: json['branchId'],
    );
  }

  // Serialize to JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'username': username,
      'password' : 'password',
      'forenames': forenames,
      'surname': surname,
      'email': email,
      'phone': phone,
      'isActive': isActive,
      'role': role,
      'organization': {'id':organizationId},
      'branchId': {'id':branchId},
    };
  }
}
