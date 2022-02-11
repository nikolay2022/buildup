package project

import com.intellij.ide.util.PropertiesComponent
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
lateinit var token: String

class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        val tgid = PropertiesComponent.getInstance().getValue(KEY_TGID)
        if (tgid == null || tgid.isEmpty()) {
            Messages.showInputDialog(
                project,
                "enter your id",
                "enter the id you received from the telegram bot @irlix_buildUp_bot",
                Messages.getQuestionIcon()
            ).also {
                val httpclient: HttpClient = HttpClients.createDefault()
                val httppost = HttpPost("https://bot.zhmail.ru/app.php")
                val params: MutableList<NameValuePair> = ArrayList<NameValuePair>(2)
                params.add(BasicNameValuePair("sid", it.toString()))
                params.add(BasicNameValuePair("type", "check"))
                httppost.setEntity(UrlEncodedFormEntity(params, "UTF-8"))
                val response: HttpResponse = httpclient.execute(httppost)
                val resp:String = EntityUtils.toString(response.entity)
                if(resp == "Error") {
                    Messages.showErrorDialog(httpclient.execute(httppost).toString(),"the ID entered is incorrect. messages will not come. you can enter id if you click on the button in the action bar (next to the \"build project\" button).")
                }
                else{
                    token = resp
                    PropertiesComponent.getInstance().setValue(KEY_TGID, token)
                }

            }
        } else{
            token = tgid
        }
    }

    private companion object {
        const val KEY_TGID = "tgid"
    }
}
