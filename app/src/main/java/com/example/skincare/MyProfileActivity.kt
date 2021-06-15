package com.example.skincare

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_my_profile.*

class MyProfileActivity : AppCompatActivity() {
    var name: EditText? = null;
    val database = FirebaseDatabase.getInstance().getReference("users")
    val auth = FirebaseAuth.getInstance();
    lateinit var toolbar: Toolbar;
    lateinit var gender: String;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)



        name = findViewById(R.id.myprofile_Name)



        disable()
        ShowProfile();
        Listeners()



        if (myprofile_male.isChecked) {
            gender = "Male"
        } else {
            gender = "Female"
        }

    }

    //disable some buttons based on condition
    fun disable() {
        update_profile.isEnabled = false;
        myprofile_Name.isEnabled = false
        myprofile_Email.isEnabled = false

        myprofile_male.isEnabled = false
        myprofile_female.isEnabled = false

    }

    //initialized click listeners here
    fun Listeners() {

        edit_profile.setOnClickListener {
            myprofile_Name.isEnabled = true
            myprofile_Email.isEnabled = true

            update_profile.isEnabled = true
            myprofile_female.isEnabled = true
            myprofile_male.isEnabled = true
        }

        update_profile.setOnClickListener {
            val uid = getDefaults("userid", this).toString()
            update_profile.isEnabled = false
            Toast.makeText(this, "" + myprofile_Name.text, Toast.LENGTH_SHORT).show()
            database.child(uid).child("userName").setValue(myprofile_Name.text.toString())
            database.child(uid).child("userEmail").setValue(myprofile_Email.text.toString())

            database.child(uid).child("userGender").setValue(gender)

            setDefaults("username", myprofile_Name.text.toString(), this)
            setDefaults("useremail", myprofile_Email.text.toString(), this)

            setDefaults("userGender", gender.toString(), this)
            disable()


        }
        myprofile_male.setOnClickListener {
            myprofile_female.isChecked = false
            myprofile_male.isChecked = true
            gender = "Male"
        }

        myprofile_female.setOnClickListener {
            myprofile_male.isChecked = false
            myprofile_female.isChecked = true
            gender = "Female"
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

    //this will show current user profile
    fun ShowProfile() {


        myprofile_Name.setText(getDefaults("username", this))
        myprofile_Email.setText(getDefaults("useremail", this))

        if (getDefaults("userGender", this) == "Female") {
            myprofile_female.isChecked = true;
            myprofile_female.isEnabled = false
            myprofile_male.isEnabled = false

        } else {
            myprofile_male.isChecked = true
            myprofile_male.isEnabled = false
            myprofile_male.isEnabled = false
        }

    }

    override fun onBackPressed() {
        //super.onBackPressed()
        finish()
    }
}