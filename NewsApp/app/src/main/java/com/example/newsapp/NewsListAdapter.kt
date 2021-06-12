package com.example.newsapp

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class NewsListAdapter(
    private var titles: List<String>,
    private var details: List<String>,
    private var images: List<String>,
    private var links: List<String>,
    private val pubdate:List<String>
): RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    //    private val item: ArrayList<News> = ArrayList()
    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itmtitles: TextView = itemView.findViewById(R.id.headlineOfNews)
        val itmdetails: TextView = itemView.findViewById(R.id.newsDes)
        val itmimages: ImageView = itemView.findViewById(R.id.imageOfNews)
        val publishedate: TextView? = null

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
//                val intent = Intent(Intent.ACTION_VIEW)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(links[position])
                startActivity(itemView.context, intent, null)
            }
        }
        init{
            itemView.setOnLongClickListener { v:View ->
                val position:Int = adapterPosition
                val dataTime: String = pubdate[position]
                Snackbar.make(itemView,"This news was published at $dataTime hours",Snackbar.LENGTH_SHORT).show()
                true
            }

        }

    }
//


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_view_recview, parent, false)
            return NewsViewHolder(view)

        }

        override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

            holder.itmtitles.text = titles[position]
            holder.itmdetails.text = details[position]

            Glide.with(holder.itmimages).load(images[position]).into(holder.itmimages)

        }

        override fun getItemCount(): Int {
            return titles.size
        }
    }
