package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.api.SubjectResponse

interface SubjectService {

    @GET("json.php")
    fun getAll(@Query("limit") limit: Int = 1000): Observable<List<SubjectResponse>>
}