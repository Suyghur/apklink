package cn.yyxx.apklink.ext

import java.lang.reflect.Array


private enum class Level {
    DEBUG, ERROR
}

fun Any.log() {
    print(Level.DEBUG)
}

fun Any.painc() {
    print(Level.ERROR)
}

private fun Any?.print(level: Level) {
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
        Level.DEBUG -> println(msg)
        Level.ERROR -> {
            System.err.println(msg)
            throw Exception(msg)
        }
    }
}

