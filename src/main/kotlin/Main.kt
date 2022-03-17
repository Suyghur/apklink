import cn.yyxx.apklink.GlobalConfig
import cn.yyxx.apklink.TaskImpl
import cn.yyxx.apklink.bean.TaskBean
import cn.yyxx.apklink.ext.loge
import com.google.gson.Gson
import java.io.File
import java.nio.charset.Charset

fun main(args: Array<String>) {
    if (args.size != 2) {
        "输入参数有误，请检查".loge()
        return
    }
    val option = args[0]
    val argument = args[1]
    if (option == "-f") {
        GlobalConfig.init()
        val file = File(argument)
        val json = file.readText(Charset.forName("UTF-8"))
        val bean = Gson().fromJson(json, TaskBean::class.java)
        TaskImpl(bean)
            .cleanWorkspace()
            .decompileApk()
            .handleOriginSmali()
            .execChannelExtraScript()
            .recompileApk()
    } else {
        "输入参数有误，请检查".loge()
    }
}
