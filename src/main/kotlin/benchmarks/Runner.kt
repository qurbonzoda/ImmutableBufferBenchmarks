package benchmarks

import org.openjdk.jmh.results.RunResult
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder
import org.openjdk.jmh.runner.options.TimeValue
import java.io.FileWriter

fun main(args: Array<String>) {
    val implementation = "immutableBuffer"
    val outputFile = "teamcityArtifacts/$implementation.csv"
    val options = OptionsBuilder()
            .forks(2)
            .jvmArgs("-Xms3072m", "-Xmx3072m")
            .warmupIterations(10)
            .measurementIterations(10)
            .warmupTime(TimeValue.milliseconds(1000))
            .measurementTime(TimeValue.milliseconds(1000))
            .addProfiler("gc")

    val runResults = Runner(options.build()).run()
    printResults(runResults, implementation, outputFile)
}

fun printResults(runResults: Collection<RunResult>, implementation: String, outputFile: String) {
    val csvHeader = "Implementation,Method,maxBufferSize,sizeType,copyType,copyImpl,Score,Score Error,Allocation Rate"

    val fileWriter = FileWriter(outputFile)

    fileWriter.appendln(csvHeader)

    runResults.forEach {
        fileWriter.appendln(csvRowFrom(it, implementation))
    }

    fileWriter.flush()
    fileWriter.close()
}

fun csvRowFrom(result: RunResult, implementation: String): String {
    val method = result.primaryResult.getLabel()
    val maxBufferSize = result.params.getParam("maxBufferSize").toInt()
    val score = result.primaryResult.getScore()
    val scoreError = result.primaryResult.getScoreError()
    val allocationRate = result.secondaryResults["·gc.alloc.rate.norm"]!!.getScore()

    val bufferType = result.params.getParam("bufferType")
    val (sizeType, copyType, copyImpl) = buffer(bufferType)

    return "$implementation,$method,$maxBufferSize,$sizeType,$copyType,$copyImpl,%.3f,%.3f,%.3f"
            .format(score, scoreError, allocationRate)
}

fun buffer(impl: String): Triple<String, String, String> {
    if (impl.startsWith("FIXED_SIZE")) {
        if (impl.startsWith("FIXED_SIZE_FIXED_COPY")) {
            return Triple("FIXED", "FIXED", impl.substring(21))
        } else if (impl.startsWith("FIXED_SIZE_VARIABLE_COPY")) {
            return Triple("FIXED", "VARIABLE", impl.substring(25))
        }
    } else if (impl.startsWith("VARIABLE_SIZE")) {
        return Triple("VARIABLE", "VARIABLE", impl.substring(14))
    }
    throw IllegalArgumentException()
}