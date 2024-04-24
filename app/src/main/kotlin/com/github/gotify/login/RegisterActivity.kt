package com.github.gotify.login

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.gotify.R
import com.github.gotify.Settings
import com.github.gotify.Utils
import com.github.gotify.api.Api
import com.github.gotify.api.ApiException
import com.github.gotify.api.ClientFactory
import com.github.gotify.client.api.RegisterApi
import com.github.gotify.client.model.Register
import com.github.gotify.databinding.ActivityRegisterBinding
import okhttp3.HttpUrl
import org.tinylog.kotlin.Logger

internal class RegisterActivity : AppCompatActivity()  {
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
        if (!settings.tokenExists()) {
            Toast.makeText(
                applicationContext,
                R.string.not_loggedin_share,
                Toast.LENGTH_SHORT
            ).show()
            finish()
            return
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        binding.registerLogin.setOnClickListener { doRegisterAccount() }
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
    private fun doRegisterAccount() {
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
                modifiedHttpUrl = httpUrl.newBuilder()
                        .port(port.toInt())
                        .build().toString()
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

        val client = ClientFactory.clientToken(
            modifiedHttpUrl,
            settings.sslSettings(),
            settings.token
        )
        try{
            val registerApi = client.createService(RegisterApi::class.java)
            val username = binding.registerUsername.text.toString()
            val password = binding.registerPassword.text.toString()
            val x_session_id = binding.xSessionId.text.toString()
            val registerForm = Register()
            registerForm.username = username
            registerForm.password = password
            registerForm.x_session_id = x_session_id
            Api.execute(registerApi.createRegister(registerForm))
        } catch (apiException: ApiException) {
            Logger.error(apiException, "注册失败")
        }
    }


}