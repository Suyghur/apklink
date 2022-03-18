package cn.yyxx.apklink

import cn.yyxx.apklink.bean.TaskBean
import cn.yyxx.apklink.ext.*
import cn.yyxx.apklink.internal.IChannel
import cn.yyxx.apklink.internal.ITask
import java.io.File
import java.io.FileOutputStream
import java.util.*

class TaskImpl(private val bean: TaskBean) : ITask {

    private val channelImpl: IChannel? by lazy {
        ChannelImplManager.getChannelImpl(bean)
    }

    override fun cleanWorkspace(): ITask {
        val workspace = File(GlobalConfig.workspace)
        if (workspace.exists()) {
            workspace.deleteRecursively()
        }
        workspace.mkdirs()
        "initialize workspace ...".log()
        val channelName = bean.commConfig.get("channel").asString
        with(File("${GlobalConfig.libChannel}/$channelName")) {
            if (exists()) {
                copyRecursively(File(GlobalConfig.channel))
            } else {
                "$absolutePath is not exists".painc()
            }
        }

        with((File(GlobalConfig.libComm))) {
            if (exists()) {
                copyRecursively(File(GlobalConfig.comm))
            } else {
                "$absolutePath is not exists".painc()
            }
        }

        return this
    }

    override fun decompileApk(): ITask {
        decompile(bean.originApk)
        return this
    }

    override fun handleOriginSmali(): ITask {
        // TODO 可优化多个dex文件夹的判断

        // 判断smali2，整合到一个smali中方便移除旧代码
        val smaliPath = "${GlobalConfig.decompile}/smali"
        val smali2Path = "${GlobalConfig.decompile}/smali_classes2"
        val smali = File(smaliPath)
        val smali2 = File(smali2Path)
        if (smali2.exists()) {
            smali2.copyRecursively(smali)
            smali2.deleteRecursively()
        }

        // cn/yyxx
        val yyxxPath = "$smaliPath/cn/yyxx"
        File(yyxxPath).deleteRecursively()

        // com
        val comPath = "$smaliPath/com"
        val tencentPath = "$comPath/tencent"
        val comPaths = mutableListOf(
            "adjust", "android", "asus", "bun",
            "dolin", "facebook", "google", "huawei",
            "netease", "samsung", "tencent", "zui"
        )
        File(comPath).walk().maxDepth(1)
            .filter {
                it.isDirectory
            }.filter {
                comPaths.contains(it.name)
            }.forEach {
                if (it.absolutePath.equals(tencentPath)) {
                    // 移除mmkv
                    val mmkvPath = "$tencentPath/mmkv"
                    File(mmkvPath).deleteRecursively()
                } else {
                    it.deleteRecursively()
                }
            }

        val xiPath = "$smaliPath/XI"
        File(xiPath).deleteRecursively()
        return this
    }

    override fun handleOriginRes(): ITask {
        // 移除母包assets中sdk资源
        val assetsRes = mutableListOf(
            "yyxx_game",
            "supplierconfig.json",
            "zlsioh.dat"
        )
        File("${GlobalConfig.decompile}/assets").walk().maxDepth(1).filter {
            assetsRes.contains(it.name)
        }.forEach {
            it.deleteRecursively()
        }

        // 移除母包lib中的sdk资源
        val abis = mutableListOf<String>()
        val commSo = mutableListOf(
            "libdolin-zap.so",
            "libeyuancomm.so",
            "libmmkv.so",
            "libsecsdk.so"
        )
        val lib = File("${GlobalConfig.decompile}/lib")
        lib.walk().maxDepth(1)
            .filter {
                it.isDirectory
            }.forEach {
                abis.add(it.absolutePath)
            }

        abis.forEach { abi ->
            File(abi).walk().maxDepth(1)
                .filter {
                    it.extension == "so"
                }.filter {
                    commSo.contains(it.name)
                }.filter {
                    it.isFile
                }.forEach {
                    it.delete()
                }
        }
        return this
    }

    override fun handleCommSmali(): ITask {
        // 处理融合jar
        jar2dex("${GlobalConfig.comm}/jars", "${GlobalConfig.comm}/jars")
        dex2smali("${GlobalConfig.comm}/jars/classes.dex", "${GlobalConfig.decompile}/smali")
        return this
    }

    override fun handleCommRes(): ITask {
        // 复制融合sdk的assets
        val decompileAssets = File("${GlobalConfig.decompile}/assets")

        val commAssets = File("${GlobalConfig.comm}/assets")
        if (commAssets.exists()) {
            commAssets.copyRecursively(decompileAssets)
        }

        val channelAssets = File("${GlobalConfig.channel}/assets")
        if (channelAssets.exists()) {
            channelAssets.copyRecursively(decompileAssets)
        }

        handleCommConfig()
        return this
    }

    private fun handleCommConfig() {
        val file = File("${GlobalConfig.decompile}/assets/yyxx_game/yyxx_comm.properties")
        file.parentFile.mkdirs()
        file.createNewFile()
        val properties = Properties()
        properties.setProperty("YYXX_GAME_CODE", bean.commConfig.get("game_code").asString)
        properties.setProperty("YYXX_GCP_CODE", bean.commConfig.get("gcp_code").asString)
        properties.setProperty("YYXX_CHANNEL_ID", bean.commConfig.get("channel_id").asString)
        properties.setProperty("YYXX_ADJUST_APP_ID", bean.commConfig.get("adjust_id").asString)
        FileOutputStream(file).use { fos ->
            properties.store(fos, null)
        }
    }

    override fun handleChannelSmali(): ITask {
        // 处理渠道jar
        jar2dex("${GlobalConfig.channel}/jars", "${GlobalConfig.channel}/jars")
        dex2smali("${GlobalConfig.channel}/jars/classes.dex", "${GlobalConfig.decompile}/smali")
        return this
    }

    override fun handleChannelRes(): ITask {

        return this
    }

    override fun execCommExtraScript(): ITask {
        return this
    }

    override fun execChannelExtraScript(): ITask {
        channelImpl?.execChannelExtraScript(bean)
        return this
    }

    override fun generateNewR(): ITask {
        return this
    }

    override fun splitDex(): ITask {
        return this
    }

    override fun recompileApk(): ITask {
        compile(bean.outputApk)
        return this
    }

    override fun signApk(): ITask {
        return this
    }
}