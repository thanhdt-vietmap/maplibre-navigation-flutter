import 'package:flutter/src/foundation/basic_types.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:maplibre_navigation_flutter/maplibre_navigation_flutter.dart';
import 'package:maplibre_navigation_flutter/maplibre_navigation_flutter_platform_interface.dart';
import 'package:maplibre_navigation_flutter/maplibre_navigation_flutter_method_channel.dart';
import 'package:maplibre_navigation_flutter/models/latlng.dart';
import 'package:maplibre_navigation_flutter/models/options.dart';
import 'package:maplibre_navigation_flutter/models/route_event.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockMaplibreNavigationFlutterPlatform
    with MockPlatformInterfaceMixin
    implements MaplibreNavigationFlutterPlatform {
  @override
  Future<String?> getPlatformVersion() => Future.value('42');

  @override
  Future addWayPoints({required List<LatLng> wayPoints}) {
    // TODO: implement addWayPoints
    throw UnimplementedError();
  }

  @override
  Future<void> animateCamera({
    required LatLng latLng,
    double? bearing,
    double? zoom,
    double? tilt,
  }) {
    // TODO: implement animateCamera
    throw UnimplementedError();
  }

  @override
  Future<bool?> enableOfflineRouting() {
    // TODO: implement enableOfflineRouting
    throw UnimplementedError();
  }

  @override
  Future<bool?> finishNavigation() {
    // TODO: implement finishNavigation
    throw UnimplementedError();
  }

  @override
  Future<double?> getDistanceRemaining() {
    // TODO: implement getDistanceRemaining
    throw UnimplementedError();
  }

  @override
  Future<double?> getDurationRemaining() {
    // TODO: implement getDurationRemaining
    throw UnimplementedError();
  }

  @override
  Future<void> moveCamera({
    required LatLng latLng,
    double? bearing,
    double? zoom,
    double? tilt,
  }) {
    // TODO: implement moveCamera
    throw UnimplementedError();
  }

  @override
  Future registerRouteEventListener(ValueSetter<RouteEvent> listener) {
    // TODO: implement registerRouteEventListener
    throw UnimplementedError();
  }

  @override
  Future<bool?> startFreeDrive(MapOptions options) {
    // TODO: implement startFreeDrive
    throw UnimplementedError();
  }

  @override
  Future<bool?> startNavigation({MapOptions? options}) {
    // TODO: implement startNavigation
    throw UnimplementedError();
  }
}

void main() {
  final MaplibreNavigationFlutterPlatform initialPlatform =
      MaplibreNavigationFlutterPlatform.instance;

  test('$MethodChannelMaplibreNavigationFlutter is the default instance', () {
    expect(
      initialPlatform,
      isInstanceOf<MethodChannelMaplibreNavigationFlutter>(),
    );
  });

  test('getPlatformVersion', () async {
    MaplibreNavigationFlutter maplibreNavigationFlutterPlugin =
        MaplibreNavigationFlutter();
    MockMaplibreNavigationFlutterPlatform fakePlatform =
        MockMaplibreNavigationFlutterPlatform();
    MaplibreNavigationFlutterPlatform.instance = fakePlatform;

    expect(await maplibreNavigationFlutterPlugin.getPlatformVersion(), '42');
  });
}
