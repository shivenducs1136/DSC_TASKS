package com.example.instagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val postsObjects : MutableList<Datapost> = mutableListOf<Datapost>()
        postsObjects.add(Datapost("Shiv.endu","badhiya bhamiya","https://picsum.photos/450","https://picsum.photos/450"))
        postsObjects.add(Datapost("Shiv.endu1","badhiya bhamiya1","https://picsum.photos/450","https://picsum.photos/450"))
        postsObjects.add(Datapost("Shiv.endu2","badhiya bhamiya2","https://picsum.photos/450","https://picsum.photos/450"))
        postsObjects.add(Datapost("Shiv.endu3","badhiya bhamiya3","https://picsum.photos/450","https://picsum.photos/450"))
        postsObjects.add(Datapost("Shiv.endu4","badhiya bhamiya4","https://picsum.photos/450","https://picsum.photos/450"))
        postsObjects.add(Datapost("Shiv.endu5","badhiya bhamiya5","https://picsum.photos/450","https://picsum.photos/450"))



        stories.adapter = MyAdapter(postsObjects)
        stories.layoutManager = LinearLayoutManager(this)

    }
}