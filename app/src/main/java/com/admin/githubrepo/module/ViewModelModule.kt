package com.admin.kotlin_apigithub

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.admin.githubrepo.module.ViewModelFactory
import com.admin.githubrepo.ui.main.MainViewModel
import com.admin.githubrepo.ui.main.MainViewModel2
import com.faz.tudo.express.di.module.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap




/**
 * Nos faz injetar dependências via injeção de construtor
 */

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModelViewModel(mainkViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel2::class)
    abstract fun bindsMainViewModelViewModel2(mainkViewModel2: MainViewModel2): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory



}
