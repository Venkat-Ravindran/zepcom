package repository

import com.google.gson.JsonArray
import interfaces.IRepository
import io.reactivex.Observable

interface IJSONRepository : IRepository {
    fun getJSONHistory(): Observable<JsonArray>
}