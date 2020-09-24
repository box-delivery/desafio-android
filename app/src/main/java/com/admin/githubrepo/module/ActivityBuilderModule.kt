package com.admin.githubrepo.module

import com.admin.githubrepo.MainActivity
import com.admin.githubrepo.ui.main.PullActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * O módulo que fornece o serviço de injeção do Android para Activities.
 * */

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun pullacv(): PullActivity




}
