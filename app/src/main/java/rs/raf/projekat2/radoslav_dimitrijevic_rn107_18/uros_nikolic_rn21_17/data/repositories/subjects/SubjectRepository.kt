package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.repositories.subjects

import io.reactivex.Observable
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.Resource
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.room.SubjectFilter
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.Subject

interface SubjectRepository {

    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<Resource<List<Subject>>>
    fun filter(filter: SubjectFilter): Observable<Resource<List<Subject>>>
    fun getGroups(): Observable<Resource<Set<String>>>

}