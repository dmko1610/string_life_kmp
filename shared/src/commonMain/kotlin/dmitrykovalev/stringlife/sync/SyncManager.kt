package dmitrykovalev.stringlife.sync

import dmitrykovalev.stringlife.network.InstrumentApiClient
import dmitrykovalev.stringlife.network.InstrumentRequestDto
import dmitrykovalev.stringlife.repository.InstrumentRepository
import kotlin.time.Clock

class SyncManager(
    private val repository: InstrumentRepository, private val apiClient: InstrumentApiClient
) {
    suspend fun sync() {
        val unsynced = repository.getUnsynced()
        var lastError: Exception? = null

        for (instrument in unsynced) {
            try {
                apiClient.create(
                    InstrumentRequestDto(
                        name = instrument.name,
                        type = instrument.type.name,
                        lastStringChangeDate = instrument.lastStringChangeDate?.toString(),
                    )
                )
                repository.markSynced(instrument.id, Clock.System.now().toEpochMilliseconds())
            } catch (e: Exception) {
                lastError = e
            }
        }
        lastError?.let { throw it }
    }
}
