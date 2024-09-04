package api

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import retrofit2.http.GET

/**
 * created by : chandan kumar
 */
interface Api {
    @GET(".")
    fun fetchJSONInfo(): MutableLiveData<JsonArray>

}