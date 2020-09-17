package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.repositories.users

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.Resource
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.User

interface UserRepository {

    fun setUser(user: User): Completable
    fun getUser(): Observable<Resource<User>>
    fun isLoggedIn(): Observable<Resource<User>>
}