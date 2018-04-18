package buffer.variableSize.systemArraycopy

import benchmarks.IBuffer

class Buffer(private val maxBufferSize: Int): IBuffer {
    private var array = arrayOfNulls<Any?>(this.maxBufferSize)

    override fun addLast(e: Any?) {
        if (this.array.size == this.maxBufferSize) {
            val newArray = arrayOfNulls<Any?>(1)
            newArray[0] = e
            this.array = newArray
        } else {
            val newArray = arrayOfNulls<Any?>(this.array.size + 1)
            System.arraycopy(this.array, 0, newArray, 0, this.array.size)
            newArray[this.array.size] = e
            this.array = newArray
        }
    }
}