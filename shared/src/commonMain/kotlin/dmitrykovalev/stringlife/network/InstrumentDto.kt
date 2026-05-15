package dmitrykovalev.stringlife.network

import kotlinx.serialization.Serializable

@Serializable
data class InstrumentDto(
    val id: String,
    val name: String,
    val type: String,
    val stringCount: Int,
    val lastStringChangeDate: String? = null,
    val notes: String? = null,
    val createdAt: String
)

@Serializable
data class InstrumentRequestDto(
    val name: String,
    val type: String,
    val stringCount: Int = 6,
    val lastStringChangeDate: String? = null
)


