import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:fl_chart/fl_chart.dart';

import 'package:nuka_pos/services/user_service.dart';
import 'package:nuka_pos/services/branch_service.dart';
import 'package:nuka_pos/services/product_service.dart';
import 'package:nuka_pos/services/service_service.dart';
import 'package:nuka_pos/services/inventory_service.dart';

import '../management/audit_log_page.dart';
import '../management/category_management_page.dart';
import '../management/edit_organization_page.dart';
import '../management/branch_management_page.dart';
import '../management/inventory_management_page.dart';
import '../management/product_management_page.dart';
import '../management/service_management_page.dart';
import '../management/user_management_page.dart';

class AdminDashboard extends StatefulWidget {
  const AdminDashboard({super.key});

  @override
  State<AdminDashboard> createState() => _AdminDashboardState();
}

class _AdminDashboardState extends State<AdminDashboard> {
  String? forenames;
  int? organizationId;
  int? userId;
  bool isLoading = true;

  // Actual counts:
  int userCount = 0;
  int branchCount = 0;
  int productCount = 0;
  int serviceCount = 0;
  int inventoryCount = 0;

  @override
  void initState() {
    super.initState();
    _loadUserData();
  }

  Future<void> _loadUserData() async {
    final prefs = await SharedPreferences.getInstance();
    final username = prefs.getString('username');

    if (username != null) {
      final userData = await UserService().fetchUserByUsername(username);
      if (userData != null && mounted) {
        organizationId = userData['organizationId'];
        userId = userData['id'];
        forenames = userData['forenames'];
        setState(() => isLoading = false);
        await _loadStats();
        return;
      }
    }
    setState(() => isLoading = false);
  }

  Future<void> _loadStats() async {
    if (organizationId == null) return;

    // Fetch each list to get their lengths
    final usersFuture     = UserService().getUsersByOrganization(organizationId!);
    final branchesFuture  = BranchService().getBranchesByOrganizationId(organizationId!);
    final productsFuture  = ProductService().getProductsByOrganization(organizationId!);
    final servicesFuture  = ServiceService().getServicesByOrganization(organizationId!);
    final inventoryFuture = InventoryService().getByOrganization(organizationId!);

    final results = await Future.wait([
      usersFuture,
      branchesFuture,
      productsFuture,
      servicesFuture,
      inventoryFuture,
    ]);

    if (!mounted) return;
    setState(() {
      userCount      = (results[0]).length;
      branchCount    = (results[1]).length;
      productCount   = (results[2]).length;
      serviceCount   = (results[3]).length;
      inventoryCount = (results[4]).length;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: isLoading
            ? const Text('Loading...')
            : Text('Welcome, ${forenames ?? 'Admin'}'),
        backgroundColor: Colors.deepPurple,
        centerTitle: true,
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text("Hello, $forenames 👋",
                style: const TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                )),
            const SizedBox(height: 24),

            // Statistics Row
            Row(
              children: [
                _buildStatCard(Icons.group, userCount.toString()),
                const SizedBox(width: 16),
                _buildStatCard(Icons.business, branchCount.toString()),
                const SizedBox(width: 16),
                _buildStatCard(Icons.shopping_cart, productCount.toString()),
                const SizedBox(width: 16),
                _buildStatCard(Icons.miscellaneous_services, serviceCount.toString()),
                const SizedBox(width: 16),
                _buildStatCard(Icons.inventory, inventoryCount.toString()),
              ],
            ),

            const SizedBox(height: 32),
            const Text(
              "Quick Actions",
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.w600),
            ),
            const SizedBox(height: 12),
            Wrap(
              spacing: 12,
              runSpacing: 12,
              children: [
                _dashboardButton('Edit Organization', Icons.edit, () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (_) => EditOrganizationPage(
                        organizationId: organizationId!,
                        userId: userId!,
                      ),
                    ),
                  );
                }),
                _dashboardButton('View Audit Logs', Icons.history, () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (_) =>
                          AuditLogPage(organizationId: organizationId!),
                    ),
                  );
                }),
                _dashboardButton('Manage Branches', Icons.business, () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (_) =>
                          BranchManagementPage(organizationId: organizationId!),
                    ),
                  );
                }),
                _dashboardButton('Manage Users', Icons.group, () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (_) =>
                          UserManagementPage(organizationId: organizationId!),
                    ),
                  );
                }),
                _dashboardButton('Manage Categories', Icons.category, () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (_) =>
                          CategoryManagementPage(organizationId: organizationId!),
                    ),
                  );
                }),
                _dashboardButton('Manage Products', Icons.shopping_cart, () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (_) =>
                          ProductManagementPage(organizationId: organizationId!),
                    ),
                  );
                }),
                _dashboardButton('Manage Services', Icons.miscellaneous_services, () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (_) =>
                          ServiceManagementPage(organizationId: organizationId!),
                    ),
                  );
                }),
                _dashboardButton('Manage Inventory', Icons.inventory, () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (_) =>
                          InventoryManagementPage(organizationId: organizationId!),
                    ),
                  );
                }),
              ],
            ),

            const SizedBox(height: 32),
            // Performance Overview
            const Text(
              "Performance Overview",
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.w600),
            ),
            const SizedBox(height: 12),
            Row(
              children: [
                _performanceCard('Best Products', '↑ 10%', Colors.green),
                const SizedBox(width: 16),
                _performanceCard('Worst Products', '↓ 5%', Colors.red),
              ],
            ),

            const SizedBox(height: 32),
            // Transaction Trend (Line Chart)
            const Text(
              "Transaction Trend",
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.w600),
            ),
            const SizedBox(height: 12),
            SizedBox(
              height: 200,
              child: LineChart(
                LineChartData(
                  gridData: const FlGridData(show: true),
                  titlesData: const FlTitlesData(show: true),
                  borderData: FlBorderData(show: true),
                  lineBarsData: [
                    LineChartBarData(spots: [
                      const FlSpot(0, 1),
                      const FlSpot(1, 3),
                      const FlSpot(2, 2),
                      const FlSpot(3, 4),
                    ])
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildStatCard(IconData icon, String count) {
    return Expanded(
      child: Card(
        elevation: 3,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
        child: Padding(
          padding: const EdgeInsets.symmetric(vertical: 16),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Icon(icon, size: 36, color: Colors.deepPurple),
              const SizedBox(height: 8),
              Text(
                count,
                style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
              ),
            ],
          ),
        ),
      ),
    );
  }



  Widget _dashboardButton(String label, IconData icon, VoidCallback onPressed) {
    return ElevatedButton.icon(
      style: ElevatedButton.styleFrom(
        backgroundColor: Colors.deepPurple,
        foregroundColor: Colors.white,
        padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 14),
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
      ),
      icon: Icon(icon),
      label: Text(label),
      onPressed: organizationId == null ? null : onPressed,
    );
  }

  Widget _performanceCard(String label, String performance, Color color) {
    return Card(
      elevation: 3,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            Text(label, style: const TextStyle(fontSize: 16)),
            const SizedBox(height: 8),
            Text(performance,
                style: TextStyle(fontSize: 24, color: color, fontWeight: FontWeight.bold)),
          ],
        ),
      ),
    );
  }
}
