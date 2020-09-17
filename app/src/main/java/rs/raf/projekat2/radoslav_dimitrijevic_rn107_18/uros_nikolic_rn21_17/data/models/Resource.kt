package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Loading<out T>(val message: String = "") : Resource<T>()
    data class Error<out T>(val error: Throwable = Throwable(), val data: T? = null) : Resource<T>()
}