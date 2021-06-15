package com.example.skincare

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.skincare.Constants.Constant
import com.example.skincare.Fragments.FragmentActivity
import kotlinx.android.synthetic.main.activity_more_info.*

class MoreInfoActivity : AppCompatActivity() {

    lateinit var cardHome: CardView;
    lateinit var cardMedi: CardView;
    lateinit var result_text: TextView;

    lateinit var cures: Button;
    lateinit var btn_sym: Button;
    var flag = false;

    var str: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_info)


        val intent = intent
        //  String str = intent.getStringExtra("class");
        //  String str = intent.getStringExtra("class");
        str = intent.getStringExtra("class")
        //Bindings
        //Bindings
        cures = findViewById(R.id.btn_cure)
        cardHome = findViewById(R.id.card_cures)
        cardMedi = findViewById<CardView>(R.id.card_medications)
        result_text = findViewById(R.id.result_text)
        result_text.setText(str.toString())


        fragments.setOnClickListener(View.OnClickListener {

            val intent = Intent(this@MoreInfoActivity, FragmentActivity::class.java)

            startActivity(intent)
        })


        btn_sym = findViewById<Button>(R.id.btn_sym)

//based on syptoms it will generate result
        btn_sym.setOnClickListener(View.OnClickListener {
            checkBox1.setVisibility(View.VISIBLE)
            checkbox2.setVisibility(View.VISIBLE)
            checkbox3.setVisibility(View.VISIBLE)
            checkbox4.setVisibility(View.VISIBLE)
            checktxt1.setVisibility(View.VISIBLE)
            checktxt2.setVisibility(View.VISIBLE)
            checktxt3.setVisibility(View.VISIBLE)
            checktxt4.setVisibility(View.VISIBLE)
            if (str == Constant.CYST) {
                checktxt1.setText("Deep in skin? ")
                checktxt2.setText("Burstable? ")
                checktxt3.setText("Etching? ")
                checktxt4.setText("Hormonal issue? ")
            } else if (str == Constant.NODULES) {
                checktxt1.setText("Lumps under skin? ")
                checktxt2.setText("Hard lumps?  ")
                checktxt3.setText("Circum scribed in shape?  ")
                checktxt4.setText("Sense of extra mass on skin?  ")
            } else if (str == Constant.SCARS) {
                checktxt1.setText("Deep in skin? ")
                checktxt2.setText("Painful ")
                checktxt3.setText("Redness ")
                checktxt4.setText("Etching? ")
            } else if (str == Constant.WHITE_HEADS) {
                checktxt1.setText("Blocked Pores?  ")
                checktxt2.setText("Genetic Issue?  ")
                checktxt3.setText("White head bumps? ")
                checktxt4.setText("Any family member have this?  ")
            } else if (str == Constant.POSTULES) {
                checktxt1.setText("Leisons are red with white heads? ")
                checktxt2.setText("Skin inflammation?  ")
                checktxt3.setText("Painful to touch?  ")
                checktxt4.setText("Flat or raised ?  ")
            } else if (str == Constant.CLEAR_SKIN) {
                Toast.makeText(
                    this@MoreInfoActivity,
                    "Your Skin is Normal you don't have Any disease ",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                cures.setVisibility(View.GONE)
            }
        })


//This will show result according to the disease
        cures.setOnClickListener(View.OnClickListener {
            if (!flag) {
                //      cardHome.setVisibility(View.VISIBLE);
                //       cardMedi.setVisibility(View.VISIBLE);
                //       isVisible = true;
                if (str == Constant.CYST) {
                    if (checkBox1.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_home.setText("Mix a crushed aspirin tablet with water to make a paste and applying this to a cystic pimple")
                        cure_medi.setText("Excision is recommended")
                    }
                    if (checkbox2.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Wound Toilet and Cleansing under Aseptic measures associated with other measures.
                                Cap Cephradine 500mg (1+1+1)
                                
                                """.trimIndent()
                        )
                        cure_home.setText(
                            "Mixing small amounts of water with turmeric powder creates a thick paste." +
                                    " Apply this paste directly to the cystic acne and leave it in place for about 45 minutes"
                        )
                    }
                    if (checkbox3.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Apply Clobevate Cream. Twice a day.
                                
                                Take Antihistamine drugs.
                                
                                """.trimIndent()
                        )
                        cure_home.setText("Rubbing an ice cube on the cystic acne spot until the cold becomes uncomfortable. Do this three times daily.")
                    }
                    if (checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """ Advice to reduce weight and use oil free diet.
 Refer to Endocrionologist.
"""
                        )
                        cure_home.setText(
                            "Get probiotics by eating yogurt, veggies, and other foods said to contain healthy bacteria." +
                                    " It will give you clearer skin. Use oil-free diet. " +
                                    "Drink plenty of water."
                        )
                    }
                    if (checkBox1.isChecked() && checkbox2.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """ Excision is recommended.
Wound Toilet and Cleansing under Aseptic measures associated with other measures.
Cap Cephradine 500mg (1+1+1)
"""
                        )
                        cure_home.setText(
                            """
                                Mix a crushed aspirin tablet with water to make a paste and applying this to a cystic pimple
                                Mixing small amounts of water with turmeric powder creates a thick paste. Apply this paste directly to the cystic acne and leave it in place for about 45 minutes.
                                
                                """.trimIndent()
                        )
                    }
                    if (checkBox1.isChecked() && checkbox3.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """ Excision is recommended.
Apply Clobevate Cream. Twice a day.
Take Antihistamine drugs.
"""
                        )
                        cure_home.setText(
                            """
                                Mix a crushed aspirin tablet with water to make a paste and applying this to a cystic pimple.
                                Rubbing an ice cube on the cystic acne spot until the cold becomes uncomfortable. Do this three times daily.
                                
                                
                                """.trimIndent()
                        )
                    }
                    if (checkBox1.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """Excision is recommended.
 Advice to reduce weight and use oil free diet.
 Refer to Endocrionologist.
"""
                        )
                        cure_home.setText(
                            """
                                Mix a crushed aspirin tablet with water to make a paste and applying this to a cystic pimple.
                                Get probiotics by eating yogurt, veggies, and other foods said to contain healthy bacteria. It will give you clearer skin. Use oil-free diet. Drink plenty of water
                                
                                """.trimIndent()
                        )
                    }
                    if (checkbox2.isChecked() && checkbox3.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Wound Toilet and Cleansing under Aseptic measures associated with other measures.
                                Cap Cephradine 500mg (1+1+1)
                                Apply Clobevate Cream. Twice a day.
                                Take Antihistamine drugs.
                                
                                """.trimIndent()
                        )
                        cure_home.setText(
                            """
                                Mixing small amounts of water with turmeric powder creates a thick paste. Apply this paste directly to the cystic acne and leave it in place for about 45 minutes.
                                Rubbing an ice cube on the cystic acne spot until the cold becomes uncomfortable. Do this three times daily.
                                
                                """.trimIndent()
                        )
                    }
                    if (checkbox2.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """Wound Toilet and Cleansing under Aseptic measures associated with other measures.
Cap Cephradine 500mg (1+1+1)
Advice to reduce weight and use oil free diet.
 Refer to Endocrionologist
"""
                        )
                        cure_home.setText(
                            """
                                Mixing small amounts of water with turmeric powder creates a thick paste. Apply this paste directly to the cystic acne and leave it in place for about 45 minutes.
                                Get probiotics by eating yogurt, veggies, and other foods said to contain healthy bacteria. It will give you clearer skin. Use oil-free diet. Drink plenty of water.
                                
                                """.trimIndent()
                        )
                    }
                    if (checkbox3.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """Apply Clobevate Cream. Twice a day.
Take Antihistamine drugs.
 Advice to reduce weight and use oil free diet.
 Refer to Endocrionologist.
"""
                        )
                        cure_home.setText(
                            """
                                Rubbing an ice cube on the cystic acne spot until the cold becomes uncomfortable. Do this three times daily.
                                Get probiotics by eating yogurt, veggies, and other foods said to contain healthy bacteria. It will give you clearer skin. Use oil-free diet. Drink plenty of water.
                                
                                """.trimIndent()
                        )
                    }
                    if (checkBox1.isChecked() && checkbox2.isChecked() && checkbox3.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Clindamycin gel ( apply on skin in day or night)
                                Augmentin Capsules 500 mg for 5 days
                                
                                """.trimIndent()
                        )
                        cure_home.setText(
                            """
                                Use sunscreen daily.
                                Clean your face gently with cleanser.
                                Reduce Stress.
                                
                                """.trimIndent()
                        )
                    }
                    if (checkBox1.isChecked() == false && checkbox2.isChecked() == false && checkbox3.isChecked() == false && checkbox4.isChecked() == false) {
                        Toast.makeText(
                            this@MoreInfoActivity,
                            "Kindly provide Symptoms ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else if (str == Constant.NODULES) {
                    if (checkBox1.isChecked() && checkbox2.isChecked() && checkbox3.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_home.setText(
                            """
                                Drink excess of water
                                Avoid makeup
                                Avoid use of medicines that contains drugs.
                                
                                """.trimIndent()
                        )
                        cure_medi.setText("Surgical Excision By dermatologist.")
                    }
                    if (checkBox1.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_home.setText(
                            "Use Apple cider vinegar as it has many antibacterial properties." +
                                    " Dilute apple cider vinegar with warm water and apply to the affected skin."
                        )
                        cure_medi.setText(
                            """
                                No Rx.
                                Reassurance.
                                Observe for any increase in size/pain.If so, then refer to Dermatologist.
                                
                                """.trimIndent()
                        )
                    }
                    if (checkBox1.isChecked() && checkbox2.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_home.setText(
                            "Use Apple cider vinegar as it has many antibacterial properties." +
                                    " Dilute apple cider vinegar with warm water and apply to the affected skin." +
                                    "Cleanse the skin with olive oil." +
                                    " It prevents clogging and breakouts."
                        )
                        cure_medi.setText(
                            """
                                No Rx.
                                Reassurance.
                                Observe for any increase in size/pain.If so, then refer to Dermatologist.
                                Biopsy and excision is suggested.
                                """.trimIndent()
                        )
                    }
                    if (checkBox1.isChecked() && checkbox3.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_home.setText(
                            """
                                Use Apple cider vinegar as it has many antibacterial properties. Dilute apple cider vinegar with warm water and apply to the affected skin.Do meditation and yoga to reduce stress as stress targets the acne breakouts.
                                
                                """.trimIndent()
                        )
                        cure_medi.setText(
                            """
                                No Rx.
                                Reassurance.
                                Observe for any increase in size/pain.If so, then refer to Dermatologist.
                                If disfigurement in shape then excision otherwise Reassurance and No Rx.
                                """.trimIndent()
                        )
                    }
                    if (checkBox1.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_home.setText(
                            "Use Apple cider vinegar as it has many antibacterial properties." +
                                    " Dilute apple cider vinegar with warm water and apply to the affected skin." +
                                    "Use olive oil daily for cleansing of skin. It will reduce clogging." +
                                    " And do meditation regularly to release stress."
                        )
                        cure_medi.setText(
                            """
                                No Rx.
                                Reassurance.
                                Observe for any increase in size/pain.If so, then refer to Dermatologist.
                                Refer to General Surgical Department
                                """.trimIndent()
                        )
                    }
                    if (checkbox2.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_home.setText("Cleanse the skin with olive oil. It prevents clogging and breakouts.")
                        cure_medi.setText("Biopsy and excision is suggested.")
                    }
                    if (checkbox2.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_home.setText(
                            """
                                Cleanse the skin with olive oil. It prevents clogging and breakouts.Use olive oil daily for cleansing of skin. It will reduce clogging. And do meditation regularly to release stress.
                                
                                """.trimIndent()
                        )
                        cure_medi.setText(
                            """
                                Biopsy and excision is suggested.No Rx.
                                Refer to General Surgical Department
                                
                                """.trimIndent()
                        )
                    }
                    if (checkbox2.isChecked() && checkbox3.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_home.setText(
                            "Cleanse the skin with olive oil. It prevents clogging and breakouts." +
                                    "Do meditation and yoga to reduce stress as stress targets the acne breakouts."
                        )
                        cure_medi.setText(
                            "Biopsy and excision is suggested." +
                                    "If disfigurement in shape then excision otherwise Reassurance and No Rx."
                        )
                    }
                    if (checkbox3.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_home.setText(
                            """
                                Use olive oil daily for cleansing of skin. It will reduce clogging. And do meditation regularly to release stress.
                                Do meditation and yoga to reduce stress as stress targets the acne breakouts.
                                """.trimIndent()
                        )
                        cure_medi.setText(
                            """
                                No Rx.
                                Refer to General Surgical Department
                                If disfigurement in shape then excision otherwise Reassurance and No Rx.
                                """.trimIndent()
                        )
                    }
                    if (checkbox3.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_home.setText("Do meditation and yoga to reduce stress as stress targets the acne breakouts.")
                        cure_medi.setText("If disfigurement in shape then excision otherwise Reassurance and No Rx.")
                    }
                    if (checkbox3.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_home.setText(
                            """
                                Use olive oil daily for cleansing of skin. It will reduce clogging. And do meditation regularly to release stress.
                                
                                """.trimIndent()
                        )
                        cure_medi.setText(
                            """
                                No Rx.
                                Refer to General Surgical Department
                                
                                """.trimIndent()
                        )
                    }
                    if (checkBox1.isChecked() == false && checkbox2.isChecked() == false && checkbox3.isChecked() == false && checkbox4.isChecked() == false) {
                        Toast.makeText(
                            this@MoreInfoActivity,
                            "Kindly provide Symptoms ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else if (str == Constant.SCARS) {
                    if (checkBox1.isChecked() && checkbox2.isChecked() && checkbox3.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_home.setText(
                            """
                                Use coconut oil
                                Use shea butter
                                Use aloe vera gel
                                Use raw honey
                                Use baking soda
                                Use lemon juice
                                """.trimIndent()
                        )
                        cure_medi.setText(
                            """
                                Mederma Advanced Scar Gel. ...
                                Honeydew Natural Scar Cream
                                """.trimIndent()
                        )
                    }
                    if (checkBox1.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Cap Evion 40 mg (1 daily)
                                Use of Skin Whitening Agent.
                                Laser Treatment is suggested.
                                
                                """.trimIndent()
                        )
                        cure_home.setText("Use aloe Vera gel on your scar.Let it dry and wash with water. Do twice a day.")
                    }
                    if (checkBox1.isChecked() && checkbox2.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """Cap Evion 40 mg (1 daily)
Use of Skin Whitening Agent.
Laser Treatment is suggested.
 Cap Doxy (1+1)
Tab Brufen 400mg. (1+1)
Tab Rigix (1 Daily)
"""
                        )
                        cure_home.setText(
                            "Use aloe Vera gel on your scar.Let it dry and wash with water. Do twice a day." +
                                    "Before going to bed, cover your scar with a layer of honey. Wrap the honey-covered scar with a bandage." +
                                    " Leave it on for one full night." +
                                    " In the morning, remove the bandage and wash off the honey with warm water."
                        )
                    }
                    if (checkBox1.isChecked() && checkbox3.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Cap Evion 40 mg (1 daily)
                                Use of Skin Whitening Agent.
                                Laser Treatment is suggested.
                                Reassurance.
                                Avoid Sweating on that area.
                                
                                """.trimIndent()
                        )
                        cure_home.setText(
                            """
                                Use aloe Vera gel on your scar.Let it dry and wash with water. Do twice a day.Use onion extract to remove redness and scar.
                                
                                
                                """.trimIndent()
                        )
                    }
                    if (checkBox1.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Cap Evion 40 mg (1 daily)
                                Use of Skin Whitening Agent.
                                Laser Treatment is suggested.
                                Use Fusic H cream.
                                Tab Rigix (1 Hs).
                                Take Antihistamince Drugs.
                                
                                """.trimIndent()
                        )
                        cure_home.setText(
                            "Use aloe Vera gel on your scar.Let it dry and wash with water. Do twice a day." +
                                    "Slice a potato into medium thick rounds. Using a circular motion, rub the potato slice on your scar. " +
                                    "Dry it For 10 minutes and rinse with cold water"
                        )
                    }
                    if (checkbox2.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Cap Doxy (1+1)
                                Tab Brufen 400mg. (1+1)
                                Tab Rigix (1 Daily)
                                
                                """.trimIndent()
                        )
                        cure_home.setText(
                            "Before going to bed, cover your scar with a layer of honey. Wrap the honey-covered scar with a bandage. " +
                                    "Leave it on for one full night." +
                                    " In the morning, remove the bandage and wash off the honey with warm water"
                        )
                    }
                    if (checkbox2.isChecked() && checkbox3.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Cap Doxy (1+1)
                                Tab Brufen 400mg. (1+1)
                                Tab Rigix (1 Daily)
                                Reassurance.
                                Avoid Sweating on that area.
                                
                                """.trimIndent()
                        )
                        cure_home.setText(
                            "Before going to bed, cover your scar with a layer of honey. Wrap the honey-covered scar with a bandage. " +
                                    "Leave it on for one full night." +
                                    " In the morning, remove the bandage and wash off the honey with warm water" +
                                    "Use onion extract to remove redness and scar."
                        )
                    }
                    if (checkbox2.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Cap Doxy (1+1)
                                Tab Brufen 400mg. (1+1)
                                Tab Rigix (1 Daily)
                                Use Fusic H cream.
                                Take Antihistamince Drugs.
                                
                                """.trimIndent()
                        )
                        cure_home.setText(
                            """
                                Before going to bed, cover your scar with a layer of honey. Wrap the honey-covered scar with a bandage. Leave it on for one full night. In the morning, remove the bandage and wash off the honey with warm waterSlice a potato into medium thick rounds. Using a circular motion, rub the potato slice on your scar. Dry it For 10 minutes and rinse with cold water.
                                
                                
                                """.trimIndent()
                        )
                    }
                    if (checkbox3.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                No Rx.
                                Reassurance.
                                Avoid Sweating on that area.
                                
                                """.trimIndent()
                        )
                        cure_home.setText(
                            """
                                Use onion extract to remove redness and scar.
                                
                                
                                """.trimIndent()
                        )
                    }
                    if (checkbox4.isChecked() && checkbox3.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                No Rx.
                                Reassurance.
                                Avoid Sweating on that area.
                                Use Fusic H cream.
                                Tab Rigix (1 Hs).
                                Take Antihistamince Drugs
                                
                                """.trimIndent()
                        )
                        cure_home.setText(
                            """
                                Use onion extract to remove redness and scar.
                                Slice a potato into medium thick rounds. Using a circular motion, rub the potato slice on your scar. Dry it For 10 minutes and rinse with cold water.
                                
                                """.trimIndent()
                        )
                    }
                    if (checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Use Fusic H cream.
                                Tab Rigix (1 Hs).
                                Take Antihistamince Drugs
                                
                                """.trimIndent()
                        )
                        cure_home.setText(
                            """
                                Slice a potato into medium thick rounds. Using a circular motion, rub the potato slice on your scar. Dry it For 10 minutes and rinse with cold water.
                                
                                """.trimIndent()
                        )
                    }
                    if (checkBox1.isChecked() == false && checkbox2.isChecked() == false && checkbox3.isChecked() == false && checkbox4.isChecked() == false) {
                        Toast.makeText(
                            this@MoreInfoActivity,
                            "Kindly provide Symptoms ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else if (str == Constant.POSTULES) {
                    if (checkBox1.isChecked() && checkbox2.isChecked() && checkbox3.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_home.setText(
                            """
                                Avoid touching the leisons?
                                Drink excess of water
                                
                                """.trimIndent()
                        )
                        cure_medi.setText("Capsule Velocef (TDS)")
                    }
                    if (checkBox1.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Wash Face with warm water and mild soap containing Salicylic Acid.
                                No Rx. It may simply go away.
                                
                                """.trimIndent()
                        )
                        cure_home.setText(
                            """
                                Mosturize the skin with aloe vera gel. It will give you clear skin and will fight against dead skin cells.
                                Drink half glass of boiled neem water daily.
                                
                                """.trimIndent()
                        )
                    }
                    if (checkBox1.isChecked() && checkbox2.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Wash Face with warm water and mild soap containing Salicylic Acid.
                                No Rx. It may simply go away.
                                Use of Oral Antibiotic Doxycycline/Amoxycillin.
                                """.trimIndent()
                        )
                        cure_home.setText(
                            """
                                Mosturize the skin with aloe vera gel. It will give you clear skin and will fight against dead skin cells.
                                Drink half glass of boiled neem water daily.
                                Steep green tea in boiling water for 3–4 minutes. Allow tea to cool. Using a cotton ball, apply tea to skin or pour into a spray bottle to spritz on. Allow to dry, then rinse with water and pat dry.
                                """.trimIndent()
                        )
                    }
                    if (checkBox1.isChecked() && checkbox3.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Wash Face with warm water and mild soap containing Salicylic Acid.
                                Use Topical Product containing Benzyperoxide cream.
                                Topical Antibiotic Dapsone (1 Hs). 
                                
                                """.trimIndent()
                        )
                        cure_home.setText(
                            """Mosturize the skin with aloe vera gel. It will give you clear skin and will fight against dead skin cells.
Drink half glass of boiled neem water daily.
 Put an ice cube in a paper towel or clean wash cloth and hold it on the affected area for up to 10 minutes. It will give soothing effect to your skin."""
                        )
                    }
                    if (checkBox1.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Wash Face with warm water and mild soap containing Salicylic Acid.
                                No Rx. It may simply go away.
                                Use Clindamycin Gel. Twice a day.
                                """.trimIndent()
                        )
                        cure_home.setText(
                            """
                                Mosturize the skin with aloe vera gel. It will give you clear skin and will fight against dead skin cells.
                                Drink half glass of boiled neem water daily.
                                Mix 2 tablespoons honey and 1 teaspoon cinnamon together to form a paste. After cleansing, apply the mask to your face and leave it on for 10–15 minutes. Rinse the mask off with water.
                                
                                
                                """.trimIndent()
                        )
                    }
                    if (checkbox2.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText("Use of Oral Antibiotic Doxycycline/Amoxycillin.")
                        cure_home.setText(
                            "Steep green tea in boiling water for 3–4 minutes." +
                                    " Allow tea to cool. Using a cotton ball, apply tea to skin or pour into a spray bottle to spritz on." +
                                    " Allow to dry, then rinse with water and pat dry."
                        )
                    }
                    if (checkbox2.isChecked() && checkbox3.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Use of Oral Antibiotic Doxycycline/Amoxycillin.Use Topical Product containing Benzyperoxide cream.
                                Topical Antibiotic Dapsone (1 Hs).
                                
                                """.trimIndent()
                        )
                        cure_home.setText(
                            "Steep green tea in boiling water for 3–4 minutes." +
                                    " Allow tea to cool. Using a cotton ball, apply tea to skin or pour into a spray bottle to spritz on." +
                                    " Allow to dry, then rinse with water and pat dry." +
                                    " Put an ice cube in a paper towel or clean wash cloth and hold it on the affected area for up to 10 minutes." +
                                    " It will give soothing effect to your skin."
                        )
                    }
                    if (checkbox2.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            "Use of Oral Antibiotic Doxycycline/Amoxycillin." +
                                    "Use Clindamycin Gel. Twice a day"
                        )
                        cure_home.setText(
                            "Steep green tea in boiling water for 3–4 minutes." +
                                    " Allow tea to cool. Using a cotton ball, apply tea to skin or pour into a spray bottle to spritz on." +
                                    " Allow to dry, then rinse with water and pat dry." +
                                    "Mix 2 tablespoons honey and 1 teaspoon cinnamon together to form a paste." +
                                    " After cleansing, apply the mask to your face and leave it on for 10–15 minutes. Rinse the mask off with water."
                        )
                    }
                    if (checkbox3.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Use Topical Product containing Benzyperoxide cream.
                                Topical Antibiotic Dapsone (1 Hs).
                                
                                """.trimIndent()
                        )
                        cure_home.setText(
                            " Put an ice cube in a paper towel or clean wash cloth and hold it on the affected area for up to 10 minutes." +
                                    " It will give soothing effect to your skin."
                        )
                    }
                    if (checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText("Use Clindamycin Gel. Twice a day")
                        cure_home.setText(
                            "Mix 2 tablespoons honey and 1 teaspoon cinnamon together to form a paste. " +
                                    "After cleansing, apply the mask to your face and leave it on for 10–15 minutes. " +
                                    "Rinse the mask off with water."
                        )
                    }
                    if (checkbox3.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                Use Topical Product containing Benzyperoxide cream.
                                Topical Antibiotic Dapsone (1 Hs).
                                Use Clindamycin Gel. Twice a day
                                """.trimIndent()
                        )
                        cure_home.setText(
                            " Put an ice cube in a paper towel or clean wash cloth and hold it on the affected area for up to 10 minutes. " +
                                    "It will give soothing effect to your skin." +
                                    "Mix 2 tablespoons honey and 1 teaspoon cinnamon together to form a paste. " +
                                    "After cleansing, apply the mask to your face and leave it on for 10–15 minutes. Rinse the mask off with water."
                        )
                    }
                    if (checkBox1.isChecked() == false && checkbox2.isChecked() == false && checkbox3.isChecked() == false && checkbox4.isChecked() == false) {
                        Toast.makeText(
                            this@MoreInfoActivity,
                            "Kindly provide Symptoms ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else if (str == Constant.WHITE_HEADS) {
                    if (checkBox1.isChecked() && checkbox2.isChecked() && checkbox3.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_home.setText(
                            """
                                Use sunscreens
                                Remove makeup before going to bed.
                                Use oil free cosmetics brands.
                                Use mild cleanser to wash your face with warm water.
                                
                                """.trimIndent()
                        )
                        cure_medi.setText(
                            """
                                Doxy Capsules for 5 days
                                Refeniods CTO Reduce fats for at least one month
                                
                                """.trimIndent()
                        )
                    }
                    if (checkBox1.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """Hot Fomentation
      Use of Local Cleansing agents.
"""
                        )
                        cure_home.setText(
                            "Mix baking soda with water and apply on the skin. " +
                                    "As it is acidic it will unblock all the blocked pores"
                        )
                    }
                    if (checkBox1.isChecked() && checkbox2.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """Hot Fomentation
      Use of Local Cleansing agents.
 Reassurance.
"""
                        )
                        cure_home.setText(
                            "Mix baking soda with water and apply on the skin." +
                                    " As it is acidic it will unblock all the blocked pores" +
                                    "Use wet tea bags and leave them on areas which are affected."
                        )
                    }
                    if (checkBox1.isChecked() && checkbox3.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """Hot Fomentation
      Use of Local Cleansing agents.
Follow dermatological procedures like Laser, excision.
      Take plenty of Fluids.
      Take good Diet.
"""
                        )
                        cure_home.setText(
                            "Mix baking soda with water and apply on the skin." +
                                    " As it is acidic it will unblock all the blocked pores" +
                                    " Take an egg and separate the yolk from the white. Now, whisk the white till it's nice and frothy. " +
                                    "Apply on your face and let it be for about 20 minutes or till it's dry. Wash off with cold water."
                        )
                    }
                    if (checkBox1.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """Hot Fomentation
      Use of Local Cleansing agents.
Avoid substandard cosmetics.
Stop using drugs that are of local and substandard brand.
"""
                        )
                        cure_home.setText(
                            """
                                Mix baking soda with water and apply on the skin. As it is acidic it will unblock all the blocked poresTake 3 spoons of besan, mix some ground green chilka dal, raw milk and a few drops of lemons. Rub this on your face for 6-7 minutes, leave it on for a while and then wash off. 
                                Also drink neem water daily to get effective results in days.
                                
                                """.trimIndent()
                        )
                    }
                    if (checkbox2.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                No Rx.
                                Reassurance
                                
                                """.trimIndent()
                        )
                        cure_home.setText("Use wet tea bags and leave them on areas which are affected.")
                    }
                    if (checkbox2.isChecked() && checkbox3.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """No Rx.
Reassurance
Follow dermatological procedures like Laser, excision.
      Take plenty of Fluids.
      Take good Diet.
"""
                        )
                        cure_home.setText(
                            "Use wet tea bags and leave them on areas which are affected." +
                                    " Take an egg and separate the yolk from the white. Now, whisk the white till it's nice and frothy." +
                                    " Apply on your face and let it be for about 20 minutes or till it's dry. Wash off with cold water."
                        )
                    }
                    if (checkbox2.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """
                                No Rx.
                                Reassurance
                                Avoid substandard cosmetics.
                                Stop using drugs that are of local and substandard brand.
                                
                                """.trimIndent()
                        )
                        cure_home.setText(
                            """
                                Use wet tea bags and leave them on areas which are affected.Take 3 spoons of besan, mix some ground green chilka dal, raw milk and a few drops of lemons. Rub this on your face for 6-7 minutes, leave it on for a while and then wash off. 
                                Also drink neem water daily to get effective results in days.
                                
                                """.trimIndent()
                        )
                    }
                    if (checkbox3.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """Follow dermatological procedures like Laser, excision.
      Take plenty of Fluids.
      Take good Diet.
"""
                        )
                        cure_home.setText(
                            " Take an egg and separate the yolk from the white. Now, whisk the white till it's nice and frothy. " +
                                    "Apply on your face and let it be for about 20 minutes or till it's dry. Wash off with cold water."
                        )
                    }
                    if (checkbox3.isChecked() && checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """Follow dermatological procedures like Laser, excision.
      Take plenty of Fluids.
      Take good Diet.
Avoid substandard cosmetics.
Stop using drugs that are of local and substandard brand.
"""
                        )
                        cure_home.setText(
                            """ Take an egg and separate the yolk from the white. Now, whisk the white till it's nice and frothy. Apply on your face and let it be for about 20 minutes or till it's dry. Wash off with cold water.Take 3 spoons of besan, mix some ground green chilka dal, raw milk and a few drops of lemons. Rub this on your face for 6-7 minutes, leave it on for a while and then wash off. 
Also drink neem water daily to get effective results in days.
"""
                        )
                    }
                    if (checkbox4.isChecked()) {
                        cardHome.setVisibility(View.VISIBLE)
                        cardMedi.setVisibility(View.VISIBLE)
                        flag = true
                        cure_medi.setText(
                            """Avoid substandard cosmetics.
      Stop using drugs that are of local and substandard brand.
"""
                        )
                        cure_home.setText(
                            """
                                Take 3 spoons of besan, mix some ground green chilka dal, raw milk and a few drops of lemons. Rub this on your face for 6-7 minutes, leave it on for a while and then wash off. 
                                Also drink neem water daily to get effective results in days.
                                
                                """.trimIndent()
                        )
                    }
                    if (checkBox1.isChecked() == false && checkbox2.isChecked() == false && checkbox3.isChecked() == false && checkbox4.isChecked() == false) {
                        Toast.makeText(
                            this@MoreInfoActivity,
                            "Kindly provide Symptoms ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else if (str == Constant.CLEAR_SKIN) {
                    Toast.makeText(
                        this@MoreInfoActivity,
                        "Your Skin is Normal you don't have Any disease ",
                        Toast.LENGTH_SHORT
                    ).show()
                    cardHome.setVisibility(View.GONE)
                    cardMedi.setVisibility(View.GONE)
                    checkBox1.setVisibility(View.GONE)
                    checkbox2.setVisibility(View.GONE)
                    checkbox3.setVisibility(View.GONE)
                    checkbox4.setVisibility(View.GONE)
                    checktxt1.setVisibility(View.GONE)
                    checktxt2.setVisibility(View.GONE)
                    checktxt3.setVisibility(View.GONE)
                    checktxt4.setVisibility(View.GONE)
                    btn_sym.setVisibility(View.GONE)
                }
            } else {
                cardHome.setVisibility(View.GONE)
                cardMedi.setVisibility(View.GONE)
                flag = false
            }
        })
    }
}