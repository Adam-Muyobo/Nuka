import 'package:flutter/material.dart';

class ProfitingSection extends StatelessWidget {
  const ProfitingSection({super.key, required bool showUnderperforming});

  @override
  Widget build(BuildContext context) {
    // Placeholder data
    final bestProducts = ['Product A', 'Product B'];
    final worstProducts = ['Product C', 'Product D'];
    final topCategories = ['Category 1', 'Category 2'];

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        const Text(
          'Profiting Products & Categories',
          style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
        ),
        const SizedBox(height: 8),
        _buildList('Top Products', bestProducts),
        const SizedBox(height: 16),
        _buildList('Under performing Products', worstProducts),
        const SizedBox(height: 16),
        _buildList('Top Categories', topCategories),
      ],
    );
  }

  Widget _buildList(String title, List<String> items) {
    return Card(
      elevation: 3,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
      child: Padding(
        padding: const EdgeInsets.all(12),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(title,
                style: const TextStyle(
                    fontSize: 16, fontWeight: FontWeight.w600)),
            const SizedBox(height: 6),
            for (var item in items)
              Padding(
                padding: const EdgeInsets.symmetric(vertical: 2.0),
                child: Text('• $item'),
              )
          ],
        ),
      ),
    );
  }
}
