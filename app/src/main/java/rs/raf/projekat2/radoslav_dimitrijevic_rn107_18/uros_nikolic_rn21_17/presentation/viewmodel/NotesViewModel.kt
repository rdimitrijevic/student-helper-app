package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.Resource
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.ui.Note
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.repositories.notes.NotesRepository
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.contract.NotesContract
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.state.NotesState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class NotesViewModel(private val repository: NotesRepository) : ViewModel(),
    NotesContract.ViewModel {

    override val notesState: MutableLiveData<NotesState> = MutableLiveData()

    private val subscriptions = CompositeDisposable()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    override var showArchived: Boolean = false


    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                repository.filter(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        notesState.value = NotesState.Error(it.toString())
                    }
            }
            .subscribe(
                {
                    when (it) {
                        is Resource.Loading -> notesState.value = NotesState.Loading
                        is Resource.Success -> notesState.value = NotesState.Success(it.data)
                        is Resource.Error -> notesState.value =
                            NotesState.Error(it.error.toString())
                    }
                },
                {
                    Timber.e(it)
                    notesState.value = NotesState.Error(it.toString())
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllNotes() {
        val subscription = repository.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        is Resource.Loading -> notesState.value = NotesState.Loading
                        is Resource.Success -> notesState.value =
                            NotesState.Success(it.data.filter {
                                if (!showArchived)
                                    !it.archived
                                else
                                    true
                            })
                        is Resource.Error -> notesState.value =
                            NotesState.Error(it.error.toString())
                    }
                },
                {
                    notesState.value = NotesState.Error(it.toString())
                    Timber.e(it)
                })

        subscriptions.add(subscription)
    }

    override fun getById(id: Long) {
        val subscription = repository
            .getById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> notesState.value = NotesState.Loading
                        is Resource.Success -> notesState.value = NotesState.NoteByIdSuccess(it.data)
                        is Resource.Error -> notesState.value = NotesState.Error(it.error.toString())
                    }
                },
                {
                    notesState.value = NotesState.Error(it.toString())
                    Timber.e(it)
                }
            )

        subscriptions.add(subscription)
    }

    override fun filterNotes(filter: String) {
        publishSubject.onNext(filter)
    }

    override fun toggleArchived() {
        showArchived = showArchived.not()

/*
        if (notesState.value !is NotesState.Success) {
            return
        }

        (notesState.value as NotesState.Success).notes =
            (notesState.value as NotesState.Success).notes.filter {
                !it.archived
            }
*/

    }

    override fun addNote(note: Note) {
        val subscription = repository
            .insert(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    notesState.value = NotesState.NoteAddSuccess
                },
                {
                    notesState.value = NotesState.Error(it.toString())
                })

        subscriptions.add(subscription)
    }

    override fun removeNote(id: Long) {
        val subscription = repository
            .delete(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    notesState.value = NotesState.NoteDeleteSuccess
                },
                {
                    notesState.value = NotesState.Error(it.toString())
                }
            )
        subscriptions.add(subscription)
    }

    override fun updateNote(note: Note) {
        addNote(note)
    }

    override fun archiveNote(note: Note) {
        note.archived = true
        addNote(note)
    }

    override fun unarchiveNote(note: Note) {
        note.archived = false
        addNote(note)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}