package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui

data class Note(
    val id: Long,
    val title: String,
    val content: String,
    var archived: Boolean
)