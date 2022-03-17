package cn.yyxx.apklink.channel

import cn.yyxx.apklink.bean.TaskBean
import cn.yyxx.apklink.internal.IChannel

class ChannelEyuangameImpl : IChannel {
    override fun execChannelExtraScript(bean: TaskBean): Boolean {
        return false
    }
}