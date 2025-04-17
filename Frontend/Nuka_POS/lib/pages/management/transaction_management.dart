import 'package:flutter/material.dart';

class TransactionManagementPage extends StatefulWidget {
  const TransactionManagementPage({super.key});

  @override
  _TransactionManagementPageState createState() => _TransactionManagementPageState();
}

class _TransactionManagementPageState extends State<TransactionManagementPage> {
  List<Map<String, dynamic>> transactions = [];
  List<Map<String, dynamic>> transactionItems = [];

  void _startNewTransaction() {
    transactionItems.clear();
    showDialog(
      context: context,
      builder: (context) => _TransactionPopup(
        onTransactionComplete: (transaction) {
          setState(() {
            transactions.add(transaction);
          });
        },
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Transaction Management')),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(16),
            child: ElevatedButton(
              onPressed: _startNewTransaction,
              child: const Text('New Transaction'),
            ),
          ),
          Expanded(
            child: ListView.builder(
              itemCount: transactions.length,
              itemBuilder: (context, index) {
                final transaction = transactions[index];
                return ListTile(
                  title: Text('Transaction #${index + 1}'),
                  subtitle: Text('Total: \P${transaction['total']}'),
                  trailing: Text('Tax: \P${transaction['tax']}'),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}

class _TransactionPopup extends StatefulWidget {
  final Function(Map<String, dynamic>) onTransactionComplete;

  const _TransactionPopup({required this.onTransactionComplete, super.key});

  @override
  _TransactionPopupState createState() => _TransactionPopupState();
}

class _TransactionPopupState extends State<_TransactionPopup> {
  final List<Map<String, dynamic>> transactionItems = [];
  final TextEditingController productController = TextEditingController();
  final TextEditingController quantityController = TextEditingController();
  double totalAmount = 0.0;
  double taxAmount = 0.0;

  void _addItem() {
    final product = productController.text.trim();
    final quantity = int.tryParse(quantityController.text) ?? 1;
    if (product.isNotEmpty) {
      setState(() {
        final double price = 10.0; // Placeholder price
        final double totalItemCost = price * quantity;
        transactionItems.add({
          'product': product,
          'quantity': quantity,
          'price': price,
          'total': totalItemCost,
        });
        totalAmount += totalItemCost;
        taxAmount = totalAmount * 0.1; // 10% Tax
      });
      productController.clear();
      quantityController.clear();
    }
  }

  void _finalizeTransaction() {
    if (transactionItems.isNotEmpty) {
      widget.onTransactionComplete({
        'items': List.from(transactionItems),
        'total': totalAmount,
        'tax': taxAmount,
      });
      Navigator.pop(context);
    }
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text('New Transaction'),
      content: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          TextField(
            controller: productController,
            decoration: const InputDecoration(labelText: 'Product Name'),
          ),
          TextField(
            controller: quantityController,
            decoration: const InputDecoration(labelText: 'Quantity'),
            keyboardType: TextInputType.number,
          ),
          const SizedBox(height: 10),
          ElevatedButton(onPressed: _addItem, child: const Text('Add Item')),
          const SizedBox(height: 10),
          if (transactionItems.isNotEmpty)
            Column(
              children: transactionItems
                  .map((item) => ListTile(
                title: Text(item['product']),
                subtitle: Text('Qty: ${item['quantity']} - \P${item['total']}'),
              ))
                  .toList(),
            ),
          const SizedBox(height: 10),
          Text('Total: \P${totalAmount.toStringAsFixed(2)}'),
          Text('Tax (10%): \P${taxAmount.toStringAsFixed(2)}'),
        ],
      ),
      actions: [
        TextButton(onPressed: () => Navigator.pop(context), child: const Text('Cancel')),
        ElevatedButton(onPressed: _finalizeTransaction, child: const Text('Finalize Transaction')),
      ],
    );
  }
}
