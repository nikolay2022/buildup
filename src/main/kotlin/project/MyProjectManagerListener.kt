package project

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.ui.Messages
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils

/**
 *@author nikolay2022 on 29/11/2021
 */
lateinit var id: String

class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        val tgid = PropertiesComponent.getInstance().getValue(KEY_TGID)
        if (tgid == null || tgid.isEmpty()) {
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
                else
                {

                    Messages.showErrorDialog(response.entity.contentEncoding.value,"dd")

//                    Messages.showErrorDialog(httpclient.execute(httppost).toString(),"the ID entered is incorrect. messages will not come. you can enter id if you click on the button in the action bar (next to the \"build project\" button).")
                }

            }
        } else{
            id = tgid
        }
    }

    private companion object {
        const val KEY_TGID = "tgid"
    }
}
