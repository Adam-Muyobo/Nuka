import 'package:flutter/material.dart';

import 'package:flutter/material.dart';

class TaxRecordsPage extends StatefulWidget {
  const TaxRecordsPage({super.key});

  @override
  _TaxRecordsPageState createState() => _TaxRecordsPageState();
}

class _TaxRecordsPageState extends State<TaxRecordsPage> {
  final List<Map<String, dynamic>> taxRecords = [
    {"transactionId": 1, "amount": 5.00, "date": "2025-03-28"},
    {"transactionId": 2, "amount": 7.50, "date": "2025-03-28"},
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Tax Records")),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: ListView.builder(
          itemCount: taxRecords.length,
          itemBuilder: (context, index) {
            final record = taxRecords[index];
            return Card(
              child: ListTile(
                title: Text("Transaction #${record['transactionId']}"),
                subtitle: Text("Tax Amount: \$${record['amount']}"),
                trailing: Text(record['date']),
              ),
            );
          },
        ),
      ),
    );
  }
}
