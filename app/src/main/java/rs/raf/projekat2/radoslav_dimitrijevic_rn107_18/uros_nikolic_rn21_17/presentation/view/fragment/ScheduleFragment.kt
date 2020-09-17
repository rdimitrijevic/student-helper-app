package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_schedule.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.R
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.room.SubjectFilter
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.contract.SubjectContract
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.state.SubjectState
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.recycler.adapter.SubjectAdapter
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.recycler.diff.SubjectDiffItemCallback
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.viewmodel.SubjectViewModel
import timber.log.Timber

class ScheduleFragment : Fragment(R.layout.fragment_schedule) {

    private val scheduleViewModel: SubjectContract.ViewModel by sharedViewModel<SubjectViewModel> ()
    private lateinit var subjectAdapter: SubjectAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    fun init() {

        val items = listOf(
            "","Ponedeljak", "Utorak", "Sreda",
            "Cetvrtak", "Petak", "Subota", "Nedelja"
        )

        val adapter = ArrayAdapter(requireContext(), R.layout.box_item, items)
        (day_box.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        scheduleViewModel.subjectState.observe(viewLifecycleOwner, Observer {
            renderState(it)
        })

        schedule_rec.layoutManager = LinearLayoutManager(this.context)
        subjectAdapter = SubjectAdapter(SubjectDiffItemCallback())
        schedule_rec.adapter = subjectAdapter

        initListeners()

        scheduleViewModel.getGroups()
        scheduleViewModel.fetchAllSubjects()
        scheduleViewModel.getAllSubjects()


    }

    private fun renderState(state: SubjectState) {
        when (state) {
            is SubjectState.GroupsFetched -> {
                val items = setOf("").plus(state.groups)

                val adapter = ArrayAdapter(requireContext(), R.layout.box_item, items.toTypedArray())
                (group_box.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            }
            is SubjectState.DataFetched -> {
                Toast.makeText(context, "Data updated", Toast.LENGTH_SHORT).show()
                showLoadingState(false)
            }
            is SubjectState.Success -> {
                Timber.e("Subjects success")

                subjectAdapter.submitList(state.subjects)
                showLoadingState(false)
            }
            is SubjectState.Error -> {
                Timber.e("Subjects error")
                showLoadingState(false)
                Toast.makeText(context, state.err, Toast.LENGTH_SHORT).show()
            }
            is SubjectState.Loading -> {
                Timber.e("Subjects loading")
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        schedule_rec.isVisible = !loading
        loadingPb_schedule.isVisible = loading
    }

    fun initListeners() {

        search_subjects.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {

                Timber.e("START DAY I GROUP")
                Timber.e((day_box.editText?.text.toString() == "").toString())
                Timber.e(group_box.editText?.text.toString())
                Timber.e("END DAY I GROUP")

                scheduleViewModel.filter(
                    SubjectFilter(
                        search_subjects.query.toString(),
                        day_box.editText?.text.toString(),
                        group_box.editText?.text.toString()
                    )
                )
                return false
            }
        })

        group_box.editText?.addTextChangedListener {
            scheduleViewModel.filter(
                SubjectFilter(
                    search_subjects.query.toString(),
                    day_box.editText?.text.toString(),
                    group_box.editText?.text.toString()
                )
            )
        }
        day_box.editText?.addTextChangedListener {
            scheduleViewModel.filter(
                SubjectFilter(
                    search_subjects.query.toString(),
                    day_box.editText?.text.toString(),
                    group_box.editText?.text.toString()
                )
            )
        }
    }


}