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

    override fun finished(result: ProjectTaskManager.Result) {
        when {
            result.hasErrors() -> buildNotifier(BuildStatusModel.ERROR, project.name)
            result.isAborted -> buildNotifier(BuildStatusModel.CANCELLED, project.name)
            else -> buildNotifier(BuildStatusModel.SUCCESS, project.name)
        }
    }

    override fun started(context: ProjectTaskContext) {
        buildNotifier(BuildStatusModel.STARTED, project.name)
    }
}

