package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.R
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.contract.UserContract
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.state.UserState
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.viewmodel.UserViewModel

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    private val userViewModel: UserContract.ViewModel by viewModel<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        userViewModel.userState.observe(this, Observer {
            when (it) {
                is UserState.LoggedIn -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is UserState.LoggedOut -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        })

        userViewModel.isLoggedIn()
    }
}