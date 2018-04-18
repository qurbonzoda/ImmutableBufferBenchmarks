package buffer.variableSize.`for`

import benchmarks.IBuffer

class Buffer(private val maxBufferSize: Int): IBuffer {
    private var array = arrayOfNulls<Any?>(0)

    override fun addLast(e: Any?) {
        if (this.array.size == this.maxBufferSize) {
            val newArray = arrayOfNulls<Any?>(1)
            newArray[0] = e
            this.array = newArray
        } else {
            val newArray = arrayOfNulls<Any?>(this.array.size + 1)
            for (i in 0 until this.array.size) {
                newArray[i] = this.array[i]
            }
            newArray[this.array.size] = e
            this.array = newArray
        }
    }
}