package layout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.R
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.Note
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.recycler.diff.NotesDiffItemCallback
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.recycler.viewholder.NoteViewHolder

class NotesAdapter(
    notesDiffItemCallback: NotesDiffItemCallback,
    private val archiveFragmentFunction: (Note, Boolean) -> Unit,
    private val deleteFragmentFunction: (Note) -> Unit,
    private val editFragmentFunction: (Note) -> Unit
) : ListAdapter<Note, NoteViewHolder>(notesDiffItemCallback) {

    private val archiveAdapterFunction: (Int, Boolean) -> Unit = { pos, checked ->
        archiveFragmentFunction.invoke(getItem(pos), checked)
    }

    private val deleteAdapterFunction: (Int) -> Unit = {
        deleteFragmentFunction.invoke(getItem(it))
    }

    private val editAdapterFunction: (Int) -> Unit = {
        editFragmentFunction.invoke(getItem(it))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val containerView = layoutInflater.inflate(R.layout.viewholder_notes, parent, false)
        return NoteViewHolder(
            containerView,
            archiveAdapterFunction,
            deleteAdapterFunction,
            editAdapterFunction
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
