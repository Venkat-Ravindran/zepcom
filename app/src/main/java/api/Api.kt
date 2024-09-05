package api

import com.google.gson.JsonArray
import io.reactivex.Observable
import retrofit2.http.GET

interface Api {
    @GET(".")
    fun fetchJSONInfo(): Observable<JsonArray>

}