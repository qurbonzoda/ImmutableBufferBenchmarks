package benchmarks

interface IBuffer {
    fun addLast(e: Any?)
}

class DummyBuffer: IBuffer {
    override fun addLast(e: Any?) {
        throw AssertionError("addLast() called on DummyBuffer")
    }
}