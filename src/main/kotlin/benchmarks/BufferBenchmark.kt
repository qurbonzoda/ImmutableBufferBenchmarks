package benchmarks

import org.openjdk.jmh.annotations.*

@State(Scope.Thread)
@Fork(1)
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 20)
@Measurement(iterations = 20)
open class BufferBenchmark {
    @Param(  "1",  "2",  "3",  "4",  "5",  "8",
            "12", "15", "16", "19",
            "20", "24", "25", "28",
            "31", "32", "33", "36",
            "40", "44", "48",
            "51", "52", "55", "56",
            "60", "64", "67")
    var maxBufferSize = 0

    @Param(FIXED_SIZE_FIXED_COPY_FOR,
            FIXED_SIZE_FIXED_COPY_CLONE,
            FIXED_SIZE_FIXED_COPY_SYSTEM_ARRAYCOPY,
            FIXED_SIZE_FIXED_COPY_COPY_OF,
            FIXED_SIZE_FIXED_COPY_COPY_OF_RANGE,
            FIXED_SIZE_VARIABLE_COPY_FOR,
            FIXED_SIZE_VARIABLE_COPY_SYSTEM_ARRAYCOPY,
            VARIABLE_SIZE_FOR,
            VARIABLE_SIZE_COPY_OF,
            VARIABLE_SIZE_COPY_OF_RANGE,
            VARIABLE_SIZE_SYSTEM_ARRAYCOPY)
    var bufferType = ""

    private var iBuffer: IBuffer = DummyBuffer()

    @Setup(Level.Iteration)
    fun prepare() {
        this.iBuffer = when (this.bufferType) {
            FIXED_SIZE_FIXED_COPY_FOR                   -> buffer.fixedSize.fixedCopy.`for`.Buffer(this.maxBufferSize)
            FIXED_SIZE_FIXED_COPY_CLONE                 -> buffer.fixedSize.fixedCopy.clone.Buffer(this.maxBufferSize)
            FIXED_SIZE_FIXED_COPY_SYSTEM_ARRAYCOPY      -> buffer.fixedSize.fixedCopy.systemArraycopy.Buffer(this.maxBufferSize)
            FIXED_SIZE_FIXED_COPY_COPY_OF               -> buffer.fixedSize.fixedCopy.copyOf.Buffer(this.maxBufferSize)
            FIXED_SIZE_FIXED_COPY_COPY_OF_RANGE         -> buffer.fixedSize.fixedCopy.copyOfRange.Buffer(this.maxBufferSize)
            FIXED_SIZE_VARIABLE_COPY_FOR                -> buffer.fixedSize.variableCopy.`for`.Buffer(this.maxBufferSize)
            FIXED_SIZE_VARIABLE_COPY_SYSTEM_ARRAYCOPY   -> buffer.fixedSize.variableCopy.systemArraycopy.Buffer(this.maxBufferSize)
            VARIABLE_SIZE_FOR                           -> buffer.variableSize.`for`.Buffer(this.maxBufferSize)
            VARIABLE_SIZE_COPY_OF                       -> buffer.variableSize.copyOf.Buffer(this.maxBufferSize)
            VARIABLE_SIZE_COPY_OF_RANGE                 -> buffer.variableSize.copyOfRange.Buffer(this.maxBufferSize)
            VARIABLE_SIZE_SYSTEM_ARRAYCOPY              -> buffer.variableSize.systemArraycopy.Buffer(this.maxBufferSize)
            else -> throw AssertionError()
        }
    }

    @Benchmark
    fun addLast(): IBuffer {
        this.iBuffer.addLast("some element")
        return this.iBuffer
    }
}

const val FIXED_SIZE_FIXED_COPY_FOR                   = "FIXED_SIZE_FIXED_COPY_FOR"
const val FIXED_SIZE_FIXED_COPY_CLONE                 = "FIXED_SIZE_FIXED_COPY_CLONE"
const val FIXED_SIZE_FIXED_COPY_SYSTEM_ARRAYCOPY      = "FIXED_SIZE_FIXED_COPY_SYSTEM_ARRAYCOPY"
const val FIXED_SIZE_FIXED_COPY_COPY_OF               = "FIXED_SIZE_FIXED_COPY_COPY_OF"
const val FIXED_SIZE_FIXED_COPY_COPY_OF_RANGE         = "FIXED_SIZE_FIXED_COPY_COPY_OF_RANGE"
const val FIXED_SIZE_VARIABLE_COPY_FOR                = "FIXED_SIZE_VARIABLE_COPY_FOR"
const val FIXED_SIZE_VARIABLE_COPY_SYSTEM_ARRAYCOPY   = "FIXED_SIZE_VARIABLE_COPY_SYSTEM_ARRAYCOPY"
const val VARIABLE_SIZE_FOR                           = "VARIABLE_SIZE_FOR"
const val VARIABLE_SIZE_COPY_OF                       = "VARIABLE_SIZE_COPY_OF"
const val VARIABLE_SIZE_COPY_OF_RANGE                 = "VARIABLE_SIZE_COPY_OF_RANGE"
const val VARIABLE_SIZE_SYSTEM_ARRAYCOPY              = "VARIABLE_SIZE_SYSTEM_ARRAYCOPY"