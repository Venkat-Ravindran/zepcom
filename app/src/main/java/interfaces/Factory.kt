package interfaces

interface Factory<T> {
    fun create(): T
}