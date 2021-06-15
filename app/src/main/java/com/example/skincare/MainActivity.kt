package com.example.skincare

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ShareCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.skincare.Helper.GraphicOverlay
import com.example.skincare.Helper.RectOverlay
import com.example.skincare.foursquareAPI.activities.NearbyPlacesActivity
import com.example.skincare.modelclasses.UserData
import com.example.skincare.order.OrderMedicine
import com.google.android.gms.vision.CameraSource
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.pytorch.IValue
import org.pytorch.Module
import org.pytorch.torchvision.TensorImageUtils
import java.io.*
import java.lang.Float.MAX_VALUE

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration;
    lateinit var drawerLayout: DrawerLayout;
    lateinit var navView: NavigationView;
    lateinit var userProfileName:TextView;
    lateinit var userProfileEmail:TextView;

    //    var detector: FirebaseVisionFaceDetector? = null
    private var graphicOverlay: GraphicOverlay? = null
    var alertDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)



        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        var toogle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )



        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        navView.bringToFront();
        navView.setNavigationItemSelectedListener(this);
        // [START fcm_runtime_enable_auto_init]
        Firebase.messaging.isAutoInitEnabled = true
        // [END fcm_runtime_enable_auto_init]


        graphicOverlay = findViewById(R.id.graphic_overly);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }

        alertDialog = AlertDialog.Builder(this)
            .setMessage("Please wait,processing.....").setCancelable(false).create()

        checkvaluestore()




        Toast.makeText(
            this@MainActivity,
            "Select Image from Gallery or Capture Image from Camera...",
            Toast.LENGTH_SHORT
        )
            .show();
        mark_image_as_final.setVisibility(View.GONE);

    }


//check if record of current user is exist or not in shared preference
    fun checkvaluestore() {
        try {
            if (getDefaults("ValueStored", applicationContext).equals("Yes")) {

                if (getDefaults("username",applicationContext)!=null && getDefaults("useremail",applicationContext)!=null ){

                }



            } else {

                fetchcurrentuser()
            }
        } catch (e: Exception) {
            //fetchcurrentuser()
            Log.d("TAG", "error")
            e.printStackTrace()
        }
    }
    //shared preference to set record of current user for short time purpose
    fun setDefaults(key: String, value: String, context: Context) {
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preference.edit()
        editor.putString(key, value)
        editor.apply()
    }

    //shared preference to get record of current user for short time purpose
    fun getDefaults(key: String?, context: Context?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(key, null)
    }

    //it will fetch the record of current user

    private fun fetchcurrentuser() {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid;

        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Initialize Data")
        progressDialog.setIcon(R.drawable.authpic)
        progressDialog.setMessage("Please Wait.........")
        progressDialog.show()
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                val userData = p0.getValue(UserData::class.java);

                if (userData != null) {
                    //create new user
                    progressDialog.dismiss()

                    try {



                        setDefaults(
                            "username",
                            userData.userName,
                            this@MainActivity
                        )
                        setDefaults(
                            "useremail",
                            userData.userEmail,
                            this@MainActivity
                        )
                        setDefaults(
                            "userid",
                            userData.uid,
                            this@MainActivity
                        )
                        setDefaults(
                            "userGender",
                            userData.userGender,
                            this@MainActivity
                        )
                        setDefaults(
                            "ValueStored",
                            "Yes",
                            this@MainActivity
                        )
                        txtMainName.text = getDefaults("username", applicationContext)
                        _txtuseremail.text = getDefaults("useremail", applicationContext)

                    } catch (e: java.lang.Exception) {
                        Toast.makeText(applicationContext, "error exist" + e.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                } else {
                    progressDialog.dismiss()
                }
            }

        })
    }
// it will browse image using gallery
    fun browseImage(view: View) {
        Toast.makeText(
            this@MainActivity,
            "Kindly Select a clear picture of full face",
            Toast.LENGTH_SHORT
        ).show()
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2000)
//        btn_signout.setVisibility(View.GONE)
    }

    //this method will capture image using camera
    fun captureImage(view: View) {
        Toast.makeText(
            this@MainActivity,
            "Kindly Capture a clear picture of full face",
            Toast.LENGTH_SHORT
        ).show()
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, 1000)

        }
    }

    //it will cancel the captured image and will allow to capture new one

    fun cancelImage(view: View) {
        graphicOverlay!!.clear()
        capture_image.setVisibility(View.VISIBLE)
        browse_image.setVisibility(View.VISIBLE)
        mark_image_as_final.setVisibility(View.GONE)
        cancel_image.setVisibility(View.GONE)
        captured_image.setImageDrawable(null)

    }

    //this function will process the captured image and give result.
    fun getResultForImage(view: View) {

        runOnUiThread {         mark_image_as_final.visibility = View.GONE
            progressBarmainActivity.visibility = View.VISIBLE; }



        graphicOverlay!!.clear()

        var bitmap: Bitmap? = null
        var module: Module? = null

        try {
            //Read the image as Bitmap
            bitmap = (captured_image.getDrawable() as BitmapDrawable).bitmap

            //Here we reshape the image into 400*400
            bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true)

            //Loading the model file.
            module = Module.load(
                fetchModelFile(
                    this@MainActivity,
                    "model01.pt"
                )
            )




            //Input Tensor
            val input = TensorImageUtils.bitmapToFloat32Tensor(
                bitmap,
                TensorImageUtils.TORCHVISION_NORM_MEAN_RGB,
                TensorImageUtils.TORCHVISION_NORM_STD_RGB
            )



            //Calling the forward of the model to run our input
            val output = module.forward(IValue.from(input)).toTensor()

            val score_arr = output.dataAsFloatArray



            // Fetch the index of the value with maximum score
            var max_score: Float = -MAX_VALUE
            var ms_ix = -1
            for (i in score_arr.indices) {
                if (score_arr[i] > max_score) {
                    max_score = score_arr[i]
                    ms_ix = i
                }
            }

            //Fetching the name from the list based on the index

            //Fetching the name from the list based on the index
            val detected_class: String = ListOfDiseases.MODEL_CLASSES.MODEL_CLASSES[ms_ix]

            mark_image_as_final.visibility = View.VISIBLE
            progressBarmainActivity.visibility = View.GONE;
            val intent = Intent(this@MainActivity, MoreInfoActivity::class.java)
            intent.putExtra("class", detected_class)
            startActivity(intent)


        } catch (e: IOException) {
            finish()
        }
    }
//it will fetch mode file and check disease according to script
    @Throws(IOException::class)
    fun fetchModelFile(
        context: Context,
        modelName: String?
    ): String? {
        val file = File(context.filesDir, modelName)
        if (file.exists() && file.length() > 0) {
            return file.absolutePath
        }
        context.assets.open(modelName!!).use { `is` ->
            FileOutputStream(file).use { os ->
                val buffer = ByteArray(4 * 1024)
                var read: Int
                while (`is`.read(buffer).also { read = it } != -1) {
                    os.write(buffer, 0, read)
                }
                os.flush()
            }
            return file.absolutePath
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

//on this activity you will be checked that image is selected or captured from gallery or camera
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            capture_image.setVisibility(View.GONE)
            browse_image.setVisibility(View.GONE)
            mark_image_as_final.setVisibility(View.VISIBLE)
            cancel_image.setVisibility(View.VISIBLE)

            val extras = data!!.extras
            val imageBitmap = extras!!["data"] as Bitmap?
            //            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, imageView.getMaxWidth(), imageView.getMaxHeight(), false);
            captured_image.setImageBitmap(imageBitmap)
            //rotation degrees 0
            val image = InputImage.fromBitmap(imageBitmap, 0)
//            val image = FirebaseVisionImage.fromBitmap(imageBitmap!!)
            detectFaces(image)
        } else if (requestCode == 2000 && resultCode == Activity.RESULT_OK) {
            capture_image.setVisibility(View.GONE)
            browse_image.setVisibility(View.GONE)
            mark_image_as_final.setVisibility(View.VISIBLE)
            cancel_image.setVisibility(View.VISIBLE)
            val imageUri = data!!.data
            val imageStream: InputStream?
            try {
                imageStream = contentResolver.openInputStream(imageUri!!)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                //                selectedImage = Bitmap.createScaledBitmap(selectedImage, imageView.getMaxWidth(), imageView.getMaxHeight(), false);
                captured_image.setImageBitmap(selectedImage)
                val image = InputImage.fromBitmap(selectedImage, 0)
                detectFaces(image)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

//IN this function faces will be detected if face not found toast message will be shown
    private fun detectFaces(image: InputImage) {
        // [START set_detector_options]
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
//            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setContourMode(2)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .setMinFaceSize(0.15f)
            .enableTracking()
            .build()
        // [END set_detector_options]

        // [START get_detector]
        val detector = FaceDetection.getClient(options)
        // Or, to use the default option:
        // val detector = FaceDetection.getClient();
        // [END get_detector]

        // [START run_detector]
        val result = detector.process(image)
            .addOnSuccessListener { faces ->
                // Task completed successfully
                // [START_EXCLUDE]
                // [START get_face_info]
                graphicOverlay!!.clear()

//                    for (face in faces) {
//
//                        alertDialog!!.dismiss()
//                    }

                if (faces.size > 1) {
                    Toast.makeText(
                        this@MainActivity,
                        "Multiple faces are not allowed",
                        Toast.LENGTH_SHORT
                    )
                        .show();
                    mark_image_as_final.setVisibility(View.GONE);
                    return@addOnSuccessListener;
                }

                if (faces.size > 0) {
                    for (face in faces) {
                        graphicOverlay!!.setCameraInfo(
                            image.width,
                            image.height,
                            CameraSource.CAMERA_FACING_BACK
                        )
                        val overlay =
                            RectOverlay(graphicOverlay, face.boundingBox)
                        graphicOverlay!!.add(overlay)

                        graphicOverlay!!.add(FaceGraphic(graphicOverlay, face))

                        val bounds = face.boundingBox
                        val rotY = face.headEulerAngleY // Head is rotated to the right rotY degrees
                        val rotZ = face.headEulerAngleZ // Head is tilted sideways rotZ degrees

                        // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
                        // nose available):
                        val leftEar = face.getLandmark(FaceLandmark.LEFT_EAR)
                        leftEar?.let {
                            val leftEarPos = leftEar.position
                        }

                        // If classification was enabled:
                        if (face.smilingProbability != null) {
                            val smileProb = face.smilingProbability
                        }
                        if (face.rightEyeOpenProbability != null) {
                            val rightEyeOpenProb = face.rightEyeOpenProbability
                        }

                        // If face tracking was enabled:
                        if (face.trackingId != null) {
                            val id = face.trackingId
                        }
                    }
                    Toast.makeText(this@MainActivity, "  Face detected", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    mark_image_as_final.setVisibility(View.GONE);
                    Toast.makeText(this@MainActivity, "No Face detected", Toast.LENGTH_SHORT)
                        .show()

                    //alertDialog!!.dismiss()
                }
                // [END get_face_info]
                // [END_EXCLUDE]
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
            }
        // [END run_detector]
    }

    //On left drawer items will be selected here

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {


            R.id.nav_aboutus -> {
                startActivity(Intent(this, AboutApplicationPage::class.java))
            }
            R.id.nav_share -> {
                ShareCompat.IntentBuilder.from(this@MainActivity)
                    .setType("text/plain")
                    .setChooserTitle("\\nLet me recommend you this application\\n\\n")
                    .setText("http://play.google.com/store/apps/details?id=" + this.getPackageName())
                    .startChooser();
            }

            R.id.nav_logout -> {
                signout()

            }
            R.id.nav_editProfile -> {
                startActivity(Intent(this, MyProfileActivity::class.java))

            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //you will get signout
    fun signout() {
        FirebaseAuth.getInstance().signOut();
        setDefaults("ValueStored","no",this@MainActivity);
        val intent = Intent(this, LoginActivity::class.java);
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}