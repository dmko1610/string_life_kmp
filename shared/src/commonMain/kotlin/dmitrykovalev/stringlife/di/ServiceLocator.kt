package dmitrykovalev.stringlife.di

import dmitrykovalev.stringlife.db.DriverFactory
import dmitrykovalev.stringlife.db.createDatabase
import dmitrykovalev.stringlife.repository.InstrumentRepository

object ServiceLocator {
    lateinit var instrumentRepository: InstrumentRepository
        private set

    fun init(driverFactory: DriverFactory) {
        val db = createDatabase(driverFactory)
        instrumentRepository = InstrumentRepository(db)
    }
}
