package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.recycler.viewholder

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_notes.*
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.Note

class NoteViewHolder(
    override val containerView: View,
    archiveAction: (Int, Boolean) -> Unit,
    deleteAction: (Int) -> Unit,
    editAction: (Int) -> Unit
) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    init {
        viewholder_btn_archive.setOnCheckedChangeListener { buttonView, isChecked ->
            if (adapterPosition != RecyclerView.NO_POSITION) {

                if (isChecked) {
                    val color = Color.parseColor("#8e0000")
                    val mode = PorterDuff.Mode.SRC_ATOP
                    buttonView.background.setColorFilter(color, mode)

                } else {
                    buttonView.background.clearColorFilter()
                }

                archiveAction.invoke(adapterPosition, isChecked)
            }
        }

        viewholder_btn_edit.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION)
                editAction.invoke(adapterPosition)
        }

        viewholder_btn_delete.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION)
                deleteAction.invoke(adapterPosition)
        }
    }

    fun bind(note: Note) {
        tv_title.text = note.title
        tv_body.text = note.content
        viewholder_btn_archive.isChecked = note.archived
    }

}