// lib/models/inventory.dart
class Inventory {
  final int id;
  final int organizationId;
  final int productId;
  final int branchId;
  final int quantityAvailable;
  final int quantityReserved;
  final int quantitySold;
  final double cost;
  final String inventoryStatus;
  final DateTime createdAt;
  final DateTime updatedAt;

  Inventory({
    required this.id,
    required this.organizationId,
    required this.productId,
    required this.branchId,
    required this.quantityAvailable,
    required this.quantityReserved,
    required this.quantitySold,
    required this.cost,
    required this.inventoryStatus,
    required this.createdAt,
    required this.updatedAt,
  });

  factory Inventory.fromJson(Map<String, dynamic> json) {
    return Inventory(
      id: json['id'] as int,
      organizationId: json['organizationId'] as int,
      productId: json['productId'] as int,
      branchId: json['branchId'] as int,
      quantityAvailable: json['quantityAvailable'] as int,
      quantityReserved: json['quantityReserved'] as int,
      quantitySold: json['quantitySold'] as int,
      cost: (json['cost'] as num).toDouble(),
      inventoryStatus: json['inventoryStatus'] as String,
      createdAt: DateTime.parse(json['createdAt'] as String),
      updatedAt: DateTime.parse(json['updatedAt'] as String),
    );
  }

  /// For POST/PUT payload
  Map<String, dynamic> toJsonForRequest() {
    return {
      'organization': {'id': organizationId},
      'product': {'id': productId},
      'branch': {'id': branchId},
      'quantityAvailable': quantityAvailable,
      'quantityReserved': quantityReserved,
      'quantitySold': quantitySold,
      'cost': cost,
      'inventoryStatus': inventoryStatus,
    };
  }
}
