package api

import com.google.gson.JsonArray
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * created by : chandan kumar
 */
interface Api {
    @GET(".")
    fun fetchJSONInfo(): Observable<JsonArray>

}