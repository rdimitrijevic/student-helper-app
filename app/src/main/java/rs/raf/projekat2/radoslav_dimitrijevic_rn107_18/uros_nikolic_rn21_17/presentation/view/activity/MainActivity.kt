package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.activity

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.R
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.recycler.adapter.MainPagerAdapter

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        init()

    }

    fun init() {
        pager.adapter = MainPagerAdapter(supportFragmentManager)

        nav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_schedule -> {
                    pager.currentItem = 0
                    true
                }
                R.id.tab_notes -> {
                    pager.currentItem = 1
                    true
                }
                else -> {
                    pager.currentItem = 1
                    true
                }
            }
        }
    }

}