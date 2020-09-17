package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_notes.*
import layout.NotesAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.R
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.Note
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.contract.NotesContract
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.state.NotesState
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.activity.EditActivity
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.recycler.diff.NotesDiffItemCallback
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.viewmodel.NotesViewModel
import timber.log.Timber

class NotesFragment : Fragment(R.layout.fragment_notes) {

    private lateinit var notesAdapter: NotesAdapter
    private val notesViewModel: NotesContract.ViewModel by sharedViewModel<NotesViewModel>()

    private val archivedFunction: (Note, Boolean) -> Unit = { note, archived ->
        if (archived) {
            notesViewModel.archiveNote(note)
        } else {
            notesViewModel.unarchiveNote(note)
        }
    }

    private val editFunction: (Note) -> Unit = {
        Timber.e(it.toString())
        val intent = Intent(context, EditActivity::class.java)
        intent.putExtra(EditActivity.NOTE_ID, it.id)

        startActivity(intent)
    }

    private val deleteFunction: (Note) -> Unit = {
        Timber.e(it.toString())
        notesViewModel.removeNote(it.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        test()
    }

    private fun init() {
        notes_rec.layoutManager = LinearLayoutManager(this.context)
        notesAdapter = NotesAdapter(
            NotesDiffItemCallback(),
            archivedFunction,
            deleteFunction,
            editFunction
        )

        notes_add_btn.setOnClickListener {
            val intent = Intent(context, EditActivity::class.java)

            startActivity(intent)
        }

        notes_rec.adapter = notesAdapter
        initListeners()

        notesViewModel.notesState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })

        notesViewModel.getAllNotes()
    }

    fun test() {
        for (i in 0..15) {
            notesViewModel.addNote(Note(0, "Naslov" + i, "Sadrzaj" + i, false))
        }
    }

    private fun initListeners() {
        switch_notes.setOnCheckedChangeListener { btn, checked ->
            if (checked) {
                notesViewModel.toggleArchived()
                notesViewModel.getAllNotes()
            } else {
                notesViewModel.toggleArchived()
                notesViewModel.getAllNotes()
            }

/*            notesViewModel.notesState.value?.let {
                renderState(it)
            }*/
        }

        search_notes.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                notesViewModel.filterNotes(search_notes.query.toString())
                return false
            }
        })

    }

    private fun renderState(state: NotesState) {
        when (state) {
            is NotesState.Success -> {
                Timber.e("Notes success")
                showLoadingState(false)
                notesAdapter.submitList(state.notes)
            }
            is NotesState.Error -> {
                Timber.e("Notes error")
                showLoadingState(false)
                Toast.makeText(context, state.err, Toast.LENGTH_SHORT).show()
            }
            is NotesState.Loading -> {
                Timber.e("Notes loading")
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        notes_rec.isVisible = !loading
        loadingPb.isVisible = loading
    }

}