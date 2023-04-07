package project.listeners

import com.android.tools.idea.gradle.project.build.BuildContext
import com.android.tools.idea.gradle.project.build.BuildStatus
import com.android.tools.idea.gradle.project.build.GradleBuildListener
import com.android.tools.idea.gradle.project.build.invoker.GradleBuildInvoker
import com.intellij.openapi.project.Project
import project.model.BuildStatusModel
import project.network.BuildNotifierImpl

/**
 *@author nikolay2022 on 29/11/2021
 */
class AndroidBuildUp(private val project: Project) : GradleBuildListener {
    private val buildNotifier = BuildNotifierImpl()
    private var timeStartBuild = 0L

    override fun buildExecutorCreated(request: GradleBuildInvoker.Request) {}

    override fun buildStarted(context: BuildContext) {
        timeStartBuild = System.currentTimeMillis()
        buildNotifier.invoke(BuildStatusModel.STARTED, project.name)
    }

    override fun buildFinished(status: BuildStatus, context: BuildContext?) {
        val timeEndBuild = System.currentTimeMillis()
        val timeBuild = (timeEndBuild - timeStartBuild) / 1000L
        when (status) {
            BuildStatus.SUCCESS -> buildNotifier(BuildStatusModel.SUCCESS, project.name, timeBuild.toString())
            BuildStatus.FAILED -> buildNotifier(BuildStatusModel.ERROR, project.name, timeBuild.toString())
            BuildStatus.CANCELED -> buildNotifier(BuildStatusModel.CANCELLED, project.name, timeBuild.toString())
        }
    }
}