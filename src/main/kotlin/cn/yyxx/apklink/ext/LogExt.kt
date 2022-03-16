package cn.yyxx.apklink.ext

import java.lang.reflect.Array
import java.text.SimpleDateFormat


private val formatter: SimpleDateFormat by lazy { SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS") }

private const val TAG = "apklink"

private enum class Level {
    DEBUG, ERROR
}

fun Any.logd() {
    log(Level.DEBUG, TAG)
}

fun Any.loge() {
    log(Level.ERROR, TAG)
}

private fun Any?.log(level: Level, tag: String) {
    val msg = if (this == null) {
        "null"
    } else {
        val clz: Class<*> = javaClass
        if (clz.isArray) {
            val sb = StringBuilder(clz.simpleName)
            sb.append(" [ ")
            val len = Array.getLength(this)
            for (i in 0 until len) {
                if (i != 0 && i != len - 1) {
                    sb.append(", ")
                }
                val tmp = Array.get(this, i)
                sb.append(tmp)
            }
            sb.append(" ] ")
            sb.toString()
        } else {
            "$this"
        }
    }

    when (level) {
        Level.DEBUG -> println("${formatter.getDate()} D/$tag $msg")
        Level.ERROR -> System.err.println("${formatter.getDate()} E/$tag $msg")
    }
}

private fun SimpleDateFormat.getDate(): String {
    return format(System.currentTimeMillis())
}


