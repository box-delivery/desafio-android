package com.jlmcdeveloper.githubjavapop.ui.main

import androidx.lifecycle.ViewModel
import com.jlmcdeveloper.githubjavapop.data.Repository

class MainViewModel(val repository: Repository) : ViewModel(){
    fun test() {
        repository.test()
    }

}