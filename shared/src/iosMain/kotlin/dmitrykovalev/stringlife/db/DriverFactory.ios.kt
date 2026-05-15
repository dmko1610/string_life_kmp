package dmitrykovalev.stringlife.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DriverFactory() {
    actual fun createDriver(): SqlDriver = NativeSqliteDriver(StringLifeDb.Schema, "stringlife.db")
}