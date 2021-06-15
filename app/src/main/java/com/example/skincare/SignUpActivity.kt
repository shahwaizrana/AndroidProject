package com.example.skincare

import android.Manifest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import com.example.skincare.javamailapi.GMailSender
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*

class SignUpActivity : AppCompatActivity() {


    var sex: String? = null;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            1
        )

        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)



        //it will check the gender of User.
        checkSex();


        val verificationCodeNumber: String = getRandomNumberString().toString();

        signup_register.setOnClickListener(View.OnClickListener {
            signup_button_tv.visibility=View.GONE;
            button_progress_bar_layout.visibility=View.VISIBLE
            if (validateFields())
            {



                setDefaults("name", userName.text.toString(), this);
                setDefaults("email", signup_email.text.toString(), this);
                setDefaults("password", signup_password.text.toString(), this);
                setDefaults("gender", sex.toString(), this);
                setDefaults("verificationCode", verificationCodeNumber, this);
                sendVerificationCodeEmail(verificationCodeNumber);
            }
        })
    }

    //this will send verification code to the corresponding email for signup
    fun sendVerificationCodeEmail(verificationCode: String) {

        val emailSender: String = "wolfberrry@gmail.com";
        val to = signup_email.text.toString();
        val subject = "Email Verification";

        val message =
            "Hi" + userName.text.toString() + ",\n" +
                    "\n" +
                    "Your account is almost set up. Please enter this verification code " + verificationCode + " to verify your email.\n" +
                    "\n" +
                    "Happy coding,\n" +
                    "WolfBerry"


        try {
            val sender = GMailSender(emailSender, "wolfberry@242")
            val flag = sender.sendMail(
                subject,
                message,
                emailSender,
                to
            )
            if (flag) {
                Toast.makeText(applicationContext, "Check your email and enter code.", Toast.LENGTH_LONG).show();
                button_progress_bar_layout.visibility=View.GONE
                signup_button_tv.visibility=View.VISIBLE
                startActivity(Intent(this@SignUpActivity, VerifyEmailActivity::class.java));
                finish()
                return;
            }
            button_progress_bar_layout.visibility=View.GONE
            signup_button_tv.visibility=View.VISIBLE
            Toast.makeText(applicationContext, "Wrong Email", Toast.LENGTH_SHORT).show();


        } catch (e: Exception) {
            Log.e("SendMail", e.message, e)
        }

    }

    //this will generate 6 digit random number
    fun getRandomNumberString(): String? {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        val rnd = Random()
        val number: Int = rnd.nextInt(999999)

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number)
    }
    //shared preference to set record of current user for short time purpose
    fun setDefaults(key: String, value: String, context: Context) {
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preference.edit()
        editor.putString(key, value)
        editor.apply()
    }


// it will validate records
    fun validateFields(): Boolean {
        if (userName.text.toString().isEmpty()) {
            signup_register.isEnabled = true

            Toast.makeText(this, "Please Enter UserName", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (signup_email.text.toString().isEmpty()) {
            signup_register.isEnabled = true

            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (signup_password.text.toString().isEmpty()) {
            signup_register.isEnabled = true

            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


//check gender
    fun checkSex() {
        checkmale.setOnClickListener {
            sex = "Male"
        }
        checkfemale.setOnClickListener {
            sex = "Female"
        }

        if (checkmale.isChecked) {
            sex = "Male";
        } else {
            sex = "Female";
        }
    }
}