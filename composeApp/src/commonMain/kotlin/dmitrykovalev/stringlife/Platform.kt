package dmitrykovalev.stringlife

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform