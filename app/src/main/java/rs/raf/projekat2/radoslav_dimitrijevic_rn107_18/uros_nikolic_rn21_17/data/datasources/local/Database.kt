package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.room.NoteEntity
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.room.SubjectEntity

@Database(
    entities = [SubjectEntity::class, NoteEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters()
abstract class Database : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao
    abstract fun getSubjectDao(): SubjectDao
}