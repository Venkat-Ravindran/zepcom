package viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import repository.IJSONRepository
import zapcom.venkat.assignment.BaseViewModel


class JSONViewModel(private var repository: IJSONRepository) : BaseViewModel() {

    private var noJSONHistory: MutableLiveData<Boolean> = MutableLiveData()
    val hasNoJSONHistory: LiveData<Boolean>
        get() {
            return noJSONHistory
        }

    private var jsonHistoryList: MutableLiveData<JsonArray> = MutableLiveData()
    val getJSONHistoryList: LiveData<JsonArray>
        get() {
            return jsonHistoryList
        }

    fun fetchJSONHistoryList(): MutableLiveData<JsonArray> {
//        addDisposable(
//            repository.getJSONHistory()
//                .subscribeOn(Schedulers.io())
//                .subscribe({
//                    if (!it.isJsonArray) {
//                        noJSONHistory.value = true
//                    } else {
//                        jsonHistoryList.value = it
//                    }
//                }, {
//                    errorMessage.value = extractErrorMessage(it)
//                })
//        )
        val jsonArray: MutableLiveData<JsonArray> = repository.getJSONHistory()
        return jsonArray
    }
}