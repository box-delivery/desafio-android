package com.admin.githubrepo.adapter

import com.admin.githubrepo.model.Pull
import com.admin.githubrepo.model.Repo

interface CellClickListene {

    fun onCellClickListener(data : Repo)

    fun onCellClickListener2(data : Pull)
}