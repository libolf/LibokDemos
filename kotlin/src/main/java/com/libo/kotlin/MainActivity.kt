package com.libo.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import butterknife.BindView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    @BindView(R.id.kotlin_text) TextView kotlin_text;

    var name = "123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        kotlin_text.width = ViewGroup.LayoutParams.MATCH_PARENT
        kotlin_text.height = ViewGroup.LayoutParams.MATCH_PARENT
        kotlin_text.gravity = Gravity.CENTER
        kotlin_text.text = "hello kotlin"
        kotlin_text.text = name

    }
}
