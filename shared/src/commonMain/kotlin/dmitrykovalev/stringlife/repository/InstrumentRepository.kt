package dmitrykovalev.stringlife.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import dmitrykovalev.stringlife.db.StringLifeDb
import dmitrykovalev.stringlife.models.InstrumentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class InstrumentEntity(
    val id: String,
    val name: String,
    val type: InstrumentType,
    val lastStringChangeDate: LocalDate?,
    val updatedAt: Long
)

class InstrumentRepository(private val db: StringLifeDb) {

    fun watchAll(): Flow<List<InstrumentEntity>> =
        db.instrumentQueries.selectAllInstruments().asFlow().mapToList(Dispatchers.Default)
            .map { rows -> rows.map { it.toEntity() } }.flowOn(Dispatchers.Default)

    fun getById(id: String): Flow<InstrumentEntity?> =
        db.instrumentQueries.selectInstrumentById(id).asFlow().mapToOneOrNull(Dispatchers.Default)
            .map { it?.toEntity() }.flowOn(Dispatchers.Default)

    @OptIn(ExperimentalUuidApi::class)
    fun addInstrument(name: String, type: InstrumentType, lastStringChangeDate: LocalDate?) {
        db.instrumentQueries.insertInstrument(
            id = Uuid.random().toString(),
            name = name,
            type = type.name,
            last_string_change_date = lastStringChangeDate,
            updated_at = Clock.System.now().toEpochMilliseconds()
        )
    }

    fun deleteInstrument(id: String) {
        db.instrumentQueries.deleteInstrument(id)
    }

    private fun dmitrykovalev.stringlife.db.Instruments.toEntity() = InstrumentEntity(
        id = id,
        name = name,
        type = InstrumentType.valueOf(type),
        lastStringChangeDate = last_string_change_date,
        updatedAt = updated_at
    )
}
