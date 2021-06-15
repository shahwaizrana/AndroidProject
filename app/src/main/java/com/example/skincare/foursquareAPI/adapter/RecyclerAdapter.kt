package foursquare.places.api.adapter


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.skincare.R
import com.example.skincare.foursquareAPI.activities.RouteDrawActivity
import com.mapbox.geojson.Point

import foursquare.places.api.modules.PlacesModel


class RecyclerAdapter(
    private val context: Activity,
    private var arrayList: List<PlacesModel>
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

//    var data = listOf<PlacesModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.nearby_places_itemview, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.name.text = arrayList.get(position).name
        holder.address.text = "Address " + arrayList.get(position).address
        holder.city.text = "City " + arrayList.get(position).city
        holder.country.text = "Country " + arrayList.get(position).country
        holder.distance.text = "Distance " + arrayList.get(position).distance+" meter";
        if (arrayList.get(position).address == null) {
            holder.address.text = "Address: Not Available"
        }
        if (arrayList.get(position).city == null) {
            holder.city.text = "City: Not Available"
        }

        if (arrayList.get(position).country == null) {
            holder.country.text = "Country: Not Available"
        }

        if (arrayList.get(position).distance == null) {
            holder.distance.text = "Distance: Not Available"
        }


        holder.itemView.setOnClickListener(View.OnClickListener {


            if (arrayList.get(position).lat != null && arrayList.get(position).lng != null) {
                val intent = Intent(context, RouteDrawActivity::class.java);
                intent.putExtra(
                    "latitude",
                    arrayList.get(position).lat.toString()
                );
                intent.putExtra(
                    "longitude",
                    arrayList.get(position).lng.toString()
                );

                context.startActivity(intent);
            }

        })

    }


    open class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var name: TextView;
        var address: TextView;
        var city: TextView;
        var country: TextView;
        var distance: TextView;
//        var temperature: TextView


        init {


            name = itemView.findViewById(R.id.places_item_title)
            address = itemView.findViewById(R.id.places_address)
            city = itemView.findViewById(R.id.places_city);
            country = itemView.findViewById(R.id.places_country)
            distance = itemView.findViewById(R.id.places_distance)
//            temperature = itemView.findViewById(R.id.temperature)
        }
    }
}
