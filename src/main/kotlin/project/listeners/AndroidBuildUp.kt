package project

import com.android.tools.idea.gradle.project.build.BuildContext
import com.android.tools.idea.gradle.project.build.BuildStatus
import com.android.tools.idea.gradle.project.build.GradleBuildListener
import com.android.tools.idea.gradle.project.build.invoker.GradleBuildInvoker
import com.intellij.openapi.project.Project
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
class AndroidBuildUp(private val project: Project) : GradleBuildListener {

    override fun buildExecutorCreated(request: GradleBuildInvoker.Request) {
//        val httpclient: HttpClient = HttpClients.createDefault()
//        val httppost = HttpPost("https://bot.zhmail.ru/app.php")
//        val params: MutableList<NameValuePair> = ArrayList<NameValuePair>(2)
//        params.add(BasicNameValuePair("token", token))
//        params.add(BasicNameValuePair("type", "message"))
//        params.add(BasicNameValuePair("message", "build gradle buildExecutorCreated"))
//        httppost.setEntity(UrlEncodedFormEntity(params, "UTF-8"))
//        val response: HttpResponse = httpclient.execute(httppost)
    }

    override fun buildStarted(context: BuildContext) {
//        val httpclient: HttpClient = HttpClients.createDefault()
//        val httppost = HttpPost("https://bot.zhmail.ru/app.php")
//        val params: MutableList<NameValuePair> = ArrayList<NameValuePair>(2)
//        params.add(BasicNameValuePair("token", token))
//        params.add(BasicNameValuePair("type", "message"))
//        params.add(BasicNameValuePair("message", "build gradle started"))
//        httppost.setEntity(UrlEncodedFormEntity(params, "UTF-8"))
//        val response: HttpResponse = httpclient.execute(httppost)
    }

    override fun buildFinished(status: BuildStatus, context: BuildContext?) {
        when (status) {
            BuildStatus.SUCCESS -> {
                val httpclient: HttpClient = HttpClients.createDefault()
                val httppost = HttpPost("https://bot.zhmail.ru/app.php")
                val params: MutableList<NameValuePair> = ArrayList<NameValuePair>(2)
                params.add(BasicNameValuePair("token", token))
                params.add(BasicNameValuePair("type", "message"))
                params.add(BasicNameValuePair("message", "build success"))
                httppost.setEntity(UrlEncodedFormEntity(params, "UTF-8"))
                val response: HttpResponse = httpclient.execute(httppost)
            }

            BuildStatus.FAILED -> {
                val httpclient: HttpClient = HttpClients.createDefault()
                val httppost = HttpPost("https://bot.zhmail.ru/app.php")
                val params: MutableList<NameValuePair> = ArrayList<NameValuePair>(2)
                params.add(BasicNameValuePair("token", token))
                params.add(BasicNameValuePair("type", "message"))
                params.add(BasicNameValuePair("message", "build failed"))
                httppost.setEntity(UrlEncodedFormEntity(params, "UTF-8"))
                val response: HttpResponse = httpclient.execute(httppost)
            }

            BuildStatus.CANCELED -> {
                val httpclient: HttpClient = HttpClients.createDefault()
                val httppost = HttpPost("https://bot.zhmail.ru/app.php")
                val params: MutableList<NameValuePair> = ArrayList<NameValuePair>(2)
                params.add(BasicNameValuePair("token", token))
                params.add(BasicNameValuePair("type", "message"))
                params.add(BasicNameValuePair("message", "build canceled"))
                httppost.setEntity(UrlEncodedFormEntity(params, "UTF-8"))
                val response: HttpResponse = httpclient.execute(httppost)
            }
        }
    }

}