package project

import com.intellij.openapi.compiler.CompilationStatusListener
import com.intellij.openapi.compiler.CompileContext
import com.intellij.openapi.compiler.CompilerTopics
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import kotlin.random.Random.Default.nextBoolean

open class Drumroll(private val project: Project) {

    enum class BuildState { Success, Error, Warning }

    private val messages by lazy { project.messageBus.connect() }
    private val success = arrayOf("Ba-Dum-Tss!", "wow", "boring")
    private val error = arrayOf("failure", "doh", "oh_geez", "better_call_saul")
    private val warnings = arrayOf("metal_gear")
    private val timeCards = arrayOf("eternity_later", "moments_later", "pair_of_pants_later", "inches_later")

    init {
        messages.subscribe(CompilerTopics.COMPILATION_STATUS, object : CompilationStatusListener {
            override fun compilationFinished(aborted: Boolean, errors: Int, warnings: Int, compileContext: CompileContext) {
                notifyState(
                        when {
                            errors > 0 -> BuildState.Error
                            warnings > 0 -> BuildState.Warning
                            else -> BuildState.Success
                        }
                )
            }
        })
    }


    fun notifyState(state: BuildState) {
                if (nextBoolean() && nextBoolean())
                    timeCards.random()
                else
                    when (state) {
                        BuildState.Success -> {
                            Messages.showMessageDialog("HUI1","hhh", Messages.getInformationIcon())
                            val httpclient: HttpClient = HttpClients.createDefault()
                            val httppost = HttpPost("http://bot.zhmail.ru/app.php")
                            val params: MutableList<NameValuePair> = ArrayList<NameValuePair>(2)
                            params.add(BasicNameValuePair("tgid", id))
                            params.add(BasicNameValuePair("type", "message"))
                            httppost.setEntity(UrlEncodedFormEntity(params, "UTF-8"))
                            val response: HttpResponse = httpclient.execute(httppost)
                            success.random()
                        }
                        BuildState.Error ->{
                            val httpclient: HttpClient = HttpClients.createDefault()
                            val httppost = HttpPost("http://bot.zhmail.ru/app.php")
                            val params: MutableList<NameValuePair> = ArrayList<NameValuePair>(2)
                            params.add(BasicNameValuePair("tgid", id))
                            params.add(BasicNameValuePair("type", "message"))
                            httppost.setEntity(UrlEncodedFormEntity(params, "UTF-8"))
                            val response: HttpResponse = httpclient.execute(httppost)


                            Messages.showMessageDialog("HUI2","ff",Messages.getInformationIcon())

                            error.random()
                        }
                        BuildState.Warning -> {
                            Messages.showMessageDialog("HUI3","ggg",Messages.getInformationIcon())
                            val httpclient: HttpClient = HttpClients.createDefault()
                            val httppost = HttpPost("http://bot.zhmail.ru/app.php")
                            val params: MutableList<NameValuePair> = ArrayList<NameValuePair>(2)
                            params.add(BasicNameValuePair("tgid", id))
                            params.add(BasicNameValuePair("type", "message"))
                            httppost.setEntity(UrlEncodedFormEntity(params, "UTF-8"))
                            val response: HttpResponse = httpclient.execute(httppost)
                            warnings.random()
                        }
                    }
    }
}

