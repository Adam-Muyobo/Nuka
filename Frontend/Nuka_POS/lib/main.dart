import 'package:flutter/material.dart';
import 'package:nuka_pos/pages/landing_page.dart';
import 'package:nuka_pos/pages/auth/login_page.dart';
import 'package:nuka_pos/pages/auth/organization_signup_page.dart';
import 'package:nuka_pos/pages/dashboards/admin_dashboard.dart';
import 'package:nuka_pos/pages/dashboards/cashier_dashboard.dart';


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
        '/signup': (context) => const OrganizationSignupPage(), // Changed to new entry
        '/admin_dashboard': (context) => const AdminDashboard(),
        '/cashier_dashboard': (context) => const CashierDashboard(),
      },
    );
  }
}
