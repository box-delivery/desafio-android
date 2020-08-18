package com.jlmcdeveloper.githubjavapop.ui.baseholder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jlmcdeveloper.githubjavapop.databinding.ItemLoadingBinding

//====================== ViewHolder Loading =============================
class LoadingHolder<T>( binding: ItemLoadingBinding) :
    BaseViewHolder<T>(binding.root) {

    override fun bind(item: T?) { }
}