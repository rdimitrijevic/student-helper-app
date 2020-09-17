package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.repositories.subjects

import io.reactivex.Observable
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.datasources.local.SubjectDao
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.datasources.remote.SubjectService
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.Resource
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.room.SubjectEntity
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.room.SubjectFilter
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.Subject

class SubjectRepositoryImpl(
    private val localDataSource: SubjectDao,
    private val remoteDataSource: SubjectService
) : SubjectRepository {

    override fun fetchAll(): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAll()
            .doOnNext { list ->
                val entities = list.map {
                    SubjectEntity(
                        0,
                        it.subjectName,
                        it.subjectType,
                        it.professor,
                        it.classroom,
                        it.groups,
                        getDay(it.day),
                        it.time
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getAll(): Observable<Resource<List<Subject>>> {
        return localDataSource.getAll()
            .map { list ->
                Resource.Success(list.map {
                    Subject(
                        it.id,
                        it.subjectName,
                        it.subjectType,
                        it.professor,
                        it.classroom,
                        it.groups,
                        it.day,
                        it.time
                    )
                })
            }
    }

    override fun filter(filter: SubjectFilter): Observable<Resource<List<Subject>>> {
        return localDataSource
            .filter(filter.searchFilter)
            .map { list ->
                Resource.Success(list.map {
                    Subject(
                        it.id,
                        it.subjectName,
                        it.subjectType,
                        it.professor,
                        it.classroom,
                        it.groups,
                        it.day,
                        it.time
                    )
                }.filter {
                    if (filter.group != "") {
                        return@filter it.groups.contains(filter.group)
                    }
                    true
                }.filter {
                    if (filter.day != "") {
                        return@filter it.day.toLowerCase().trim() == filter.day.toLowerCase().trim()
                    }
                    true
                })
            }

    }

    override fun getGroups(): Observable<Resource<Set<String>>> {
        return localDataSource.getGroups().map { groups ->
            Resource.Success(
                groups.map {
                    it.split(Regex("\\s*,\\s*"))
                }.flatten().toSet()
            )
        }
    }


    private fun getDay(day: String): String {
        return when (day) {
            "PON" -> "Ponedeljak"
            "UTO" -> "Utorak"
            "SRE" -> "Sreda"
            "ČET" -> "Cetvrtak"
            "PET" -> "Petak"
            "SUB" -> "Subota"

            else -> "Nedelja"
        }
    }
}