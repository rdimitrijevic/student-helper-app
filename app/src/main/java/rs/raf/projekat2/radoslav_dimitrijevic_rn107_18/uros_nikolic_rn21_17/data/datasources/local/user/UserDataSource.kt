package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.datasources.local.user

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.auth.UserData

interface UserDataSource {

    fun getUserData(): Observable<UserData>
    fun setUserData(user: UserData): Completable
    fun isLoggedIn(): Observable<UserData>

}