package project

import com.intellij.openapi.project.Project
import com.intellij.task.ProjectTaskContext
import com.intellij.task.ProjectTaskListener
import com.intellij.task.ProjectTaskManager
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair

/**
 *@author nikolay2022 on 29/11/2021
 */

open class BuildUp(private val project: Project) : ProjectTaskListener {
    override fun finished(result: ProjectTaskManager.Result) {
        when {
            result.hasErrors() -> {
                val httpclient: HttpClient = HttpClients.createDefault()
                val httppost = HttpPost("https://bot.zhmail.ru/app.php")
                val params: MutableList<NameValuePair> = ArrayList<NameValuePair>(2)
                params.add(BasicNameValuePair("token", token))
                params.add(BasicNameValuePair("type", "message"))
                params.add(BasicNameValuePair("message", "build error"))
                httppost.setEntity(UrlEncodedFormEntity(params, "UTF-8"))
                val response: HttpResponse = httpclient.execute(httppost)

            }

            result.isAborted -> {
                val httpclient: HttpClient = HttpClients.createDefault()
                val httppost = HttpPost("https://bot.zhmail.ru/app.php")
                val params: MutableList<NameValuePair> = ArrayList<NameValuePair>(2)
                params.add(BasicNameValuePair("token", token))
                params.add(BasicNameValuePair("type", "message"))
                params.add(BasicNameValuePair("message", "build canceled"))
                httppost.setEntity(UrlEncodedFormEntity(params, "UTF-8"))
                val response: HttpResponse = httpclient.execute(httppost)
            }

            else -> {
                val httpclient: HttpClient = HttpClients.createDefault()
                val httppost = HttpPost("https://bot.zhmail.ru/app.php")
                val params: MutableList<NameValuePair> = ArrayList<NameValuePair>(2)
                params.add(BasicNameValuePair("token", token))
                params.add(BasicNameValuePair("type", "message"))
                params.add(BasicNameValuePair("message", "build success"))
                httppost.setEntity(UrlEncodedFormEntity(params, "UTF-8"))
                val response: HttpResponse = httpclient.execute(httppost)
            }
        }
    }

    override fun started(context: ProjectTaskContext) {
        super.started(context)
    }

}

