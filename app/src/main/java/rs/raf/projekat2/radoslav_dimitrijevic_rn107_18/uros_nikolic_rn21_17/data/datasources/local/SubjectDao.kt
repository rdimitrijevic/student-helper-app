package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.room.SubjectEntity

@Dao
abstract class SubjectDao {

    @Query("SELECT * FROM subjects")
    abstract fun getAll(): Observable<List<SubjectEntity>>

    @Query("SELECT * FROM subjects WHERE subject_name LIKE '%'||:filter||'%' OR professor LIKE '%'||:filter||'%'")
    abstract fun filter(filter: String): Observable<List<SubjectEntity>>

    @Query("SELECT groups FROM subjects")
    abstract fun getGroups(): Observable<List<String>>

    @Query("DELETE from subjects")
    abstract fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(entities: List<SubjectEntity>): Completable

    @Transaction
    open fun deleteAndInsertAll(entities: List<SubjectEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

}