package cn.yyxx.apklink

import cn.yyxx.apklink.bean.TaskBean
import cn.yyxx.apklink.ext.logd
import com.google.gson.Gson
import java.io.File
import java.nio.charset.Charset

fun String.execWithFile() {
    val file = File(this)
    val json = file.readText(Charset.forName("UTF-8"))
    Gson().fromJson(json, TaskBean::class.java).exec()
}

private fun TaskBean.exec() {
    logd()
    val task = Task()
    task.cleanWorkspace()
}
