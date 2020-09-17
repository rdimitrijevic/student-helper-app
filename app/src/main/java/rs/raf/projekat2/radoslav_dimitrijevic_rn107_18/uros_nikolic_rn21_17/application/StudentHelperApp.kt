package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.application;

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.modules.coreModule
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.modules.notesModule
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.modules.subjectModule
import rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.modules.userModule
import timber.log.Timber

class StudentHelperApp : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(this)
        initKoin()
    }

    private fun initKoin() {
        val modules = listOf(
            coreModule,
            userModule,
            notesModule,
            subjectModule
        )
        startKoin {
            androidLogger(Level.DEBUG)
            // Use application context
            androidContext(this@StudentHelperApp)
            // Use properties from assets/koin.properties
            androidFileProperties()
            // Use koin fragment factory for fragment instantiation
            fragmentFactory()
            // modules
            modules(modules)
        }
    }
}
