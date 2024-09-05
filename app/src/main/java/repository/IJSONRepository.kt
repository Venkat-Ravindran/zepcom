package repository

import com.google.gson.JsonArray
import interfaces.IRepository
import io.reactivex.Observable

/**
 * create by chandan kumar on 5/13/2022.
 */
interface IJSONRepository : IRepository {
    fun getJSONHistory(): Observable<JsonArray>
}