import 'package:flutter/material.dart';

/// A pop-up dialog to add items to a new sale,
/// then notify via [onSaleComplete].
class SalePopup extends StatefulWidget {
  final Function(Map<String, dynamic>) onSaleComplete;
  const SalePopup({Key? key, required this.onSaleComplete}) : super(key: key);

  @override
  State<SalePopup> createState() => _SalePopupState();
}

class _SalePopupState extends State<SalePopup> {
  final List<Map<String, dynamic>> _items = [];
  final _productCtrl = TextEditingController();
  final _quantityCtrl = TextEditingController();
  double _total = 0.0, _tax = 0.0;

  void _addItem() {
    final prod = _productCtrl.text.trim();
    final qty = int.tryParse(_quantityCtrl.text) ?? 1;
    if (prod.isEmpty) return;
    const price = 10.0;
    final lineTotal = price * qty;
    setState(() {
      _items.add({'product': prod, 'quantity': qty, 'price': price, 'total': lineTotal});
      _total += lineTotal;
      _tax = _total * 0.1;
      _productCtrl.clear();
      _quantityCtrl.clear();
    });
  }

  void _finalize() {
    if (_items.isEmpty) return;
    widget.onSaleComplete({'items': List.from(_items), 'total': _total, 'tax': _tax});
    Navigator.pop(context);
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text("New Sale"),
      content: SingleChildScrollView(
        child: Column(
          children: [
            TextField(
              controller: _productCtrl,
              decoration: const InputDecoration(labelText: 'Product'),
            ),
            TextField(
              controller: _quantityCtrl,
              decoration: const InputDecoration(labelText: 'Quantity'),
              keyboardType: TextInputType.number,
            ),
            const SizedBox(height: 8),
            ElevatedButton(onPressed: _addItem, child: const Text("Add Item")),
            const SizedBox(height: 12),
            ..._items.map((it) => ListTile(
              title: Text(it['product']),
              subtitle: Text("Qty: ${it['quantity']} – P${it['total']}"),
            )),
            const SizedBox(height: 12),
            Text("Total: P${_total.toStringAsFixed(2)}"),
            Text("Tax: P${_tax.toStringAsFixed(2)}"),
          ],
        ),
      ),
      actions: [
        TextButton(onPressed: () => Navigator.pop(context), child: const Text("Cancel")),
        ElevatedButton(onPressed: _finalize, child: const Text("Finalize")),
      ],
    );
  }
}
