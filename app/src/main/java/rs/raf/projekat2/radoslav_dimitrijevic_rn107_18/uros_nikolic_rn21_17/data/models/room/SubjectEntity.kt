package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "subject_name") val subjectName: String,
    @ColumnInfo(name = "subject_type") val subjectType: String,
    val professor: String,
    val classroom: String,
    val groups: String,
    val day: String,
    val time: String
)