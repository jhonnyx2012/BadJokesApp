package com.cornershopapp.core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VIEW_BINDING: ViewBinding> : AppCompatActivity() {

    /**
     * This attribute should not be called before [BaseActivity.onCreate] or after
     * [BaseActivity.onDestroy]
     */
    @JvmField
    protected var binding: VIEW_BINDING? = null

    abstract fun inflateBinding(): VIEW_BINDING

    fun inflateWith(bindingInflater: (LayoutInflater) -> VIEW_BINDING) : VIEW_BINDING {
        return bindingInflater.invoke(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateBinding()
        setContentView(binding!!.root)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}