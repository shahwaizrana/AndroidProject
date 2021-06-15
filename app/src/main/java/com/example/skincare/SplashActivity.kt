package com.example.skincare

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.skincare.Constants.Constant
import com.example.skincare.Location.LocationExtractor
import com.google.firebase.auth.FirebaseAuth
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity(),  LocationExtractor.LocationResult {

    private var locationExtractor: LocationExtractor? = null
    var lm: LocationManager? = null

    var gps_enabled = false
    var network_enabled = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


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

// in this method we check user is login or logout
    private fun isUserLogin() {

        val userId = FirebaseAuth.getInstance().uid;
        if (userId != null) {
            val intent = Intent(this, MainActivity::class.java);
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
            return;
        }
        runOnUiThread { showNoAccountFoundAlert() }


    }
//if there is no one login it ll prompt this dialog
    private fun showNoAccountFoundAlert() {
        val builder =
            AlertDialog.Builder(this)

        val view =
            LayoutInflater.from(this).inflate(R.layout.no_account_alert_layout, null)
        builder.setView(view)
        val alertDialog = builder.create()
        alertDialog.setCancelable(false);
        alertDialog.show()
        view.findViewById<View>(R.id.sign_up_cv)
            .setOnClickListener { v: View? ->
                alertDialog.dismiss()
                        val intent = Intent(this, SignUpActivity::class.java);
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent)
            }
        view.findViewById<View>(R.id.log_in_cv)
            .setOnClickListener { v: View? ->
                alertDialog.dismiss()
                val intent = Intent(this, LoginActivity::class.java);
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
            }
    }

    //This will chekc internet is available or not
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
                checkInternetAndPermission();
            }
        }
    }


    //THis will check user is online means its data connection is on or not
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

    //this will show dialog of internet connection
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


    //in this method we ll check perfmissions and take permission from user

    fun checkInternetAndPermission() {
        if (!isOnline(this)) {


            showAlertDialog(" your internet", "INTERNET");

        } else {
            if (ContextCompat.checkSelfPermission(
                    this.applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED

            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        Constant.GET_LOCATION_PERMISSION_REQUEST_CODE()
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),

                        Constant.GET_LOCATION_PERMISSION_REQUEST_CODE()
                    )
                }


            } else {

                getCurrentLocation();


            }
        }
    }

    fun getCurrentLocation() {
        locationExtractor = LocationExtractor()
        locationExtractor?.getLocation(this, this)
    }

    fun isNetwork(context: Context): Boolean {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return if (netInfo != null && netInfo.isConnectedOrConnecting) {
            true
        } else false
    }

    //after taking permission from user we ll navigate user according to this
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Constant.GET_LOCATION_PERMISSION_REQUEST_CODE() -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) ===
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                        Log.e("Permission", "Permission Granted for Location");
                        getCurrentLocation();
                    }
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        Constant.GET_LOCATION_PERMISSION_REQUEST_CODE()
                    )
                }
                return;
            }
        }
    }


    //in this method we ll get current location of user
     override fun gotLocation(location: Location?) {
        Constant.gloablLatitude = location!!.latitude;
        Constant.globalLongitude = location!!.longitude;

        if (Constant.gloablLatitude != null && Constant.globalLongitude != null) {
            thread(start = true) {
                Thread.sleep(1000);

                isUserLogin()
            }
        }


    }


}