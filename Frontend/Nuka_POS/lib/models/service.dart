class Service {
  final int id;
  final String name;
  final String description;
  final double price;
  final bool isActive;
  final int organizationId;
  final int categoryId;

  Service({
    required this.id,
    required this.name,
    required this.description,
    required this.price,
    required this.isActive,
    required this.organizationId,
    required this.categoryId,
  });

  factory Service.fromJson(Map<String, dynamic> json) {
    return Service(
      id: json['id'] ?? 0,
      name: json['serviceName'] ?? '',
      description: json['description'] ?? '',
      price: (json['price'] as num).toDouble(),
      isActive: json['isActive'] ?? true,
      organizationId: json['organizationId'] ?? 0,
      categoryId: json['categoryId'] ?? 0,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      "name": name,
      "description": description,
      "price": price,
      "isActive": isActive,
      "organization": {
        "id": organizationId,
      },
      "category": {
        "id": categoryId,
      }
    };
  }
}
