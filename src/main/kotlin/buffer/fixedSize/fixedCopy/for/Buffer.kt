package buffer.fixedSize.fixedCopy.`for`

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
            for (i in 0 until this.maxBufferSize) {
                newArray[i] = this.array[i]
            }
            newArray[this.size++] = e
            this.array = newArray
        }
    }
}