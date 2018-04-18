package buffer.fixedSize.fixedCopy.systemArraycopy

import benchmarks.IBuffer

class Buffer(private val maxBufferSize: Int): IBuffer {
    private var size = 0
    private var array = arrayOfNulls<Any?>(this.maxBufferSize)

    override fun addLast(e: Any?) {
        val newArray = arrayOfNulls<Any?>(this.maxBufferSize)
        if (this.size == this.maxBufferSize) {
            newArray[0] = e
            this.size = 1
            this.array = newArray
        } else {
            System.arraycopy(this.array, 0, newArray, 0, this.maxBufferSize)
            newArray[this.size++] = e
            this.array = newArray
        }
    }
}