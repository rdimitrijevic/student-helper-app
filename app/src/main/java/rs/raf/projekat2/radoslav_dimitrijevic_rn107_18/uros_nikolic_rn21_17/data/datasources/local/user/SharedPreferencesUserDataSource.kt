package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.datasources.local.user

import android.content.SharedPreferences
import androidx.core.content.edit
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.auth.UserData

class SharedPreferencesUserDataSource(
    private val sharedPreferences: SharedPreferences
) :
    UserDataSource {

    companion object {
        const val USERNAME_KEY = "USERNAME"
        const val LOGGED_IN_KEY = "LOGGED_IN"
        const val USER_ID = "USER_ID"
    }

    override fun setUserData(user: UserData): Completable {
        return Completable.fromCallable {
            sharedPreferences.edit {
                this.putBoolean(LOGGED_IN_KEY, true)
                this.putLong(USER_ID, user.id)
                this.putString(USERNAME_KEY, user.username)
                this.apply()
            }
        }
    }

    override fun getUserData(): Observable<UserData> {
        return Observable.fromCallable {
            if (!sharedPreferences.getBoolean(LOGGED_IN_KEY, false)) {
                throw Exception("User not logged in.")
            }
            val id = sharedPreferences.getLong(USER_ID, 0)
            val name = sharedPreferences.getString(USERNAME_KEY, "") ?: ""
            UserData(id, name)
        }
    }

    override fun isLoggedIn(): Observable<UserData> {
        return Observable.fromCallable {
            if (sharedPreferences.getBoolean(LOGGED_IN_KEY, false)) {
                val id = sharedPreferences.getLong(USER_ID, 0)
                val name = sharedPreferences.getString(USERNAME_KEY, "") ?: ""
                return@fromCallable UserData(id, name)
            }
            throw Exception("Not logged in.")

        }
    }
}