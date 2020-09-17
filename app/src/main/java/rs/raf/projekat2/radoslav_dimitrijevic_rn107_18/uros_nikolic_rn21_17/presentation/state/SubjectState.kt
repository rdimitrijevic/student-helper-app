package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.state

import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.Subject

sealed class SubjectState {
    object Loading : SubjectState()
    object DataFetched : SubjectState()
    data class Success(val subjects: List<Subject>) : SubjectState()
    data class Error(val err: String) : SubjectState()
    data class GroupsFetched(val groups: Set<String>) : SubjectState()
}
