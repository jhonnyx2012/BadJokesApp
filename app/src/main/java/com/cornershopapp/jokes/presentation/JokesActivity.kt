package com.cornershopapp.jokes.presentation

import android.os.Bundle
import androidx.activity.viewModels
import com.cornershopapp.core.presentation.*
import com.cornershopapp.jokes.databinding.ActivityJokesBinding
import com.cornershopapp.jokes.di.CommonInjection

class JokesActivity : BaseActivity<ActivityJokesBinding>() {

    private val viewModel : JokesViewModel by viewModels { CommonInjection }
    override fun inflateBinding() = inflateWith(ActivityJokesBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeStates()
        setupListeners()
        viewModel.onStart()
    }

    private fun setupListeners() {
        binding?.moreButton?.setOnClickListener { viewModel.onMoreButtonClicked() }
    }

    private fun observeStates() {
        viewModel.stateLiveData.observe(this) {
            binding?.run {
                when(it) {
                    is JokesState.Loading -> {
                        progressLoading.show()
                        textFirstLine.hide()
                        textSecondLine.hide()
                        textCategory.hide()
                        moreButton.disable()
                    }
                    is JokesState.Loaded -> {
                        textFirstLine.text = it.content.firstLine
                        textSecondLine.text = it.content.secondLine
                        textCategory.text = it.content.category
                        moreButton.text = "Give me more!"
                        progressLoading.hide()
                        textFirstLine.show()
                        textSecondLine.show()
                        textCategory.show()
                        moreButton.enable()
                    }
                    is JokesState.Error -> {
                        moreButton.text = "Retry"
                        textFirstLine.text = CommonInjection.provideResourcesRepository().defaultErrorMessageString
                        textSecondLine.text = it.message
                        progressLoading.hide()
                        textCategory.hide()
                        textFirstLine.show()
                        textSecondLine.show()
                        moreButton.enable()
                    }
                }
            }
        }
    }
}