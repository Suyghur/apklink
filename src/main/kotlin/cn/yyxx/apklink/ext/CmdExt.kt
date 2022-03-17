package cn.yyxx.apklink.ext

import java.io.BufferedReader
import java.io.InputStreamReader


fun String.execute(): Process {
    "execute command:\n$this".logd()
    val runtime = Runtime.getRuntime()
    return runtime.exec(this)
}

fun Process.text() {
    val output = StringBuilder()
    // 输出 Shell 执行的结果
    var line: String? = ""
    InputStreamReader(inputStream).use { isr ->
        BufferedReader(isr).use { br ->
            while (line != null) {
                line = br.readLine()
                if (line != null) {
                    output.append(line).append("\n")
                }
            }
        }
    }
    output.toString().logd()
}


