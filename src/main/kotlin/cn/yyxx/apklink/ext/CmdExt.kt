package cn.yyxx.apklink.ext

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.coroutines.CoroutineContext


fun String.execute() {
    "execute command:\n$this".log()
    val runtime = Runtime.getRuntime()
    return runtime.exec(this).text()
}

private fun Process.text() = runBlocking {
    val errJob = async(start = CoroutineStart.LAZY) { read(errorStream) }
    val infoJob = async(start = CoroutineStart.LAZY) { read(inputStream) }
    errJob.start()
    infoJob.start()
    errJob.await().log()
    infoJob.await().log()
}

private fun read(stream: InputStream): String {
    val output = StringBuilder()
    var line: String? = ""
    InputStreamReader(stream).use { isr ->
        BufferedReader(isr).use { br ->
            while (line != null) {
                line = br.readLine()
                if (line != null) {
                    output.append(line).append("\n")
                }
            }
        }
    }
    return output.toString()
}

fun execCmd(cmd: String, showResult: Boolean = false): Boolean {
    return try {
        val process = Runtime.getRuntime().exec(cmd)
        val errorStream = process.errorStream
        Thread {
            errorStream?.let {
                val isr = InputStreamReader(it)
                val br = BufferedReader(isr)
                var line = br.readLine()
                while (line != null) {
                    line = br.readLine()
                    "执行命令错误信息: $line".log()
                }
                isr.close()
                br.close()
            }
        }.start()

        val inputStream = process.inputStream
        Thread {
            inputStream?.let {
                val isr = InputStreamReader(it)
                val br = BufferedReader(isr)
                var line = br.readLine()
                while (line != null) {
                    if (showResult) {
//                            println(line)
                        "命令输出信息: $line".log()
                    }
                    line = br.readLine()
                }
                "执行命令结束 ".log()
                isr.close()
                br.close()
            }
        }.start()
        val success = process.waitFor() == 0
        success
    } catch (e: Exception) {
        "正在执行命令: $cmd 异常,错误信息: $e".log()
//            e.message?.printAsLogE()
        false
    }

}


