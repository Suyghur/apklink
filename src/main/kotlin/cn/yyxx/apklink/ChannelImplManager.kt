package cn.yyxx.apklink

import cn.yyxx.apklink.bean.TaskBean
import cn.yyxx.apklink.channel.ChannelEyuangameImpl
import cn.yyxx.apklink.channel.ChannelHuaweiImpl
import cn.yyxx.apklink.channel.ChannelOppoImpl
import cn.yyxx.apklink.ext.logd
import cn.yyxx.apklink.internal.IChannel

object ChannelImplManager {

    const val OPPO = "1"
    const val VIVO = "2"
    const val HUAWEI = "3"
    const val EYUAN_GAME = "4"

    fun getChannelImpl(bean: TaskBean): IChannel? {
        return when (bean.commConfig["channel_id"].asString) {
            OPPO -> ChannelOppoImpl()
            VIVO -> null
            HUAWEI -> ChannelHuaweiImpl()
            EYUAN_GAME -> ChannelEyuangameImpl()
            else -> null
        }
    }
}