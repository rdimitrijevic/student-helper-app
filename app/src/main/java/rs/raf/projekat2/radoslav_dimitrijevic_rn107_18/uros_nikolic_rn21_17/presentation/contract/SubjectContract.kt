package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.room.SubjectFilter
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.state.SubjectState

interface SubjectContract {

    interface ViewModel {

        val subjectState: LiveData<SubjectState>

        fun fetchAllSubjects()
        fun getAllSubjects()
        fun filter(filter: SubjectFilter)
        fun getGroups()
    }
}