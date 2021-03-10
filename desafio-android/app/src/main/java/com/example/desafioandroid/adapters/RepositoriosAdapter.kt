package com.example.desafioandroid.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.desafioandroid.R
import com.example.desafioandroid.activitys.PullsActivity
import com.example.desafioandroid.api.RetrofitClient
import com.example.desafioandroid.models.Pull
import com.example.desafioandroid.models.Repositorio
import com.example.desafioandroid.models.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_repository.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RepositoriosAdapter : RecyclerView.Adapter<RepositoriosAdapter.ViewHolder>() {

    private var list: ArrayList<Repositorio> = ArrayList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(repositorio: Repositorio) {
            with(itemView) {
                nameRepo.text = repositorio.name
                descRepo.text = repositorio.description
                numForks.text = repositorio.forks.toString()
                numStars.text = repositorio.watchers.toString()
                Picasso.get().load(repositorio.owner.avatar_url).into(avatarUser)
                loginUser.text = repositorio.owner.login
                RetrofitClient.instance.getUser(login = repositorio.owner.login).enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        nomeUser.text = response.body()?.name
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(itemView.context, "${t.message}", Toast.LENGTH_SHORT).show()
                    }

                })
                itemView.setOnClickListener {
                    RetrofitClient.instance.getPulls(login = repositorio.owner.login, repository = repositorio.name).enqueue(object : Callback<ArrayList<Pull>> {
                        override fun onResponse(
                                call: Call<ArrayList<Pull>>,
                                response: Response<ArrayList<Pull>>
                        ) {
                            val pulls: ArrayList<Pull>? = response.body()

                            val i = Intent(itemView.context, PullsActivity::class.java)
                            i.putExtra("nome", repositorio.name)
                            i.putParcelableArrayListExtra("pulls", pulls)
                            itemView.context.startActivity(i)
                        }

                        override fun onFailure(call: Call<ArrayList<Pull>>, t: Throwable) {
                            Toast.makeText(itemView.context, "${t.message}", Toast.LENGTH_SHORT).show()
                            Log.i("Error", t.message.toString())
                        }

                    })

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.card_repository, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun addList(items: ArrayList<Repositorio>){
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun clear(){
        list.clear()
        notifyDataSetChanged()
    }
}

