package org.maplibre.utilities
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import io.flutter.plugin.common.MethodCall
import org.maplibre.maplibre_navigation_flutter.MaplibreNavigationFlutterPlugin
import org.maplibre.models.VietMapEvents
import org.maplibre.models.VietMapRouteProgressEvent
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.Serializable
import java.util.*

class PluginUtilities {
    companion object {
        @JvmStatic
        fun getResourceFromContext(context: Context?, resName: String): String {
            if (context == null) {
                throw IllegalArgumentException("null context")
            }
            val stringRes = context.resources.getIdentifier(resName, "string", context.packageName)
            if (stringRes == 0) {
                throw IllegalArgumentException(
                    String.format(
                        "The 'R.string.%s' value it's not defined in your project's resources file.",
                        resName
                    )
                )
            }
            return context.getString(stringRes)
        }

        fun sendEvent(event: VietMapRouteProgressEvent) {
            val dataString = event.toJson()
            val jsonString = "{" +
                    "  \"eventType\": \"${VietMapEvents.PROGRESS_CHANGE.value}\"," +
                    "  \"data\": $dataString" +
                    "}"
            MaplibreNavigationFlutterPlugin.eventSink?.success(jsonString)
        }

        fun sendEvent(event: VietMapEvents, data: String = "") {
            val jsonString =
                //VietMapEvents.MILESTONE_EVENT == event ||
                if ( event == VietMapEvents.USER_OFF_ROUTE || event == VietMapEvents.ROUTE_BUILT|| event == VietMapEvents.ON_NEW_ROUTE_SELECTED||event == VietMapEvents.ON_MAP_LONG_CLICK
                    ||event == VietMapEvents.ON_MAP_CLICK  )
                    "{" +
                            "  \"eventType\": \"${event.value}\"," +
                            "  \"data\": $data" +
                            "}"
                else "{" +
                        "  \"eventType\": \"${event.value}\"," +
                        "  \"data\": \"$data\"" +
                        "}"
            MaplibreNavigationFlutterPlugin.eventSink?.success(jsonString)
        }

        fun getListOfStringById(key: String, call: MethodCall): ArrayList<String> {
            val logTypesList = arrayListOf<String>()
            call.argument<String>(key)?.let {
                it.split(",").forEach {
                    logTypesList.add(it)
                }
                return logTypesList
            }
            return arrayListOf()
        }

        fun getStringValueById(key: String, call: MethodCall): String {
            call.argument<String>(key)?.let {
                return it
            }
            return ""
        }

        fun getIntValueById(key: String, call: MethodCall): Int? {
            call.argument<Int>(key)?.let {
                return it
            }
            return null
        }

        fun getDoubleValueById(key: String, call: MethodCall): Double? {
            call.argument<Double>(key)?.let {
                return it
            }
            return null
        }

        fun getBoolValueById(key: String, call: MethodCall): Boolean {
            call.argument<Boolean>(key)?.let {
                return it
            }
            return false
        }

        fun getInputStreamValueById(key: String, call: MethodCall): InputStream? {
            call.argument<ByteArray>(key)?.let {
                return ByteArrayInputStream(it)
            }
            return null
        }


        fun getLocaleFromCode(locale: String): Locale {
            val locales: Array<Locale> = Locale.getAvailableLocales()

            val filtered = locales.filter {
                it.country.equals(locale, ignoreCase = true)
            }

            return if (filtered.isNotEmpty()) {
                filtered.first()
            } else {
                Locale.ENGLISH
            }
        }

        @SuppressLint("MissingPermission")
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val nw = connectivityManager.activeNetwork ?: return false
                val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
                return when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    // for other device how are able to connect with Ethernet
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    // for check internet over Bluetooth
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                    else -> false
                }
            } else {
                val nwInfo = connectivityManager.activeNetworkInfo ?: return false
                return nwInfo.isConnected
            }
        }

        fun <T : Serializable?> getSerializable(
            activity: Activity,
            name: String,
            clazz: Class<T>
        ): T {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                activity.intent.getSerializableExtra(name, clazz)!!
            else
                activity.intent.getSerializableExtra(name) as T
        }
    }

}