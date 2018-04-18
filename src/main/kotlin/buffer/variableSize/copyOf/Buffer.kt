package buffer.variableSize.copyOf

import benchmarks.IBuffer

class Buffer(private val maxBufferSize: Int): IBuffer {
    private var array = arrayOfNulls<Any?>(0)

    override fun addLast(e: Any?) {
        if (this.array.size == this.maxBufferSize) {
            val newArray = arrayOfNulls<Any?>(1)
            newArray[0] = e
            this.array = newArray
        } else {
            val newArray = this.array.copyOf(this.array.size + 1)
            newArray[this.array.size] = e
            this.array = newArray
        }
    }
}