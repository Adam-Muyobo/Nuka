class Category {
  final int id;
  final String name;
  final String description;
  final int organizationId; // Add organizationId instead of the full organization object

  Category({
    required this.id,
    required this.name,
    required this.description,
    required this.organizationId,
  });

  // Convert the Category object into JSON format
  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'description': description,
      'organization': {
        'id': organizationId, // Use organizationId here
      },
    };
  }

  // Convert JSON to Category object
  factory Category.fromJson(Map<String, dynamic> json) {
    return Category(
      id: json['id'],
      name: json['name'],
      description: json['description'],
      organizationId: json['organizationId'],
    );
  }

}
