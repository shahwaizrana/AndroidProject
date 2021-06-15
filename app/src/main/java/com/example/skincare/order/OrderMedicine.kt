package com.example.skincare.order

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.businessschema.OnDataReceived
import com.example.skincare.File.FileUtil
import com.example.skincare.R
import com.example.skincare.modelclasses.PostData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_order_medicine.*
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class OrderMedicine : AppCompatActivity() {
    var photouri: Uri? = null;
    private lateinit var auth: FirebaseAuth;
    var bitmap: Bitmap? = null

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    private var pDialog: ProgressDialog? = null
    var actualImage: File? = null
    var compressedImage: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_medicine)


        pDialog = ProgressDialog(this)
        auth = FirebaseAuth.getInstance();


        txtcancel.setOnClickListener { finish(); }
        txtpost.isEnabled = false
        //get image Listener
        imgforblog.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK);
            intent.type = "image/*";
            startActivityForResult(
                intent,
                PICK_IMAGE_REQUEST
            );
        }


        txtpost.setOnClickListener {


            txtpost.isEnabled = false
            if (photouri == null) {
                txtpost.isEnabled = true
                showToast("Please UploadPhoto")
                return@setOnClickListener
            }
            if (txtTitle.text.isEmpty() || txtTitle.text.equals("")) {
                txtpost.isEnabled = true
                showToast("Title is empty")
                return@setOnClickListener

            }

            if (phoneNumber.text.isEmpty() || phoneNumber.text.equals("")) {
                txtpost.isEnabled = true
                showToast("Enter phone number please")
                return@setOnClickListener
            }
            if (phoneNumber.text.length>11 && phoneNumber.text.length<15) {
                txtpost.isEnabled = true
                showToast("Enter valid number")
                return@setOnClickListener
            }

            if (_txtblog.text.isEmpty() || _txtblog.text.equals("")) {
                txtpost.isEnabled = true
                showToast("Description is  Empty")
                return@setOnClickListener
            }

            pDialog!!.setTitle("Uploading")
            pDialog!!.setIcon(R.drawable.ic_access_alarm)
            pDialog!!.setMessage("Please wait...")
            pDialog!!.setCancelable(false)
            pDialog!!.show()
            uploadImage(bitmap!!)


        }
    }

    fun uploadImage(bitmap: Bitmap) {

        val filename = UUID.randomUUID();
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 20, baos)
        val data: ByteArray = baos.toByteArray()

        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReference("/postOrder/"+auth.uid!!+"/$filename" + ".png")


        val uploadTask: UploadTask = storageRef.putBytes(data)


        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
            pDialog!!.dismiss()
        }.addOnSuccessListener {

            storageRef.downloadUrl.addOnSuccessListener {
                saveblogtofirebase_database(it.toString())
            }
            txtpost.isEnabled = true

        }
            .addOnCompleteListener(object : OnCompleteListener<UploadTask.TaskSnapshot> {
                override fun onComplete(p0: Task<UploadTask.TaskSnapshot>) {

                }

            })
            .addOnProgressListener {
                val progress: Double =
                    100.0 * it.getBytesTransferred() / it
                        .getTotalByteCount()
                pDialog!!.setMessage("Uploaded " + progress.toInt() + "%")
            }
    }

    fun getDefaults(key: String?, context: Context?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(key, null)
    }
    fun saveblogtofirebase_database(imageuri: String) {

        //Uri dlUri = uri;
        val c = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa")
        var datetime: String = dateFormat.format(c.time)
        val imguser: String = getDefaults("userimg", applicationContext!!).toString()
//
        val mref = FirebaseDatabase.getInstance().getReference("/postOrder/"+auth.uid).push();
        //  var mref = Firebase.database.getReference("/blogs").push();
        var blog = PostData(
            mref.key!!,
            auth.uid!!,
            txtTitle.text.toString(),
            phoneNumber.text.toString(),
            _txtblog.text.toString(),
            imageuri,
            imguser,
            datetime,
            "pending"

        )
        mref.setValue(blog).addOnSuccessListener {
            txtpost.isEnabled = true
            showToast("Finally  DataStored")
            pDialog!!.dismiss()
            startActivity(Intent(this, MyOrders::class.java))
            finish()
        }
            .addOnFailureListener {
                showToast("Error")
                pDialog!!.dismiss()
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data == null) {
                showToast("Failed to open picture!")
                return
            }
            try {
                photouri = data.data

                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, photouri)

                FileUtil.from(this, data.data!!,object : OnDataReceived {
                    override fun onDataReceivedListener(data: File?) {
                        if (data!=null){
                            actualImage=data
                            compressImage()
                        }
                    }
                })
            } catch (e: Exception) {
                showToast("Failed to read picture data!")
                e.printStackTrace()
            }
        }
    }

    private fun showToast(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    fun compressImage() {

        actualImage?.let { imageFile ->

            lifecycleScope.launch {

                 compressedImage = Compressor.compress(applicationContext, imageFile)


                setCompressedImage()



            }
        } ?: showToast("Please choose an image!")
    }

    private fun setCompressedImage() {
        compressedImage?.let {


            bitmap = BitmapFactory.decodeFile(it.absolutePath)

            txtpost.isEnabled = true


        }
    }
}