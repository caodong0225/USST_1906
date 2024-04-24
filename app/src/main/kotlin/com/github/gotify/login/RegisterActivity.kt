package com.github.gotify.login

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.github.gotify.databinding.ActivityRegisterBinding

internal class RegisterActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarDrawer.toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowCustomEnabled(true)
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

}