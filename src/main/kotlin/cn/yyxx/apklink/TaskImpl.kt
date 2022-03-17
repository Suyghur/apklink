package cn.yyxx.apklink

import cn.yyxx.apklink.bean.TaskBean
import cn.yyxx.apklink.ext.execute
import cn.yyxx.apklink.ext.logd
import cn.yyxx.apklink.ext.loge
import cn.yyxx.apklink.ext.text
import cn.yyxx.apklink.internal.IChannel
import cn.yyxx.apklink.internal.ITask
import java.io.File

class TaskImpl(private val bean: TaskBean) : ITask {

    val channelImpl: IChannel? by lazy {
        ChannelImplManager.getChannelImpl(bean)
    }

    override fun cleanWorkspace(): ITask {
        val workspace = File(GlobalConfig.workspace)
        if (workspace.exists()) {
            workspace.deleteRecursively()
        }
        workspace.mkdirs()
        return this
    }

    override fun decompileApk(): ITask {
        val apkFile = File(bean.originApk)
        if (apkFile.exists()) {
            "java -jar -Xms2048m -Xmx2048m ${GlobalConfig.apktoold261} d ${bean.originApk} -o ${GlobalConfig.decompile} --only-main-classes".execute().text()
        } else {
            "origin apk file is not exists".loge()
        }
        return this
    }

    override fun handleOriginSmali(): ITask {
        // TODO 可优化多个dex文件夹的判断

        // 判断smali2，整合到一个smali中方便移除旧代码
        val smaliPath = "${GlobalConfig.decompile}${File.separator}smali"
        val smali2Path = "${GlobalConfig.decompile}${File.separator}smali_classes2"
        val smali = File(smaliPath)
        val smali2 = File(smali2Path)
        if (smali2.exists()) {
            smali2.copyRecursively(smali)
            smali2.deleteRecursively()
        }

        // cn/yyxx
        val yyxxPath = "$smaliPath${File.separator}cn${File.separator}yyxx"
        File(yyxxPath).deleteRecursively()

        // com
        val comPath = "$smaliPath${File.separator}com"
        val adjustPath = "$comPath${File.separator}adjust"
        val comAndroidPath = "$comPath${File.separator}android"
        val asusPath = "$comPath${File.separator}asus"
        val bunPath = "$comPath${File.separator}bun"
        val dolinPath = "$comPath${File.separator}dolin"
        val facebookPath = "$comPath${File.separator}facebook"
        val googlePath = "$comPath${File.separator}google"
        val huaweeiPath = "$comPath${File.separator}huawei"
        val neteasePath = "$comPath${File.separator}netease"
        val samsungPath = "$comPath${File.separator}samsung"
        val tencentPath = "$comPath${File.separator}tencent"
        val zuiPath = "$comPath${File.separator}zui"

        val comPaths = mutableListOf(
            adjustPath, comAndroidPath, asusPath, bunPath,
            dolinPath, facebookPath, googlePath, huaweeiPath,
            neteasePath, samsungPath, tencentPath, zuiPath
        )
        File(comPath).walk().maxDepth(1).filter {
            it.isDirectory
        }.forEach {
            if (comPaths.contains(it.absolutePath)) {
                if (it.absolutePath.equals(tencentPath)) {
                    // 移除mmkv
                    val mmkvPath = "$tencentPath${File.separator}mmkv"
                    File(mmkvPath).deleteRecursively()
                } else {
                    it.deleteRecursively()
                }
            }
        }

        val xiPath = "$smaliPath${File.separator}XI"
        File(xiPath).deleteRecursively()

        return this
    }

    override fun handleOriginRes(): ITask {
        return this
    }

    override fun handleCommSmali(): ITask {
        return this
    }

    override fun handleCommRes(): ITask {
        return this
    }

    override fun handleChannelSmali(): ITask {
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
        val output = File(GlobalConfig.decompile)
        if (output.exists()) {
            "java -jar -Xms2048m -Xmx2048m ${GlobalConfig.apktoold261} b ${GlobalConfig.decompile} -o ${bean.outputApk}".execute().text()
        } else {
            "${GlobalConfig.decompile} file is not exists".loge()
        }
        return this
    }

    override fun signApk(): ITask {
        return this
    }
}