package com.github.gotify.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.github.gotify.Settings
import com.github.gotify.Utils
import com.github.gotify.api.ApiException
import com.github.gotify.api.Callback
import com.github.gotify.api.ClientFactory
import com.github.gotify.client.model.Register
import com.github.gotify.client.model.RegisterResponse
import com.github.gotify.databinding.ActivityRegisterBinding
import okhttp3.HttpUrl
import org.tinylog.kotlin.Logger

internal class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var settings: Settings
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        settings = Settings(this)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarDrawer.toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowCustomEnabled(true)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        binding.registerLogin.setOnClickListener {
            // 禁用按钮
            binding.registerLogin.isEnabled = false
            doRegisterAccount { binding.registerLogin.isEnabled = true }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 处理 ActionBar 中的返回按钮点击事件
        return when (item.itemId) {
            android.R.id.home -> {
                // 返回上一个活动或父活动
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun onCreateUser(registerInfo: RegisterResponse) {
        if (registerInfo.code != "200") {
            Utils.showSnackBar(this, registerInfo.message)
        } else {
            Utils.showSnackBar(this, registerInfo.message)
            // 创建一个 Handler，并在一段时间后执行 finish() 方法
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 1000) // 延迟 1 秒执行
        }
    }

    private fun doRegisterAccount(callback: (Boolean) -> Unit) {
        // 注册账号
        val url = settings.url
        val port = binding.serverAddress.text.toString()
        val modifiedHttpUrl: String
        // 判断port是否为整数
        val portInt = port.toIntOrNull()
        if (portInt != null) {
            // port 是一个整数
            val httpUrl = HttpUrl.parse(url)
            if (httpUrl != null) {
                // 检查 URL 是否包含端口信息
                modifiedHttpUrl = httpUrl.newBuilder().port(port.toInt()).build().toString()
            } else {
                // URL 不是一个有效的 HTTP URL
                Utils.showSnackBar(this, "服务器地址不是一个有效的URL")
                return
            }
        } else {
            // port 不是一个整数
            Utils.showSnackBar(this, "port不是一个整数")
            return
        }

        val username = binding.registerUsername.text.toString()
        val password = binding.registerPassword.text.toString()
        val xSessionId = binding.xSessionId.text.toString()
        val registerForm = Register(username, password, xSessionId)

        try {
            ClientFactory.registerUser(
                modifiedHttpUrl,
                settings.sslSettings()
            ).createRegister(registerForm)
                .enqueue(
                    Callback.callInUI(
                        this,
                        onSuccess = Callback.SuccessBody { registerInfo ->
                            onCreateUser(registerInfo)
                            // 注册成功，调用回调函数并传递 true
                            callback(true)
                        },
                        onError = { exception ->
                            Logger.error(exception, "注册失败")
                            Utils.showSnackBar(this, "注册失败")
                            // 注册失败，调用回调函数并传递 false
                            callback(false)
                        }
                    )
                )
        } catch (apiException: ApiException) {
            Utils.showSnackBar(this, "注册失败")
            // 注册失败，调用回调函数并传递 false
            callback(false)
        }
    }
}
