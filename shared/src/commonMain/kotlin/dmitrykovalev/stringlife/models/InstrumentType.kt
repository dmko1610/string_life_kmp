package dmitrykovalev.stringlife.models

enum class InstrumentType {
    ELECTRIC, ACOUSTIC, BASS;

    val displayName: String
        get() = when (this) {
            ELECTRIC -> "Electric"
            ACOUSTIC -> "Acoustic"
            BASS -> "Bass"
        }
}