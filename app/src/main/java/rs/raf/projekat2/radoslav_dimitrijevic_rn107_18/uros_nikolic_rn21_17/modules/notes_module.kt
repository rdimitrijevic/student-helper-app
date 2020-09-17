package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.datasources.local.Database
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.repositories.notes.NotesRepository
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.repositories.notes.NotesRepositoryImpl
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.viewmodel.NotesViewModel


val notesModule = module {

    viewModel { NotesViewModel(repository = get()) }

    single<NotesRepository> {
        NotesRepositoryImpl(
            notesDao = get()
        )
    }

    single { get<Database>().getNotesDao() }

}
