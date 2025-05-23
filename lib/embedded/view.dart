import 'dart:io';

import 'package:maplibre_navigation_flutter/models/options.dart';
import 'package:maplibre_navigation_flutter/models/route_event.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';

import 'controller.dart';

/// Callback method for when the navigation view is ready to be used.
///
/// Pass to [MapNavigationView.onMapCreated] to receive a [MapNavigationViewController] when the
/// map is created.
typedef OnNavigationViewCreatedCallBack = void Function(
    MapNavigationViewController controller);

///Embeddable Navigation View.
class MapNavigationView extends StatelessWidget {
  static const StandardMessageCodec _decoder = StandardMessageCodec();
  final MapOptions options;
  final OnNavigationViewCreatedCallBack? onCreated;
  final ValueSetter<RouteEvent>? onRouteEvent;

  static const String viewType = 'VietMapNavigationPluginView';

  const MapNavigationView(
      {super.key, required this.options, this.onCreated, this.onRouteEvent});

  @override
  Widget build(BuildContext context) {
    if (Platform.isAndroid) {
      // using Hybrid Composition

      return AndroidView(
        viewType: viewType,
        onPlatformViewCreated: _onPlatformViewCreated,
        gestureRecognizers: const <Factory<OneSequenceGestureRecognizer>>{},
        creationParams: options.toMap(),
        creationParamsCodec: const StandardMessageCodec(),
      );
      // return PlatformViewLink(
      //   viewType: viewType,
      //   surfaceFactory: (context, controller) {
      //     return AndroidViewSurface(
      //       controller: controller as AndroidViewController,
      //       gestureRecognizers: const <Factory<OneSequenceGestureRecognizer>>{},
      //       hitTestBehavior: PlatformViewHitTestBehavior.opaque,
      //     );
      //   },
      //   onCreatePlatformView: (params) {
      //     return PlatformViewsService.initAndroidView(
      //       id: params.id,
      //       viewType: viewType,
      //       layoutDirection: TextDirection.ltr,
      //       creationParams: options.toMap(),
      //       creationParamsCodec: _decoder,
      //       onFocus: () {
      //         params.onFocusChanged(true);
      //       },
      //     )
      //       ..addOnPlatformViewCreatedListener(params.onPlatformViewCreated)
      //       ..addOnPlatformViewCreatedListener(_onPlatformViewCreated)
      //       ..create();
      //   },
      // );
    } else if (Platform.isIOS) {
      return UiKitView(
          viewType: viewType,
          onPlatformViewCreated: _onPlatformViewCreated,
          creationParams: options.toMap(),
          creationParamsCodec: _decoder);
    } else {
      return Container();
    }
  }

  void _onPlatformViewCreated(int id) {
    if (onCreated == null) {
      return;
    }
    onCreated!(MapNavigationViewController(id, onRouteEvent));
  }
}
