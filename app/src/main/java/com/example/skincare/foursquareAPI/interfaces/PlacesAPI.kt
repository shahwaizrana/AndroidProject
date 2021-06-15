package foursquare.places.api.interfaces


import com.example.skincare.foursquareAPI.modules.NearbyPlace
import retrofit2.Call
import retrofit2.http.Query
import retrofit2.http.GET

public interface PlacesAPI {


    @GET("v2/venues/search?")
    fun getVenues(
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret: String,
        @Query("query") query: String,
        @Query("radius") radius: String,
        @Query("ll") ll: String,
        @Query("v") v: String
    ): Call<NearbyPlace>


    companion object {
        val PLACES_BASE_URL = "https://api.foursquare.com/"
    }
}


