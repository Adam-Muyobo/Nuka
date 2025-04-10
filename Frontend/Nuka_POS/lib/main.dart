import 'package:flutter/material.dart';
import 'package:nuka_pos/pages/landing_page.dart';
import 'package:nuka_pos/pages/auth/login_page.dart';
import 'package:nuka_pos/pages/auth/signup_page.dart';
import 'package:nuka_pos/pages/dashboards/admin_dashboard.dart';
import 'package:nuka_pos/pages/dashboards/cashier_dashboard.dart';
import 'package:nuka_pos/pages/management/user_management.dart';
import 'package:nuka_pos/pages/management/product_management.dart';
import 'package:nuka_pos/pages/management/transaction_management.dart';
import 'package:nuka_pos/pages/management/payment_management.dart';
import 'package:nuka_pos/pages/management/tax_record_management.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Nuka POS',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      initialRoute: '/',
      routes: {
        '/': (context) => const LandingPage(),
        '/login': (context) => const LoginPage(),
        '/signup': (context) => const SignupPage(),
        '/admin_dashboard': (context) => const AdminDashboard(),
        '/cashier_dashboard': (context) => const CashierDashboard(),
        '/user_management': (context) => const UserManagementPage(),
        '/product_management': (context) => const ProductManagementPage(),
        '/transaction_management': (context) => const TransactionManagementPage(),
        '/payment_management': (context) => const PaymentsPage(),
        '/tax_record_management': (context) => const TaxRecordsPage(),
      },
    );
  }
}
