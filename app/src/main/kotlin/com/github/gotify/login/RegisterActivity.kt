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
import com.github.gotify.client.model.Error
import com.github.gotify.client.model.User
import com.github.gotify.databinding.ActivityRegisterBinding
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
    private fun onCreateUser(registerInfo: User, modifiedHttpUrl: String) {
        Utils.showSnackBar(this, "注册成功")
        settings.signUrl = modifiedHttpUrl
        // 创建一个 Handler，并在一段时间后执行 finish() 方法
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 1000) // 延迟 1 秒执行
    }

    private fun doRegisterAccount(callback: (Boolean) -> Unit) {
        val username = binding.registerUsername.text.toString()
        val password = binding.registerPassword.text.toString()
        val xSessionId = binding.xSessionId.text.toString()

        val modifiedHttpUrl = settings.signUrl
        try {
            ClientFactory.registerUser(
                settings.signUrl,
                settings.sslSettings()
            ).createRegister(username, password, xSessionId)
                .enqueue(
                    Callback.callInUI(
                        this,
                        onSuccess = Callback.SuccessBody { registerInfo ->
                            onCreateUser(registerInfo, modifiedHttpUrl)
                            // 注册成功，调用回调函数并传递 true
                            callback(true)
                        },
                        onError = { exception ->
                            when (exception.code) {
                                429 -> {
                                    Utils.showSnackBar(this, "x-session-id不合法")
                                }
                                430 -> {
                                    Utils.showSnackBar(this, "您已经注册过了")
                                }
                                431 -> {
                                    Utils.showSnackBar(this, "用户名已存在")
                                }
                                else -> {
                                    Utils.showSnackBar(this, "注册失败")
                                }
                            }
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
