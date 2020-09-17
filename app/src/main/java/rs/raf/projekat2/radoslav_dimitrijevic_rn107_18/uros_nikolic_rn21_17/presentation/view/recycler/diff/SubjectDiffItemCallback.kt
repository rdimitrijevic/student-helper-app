package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.Subject

class SubjectDiffItemCallback : DiffUtil.ItemCallback<Subject>() {
    override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean {
        return oldItem.subjectName == newItem.subjectName
                && oldItem.subjectType == newItem.subjectType
                && oldItem.classroom == newItem.classroom
                && oldItem.groups == newItem.groups
                && oldItem.day == newItem.day
                && oldItem.time == newItem.time
    }
}