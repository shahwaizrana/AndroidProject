package com.example.skincare.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.skincare.R
import com.example.skincare.foursquareAPI.activities.NearbyPlacesActivity
import com.example.skincare.order.MyOrders
import com.example.skincare.order.OrderMedicine
import kotlinx.android.synthetic.main.fragment_description.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DescriptionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DescriptionFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var findnearbyClinics: Button;
    lateinit var orderMedicines: Button;
    lateinit var myOrderMedicine:Button;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_description, container, false)

        findnearbyClinics=view.findViewById(R.id.findClinics)
        orderMedicines=view.findViewById(R.id.orderMedicines);
        myOrderMedicine=view.findViewById(R.id.myOrderedMedicines);
        findnearbyClinics.setOnClickListener(View.OnClickListener {

        startActivity(Intent(context, NearbyPlacesActivity::class.java))
        })

        orderMedicines.setOnClickListener(View.OnClickListener {
            startActivity(Intent(context, OrderMedicine::class.java))

        })

        myOrderMedicine.setOnClickListener(View.OnClickListener {
            startActivity(Intent(context, MyOrders::class.java))
        })

        return view;
    }


}