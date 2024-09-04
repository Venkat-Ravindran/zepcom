package api

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * created by : chandan kumar
 */
interface Api {
    @GET()
    fun fetchJSONInfo(): Observable<JsonObject>

}