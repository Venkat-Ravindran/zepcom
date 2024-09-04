package repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import interfaces.IRepository

/**
 * create by chandan kumar on 5/13/2022.
 */
interface IJSONRepository : IRepository {
    fun getJSONHistory(): MutableLiveData<JsonArray>
}