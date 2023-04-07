package project.network

import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import project.listeners.token
import project.model.BuildStatusModel

class BuildNotifierImpl : BuildNotifier {

    override operator fun invoke(buildStatusModel: BuildStatusModel, projectName: String, buildTime: String) {
//        CoroutineScope(Dispatchers.IO + Job()).launch {
        notify(buildStatusModel, projectName, buildTime)
//        }
    }

    private fun notify(buildStatusModel: BuildStatusModel, projectName: String, buildTime: String = "0") {
        val httpclient: org.apache.http.client.HttpClient = HttpClients.createDefault()
        val httppost = HttpPost("https://api.zhmail.ru/bots/buildup/notification")
        val params: MutableList<NameValuePair> = ArrayList<NameValuePair>(2)
        params.add(BasicNameValuePair("token", token))
        params.add(BasicNameValuePair("status", buildStatusModel.name))
        params.add(BasicNameValuePair("projectName", projectName))
        params.add(BasicNameValuePair("buildTime", buildTime))
        httppost.setEntity(UrlEncodedFormEntity(params, "UTF-8"))
        val response: HttpResponse = httpclient.execute(httppost)
    }
}