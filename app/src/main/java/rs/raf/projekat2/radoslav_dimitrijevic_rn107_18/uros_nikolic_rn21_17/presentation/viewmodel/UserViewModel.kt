package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.Resource
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.User
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.repositories.users.UserRepository
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.contract.UserContract
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.state.UserState

class UserViewModel(private val repository: UserRepository) : ViewModel(), UserContract.ViewModel {


    override val userState: MutableLiveData<UserState> = MutableLiveData()
    private val subscriptions = CompositeDisposable()

    override fun login(user: User) {
        val subscription = repository
            .setUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    userState.value = UserState.LoggedIn(user)
                },
                {
                    userState.value = UserState.Error(it.toString())
                })
        subscriptions.add(subscription)
    }

    override fun isLoggedIn() {
        val subscription = repository
            .isLoggedIn()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        is Resource.Success -> userState.value = UserState.LoggedIn(it.data)
                        is Resource.Error -> userState.value = UserState.LoggedOut
                    }
                },
                {
                    userState.value = UserState.LoggedOut
                }
            )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }


}