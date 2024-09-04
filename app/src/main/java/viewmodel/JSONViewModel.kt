package viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
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

    private var jsonHistoryList: MutableLiveData<JsonObject> = MutableLiveData()
    val getJSONHistoryList: LiveData<JsonObject>
        get() {
            return jsonHistoryList
        }

    fun fetchJSONHistoryList() {
        showLoading.value = true
        addDisposable(
            repository.getJSONHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(
                    AndroidSchedulers.mainThread()
                ).subscribe({
                    showLoading.value = false
                    if (!it.isJsonObject) {
                        noJSONHistory.value = true
                    } else {
                        jsonHistoryList.value = it
                    }
                }, {
                    showLoading.value = false
                    errorMessage.value = extractErrorMessage(it)
                })
        )
    }
}