package dmitrykovalev.stringlife.di

import dmitrykovalev.stringlife.BuildKonfig
import dmitrykovalev.stringlife.db.DriverFactory
import dmitrykovalev.stringlife.db.createDatabase
import dmitrykovalev.stringlife.network.InstrumentApiClient
import dmitrykovalev.stringlife.repository.InstrumentRepository
import dmitrykovalev.stringlife.sync.SyncManager
import dmitrykovalev.stringlife.viewmodel.InstrumentViewModel

object ServiceLocator {
    lateinit var instrumentRepository: InstrumentRepository
        private set

    lateinit var instrumentViewModel: InstrumentViewModel
        private set

    lateinit var instrumentApiClient: InstrumentApiClient
        private set

    lateinit var syncManager: SyncManager
        private set

    fun init(driverFactory: DriverFactory) {
        val db = createDatabase(driverFactory)
        instrumentRepository = InstrumentRepository(db)
        instrumentApiClient = InstrumentApiClient(BuildKonfig.BASE_URL)
        syncManager = SyncManager(instrumentRepository, instrumentApiClient)
        instrumentViewModel = InstrumentViewModel(instrumentRepository, syncManager)
    }
}
