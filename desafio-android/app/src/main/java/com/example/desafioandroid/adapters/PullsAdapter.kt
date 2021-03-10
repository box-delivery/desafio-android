package com.example.desafioandroid.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.desafioandroid.R
import com.example.desafioandroid.api.RetrofitClient
import com.example.desafioandroid.models.Pull
import com.example.desafioandroid.models.Repositorio
import com.example.desafioandroid.models.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_pulls.view.*
import kotlinx.android.synthetic.main.card_repository.view.*
import retrofit2.Call
import retrofit2.Response

class PullsAdapter() : RecyclerView.Adapter<PullsAdapter.PullsViewHolder>() {

    private val list: ArrayList<Pull> = ArrayList()

    inner class PullsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pull: Pull) {
            with(itemView) {
                titlePull.text = pull.title
                bodyPull.text = pull.body
                Picasso.get().load(pull.user.avatar_url).into(avatarUserPull)
                loginUserPull.text = pull.user.login
                RetrofitClient.instance.getUser(login = pull.user.login).enqueue(object : retrofit2.Callback<User>{
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        nomeUserPull.text = response.body()?.name
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(itemView.context, "${t.message}", Toast.LENGTH_SHORT).show()
                    }

                })
                itemView.setOnClickListener {
                    getUrlFromIntent(itemView, pull.html_url)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullsViewHolder {
        return PullsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_pulls, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PullsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun addList(items: ArrayList<Pull>){
        this.list.addAll(items)
        notifyDataSetChanged()
    }

    fun getUrlFromIntent(view: View, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        view.context.startActivity(intent)
    }
}