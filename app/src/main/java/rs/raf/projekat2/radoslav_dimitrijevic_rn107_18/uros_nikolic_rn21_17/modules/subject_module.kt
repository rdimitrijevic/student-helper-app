package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.datasources.local.Database
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.datasources.local.SubjectDao
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.datasources.remote.SubjectService
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.repositories.subjects.SubjectRepository
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.repositories.subjects.SubjectRepositoryImpl
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.viewmodel.SubjectViewModel

val subjectModule = module {

    viewModel { SubjectViewModel(repository = get()) }

    single<SubjectRepository> { SubjectRepositoryImpl(
        localDataSource = get(),
        remoteDataSource = get()
    ) }

    single<SubjectDao> { get<Database>().getSubjectDao() }
    single<SubjectService> { create( retrofit = get()) }

}