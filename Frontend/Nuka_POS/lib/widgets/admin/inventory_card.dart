// lib/widgets/inventory_card.dart
import 'package:flutter/material.dart';
import '../../models/Category.dart';
import '../../models/Product.dart';
import '../../models/inventory.dart';

class InventoryCard extends StatelessWidget {
  final Inventory inventory;
  final Product product;
  final Category category;
  final VoidCallback onTap;
  final VoidCallback onDelete;

  const InventoryCard({
    Key? key,
    required this.inventory,
    required this.product,
    required this.category,
    required this.onTap,
    required this.onDelete,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.symmetric(vertical: 4),
      child: ListTile(
        onTap: onTap,
        title: Text(product.name),
        subtitle: Text('Category: ${category.name}\n'
            'Available: ${inventory.quantityAvailable}\n'
            'Reserved: ${inventory.quantityReserved}\n'
            'Sold: ${inventory.quantitySold}'),
        isThreeLine: true,
        trailing: IconButton(
          icon: const Icon(Icons.delete, color: Colors.red),
          onPressed: onDelete,
        ),
      ),
    );
  }
}
