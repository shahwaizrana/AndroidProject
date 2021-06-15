package com.example.skincare

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AboutApplicationPage : AppCompatActivity() {
    var about_text: TextView? = null
    var use_text:TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_application_page)

        use_text = findViewById(R.id.use_text)
        about_text = findViewById(R.id.about_text)
        about_text!!.setText(
            "\"Our Application purpose is to detect the type of acne in your face." +
                    "Our Application will also help you by providing best treatment for the type of Acne you have"
        )
        use_text!!.setText(
            """
                You have to provide the picture of your infected area of face.
                The picture must be a clear picture having a full face.
                Application will detect the Face first and then type of acne you have.
                After detection it will tell you type of acne you have and then ask you to enter the Symptoms you have.
                After providing the symptoms it will provide you medication and home remedies for that disease.
                I hope our Application will satisfy you and help you in a better way to deal with the face acne disease
                """.trimIndent()
        )

    }
}