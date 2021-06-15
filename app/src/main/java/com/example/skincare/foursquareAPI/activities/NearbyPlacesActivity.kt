package com.example.skincare.foursquareAPI.activities

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skincare.Constants.Constant
import com.example.skincare.R
import com.example.skincare.foursquareAPI.modules.NearbyPlace
import foursquare.places.api.adapter.RecyclerAdapter
import foursquare.places.api.interfaces.PlacesAPI
import foursquare.places.api.modules.PlacesModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NearbyPlacesActivity : AppCompatActivity() {
    private var destination_latitude: Double? = null;
    private var destination_longitude: Double? = null;
    private var recyclerView: RecyclerView? = null;

    val data: ArrayList<PlacesModel> = ArrayList()

    var adapter: RecyclerAdapter? = null;

    private lateinit var linearLayoutManager: LinearLayoutManager


    var lm: LocationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby_places)

        recyclerView = findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = linearLayoutManager

//        destination_latitude = intent.getStringExtra("latitude")!!.toDouble()
//        destination_longitude = intent.getStringExtra("longitude")!!.toDouble();

        destination_latitude = Constant.gloablLatitude;
        destination_longitude = Constant.globalLongitude;


        if (lm == null) {
            lm =
                this.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        }

        if (isOnline(this)) {

            getPlacesFromAPI();

        } else {
            showAlertDialog(" your internet", "INTERNET")
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
                    getPlacesFromAPI();


                } else {
                    showAlertDialog(" your internet", "INTERNET");
                }
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

    fun getPlacesFromAPI() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(PlacesAPI.PLACES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PlacesAPI::class.java);

        val call = service.getVenues(
            "MZJGXSVOYLVY4DTA3TCIXXBV4J4QBXMOUJBPSVXO2OOFVQ43",
            "TZ23MAGRFKNWWG3GPCTWEFGY4GU5R1K5VXYSF1L5J4Q5E22N",
            "Skin Specialist",
            "3000",
            "" + destination_latitude.toString() + "," + destination_longitude.toString() + "",
            "20201230"
        );
        call.enqueue(object : Callback<NearbyPlace> {
            override fun onResponse(
                call: Call<NearbyPlace>,
                placesResponse: Response<NearbyPlace>
            ) {
                if (placesResponse.code() == 200) {


                    val movieResponse = placesResponse.body()!!


                    val responseBody = movieResponse.response.venues

                    var name: String? = null;
                    var city: String? = null;
                    var country: String? = null;
                    var distance: Int? = null;
                    var address: String? = null;
                    var lat: Double? = null;
                    var lng: Double? = null;

                    for (item in responseBody) {

                        if (item.name != null) {
                            name = item.name;
                        }


                        if (item.location.country != null) {
                            country = item.location.country;
                        }
                        if (item.location.city != null) {
                            city = item.location.city;
                        }
                        if (item.location.address != null) {
                            address = item.location.address;
                        }
                        if (item.location.lat != null) {
                            lat = item.location.lat;
                        }
                        if (item.location.lng != null) {
                            lng = item.location.lng;
                        }
                        if (item.location.distance != null) {
                            distance = item.location.distance.toInt();
                        }
                        data.add(PlacesModel(name,city,country,address,distance,lat,lng));

//                        data.add(PlacesModel(name.toString(),city.toString(),country.toString(),address.toString(),distance?.toInt(),lat,lng));

                    }


                    adapter = RecyclerAdapter(this@NearbyPlacesActivity, data)
                    linearLayoutManager.orientation = LinearLayoutManager.VERTICAL;
                    recyclerView!!.layoutManager = LinearLayoutManager(this@NearbyPlacesActivity)
                    recyclerView!!.adapter = adapter
                }
            }

            override fun onFailure(call: Call<NearbyPlace>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()

            }
        });
    }
}