package com.cornershopapp.jokes.presentation

import android.os.Bundle
import androidx.activity.viewModels
import com.cornershopapp.core.presentation.*
import com.cornershopapp.jokes.databinding.ActivityJokesBinding
import com.cornershopapp.jokes.di.CommonInjection
import com.cornershopapp.jokes.domain.model.Joke

class JokesActivity : BaseActivity<ActivityJokesBinding>() {

    private val viewModel : JokesViewModel by viewModels { CommonInjection }
    override fun inflateBinding() = inflateWith(ActivityJokesBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeStates()
        setupListeners()
        viewModel.onStart()
    }

    private fun observeStates() {
        viewModel.stateLiveData.observe(this) {
            when(it) {
                is JokesState.Loading -> setLoading()
                is JokesState.Loaded -> setLoaded(it.content)
                is JokesState.Error -> setError(it.message)
            }
        }
    }

    private fun setupListeners() {
        binding?.moreButton?.setOnClickListener { viewModel.onMoreButtonClicked() }
    }

    private fun setError(message: String) {
        binding?.run {
            moreButton.text = "Retry"
            textFirstLine.text = CommonInjection.provideResourcesRepository().defaultErrorMessageString
            textSecondLine.text = message
            progressLoading.hide()
            textCategory.hide()
            textFirstLine.show()
            textSecondLine.show()
            moreButton.enable()
        }
    }

    private fun setLoaded(content: Joke) {
        binding?.run {
            textFirstLine.text = content.firstLine
            textSecondLine.text = content.secondLine
            textCategory.text = content.category
            moreButton.text = "Give me more!"
            progressLoading.hide()
            textFirstLine.show()
            textSecondLine.show()
            textCategory.show()
            moreButton.enable()
        }
    }

    private fun setLoading() {
        binding?.run {
            progressLoading.show()
            textFirstLine.hide()
            textSecondLine.hide()
            textCategory.hide()
            moreButton.disable()
        }
    }
}