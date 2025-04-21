import 'package:flutter/material.dart';

/// A pair of latitude and longitude coordinates, stored as degrees.
@immutable
class LatLng {
  /// Creates a geographical location specified in degrees [latitude] and
  /// [longitude].
  ///
  /// The latitude is clamped to the inclusive interval from -90.0 to +90.0.
  ///
  /// The longitude is normalized to the half-open interval from -180.0
  /// (inclusive) to +180.0 (exclusive)
  const LatLng(double latitude, double longitude)
    : latitude =
          (latitude < -90.0 ? -90.0 : (90.0 < latitude ? 90.0 : latitude)),
      longitude = (longitude + 180.0) % 360.0 - 180.0;

  /// The latitude in degrees between -90.0 and 90.0, both inclusive.
  final double latitude;

  /// The longitude in degrees between -180.0 (inclusive) and 180.0 (exclusive).
  final double longitude;

  LatLng operator +(LatLng o) {
    return LatLng(latitude + o.latitude, longitude + o.longitude);
  }

  LatLng operator -(LatLng o) {
    return LatLng(latitude - o.latitude, longitude - o.longitude);
  }

  dynamic toJson() {
    return <double>[latitude, longitude];
  }

  dynamic toGeoJsonCoordinates() {
    return <double>[longitude, latitude];
  }

  LatLng._fromJson(List<dynamic> json) : this(json[0], json[1]);

  @override
  String toString() => 'LatLng($latitude, $longitude)';

  @override
  bool operator ==(Object other) {
    return other is LatLng &&
        other.latitude == latitude &&
        other.longitude == longitude;
  }

  @override
  int get hashCode => Object.hash(latitude, longitude);
}
