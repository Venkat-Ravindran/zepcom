package zapcom.venkat.assignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    protected var showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isShowLoading: LiveData<Boolean>
        get() {
            return showLoading
        }
    protected var progressDialog: MutableLiveData<Boolean> = MutableLiveData()
    val isProgressDialog: LiveData<Boolean>
        get() {
            return progressDialog
        }

    protected var errorMessage: MutableLiveData<String> = MutableLiveData()
    val getErrorMessage: LiveData<String>
        get() {
            return errorMessage
        }

    protected var errorMessageForToast: MutableLiveData<String> = MutableLiveData()
    val getErrorMessageForToast: LiveData<String>
        get() {
            return errorMessageForToast
        }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun clearDisposables() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        clearDisposables()
    }

    fun extractErrorMessage(throwable: Throwable) : String?{
        return  throwable.message
    }
}
