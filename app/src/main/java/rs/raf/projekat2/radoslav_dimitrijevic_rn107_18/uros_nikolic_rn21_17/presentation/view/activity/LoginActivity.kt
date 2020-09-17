package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.R
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.User
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.contract.UserContract
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.state.UserState
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.viewmodel.UserViewModel
import timber.log.Timber

class LoginActivity : AppCompatActivity(R.layout.activity_login) {

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
                is UserState.Error -> {
                    Toast.makeText(this, it.err, Toast.LENGTH_LONG).show()
                    Timber.e(String.format("!!!LoginActivity || Error below !!!\n%s", it.err))
                }
            }
        })
        init()

    }

    private fun init() {
        btn_login.setOnClickListener {
            if( name.text.isNullOrEmpty()
                || last_name.text.isNullOrEmpty()
                || pin.text.isNullOrEmpty()) {

                Toast.makeText(
                    this,
                    getString(R.string.edit_text_err_msg),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (pin.text.toString() != "1111") {
                Toast.makeText(
                    this,
                    getString(R.string.wrong_pin_err_msg),
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }
            userViewModel.login(User(0xdeadf00d, name.text.toString()))
        }
    }

}