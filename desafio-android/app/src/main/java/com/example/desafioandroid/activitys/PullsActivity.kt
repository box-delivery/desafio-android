package com.example.desafioandroid.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafioandroid.R
import com.example.desafioandroid.adapters.PullsAdapter
import com.example.desafioandroid.models.Pull

import kotlinx.android.synthetic.main.activity_pulls.*

class PullsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pulls)
        val pulls: ArrayList<Pull>? = intent.extras?.getParcelableArrayList<Pull>("pulls")
        var nome = intent.extras?.getString("nome")
        this.title = nome
        val adapter = PullsAdapter()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvPulls.layoutManager = layoutManager
        rvPulls.setHasFixedSize(true)
        if (pulls != null) {
            if (pulls.isEmpty()){
                Toast.makeText(applicationContext, "Sem pull requests para este repositório!", Toast.LENGTH_LONG).show()
            }
            adapter.addList(pulls)
        }
        
        rvPulls.adapter = adapter
    }


}