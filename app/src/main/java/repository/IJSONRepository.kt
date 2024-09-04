package repository

import com.google.gson.JsonObject
import interfaces.IRepository
import io.reactivex.Observable

/**
 * create by chandan kumar on 5/13/2022.
 */
interface IJSONRepository : IRepository {
    fun getJSONHistory(): Observable<JsonObject>
}