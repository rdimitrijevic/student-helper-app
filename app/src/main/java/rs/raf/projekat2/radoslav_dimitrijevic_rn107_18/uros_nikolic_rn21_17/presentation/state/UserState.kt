package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.state

import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.User

sealed class UserState {
    data class LoggedIn(val user: User) : UserState()
    object LoggedOut : UserState()
    data class Error(val err: String) : UserState()

}