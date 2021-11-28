package project

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.ui.Messages

lateinit var id:String
class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        Messages.showInputDialog(
            project, "enter your id",
            "enter the id you received from the telegram bot @zhmail_assistant_bot", Messages.getQuestionIcon()
        ).also { id = it.toString() }
    }
}
