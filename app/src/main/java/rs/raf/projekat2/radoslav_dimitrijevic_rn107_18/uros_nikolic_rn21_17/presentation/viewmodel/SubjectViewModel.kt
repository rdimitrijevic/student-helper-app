package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.Resource
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.room.SubjectFilter
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.repositories.subjects.SubjectRepository
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.contract.SubjectContract
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.state.SubjectState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SubjectViewModel(private val repository: SubjectRepository) : ViewModel(),
    SubjectContract.ViewModel {

    override val subjectState: MutableLiveData<SubjectState> = MutableLiveData()
    private val subscriptions = CompositeDisposable()
    private val publishSubject: PublishSubject<SubjectFilter> = PublishSubject.create()

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                repository.filter(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        subjectState.value = SubjectState.Error(it.toString())
                    }
            }
            .subscribe(
                {
                    when (it) {
                        is Resource.Loading -> subjectState.value = SubjectState.Loading
                        is Resource.Success -> subjectState.value = SubjectState.Success(it.data)
                        is Resource.Error -> subjectState.value =
                            SubjectState.Error(it.error.toString())
                    }
                    Timber.e("RADI FILTER")
                    Timber.e(it.toString())
                },
                {
                    Timber.e(it)
                    subjectState.value = SubjectState.Error(it.toString())
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllSubjects() {
        val subscription = repository
            .fetchAll()
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        is Resource.Loading -> subjectState.value = SubjectState.Loading
                        is Resource.Success -> subjectState.value = SubjectState.DataFetched
                        is Resource.Error -> subjectState.value = SubjectState.Error(it.toString())
                    }
                },
                {
                    subjectState.value = SubjectState.Error(it.toString())
                }
            )

        subscriptions.add(subscription)
    }

    override fun getAllSubjects() {
        val subscription = repository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        is Resource.Success -> subjectState.value = SubjectState.Success(it.data)
                        is Resource.Error -> subjectState.value = SubjectState.Error(it.toString())
                    }
                },
                {
                    subjectState.value = SubjectState.Error(it.toString())
                }
            )
        subscriptions.add(subscription)
    }

    override fun filter(filter: SubjectFilter) {
        publishSubject.onNext(filter)
    }

    override fun getGroups() {
        val subscription = repository
            .getGroups()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        is Resource.Success -> subjectState.value =
                            SubjectState.GroupsFetched(it.data)
                        is Resource.Error -> subjectState.value = SubjectState.Error(it.toString())
                    }
                },
                {
                    subjectState.value = SubjectState.Error(it.toString())
                }
            )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}