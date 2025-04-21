import 'package:flutter/foundation.dart';
import 'package:maplibre_navigation_flutter/models/latlng.dart';
import 'package:maplibre_navigation_flutter/models/options.dart';
import 'package:maplibre_navigation_flutter/models/route_event.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'maplibre_navigation_flutter_method_channel.dart';

abstract class MaplibreNavigationFlutterPlatform extends PlatformInterface {
  /// Constructs a DemoPluginPlatform.
  MaplibreNavigationFlutterPlatform() : super(token: _token);

  static final Object _token = Object();

  static MaplibreNavigationFlutterPlatform _instance =
      MethodChannelMaplibreNavigationFlutter();

  /// The default instance of [MaplibreNavigationFlutterPlatform] to use.
  ///
  /// Defaults to [MethodChannelVietmapNavigationPlugin].
  static MaplibreNavigationFlutterPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [MaplibreNavigationFlutterPlatform] when
  /// they register themselves.
  static set instance(MaplibreNavigationFlutterPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  ///Total distance remaining in meters along route.
  Future<double?> getDistanceRemaining() {
    throw UnimplementedError(
      'getDistanceRemaining() has not been implemented.',
    );
  }

  ///Total seconds remaining on all legs.
  Future<double?> getDurationRemaining() {
    throw UnimplementedError(
      'getDurationRemaining() has not been implemented.',
    );
  }

  /// Free-drive mode is a unique Mapbox Navigation SDK feature that allows
  /// drivers to navigate without a set destination. This mode is sometimes
  /// referred to as passive navigation.
  /// [options] options used to generate the route and used while navigating
  /// Begins to generate Route Progress
  ///
  Future<bool?> startFreeDrive(MapOptions options) async {
    throw UnimplementedError('startFreeDrive() has not been implemented.');
  }

  ///Show the Navigation View and Begins Direction Routing
  ///
  /// [wayPoints] must not be null and have at least 2 items. A collection of
  /// [WayPoint](longitude, latitude and name). Must be at least 2 or at
  /// most 25. Cannot use drivingWithTraffic mode if more than 3-waypoints.
  /// [options] options used to generate the route and used while navigating
  /// Begins to generate Route Progress
  ///
  Future<bool?> startNavigation({MapOptions? options}) async {
    throw UnimplementedError('startNavigation() has not been implemented.');
  }

  ///Adds waypoints or stops to an on-going navigation
  ///
  /// [wayPoints] must not be null and have at least 1 item. The way points will
  /// be inserted after the currently navigating \
  /// waypoint in the existing navigation
  Future<dynamic> addWayPoints({required List<LatLng> wayPoints}) {
    throw UnimplementedError(
      'addWayPoints({required wayPoints }) has not been implemented.',
    );
  }

  ///Ends Navigation and Closes the Navigation View
  Future<bool?> finishNavigation() async {
    throw UnimplementedError('finishNavigation() has not been implemented.');
  }

  /// Will download the navigation engine and the user's region
  /// to allow offline routing
  Future<bool?> enableOfflineRouting() async {
    throw UnimplementedError(
      'enableOfflineRouting() has not been implemented.',
    );
  }

  /// Event listener
  Future<dynamic> registerRouteEventListener(
    ValueSetter<RouteEvent> listener,
  ) async {
    throw UnimplementedError(
      'registerEventListener() has not been implemented.',
    );
  }

  Future<void> moveCamera({
    required LatLng latLng,
    double? bearing,
    double? zoom,
    double? tilt,
  }) async {
    throw UnimplementedError('moveCamera() has not been implemented.');
  }

  Future<void> animateCamera({
    required LatLng latLng,
    double? bearing,
    double? zoom,
    double? tilt,
  }) async {
    throw UnimplementedError('animateCamera() has not been implemented.');
  }
}
