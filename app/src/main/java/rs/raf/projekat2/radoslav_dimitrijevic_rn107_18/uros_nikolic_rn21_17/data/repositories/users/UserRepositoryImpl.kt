package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.repositories.users

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.datasources.local.user.UserDataSource
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.Resource
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.auth.UserData
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.User

class UserRepositoryImpl(private val dataSource: UserDataSource) : UserRepository {


    override fun setUser(user: User): Completable {
        return dataSource.setUserData(
            UserData(user.id, user.username)
        )
    }

    override fun getUser(): Observable<Resource<User>> {
        return dataSource.getUserData().map {
            Resource.Success(
                User(it.id, it.username)
            )
        }
    }

    override fun isLoggedIn(): Observable<Resource<User>> {
        return dataSource.isLoggedIn().map {
            Resource.Success(
                User(it.id, it.username)
            )
        }
    }


}