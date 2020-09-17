package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_edit.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.R
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.Note
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.contract.NotesContract
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.state.NotesState
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.viewmodel.NotesViewModel
import timber.log.Timber

class EditActivity : AppCompatActivity(R.layout.activity_edit) {

    private val editViewModel: NotesContract.ViewModel by viewModel<NotesViewModel>()
    private var id: Long = -1
    private var archived: Boolean = false

    companion object {
        const val NOTE_ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        id = intent.getLongExtra(NOTE_ID, -1)

        init()
    }


    fun init() {

        edit_cancel_btn.setOnClickListener {
            finish()
        }

        edit_ok_btn.setOnClickListener {
            val note: Note

            if( title_tf.text.isNullOrEmpty()
                || edit_body_tf.text.isNullOrEmpty() ) {
                Toast.makeText(
                    this,
                    getString(R.string.edit_text_err_msg),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (id >= 0) {
                note = Note(
                    id,
                    title_tf.text.toString(),
                    edit_body_tf.text.toString(),
                    archived
                )
            } else {
                note = Note(
                    0,
                    title_tf.text.toString(),
                    edit_body_tf.text.toString(),
                    archived
                )
            }

            editViewModel.addNote(note)
        }

        editViewModel.notesState.observe(this, Observer {
            renderState(it)
        })

        if (id >= 0) {
            editViewModel.getById(id)
        }
    }

    private fun renderState(state: NotesState) {
        when (state) {
            is NotesState.Loading -> {
                showLoadingState(true)
                Timber.e("Note loading")
            }
            is NotesState.NoteAddSuccess -> {
                showLoadingState(false)
                Timber.e("Note added")
                finish()
            }
            is NotesState.Error -> {
                showLoadingState(false)
                Timber.e("Note error: %s", state.err)
                Toast.makeText(
                    this,
                    state.err,
                    Toast.LENGTH_LONG
                ).show()
            }
            is NotesState.NoteByIdSuccess -> {
                showLoadingState(false)
                Timber.e("Single note aquired")

                title_tf.setText(
                    state.note.title.toCharArray(),
                    0,
                    state.note.title.length
                )

                edit_body_tf.setText(
                    state.note.content.toCharArray(),
                    0,
                    state.note.content.length
                )

                archived = state.note.archived
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        loadingPb_edit.isVisible = loading
    }

}