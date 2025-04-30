import 'package:flutter/material.dart';

class StartSaleButton extends StatelessWidget {
  const StartSaleButton({super.key});

  void _showSaleDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: const Text('Start New Sale'),
        content: const Text('Sale form and item selection will go here.'),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Cancel'),
          ),
          ElevatedButton(
            onPressed: () {
              // Submit logic here later
              Navigator.pop(context);
            },
            style: ElevatedButton.styleFrom(
              backgroundColor: Colors.deepPurple,
              foregroundColor: Colors.white,
            ),
            child: const Text('Proceed'),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return FloatingActionButton.extended(
      onPressed: () => _showSaleDialog(context),
      label: const Text('Start Sale'),
      icon: const Icon(Icons.point_of_sale),
      backgroundColor: Colors.deepPurple,
      foregroundColor: Colors.white,
    );
  }
}
