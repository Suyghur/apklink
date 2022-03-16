package cn.yyxx.apklink.bean

import com.google.gson.annotations.SerializedName

data class TaskBean(
    @SerializedName("channel")
    var channelName: String,
    @SerializedName("channel_id")
    var channelId: String,
    @SerializedName("apk_path")
    var apkPath: String,
    @SerializedName("output_path")
    var outputPath: String
)