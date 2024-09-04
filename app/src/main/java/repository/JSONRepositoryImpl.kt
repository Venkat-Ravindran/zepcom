package repository

import api.Api
import com.google.gson.JsonObject
import io.reactivex.Observable

/**
 * create by chandan kumar on 5/13/2022.
 */

class JSONRepositoryImpl(private val api: Api) : IJSONRepository {

    override fun getJSONHistory(): Observable<JsonObject> {
        return api.fetchJSONInfo()
    }
}
