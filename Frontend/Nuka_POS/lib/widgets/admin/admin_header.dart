import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AdminHeader extends StatelessWidget implements PreferredSizeWidget {
  final VoidCallback onSettingsPressed;

  const AdminHeader({super.key, required this.onSettingsPressed, required bool isMobile, String? username, String? role});

  Future<String> _getUsernameAndRole() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    final username = prefs.getString('username') ?? 'User';
    final role = prefs.getString('role') ?? 'ADMIN';
    return '$username • $role';
  }

  @override
  Widget build(BuildContext context) {
    return AppBar(
      backgroundColor: Colors.deepPurple,
      title: Row(
        children: [
          Image.asset(
            'images/nuka_logo.png',
            height: 50,
          ),
        ],
      ),
      actions: [
        FutureBuilder<String>(
          future: _getUsernameAndRole(),
          builder: (context, snapshot) {
            return Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: Center(
                child: Text(
                  snapshot.data ?? '',
                  style: const TextStyle(color: Colors.white),
                ),
              ),
            );
          },
        ),
        IconButton(
          icon: const Icon(Icons.settings),
          onPressed: onSettingsPressed,
        ),
      ],
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight);
}
