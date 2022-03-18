package cn.yyxx.apklink.ext

import cn.yyxx.apklink.GlobalConfig
import java.io.File


fun decompile(target: String) {
    if (File(target).exists()) {
        "java -jar -Xms2048m -Xmx2048m ${GlobalConfig.apktoold261} d $target -o ${GlobalConfig.decompile} --only-main-classes".execute()
    } else {
        "origin apk file is not exists".painc()
    }
}

fun compile(output: String) {
    "java -jar -Xms2048m -Xmx2048m ${GlobalConfig.apktoold261} b ${GlobalConfig.decompile} -o $output".execute()
}

fun jar2dex(src: String, dst: String) {
    val jars = StringBuilder()
    File(src).walk().maxDepth(1).filter {
        it.extension == "jar"
    }.forEach {
        jars.append(" ").append(it.absolutePath)
    }

    "${GlobalConfig.d8} --lib ${GlobalConfig.androidJar} --output $dst $jars".execute()
}

fun dex2smali(src: String, dst: String) {
    "java -jar -Xms2048m -Xmx2048m ${GlobalConfig.baksmali} -o $dst $src".execute()
}