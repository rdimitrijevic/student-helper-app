package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui;

data class Subject(
    val id: Long,
    val subjectName: String,
    val subjectType: String,
    val professor: String,
    val classroom: String,
    val groups: String,
    val day: String,
    val time: String
)
