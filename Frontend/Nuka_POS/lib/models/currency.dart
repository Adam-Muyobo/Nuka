class Currency {
  final int id;
  final String name;
  final String symbol;
  final String country;

  Currency({
    required this.id,
    required this.name,
    required this.symbol,
    required this.country,
  });

  factory Currency.fromJson(Map<String, dynamic> json) {
    return Currency(
      id: json['id'],
      name: json['name'],
      symbol: json['symbol'],
      country: json['country'],
    );
  }
}
