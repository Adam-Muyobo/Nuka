import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:nuka_pos/services/user_service.dart';
import 'package:nuka_pos/services/product_service.dart';
import 'package:nuka_pos/models/Product.dart';        //  ⬅ uppercase
import 'package:nuka_pos/services/service_service.dart';
import 'package:nuka_pos/models/Service.dart';        //  ⬅ uppercase

import '../../widgets/sale_popup.dart';

class CashierDashboard extends StatefulWidget {
  const CashierDashboard({super.key});
  @override
  State<CashierDashboard> createState() => _CashierDashboardState();
}

class _CashierDashboardState extends State<CashierDashboard>
    with SingleTickerProviderStateMixin {
  String? forenames;
  bool isLoading = true;

  List<Map<String, dynamic>> transactions = [];

  List<Product> products = [];
  List<Product> filteredProducts = [];
  final productSearchCtrl = TextEditingController();

  List<Service> services = [];
  List<Service> filteredServices = [];
  final serviceSearchCtrl = TextEditingController();

  late final TabController _tabController;

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 2, vsync: this);
    _loadUserDetails();
  }

  Future<void> _loadUserDetails() async {
    final prefs = await SharedPreferences.getInstance();
    final username = prefs.getString('username');
    if (username != null) {
      final userData = await UserService().fetchUserByUsername(username);
      if (mounted) {
        setState(() {
          forenames = userData?['forenames'];
          isLoading = false;
        });
        final orgId = userData?['organizationId'];
        if (orgId != null) await _loadItems(orgId);
      }
    } else {
      setState(() => isLoading = false);
    }
  }

  Future<void> _loadItems(int orgId) async {
    try {
      final fetchedP = await ProductService().getProductsByOrganization(orgId);
      final fetchedS = await ServiceService().getServicesByOrganization(orgId);
      setState(() {
        products = fetchedP;            // no cast
        filteredProducts = fetchedP;    // no cast
        services = fetchedS;            // no cast
        filteredServices = fetchedS;    // no cast
      });
    } catch (e) {
      // TODO: handle error
    }
  }

  void _openSalePopup() {
    showDialog(
      context: context,
      builder: (_) => SalePopup(onSaleComplete: (sale) {
        setState(() => transactions.insert(0, sale));
      }),
    );
  }

  Widget _transactionCard(Map<String, dynamic> tx, int idx) {
    return Card(
      elevation: 4,
      margin: const EdgeInsets.symmetric(vertical: 6),
      child: ListTile(
        leading: const Icon(Icons.receipt_long, color: Colors.deepPurple),
        title: Text("Sale #${idx + 1}"),
        subtitle: Text("Total: P${tx['total']?.toStringAsFixed(2)}"),
        trailing: Text("Tax: P${tx['tax']?.toStringAsFixed(2)}"),
      ),
    );
  }

  @override
  void dispose() {
    _tabController.dispose();
    productSearchCtrl.dispose();
    serviceSearchCtrl.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey[100],
      appBar: AppBar(
        title: isLoading
            ? const Text('Loading...')
            : Text('Welcome, ${forenames ?? 'Cashier'}'),
        backgroundColor: Colors.deepPurple,
        foregroundColor: Colors.white,
        centerTitle: true,
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // Start Sale
          Padding(
            padding: const EdgeInsets.all(16),
            child: ElevatedButton.icon(
              onPressed: _openSalePopup,
              icon: const Icon(Icons.point_of_sale),
              label: const Text('Start New Sale'),
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.deepPurple,
                foregroundColor: Colors.white,
              ),
            ),
          ),

          // Recent Sales
          const Padding(
            padding: EdgeInsets.symmetric(horizontal: 16),
            child: Text("Recent Sales",
                style:
                TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
          ),
          const SizedBox(height: 8),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16),
            child: Column(
              children: transactions
                  .take(3)
                  .toList()
                  .asMap()
                  .entries
                  .map((e) => _transactionCard(e.value, e.key))
                  .toList(),
            ),
          ),
          const SizedBox(height: 16),

          // Tabs
          TabBar(
            controller: _tabController,
            labelColor: Colors.deepPurple,
            unselectedLabelColor: Colors.grey,
            tabs: const [
              Tab(text: 'Products'),
              Tab(text: 'Services'),
            ],
          ),

          // Tab Views
          Expanded(
            child: TabBarView(
              controller: _tabController,
              children: [
                // Products
                _buildSearchList<Product>(
                  items: filteredProducts,
                  searchCtrl: productSearchCtrl,
                  hint: 'Search products',
                  onSearch: (q) {
                    setState(() {
                      filteredProducts = products
                          .where((p) => p.name
                          .toLowerCase()
                          .contains(q.toLowerCase()))
                          .toList();
                    });
                  },
                  itemBuilder: (p) => ExpansionTile(
                    title: Text(p.name),
                    children: [
                      ListTile(
                        title: Text(p.description),
                        subtitle:
                        Text('P${p.price.toStringAsFixed(2)}'),
                      ),
                    ],
                  ),
                ),

                // Services
                _buildSearchList<Service>(
                  items: filteredServices,
                  searchCtrl: serviceSearchCtrl,
                  hint: 'Search services',
                  onSearch: (q) {
                    setState(() {
                      filteredServices = services
                          .where((s) => s.name
                          .toLowerCase()
                          .contains(q.toLowerCase()))
                          .toList();
                    });
                  },
                  itemBuilder: (s) => ExpansionTile(
                    title: Text(s.name),
                    children: [
                      ListTile(
                        title: Text(s.description),
                        subtitle:
                        Text('P${s.price.toStringAsFixed(2)}'),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  /// Generic search + list builder
  Widget _buildSearchList<T>({
    required List<T> items,
    required TextEditingController searchCtrl,
    required String hint,
    required void Function(String) onSearch,
    required Widget Function(T) itemBuilder,
  }) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16),
      child: Column(
        children: [
          TextField(
            controller: searchCtrl,
            decoration: InputDecoration(
              labelText: hint,
              prefixIcon: const Icon(Icons.search),
              border:
              OutlineInputBorder(borderRadius: BorderRadius.circular(8)),
            ),
            onChanged: onSearch,
          ),
          const SizedBox(height: 8),
          Expanded(
            child: items.isEmpty
                ? const Center(child: Text('No items'))
                : ListView.builder(
              itemCount: items.length,
              itemBuilder: (_, i) => itemBuilder(items[i]),
            ),
          ),
        ],
      ),
    );
  }
}
