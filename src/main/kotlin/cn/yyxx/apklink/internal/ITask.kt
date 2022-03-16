package cn.yyxx.apklink.internal

import cn.yyxx.apklink.bean.TaskBean

interface ITask {
    fun cleanWorkspace(): Boolean
    fun decompileApk(bean: TaskBean): Boolean
    fun handleOriginSmali(bean: TaskBean): Boolean
    fun handleOriginRes(bean: TaskBean): Boolean
    fun handleChannelSmali(bean: TaskBean): Boolean
    fun handleChannelRes(bean: TaskBean): Boolean
    fun execChannelExtraScript(bean: TaskBean): Boolean
    fun generateNewR(bean: TaskBean): Boolean
    fun splitDex(bean: TaskBean): Boolean
    fun recompileApk(bean: TaskBean): Boolean
    fun signApk(bean: TaskBean): Boolean
}