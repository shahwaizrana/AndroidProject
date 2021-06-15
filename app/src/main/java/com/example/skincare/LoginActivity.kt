package com.example.skincare

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        // Initialize Firebase Auth
        auth = Firebase.auth;

        //Login button from login.xml
        login_button.setOnClickListener(View.OnClickListener {
            login_button.isEnabled = false;
            signin_button_tv.visibility=View.GONE
            login_button_progress_bar_layout.visibility = View.VISIBLE;
            performSignIn();
        });

    }

    /**
     * This function will perform sign in using firebase
     */
    fun performSignIn() {
        val emailPattern: String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        val userEmail = login_email.text.toString();
        val userPassword = login_pass.text.toString();

        if (userEmail.isEmpty() || userEmail == null || userPassword.isEmpty() || userPassword.isEmpty()) {
            login_button.isEnabled = true;
            signin_button_tv.visibility=View.VISIBLE
            login_button_progress_bar_layout.visibility = View.GONE;
            Toast.makeText(this, "Please Enter Text in Email/Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!userEmail.trim().matches(emailPattern.toRegex())) {
            signin_button_tv.visibility=View.VISIBLE
            login_button_progress_bar_layout.visibility = View.GONE;
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {


                    signin_button_tv.visibility=View.VISIBLE
                    login_button_progress_bar_layout.visibility = View.GONE;

                    login_button.isEnabled = true;
                    Toast.makeText(baseContext, "Login Successfully", Toast.LENGTH_SHORT).show();
                    val user = auth.currentUser
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish();

                } else {



                    signin_button_tv.visibility=View.VISIBLE
                    login_button_progress_bar_layout.visibility = View.GONE;
                    login_button.isEnabled = true;
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            }
            .addOnFailureListener(this) {


                signin_button_tv.visibility=View.VISIBLE
                login_button_progress_bar_layout.visibility = View.GONE;
                login_button.isEnabled = true;
                Toast.makeText(baseContext, "Please Try Again", Toast.LENGTH_SHORT).show();
            }


    }



}