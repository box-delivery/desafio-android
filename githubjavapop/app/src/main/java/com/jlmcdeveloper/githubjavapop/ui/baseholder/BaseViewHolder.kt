package com.jlmcdeveloper.githubjavapop.ui.baseholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
    abstract fun bind(item: T?)
}