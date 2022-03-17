package cn.yyxx.apklink.bean

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class TaskBean(
    @SerializedName("origin_apk") var originApk: String,
    @SerializedName("output_apk") var outputApk: String,
    @SerializedName("version_name") var versionName: String,
    @SerializedName("version_code") var versionCode: String,
    @SerializedName("comm_config") var commConfig: JsonObject,
    @SerializedName("channel_config") var channelConfig: JsonObject,
    @SerializedName("sdk_log") var sdkLog: JsonObject,
    @SerializedName("ext_log") var extLog: JsonObject
)