import 'package:flutter/material.dart';

class SalesOverview extends StatelessWidget {
  const SalesOverview({super.key});

  @override
  Widget build(BuildContext context) {
    // Placeholder sales data
    const todaySales = 15;
    const totalRevenue = 2500.00;

    return Card(
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      elevation: 4,
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text('Today\'s Sales',
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            const SizedBox(height: 10),
            const Text('Transactions: $todaySales'),
            Text('Revenue: BWP ${totalRevenue.toStringAsFixed(2)}'),
          ],
        ),
      ),
    );
  }
}
