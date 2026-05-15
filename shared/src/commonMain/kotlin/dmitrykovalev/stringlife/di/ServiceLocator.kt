package dmitrykovalev.stringlife.di

import dmitrykovalev.stringlife.db.DriverFactory
import dmitrykovalev.stringlife.db.createDatabase
import dmitrykovalev.stringlife.network.InstrumentApiClient
import dmitrykovalev.stringlife.repository.InstrumentRepository
import dmitrykovalev.stringlife.viewmodel.InstrumentViewModel
import dmitrykovalev.stringlife.BuildKonfig

object ServiceLocator {
    lateinit var instrumentRepository: InstrumentRepository
        private set

    lateinit var instrumentViewModel: InstrumentViewModel
        private set

    lateinit var instrumentApiClient: InstrumentApiClient
        private set

    fun init(driverFactory: DriverFactory) {
        val db = createDatabase(driverFactory)
        instrumentRepository = InstrumentRepository(db)
        instrumentViewModel = InstrumentViewModel(instrumentRepository)
        instrumentApiClient = InstrumentApiClient(BuildKonfig.BASE_URL)
    }
}
