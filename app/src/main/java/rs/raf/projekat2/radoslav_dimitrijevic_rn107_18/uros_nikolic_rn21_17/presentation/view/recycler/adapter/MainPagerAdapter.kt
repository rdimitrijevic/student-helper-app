package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.recycler.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.fragment.NotesFragment
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.fragment.ScheduleFragment

class MainPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 2
        const val FRAGMENT_1 = 0
        const val FRAGMENT_2 = 1
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            FRAGMENT_1 -> ScheduleFragment()
            FRAGMENT_2 -> NotesFragment()
            else -> NotesFragment()
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            FRAGMENT_1 -> ""
            FRAGMENT_2 -> ""
            else -> ""
        }
    }

}