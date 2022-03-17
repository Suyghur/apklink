package cn.yyxx.apklink.ext

import java.io.File


fun String.exists(): Boolean {
    return File(this).exists()
}