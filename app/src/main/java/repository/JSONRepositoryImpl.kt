package repository

import androidx.lifecycle.MutableLiveData
import api.Api
import com.google.gson.JsonArray

/**
 * create by chandan kumar on 5/13/2022.
 */

class JSONRepositoryImpl(private val api: Api) : IJSONRepository {

    override fun getJSONHistory(): MutableLiveData<JsonArray> {
        return api.fetchJSONInfo()
    }
}
