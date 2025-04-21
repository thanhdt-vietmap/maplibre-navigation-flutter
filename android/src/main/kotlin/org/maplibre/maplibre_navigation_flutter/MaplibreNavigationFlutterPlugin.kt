package org.maplibre.maplibre_navigation_flutter

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.embedding.engine.plugins.lifecycle.HiddenLifecycleReference
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.platform.PlatformViewRegistry

/** MaplibreNavigationFlutterPlugin */
class MaplibreNavigationFlutterPlugin: FlutterPlugin, ActivityAware, EventChannel.StreamHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel: MethodChannel
  private var activity: Activity? = null
  private lateinit var progressEventChannel: EventChannel
  private var currentContext: Context? = null
  private var flutterPluginBinding: FlutterPlugin.FlutterPluginBinding?=null
  private var flutterAssets: FlutterPlugin.FlutterAssets? = null
  private var lifecycle: Lifecycle? = null
  private var isInitialized = false
  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    this.flutterPluginBinding = flutterPluginBinding
    flutterAssets = flutterPluginBinding.flutterAssets;
    val messenger = flutterPluginBinding.binaryMessenger
    channel = MethodChannel(messenger, "navigation_plugin")

    progressEventChannel = EventChannel(messenger, "navigation_plugin/events")
    progressEventChannel.setStreamHandler(this)

    platformViewRegistry = flutterPluginBinding.platformViewRegistry
    binaryMessenger = messenger

    registerFlutterBinding()
  }


  private fun registerFlutterBinding(){
    if(flutterPluginBinding!=null&&activity!=null&&!isInitialized) {
      isInitialized = true
      flutterPluginBinding!!.platformViewRegistry
        .registerViewFactory(
          viewId,
          NavigationViewFactory(
            binaryMessenger!!,
            object : LifecycleProvider {
              override fun getVietMapLifecycle(): Lifecycle? {
                return lifecycle
              }
            }, activity
          )
        )
    }
  }
  companion object {

    var eventSink: EventChannel.EventSink? = null

    var currentActivity: Activity? = null
    var platformViewRegistry: PlatformViewRegistry? = null
    var binaryMessenger: BinaryMessenger? = null

    var viewId = "VietMapNavigationPluginView"
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    Log.d("PackageLifecycle", "onDetachedFromEngine")
    currentActivity = null
    activity = null
    channel.setMethodCallHandler(null)
    progressEventChannel.setStreamHandler(null)
  }

  override fun onListen(args: Any?, events: EventChannel.EventSink?) {

    eventSink = events
  }

  override fun onCancel(args: Any?) {
    eventSink = null
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    Log.d("PackageLifecycle", "onReattachedToActivityForConfigChanges")
//        activity

    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
      activity = binding.activity

    } else {
      currentActivity = binding.activity
    }
    onAttachedToActivity(binding);
  }

  override fun onDetachedFromActivity() {
    Log.d("PackageLifecycle", "onDetachedFromActivity")
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
      activity?.finish()
      activity = null
    } else {
      currentActivity!!.finish()
      currentActivity = null
    }
    registerFlutterBinding()
    lifecycle = null;
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
      if (currentActivity == null) {
        currentActivity = binding.activity
        activity = binding.activity
      }
    } else {
      activity = binding.activity
    }
    registerFlutterBinding()
    if (currentContext == null) {
      currentContext = binding.activity.applicationContext
    }
    Log.d("onAttachedToActivity", "--------------------------------")
//        Vietmap.getInstance(currentContext);


//        flutterPluginBinding
//            .platformViewRegistry
//            .registerViewFactory(
//                viewId,
//                MapViewFactory(binaryMessenger!!,
//                    object : LifecycleProvider {
//                        override fun getVietMapLifecycle(): Lifecycle? {
//                            return lifecycle
//                        }
//                    })
//            )
//        initWithActivity
    /** Provides a static method for extracting lifecycle objects from Flutter plugin bindings. */
    /** Provides a static method for extracting lifecycle objects from Flutter plugin bindings.  */

    lifecycle = FlutterLifecycleAdapter().getActivityLifecycle(binding);
  }

  override fun onDetachedFromActivityForConfigChanges() {
    Log.d("PackageLifecycle", "onDetachedFromActivityForConfigChanges")

    onDetachedFromActivity();
  }
}


class FlutterLifecycleAdapter {
  /**
   * Returns the lifecycle object for the activity a plugin is bound to.
   *
   *
   * Returns null if the Flutter engine version does not include the lifecycle extraction code.
   * (this probably means the Flutter engine version is too old).
   */
  fun getActivityLifecycle(
    activityPluginBinding: ActivityPluginBinding
  ): Lifecycle {
    val reference = activityPluginBinding.lifecycle as HiddenLifecycleReference
    return reference.lifecycle
  }
}

interface LifecycleProvider {
  fun getVietMapLifecycle(): Lifecycle?
}

class ProxyLifecycleProvider(
  activity: Activity
) : Application.ActivityLifecycleCallbacks, LifecycleOwner, LifecycleProvider {

  override val lifecycle = LifecycleRegistry(this)
  private val registrarActivityHashCode: Int = activity.hashCode()

  init {
    activity.application.registerActivityLifecycleCallbacks(this)
  }

  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    if (activity.hashCode() != registrarActivityHashCode) return
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
  }

  override fun onActivityStarted(activity: Activity) {
    if (activity.hashCode() != registrarActivityHashCode) return
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
  }

  override fun onActivityResumed(activity: Activity) {
    if (activity.hashCode() != registrarActivityHashCode) return
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
  }

  override fun onActivityPaused(activity: Activity) {
    if (activity.hashCode() != registrarActivityHashCode) return
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  }

  override fun onActivityStopped(activity: Activity) {
    if (activity.hashCode() != registrarActivityHashCode) return
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
  }

  override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
  }

  override fun onActivityDestroyed(activity: Activity) {
    if (activity.hashCode() != registrarActivityHashCode) return
    activity.application.unregisterActivityLifecycleCallbacks(this)
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  }

  override fun getVietMapLifecycle(): Lifecycle {
    return lifecycle
  }

//    override fun getLifecycle(): Lifecycle {
//        return lifecycle
//    }
}