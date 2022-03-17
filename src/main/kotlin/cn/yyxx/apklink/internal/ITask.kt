package cn.yyxx.apklink.internal


interface ITask {
    fun cleanWorkspace(): ITask
    fun decompileApk(): ITask
    fun handleOriginSmali(): ITask
    fun handleOriginRes(): ITask
    fun handleCommSmali(): ITask
    fun handleCommRes(): ITask
    fun handleChannelSmali(): ITask
    fun handleChannelRes(): ITask
    fun execCommExtraScript(): ITask
    fun execChannelExtraScript(): ITask
    fun generateNewR(): ITask
    fun splitDex(): ITask
    fun recompileApk(): ITask
    fun signApk(): ITask
}