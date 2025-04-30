import 'package:flutter/material.dart';
import 'package:nuka_pos/services/audit_log_service.dart';

class AuditLogPage extends StatefulWidget {
  final int organizationId;

  const AuditLogPage({super.key, required this.organizationId});

  @override
  State<AuditLogPage> createState() => _AuditLogPageState();
}

class _AuditLogPageState extends State<AuditLogPage> {
  late Future<List<dynamic>> _logsFuture;

  @override
  void initState() {
    super.initState();
    _logsFuture = AuditLogService().fetchLogsByOrganizationId(widget.organizationId);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Audit Logs'), backgroundColor: Colors.deepPurple),
      body: FutureBuilder<List<dynamic>>(
        future: _logsFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (snapshot.hasData) {
            final logs = snapshot.data!;
            return ListView.separated(
              padding: const EdgeInsets.all(16),
              itemCount: logs.length,
              separatorBuilder: (_, __) => const Divider(),
              itemBuilder: (context, index) {
                final log = logs[index];
                return ListTile(
                  leading: const Icon(Icons.history, color: Colors.deepPurple),
                  title: Text('${log['action']} - ${log['entityName']}'),
                  subtitle: Text('User ID: ${log['userId']} • ${log['timestamp']}'),
                  trailing: const Icon(Icons.chevron_right),
                );
              },
            );
          } else {
            return const Center(child: Text('No logs found.'));
          }
        },
      ),
    );
  }
}
