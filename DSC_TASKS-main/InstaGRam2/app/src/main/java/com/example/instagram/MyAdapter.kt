package com.example.instagram

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.post.view.*

class MyAdapter (val Datapost:List<Datapost>): RecyclerView.Adapter<MyAdapter.MyViewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view =inflater.inflate(R.layout.post,parent,false)
        return MyViewholder(view)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {

        holder.txtUsername.text = Datapost[position].user_name
        holder.txtcommenter.text = Datapost[position].commenter


    }

    override fun getItemCount(): Int {
        return Datapost.size
    }
    class MyViewholder( val itemView: View) : RecyclerView.ViewHolder(itemView){
        var txtUsername = itemView.findViewById<TextView>(R.id.username)
        var imgdp = itemView.findViewById<ImageView>(R.id.dp_profile)
        var imgpost = itemView.findViewById<ImageView>(R.id.post_pic)
        var txtcommenter = itemView.findViewById<TextView>(R.id.commented)
        fun bind(Datapost: Datapost){
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.dpdp)
                .error(R.drawable.ic_instagram)
            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(Datapost.img_dp_url)
                .into(imgdp)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(Datapost.img_post_url)
                .into(imgpost)




        }
    }
}
