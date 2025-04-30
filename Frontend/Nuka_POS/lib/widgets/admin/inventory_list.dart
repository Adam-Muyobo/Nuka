// lib/widgets/inventory_list.dart
import 'package:flutter/material.dart';
import '../../models/Category.dart';
import '../../models/Product.dart';
import '../../models/inventory.dart';
import 'inventory_card.dart';

class InventoryList extends StatelessWidget {
  final List<Inventory> items;
  final Map<int, Product> productMap;
  final Map<int, Category> categoryMap;
  final void Function(Inventory) onEdit;
  final void Function(int) onDelete;

  const InventoryList({
    Key? key,
    required this.items,
    required this.productMap,
    required this.categoryMap,
    required this.onEdit,
    required this.onDelete,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    if (items.isEmpty) {
      return const Center(child: Text('No inventory items'));
    }
    return ListView.builder(
      itemCount: items.length,
      itemBuilder: (ctx, i) {
        final inv = items[i];
        final prod = productMap[inv.productId]!;
        final cat = categoryMap[prod.categoryId]!;
        return InventoryCard(
          inventory: inv,
          product: prod,
          category: cat,
          onTap: () => onEdit(inv),
          onDelete: () => onDelete(inv.id),
        );
      },
    );
  }
}
