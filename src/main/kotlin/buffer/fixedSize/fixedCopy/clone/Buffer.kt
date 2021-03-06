package buffer.fixedSize.fixedCopy.clone

import benchmarks.IBuffer

class Buffer(private val maxBufferSize: Int): IBuffer {
    private var size = 0
    private var array = arrayOfNulls<Any?>(this.maxBufferSize)

    override fun addLast(e: Any?) {
        if (this.size == this.maxBufferSize) {
            val newArray = arrayOfNulls<Any?>(this.maxBufferSize)
            newArray[0] = e
            this.size = 1
            this.array = newArray
        } else {
            val newArray = this.array.clone()
            newArray[this.size++] = e
            this.array = newArray
        }
    }
}