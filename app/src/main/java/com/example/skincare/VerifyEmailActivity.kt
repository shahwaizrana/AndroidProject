package com.example.skincare

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.skincare.modelclasses.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_verify_email.*
import java.util.*

class VerifyEmailActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    private var pDialog: ProgressDialog? = null;
    lateinit var email: String;
    lateinit var name: String;
    lateinit var gender: String;
    lateinit var password: String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_email)

        auth = FirebaseAuth.getInstance();
        //Button Listeners
        clickListeners();
        initializeDialog();
    }

    //this will validate first and signup user
    fun clickListeners() {
        var verificationCodeNumber: Int? = this!!.getDefaults("verificationCode", this)?.toInt();



        verify_email_code_button.setOnClickListener(View.OnClickListener {
            verify_button_progress_bar_layout.visibility=View.VISIBLE
            verify_button_tv.visibility=View.GONE

            if (email_verification_code.text.isEmpty()) {

                verify_button_progress_bar_layout.visibility=View.GONE
                verify_button_tv.visibility=View.VISIBLE
                Toast.makeText(
                    baseContext, "Enter Code here",
                    Toast.LENGTH_SHORT
                ).show()

                return@OnClickListener;
            }
            if (verificationCodeNumber == email_verification_code.text.toString().toInt()) {
                performRegistration();



            } else {
                verify_button_progress_bar_layout.visibility=View.GONE
                verify_button_tv.visibility=View.VISIBLE
                Toast.makeText(
                    baseContext, "You have entered wrong verification Code.Please Try Again.",
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener;
            }

        });



    }


//shared preference to save record for short term purpose
    fun getDefaults(key: String?, context: Context?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(key, null)
    }

    fun initializeDialog() {
        pDialog = ProgressDialog(this);
        pDialog!!.setTitle("Registering");
        pDialog!!.setIcon(R.drawable.authpic);
        pDialog!!.setMessage("Please Wait.........");
        pDialog!!.setCancelable(false);
    }

    //in this method user will get signup using firebase
    fun performRegistration() {
        email = getDefaults("email", applicationContext).toString();
        password = getDefaults("password", applicationContext).toString();
        name = getDefaults("name", applicationContext).toString();
        gender = getDefaults("gender", applicationContext).toString();



        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.e("TAG", "createUserWithEmail:success")
                    verify_button_progress_bar_layout.visibility=View.GONE
                    verify_button_tv.visibility=View.VISIBLE
                    SaveUserToFirebaseDatabase();
                } else {
                    verify_button_progress_bar_layout.visibility=View.GONE
                    verify_button_tv.visibility=View.VISIBLE
                    return@addOnCompleteListener;
                    // If sign in fails, display a message to the user.
                    Log.e("TAG", "createUserWithEmail:failure", task.exception)
                    pDialog!!.dismiss()
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
            .addOnFailureListener {
                pDialog!!.dismiss()
                signup_register.isEnabled = true
                verify_button_progress_bar_layout.visibility=View.GONE
                verify_button_tv.visibility=View.VISIBLE
                Toast.makeText(
                    baseContext,
                    "Failed to create user: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show();
            }

    }

    //in this method user data will store into firebase
    private fun SaveUserToFirebaseDatabase() {
        val uid = FirebaseAuth.getInstance().uid ?: "";
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        //   database = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = UserData(
            auth.uid!!,
            name,
            email,
            gender
        );

        ref.setValue(user)
            .addOnSuccessListener {
                pDialog!!.dismiss()
                verify_button_progress_bar_layout.visibility=View.GONE
                verify_button_tv.visibility=View.VISIBLE
                Toast.makeText(this, "Finally Data Stored", Toast.LENGTH_SHORT).show();
                startActivity(Intent(this@VerifyEmailActivity, MainActivity::class.java));
                finish()
            }
            .addOnFailureListener {
                pDialog!!.dismiss()
                verify_button_progress_bar_layout.visibility=View.GONE
                verify_button_tv.visibility=View.VISIBLE
                Toast.makeText(this, "Data could not store", Toast.LENGTH_SHORT).show();

            }
    }
}