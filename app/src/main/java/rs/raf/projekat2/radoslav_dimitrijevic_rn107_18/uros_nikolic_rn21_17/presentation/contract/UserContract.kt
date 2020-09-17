package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.User
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.state.UserState

interface UserContract {

    interface ViewModel {

        val userState: LiveData<UserState>

        fun login(user: User)
        fun isLoggedIn()
    }
}