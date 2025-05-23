import 'package:maplibre_navigation_flutter/models/latlng.dart';

import 'route_step.dart';
import '../helpers.dart';

///A RouteLeg object defines a single leg of a route between two waypoints.
///If the overall route has only two waypoints, it has a single RouteLeg object that covers the entire route.
///The route leg object includes information about the leg, such as its name, distance, and expected travel time.
///Depending on the criteria used to calculate the route, the route leg object may also include detailed turn-by-turn instructions.
class RouteLeg {
  String? profileIdentifier;
  String? name;
  double? distance;
  double? expectedTravelTime;
  LatLng? source;
  LatLng? destination;
  List<RouteStep>? steps;

  RouteLeg(
    this.profileIdentifier,
    this.name,
    this.distance,
    this.expectedTravelTime,
    this.source,
    this.destination,
    this.steps,
  );

  RouteLeg.fromJson(Map<String, dynamic> json) {
    var sourceJson = {};
    var destinationJson = {};

    if (json['source'] != null) {
      sourceJson = json['source'];
    }
    if (json['destination'] != null) {
      destinationJson = json['destination'];
    }
    profileIdentifier = json["profileIdentifier"];
    name = json["name"];
    distance = isNullOrZero(json["distance"]) ? 0.0 : json["distance"] + .0;
    expectedTravelTime =
        isNullOrZero(json["expectedTravelTime"])
            ? 0.0
            : json["expectedTravelTime"] + .0;
    source =
        json['source'] == null
            ? null
            : LatLng(sourceJson['latitude'], sourceJson['longitude']);
    destination =
        json['destination'] == null
            ? null
            : LatLng(destinationJson['latitude'], destinationJson['longitude']);
    steps =
        (json['steps'] as List?)
            ?.map(
              (e) =>
                  e == null
                      ? null
                      : RouteStep.fromJson(e as Map<String, dynamic>),
            )
            .cast<RouteStep>()
            .toList();
  }
}
