package cn.yyxx.apklink

import java.io.File
import java.nio.file.Paths

object GlobalConfig {

    var root = ""
    var workspace = ""
    var sdk = ""
    var tools = ""
    var libChannel = ""
    var libComm = ""
    var decompile = ""
    var channel = ""
    var comm = ""
    var apktoold261 = ""
    var d8 = ""
    var baksmali = ""
    var androidJar = ""

    fun init() {
        root = Paths.get("").toAbsolutePath().toString()
        sdk = "$root${File.separator}sdk"
        libChannel = "$sdk${File.separator}channel"
        libComm = "$sdk${File.separator}comm"

        workspace = "$root${File.separator}workspace"
        decompile = "$workspace${File.separator}decompile"
        channel = "$workspace${File.separator}channel"
        comm = "$workspace${File.separator}comm"

        tools = "$root${File.separator}tools"
        apktoold261 = "$tools${File.separator}apktool261.jar"
        d8 = "$tools${File.separator}d8"
        baksmali = "$tools${File.separator}baksmali.jar"
        androidJar = "$tools${File.separator}android.jar"
    }
}