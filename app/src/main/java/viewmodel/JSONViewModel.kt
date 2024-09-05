package viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

    fun fetchJSONHistoryList() {
        addDisposable(
            repository.getJSONHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(
                    AndroidSchedulers.mainThread()
                )
                .subscribe({
                    if (!it.isJsonArray) {
                        noJSONHistory.value = true
                    } else {
                        jsonHistoryList.value = it
                    }
                }, {
                    errorMessage.value = extractErrorMessage(it)
                })
        )
    }
}