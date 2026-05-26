package dmitrykovalev.stringlife.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class InstrumentApiClient(private val baseUrl: String) {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }
    }

    suspend fun getAll(): List<InstrumentDto> = client.get("$baseUrl/api/instruments").body()

    suspend fun create(request: InstrumentRequestDto): InstrumentDto =
        client.post("$baseUrl/api/instruments") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun delete(id: String) {
        client.delete("$baseUrl/api/instruments/$id")
    }
}