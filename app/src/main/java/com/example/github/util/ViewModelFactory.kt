package com.example.github.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.github.data.repo.Repos

class ViewModelFactory(private val arg: Repos) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(arg.javaClass)
                .newInstance(arg)
        }

}