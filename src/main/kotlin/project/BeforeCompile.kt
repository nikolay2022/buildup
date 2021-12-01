package project

import com.intellij.openapi.compiler.CompileContext
import com.intellij.openapi.compiler.CompileTask
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

class BeforeCompile : CompileTask {
    override fun execute(context: CompileContext): Boolean {
        val httpclient: HttpClient = HttpClients.createDefault()
        val httppost = HttpPost("https://bot.zhmail.ru/app.php")
        val params: MutableList<NameValuePair> = ArrayList<NameValuePair>(2)
        params.add(BasicNameValuePair("token", token))
        params.add(BasicNameValuePair("type", "message"))
        params.add(BasicNameValuePair("message", "build execute"))
        httppost.setEntity(UrlEncodedFormEntity(params, "UTF-8"))
        val response: HttpResponse = httpclient.execute(httppost)
        return true
    }
}