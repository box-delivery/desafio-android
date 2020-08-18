package com.jlmcdeveloper.githubjavapop.ui.baseholder

import com.jlmcdeveloper.githubjavapop.databinding.ItemEmptyBinding


class EmptyHolder<T>(private val loadList: ()-> Unit, private val binding: ItemEmptyBinding) :
    BaseViewHolder<T>(binding.root) {

    override fun bind(item: T?) {
        binding.btnUpdate.setOnClickListener { loadList() }
    }
}