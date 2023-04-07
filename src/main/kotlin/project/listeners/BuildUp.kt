package project.listeners

import com.intellij.openapi.project.Project
import com.intellij.task.ProjectTaskContext
import com.intellij.task.ProjectTaskListener
import com.intellij.task.ProjectTaskManager
import project.model.BuildStatusModel
import project.network.BuildNotifierImpl

/**
 *@author nikolay2022 on 29/11/2021
 */

open class BuildUp(private val project: Project) : ProjectTaskListener {
    private val buildNotifier = BuildNotifierImpl()
    private var timeStartBuild = 0L

    override fun finished(result: ProjectTaskManager.Result) {
        val timeEndBuild = System.currentTimeMillis()
        val timeBuild = (timeEndBuild - timeStartBuild) / 1000L
        when {
            result.hasErrors() -> buildNotifier(BuildStatusModel.ERROR, project.name, timeBuild.toString())
            result.isAborted -> buildNotifier(BuildStatusModel.CANCELLED, project.name, timeBuild.toString())
            else -> buildNotifier(BuildStatusModel.SUCCESS, project.name, timeBuild.toString())
        }
    }

    override fun started(context: ProjectTaskContext) {
        timeStartBuild = System.currentTimeMillis()
        buildNotifier(BuildStatusModel.STARTED, project.name)
    }
}

