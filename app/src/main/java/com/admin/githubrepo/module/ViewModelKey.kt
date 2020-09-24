package com.faz.tudo.express.di.module

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass


/**
 * ViewModel Key, que serve como chave única para manter as instâncias do viewmodel na fábrica
 * */
@Target(AnnotationTarget.FUNCTION)
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)