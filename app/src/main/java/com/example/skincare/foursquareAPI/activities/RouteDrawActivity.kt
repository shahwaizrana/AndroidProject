package com.example.skincare.foursquareAPI.activities

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.skincare.Constants.Constant
import com.example.skincare.R
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RouteDrawActivity : AppCompatActivity(), OnMapReadyCallback {
    private var navigationMapRoute: NavigationMapRoute? = null;
    private var mapboxMap: MapboxMap? = null;
    private var mapView: MapView? = null;
    private val TAG = "DrawRouteActivity"
    private var currentRoute: DirectionsRoute? = null;
    private var latitude: Double? = null;
    private var longitude: Double? = null;

    private var navigationButton: Button? = null;


    var lm: LocationManager? = null

    var gps_enabled = false
    var network_enabled = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))
        setContentView(R.layout.activity_route_draw)


        mapView = findViewById(R.id.route_mapView)
        navigationButton = findViewById(R.id.startButton);


        mapView?.onCreate(savedInstanceState)

        latitude = intent.getStringExtra("latitude")!!.toDouble();
        longitude = intent.getStringExtra("longitude")!!.toDouble();

        if (lm == null) {
            lm =
                this.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        }

        if (isOnline(this)) {
            isNetworkAvailable();


        } else {
            showAlertDialog(" your internet", "INTERNET")
        }

    }


    fun isNetworkAvailable() {
        try {
            network_enabled = lm!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            Log.i("internet", "" + network_enabled + "")
        } catch (ex: Exception) {
            Log.e("Network Exception", ex.message!!)
        }

        if (!network_enabled) {
            showAlertDialog(" your gps location", "LOCATION");

        } else {
            try {
                gps_enabled = lm!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            } catch (ex: java.lang.Exception) {
                Log.e("GPS Exception", ex.message!!)
            }

            if (!gps_enabled) {
                showAlertDialog("your gps location", "LOCATION");

            } else {

                mapView?.getMapAsync(this);
            }
        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                } else {
                    TODO("VERSION.SDK_INT < M")
                }
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    fun showAlertDialog(msg: String, type: String) {
        val builder = AlertDialog.Builder(this);
        builder.setTitle(R.string.dialogTitle);
        builder.setMessage(getString(R.string.dialogMessage) + msg)

        builder.setPositiveButton("Yes") { dialogInterface, which ->

            if (type.equals("INTERNET")) {
                if (isOnline(this)) {
                    isNetworkAvailable();


                } else {
                    showAlertDialog(" your internet", "INTERNET");
                }
            } else {
                isNetworkAvailable();
            }


        }

        //performing negative action
        builder.setNegativeButton("Cancel") { dialogInterface, which ->

            finish();
        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS) { style ->

            addMarkerOnMap();

            var origin: Point =
                Point.fromLngLat(Constant.globalLongitude!!, Constant.gloablLatitude!!)
            var destination: Point = Point.fromLngLat(longitude!!, latitude!!)

            getRoute(origin, destination);


            navigationButton!!.setOnClickListener(View.OnClickListener {

                var simulateRoute: Boolean = true;

                val options: NavigationLauncherOptions = NavigationLauncherOptions.builder()
                    .directionsRoute(currentRoute)
                    .shouldSimulateRoute(simulateRoute)
                    .build();
// Call this method with Context from within an Activity
                NavigationLauncher.startNavigation(this@RouteDrawActivity, options);


            });


        }
    }



    private fun addMarkerOnMap() {
        mapboxMap?.addMarker(
            MarkerOptions()
                .position(
                    LatLng(
                        Constant.gloablLatitude!!.toDouble(),
                        Constant.globalLongitude!!.toDouble()
                    )
                )
                .title("Eiffel Tower")

        )


        mapboxMap!!.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.Builder()

                    .target(
                        LatLng(
                            Constant.gloablLatitude!!.toDouble(),
                            Constant.globalLongitude!!.toDouble()
                        )
                    )
                    .zoom(14.0)
                    .build()
            ), 4000
        )
    }

    private fun getRoute(
        origin: Point,
        destination: Point
    ) {
        NavigationRoute.builder(this)
            .accessToken(getString(R.string.mapbox_access_token))
            .origin(origin)
            .destination(destination)
            .build()
            .getRoute(object : Callback<DirectionsResponse?> {
                override fun onResponse(
                    call: Call<DirectionsResponse?>?,
                    response: Response<DirectionsResponse?>
                ) {
// You can get the generic HTTP info about the response
                    Log.d(TAG, "Response code: " + response.code())
                    if (response.body() == null) {
                        Log.e(
                            TAG,
                            "No routes found, make sure you set the right user and access token."
                        )
                        return
                    } else if (response.body()!!.routes().size < 1) {
                        Log.e(TAG, "No routes found")
                        return
                    }
                    currentRoute = response.body()!!.routes().get(0)

// Draw the route on the map
                    if (navigationMapRoute != null) {
                        navigationMapRoute!!.removeRoute()
                    } else {
                        navigationButton?.isEnabled = true
                        navigationMapRoute =
                            mapView?.let {
                                mapboxMap?.let { it1 ->
                                    NavigationMapRoute(
                                        null,
                                        it, it1, R.style.NavigationMapRoute
                                    )
                                }
                            }
                    }
                    navigationMapRoute!!.addRoute(currentRoute)
                }

                override fun onFailure(
                    call: Call<DirectionsResponse?>?,
                    throwable: Throwable
                ) {
                    Log.e(TAG, "Error: " + throwable.message)
                }
            })
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }


    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }
}