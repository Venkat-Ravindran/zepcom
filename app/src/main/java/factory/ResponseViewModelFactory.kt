package factory

import interfaces.Factory
import repository.IJSONRepository
import viewmodel.JSONViewModel

/**
 * patient history factory class
 */
class ResponseViewModelFactory(userRepository: IJSONRepository) :
    Factory<JSONViewModel> {
    private var mPatientHistoryViewModel: JSONViewModel = JSONViewModel(
        userRepository
    )
    override fun create(): JSONViewModel {
        return mPatientHistoryViewModel
    }
}