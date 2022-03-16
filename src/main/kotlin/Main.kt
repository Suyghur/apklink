import cn.yyxx.apklink.execWithFile
import cn.yyxx.apklink.ext.loge

fun main(args: Array<String>) {
    if (args.size != 2) {
        "输入参数有误，请检查".loge()
        return
    }
    val option = args[0]
    val argument = args[1]
    if (option == "-f") {
        argument.execWithFile()
    } else {
        "输入参数有误，请检查".loge()
    }
//    val test =
//        "{\"channel\":\"huawei\",\"channel_id\":\"3\",\"apk_path\":\"/Users/suyghur/Develop/yyxx/backend/apklink/test.apk\",\"output_path\":\"/Users/suyghur/Develop/yyxx/backend/apklink/workspace/test\"}"
//    val json = args[0]
////        """{"channel":"huawei","channel_id":"3","apk_path":"/Users/suyghur/Develop/yyxx/backend/apklink/test.apk","output_path":"/Users/suyghur/Develop/yyxx/backend/apklink/workspace/test"}"""
//    val bean = Gson().fromJson(json, TaskBean::class.java)
//    println(bean.toString())
}
