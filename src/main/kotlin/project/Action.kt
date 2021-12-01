package project

import com.android.tools.lint.detector.api.Project
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.ui.Messages
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils


class Action: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project: com.intellij.openapi.project.Project? = e.getData(PlatformDataKeys.PROJECT)
        Messages.showInputDialog(
            project,
            "enter your id",
            "enter the id you received from the telegram bot @zhmail_assistant_bot",
            Messages.getQuestionIcon()
        ).also {
            val httpclient: HttpClient = HttpClients.createDefault()
            val httppost = HttpPost("http://bot.zhmail.ru/app.php")
            val params: MutableList<NameValuePair> = ArrayList<NameValuePair>(2)
            params.add(BasicNameValuePair("tgid", it.toString()))
            params.add(BasicNameValuePair("type", "check"))
            httppost.setEntity(UrlEncodedFormEntity(params, "UTF-8"))
            val response: HttpResponse = httpclient.execute(httppost)
            if(EntityUtils.toString(response.entity) == "1") {
                PropertiesComponent.getInstance().setValue(KEY_TGID, it.toString())
                id = it.toString()
            }
            else{
                Messages.showErrorDialog("the id you entered is incorrect","ERROR")
            }
        }

    }
    private companion object {
        const val KEY_TGID = "tgid"
    }
}