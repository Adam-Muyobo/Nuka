class Product {
  final int id;
  final String name;
  final String description;
  final double price;
  final double cost;
  final String? barcode;
  final bool isActive;
  final int organizationId;
  final int categoryId;

  Product({
    required this.id,
    required this.name,
    required this.description,
    required this.price,
    required this.cost,
    this.barcode,  // Nullable barcode field
    required this.isActive,
    required this.organizationId,
    required this.categoryId,
  });

  // Convert Product to JSON
  Map<String, dynamic> toJson() {
    final data = {
      'name': name,
      'description': description,
      'price': price,
      'cost': cost,
      'barcode': barcode,  // Include barcode only if not null
      'isActive': isActive,
      'organization': {'id':organizationId},
      'category': {'id':categoryId},
    };

    return data;
  }

  // Create a Product from JSON response
  factory Product.fromJson(Map<String, dynamic> json) {
    return Product(
      id: json['id'],
      name: json['name'],
      description: json['description'],
      price: json['price'],
      cost: json['cost'],
      barcode: json['barcode'],  // Handle nullable barcode
      isActive: json['isActive'],
      organizationId: json['organizationId'],
      categoryId: json['categoryId'],
    );
  }
}
