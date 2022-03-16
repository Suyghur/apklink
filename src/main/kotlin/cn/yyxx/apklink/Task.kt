package cn.yyxx.apklink

import cn.yyxx.apklink.bean.TaskBean
import cn.yyxx.apklink.ext.logd
import cn.yyxx.apklink.ext.loge
import cn.yyxx.apklink.internal.ITask
import java.nio.file.Paths

class Task : ITask {

    override fun cleanWorkspace(): Boolean {
        Paths.get("").toAbsolutePath().toString().logd()
        return false
    }

    override fun decompileApk(bean: TaskBean): Boolean {
        return false
    }

    override fun handleOriginSmali(bean: TaskBean): Boolean {
        return false
    }

    override fun handleOriginRes(bean: TaskBean): Boolean {
        return false
    }

    override fun handleChannelSmali(bean: TaskBean): Boolean {
        return false
    }

    override fun handleChannelRes(bean: TaskBean): Boolean {
        return false
    }

    override fun execChannelExtraScript(bean: TaskBean): Boolean {
        return false
    }

    override fun generateNewR(bean: TaskBean): Boolean {
        return false
    }

    override fun splitDex(bean: TaskBean): Boolean {
        return false
    }

    override fun recompileApk(bean: TaskBean): Boolean {
        return false
    }

    override fun signApk(bean: TaskBean): Boolean {
        return false
    }
}