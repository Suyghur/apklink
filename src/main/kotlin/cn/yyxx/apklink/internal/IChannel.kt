package cn.yyxx.apklink.internal

import cn.yyxx.apklink.bean.TaskBean

interface IChannel {
    fun execChannelExtraScript(bean: TaskBean): Boolean
}