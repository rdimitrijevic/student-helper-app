package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.view.recycler.viewholder;

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_subjects.*
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.Subject

class SubjectViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bind(subject: Subject) {
        val subjectType = subject.subjectName + " - " + subject.subjectType
        subject_type_tf.text = subjectType
        professor_tf.text = subject.professor
        classroom_tf.text = subject.classroom
        groups_tf.text = subject.groups
        day_tf.text = subject.day
        time_tf.text = subject.time
    }

}
