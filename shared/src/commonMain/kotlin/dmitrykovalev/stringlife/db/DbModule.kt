package dmitrykovalev.stringlife.db

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.LocalDate

fun createDatabase(driverFactory: DriverFactory): StringLifeDb {
    return StringLifeDb(
        driver = driverFactory.createDriver(),
        instrumentsAdapter = Instruments.Adapter(
            last_string_change_dateAdapter = object : ColumnAdapter<LocalDate, String> {
                override fun decode(databaseValue: String) = LocalDate.parse(databaseValue)
                override fun encode(value: LocalDate) = value.toString()
            }
        )
    )
}
