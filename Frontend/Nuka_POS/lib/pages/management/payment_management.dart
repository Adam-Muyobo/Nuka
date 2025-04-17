import 'package:flutter/material.dart';

class PaymentsPage extends StatefulWidget {
  const PaymentsPage({super.key});

  @override
  _PaymentsPageState createState() => _PaymentsPageState();
}

class _PaymentsPageState extends State<PaymentsPage> {
  final List<Map<String, dynamic>> payments = [
    {"transactionId": 1, "amountPaid": 50.00, "method": "Cash"},
    {"transactionId": 2, "amountPaid": 75.00, "method": "Card"},
  ];

  void _addPayment() {
    showDialog(
      context: context,
      builder: (context) => _PaymentPopup(
        onPaymentAdded: (payment) {
          setState(() {
            payments.add(payment);
          });
        },
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Payments")),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            ElevatedButton(
              onPressed: _addPayment,
              child: const Text("Add Payment"),
            ),
            const SizedBox(height: 16),
            Expanded(
              child: ListView.builder(
                itemCount: payments.length,
                itemBuilder: (context, index) {
                  final payment = payments[index];
                  return Card(
                    child: ListTile(
                      title: Text("Transaction #${payment['transactionId']}"),
                      subtitle: Text("Amount Paid: \P${payment['amountPaid']}"),
                      trailing: Text(payment['method']),
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _PaymentPopup extends StatefulWidget {
  final Function(Map<String, dynamic>) onPaymentAdded;

  const _PaymentPopup({required this.onPaymentAdded, super.key});

  @override
  _PaymentPopupState createState() => _PaymentPopupState();
}

class _PaymentPopupState extends State<_PaymentPopup> {
  final TextEditingController transactionIdController = TextEditingController();
  final TextEditingController amountController = TextEditingController();
  String paymentMethod = "Cash";

  void _submitPayment() {
    final int? transactionId = int.tryParse(transactionIdController.text);
    final double? amountPaid = double.tryParse(amountController.text);

    if (transactionId != null && amountPaid != null) {
      widget.onPaymentAdded({
        "transactionId": transactionId,
        "amountPaid": amountPaid,
        "method": paymentMethod,
      });
      Navigator.pop(context);
    }
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text("New Payment"),
      content: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          TextField(
            controller: transactionIdController,
            decoration: const InputDecoration(labelText: "Transaction ID"),
            keyboardType: TextInputType.number,
          ),
          TextField(
            controller: amountController,
            decoration: const InputDecoration(labelText: "Amount Paid"),
            keyboardType: TextInputType.number,
          ),
          DropdownButton<String>(
            value: paymentMethod,
            onChanged: (newValue) {
              setState(() {
                paymentMethod = newValue!;
              });
            },
            items: ["Cash", "Card", "Mobile Money"]
                .map((method) => DropdownMenuItem(
              value: method,
              child: Text(method),
            ))
                .toList(),
          ),
        ],
      ),
      actions: [
        TextButton(onPressed: () => Navigator.pop(context), child: const Text("Cancel")),
        ElevatedButton(onPressed: _submitPayment, child: const Text("Confirm Payment")),
      ],
    );
  }
}
