import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

class AdminNavButtons extends StatelessWidget {
  const AdminNavButtons({super.key});

  @override
  Widget build(BuildContext context) {
    final List<_AdminButtonConfig> buttons = [
      _AdminButtonConfig('User Management', Icons.person, '/admin/user-management'),
      _AdminButtonConfig('Product Management', Icons.inventory, '/admin/product-management'),
      _AdminButtonConfig('Transactions', Icons.shopping_cart, '/admin/transactions'),
      _AdminButtonConfig('Payments', Icons.payment, '/admin/payments'),
      _AdminButtonConfig('Tax Records', Icons.receipt, '/admin/tax-records'),
    ];

    return Wrap(
      spacing: 16,
      runSpacing: 16,
      alignment: WrapAlignment.center,
      children: buttons.map((btn) {
        return ElevatedButton.icon(
          onPressed: () => context.push(btn.route),
          icon: Icon(btn.icon, color: Colors.white),
          label: Text(btn.label),
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.deepPurple,
            foregroundColor: Colors.white,
            padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 16),
            shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
          ),
        );
      }).toList(),
    );
  }
}

class _AdminButtonConfig {
  final String label;
  final IconData icon;
  final String route;

  _AdminButtonConfig(this.label, this.icon, this.route);
}
