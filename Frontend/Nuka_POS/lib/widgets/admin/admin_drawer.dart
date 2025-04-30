import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AdminDrawer extends StatelessWidget {
  const AdminDrawer({super.key, String? username});

  Future<Map<String, String>> _getUserDetails() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    final fullName = prefs.getString('username') ?? 'User';
    final branch = prefs.getString('branch_name'); // Optional
    return {'name': fullName, 'branch': branch ?? ''};
  }

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: FutureBuilder<Map<String, String>>(
        future: _getUserDetails(),
        builder: (context, snapshot) {
          final user = snapshot.data;

          return Column(
            children: [
              DrawerHeader(
                decoration: const BoxDecoration(
                  color: Colors.deepPurple,
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const CircleAvatar(
                      radius: 30,
                      backgroundColor: Colors.white,
                      child: Icon(Icons.person, size: 40, color: Colors.deepPurple),
                    ),
                    const SizedBox(height: 10),
                    Text(
                      user?['name'] ?? 'User',
                      style: const TextStyle(color: Colors.white, fontSize: 18),
                    ),
                    if ((user?['branch']?.isNotEmpty ?? false))
                      Text(
                        'Branch: ${user!['branch']}',
                        style: const TextStyle(color: Colors.white70),
                      ),
                  ],
                ),
              ),
              ListTile(
                leading: const Icon(Icons.settings),
                title: const Text('Settings'),
                onTap: () {
                  // Handle settings nav
                },
              ),
              ListTile(
                leading: const Icon(Icons.logout),
                title: const Text('Logout'),
                onTap: () {
                  // Handle logout nav
                },
              ),
            ],
          );
        },
      ),
    );
  }
}
