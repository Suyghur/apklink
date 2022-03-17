package cn.yyxx.apklink

import java.io.File
import java.nio.file.Paths

object GlobalConfig {

    var root = ""
    var workspace = ""
    var decompile = ""
    var tools = ""
    var apktoold261 = ""

    fun init() {
        root = Paths.get("").toAbsolutePath().toString()
        workspace = "$root${File.separator}workspace"
        decompile = "$workspace${File.separator}decompile"
        tools = "$root${File.separator}tools"
        apktoold261 = "$tools${File.separator}apktool261.jar"
    }
}