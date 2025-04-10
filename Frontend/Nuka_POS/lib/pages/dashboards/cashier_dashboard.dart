import 'package:flutter/material.dart';
import 'package:nuka_pos/pages/management/transaction_management.dart';
import 'package:nuka_pos/pages/management/payment_management.dart';

class CashierDashboard extends StatelessWidget {
  const CashierDashboard({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Cashier Dashboard')),
      body: GridView.count(
        crossAxisCount: MediaQuery.of(context).size.width < 600 ? 2 : 3,
        padding: const EdgeInsets.all(16),
        children: [
          _buildDashboardCard(context, 'Transactions', Icons.shopping_cart, const TransactionManagementPage()),
          _buildDashboardCard(context, 'Payments', Icons.payment, const PaymentsPage()),
        ],
      ),
    );
  }

  Widget _buildDashboardCard(BuildContext context, String title, IconData icon, Widget page) {
    return GestureDetector(
      onTap: () => Navigator.push(context, MaterialPageRoute(builder: (context) => page)),
      child: Card(
        elevation: 5,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(icon, size: 40, color: Colors.deepPurple),
            const SizedBox(height: 10),
            Text(title, style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
          ],
        ),
      ),
    );
  }
}

