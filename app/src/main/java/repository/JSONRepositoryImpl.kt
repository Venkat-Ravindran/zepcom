package repository

import api.Api
import com.google.gson.JsonArray
import io.reactivex.Observable

class JSONRepositoryImpl(private val api: Api) : IJSONRepository {

    override fun getJSONHistory(): Observable<JsonArray> {
        return api.fetchJSONInfo()
    }
}
