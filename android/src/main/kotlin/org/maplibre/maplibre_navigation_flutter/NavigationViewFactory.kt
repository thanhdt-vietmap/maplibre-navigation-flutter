package org.maplibre.maplibre_navigation_flutter

import android.app.Activity
import android.content.Context
import android.util.Log
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory
import org.maplibre.android.MapLibre

class NavigationViewFactory (private val messenger: BinaryMessenger, private val lifecycle: LifecycleProvider, private val activity: Activity?) : PlatformViewFactory(
    StandardMessageCodec.INSTANCE) {
    override fun create(context: Context?, viewId: Int, args: Any?, ): PlatformView {
        val controller = FlutterMapLibreNavigationView(context!!, messenger, viewId,  args, lifecycle, activity)
        Log.d("Lifecycle","onInit")
        MapLibre.getInstance(context)

        controller.init()
        return controller
    }
}