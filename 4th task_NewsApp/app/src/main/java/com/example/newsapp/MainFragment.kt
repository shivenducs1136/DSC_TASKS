package com.example.newsapp

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL ="https://newsapi.org/"

class MainFragment : Fragment(R.layout.fragment_main), Toolbar.OnMenuItemClickListener {


    lateinit var binding: FragmentMainBinding
    lateinit var navController: NavController
//    lateinit var viewModel:NewsViewHolder
    private lateinit var mAdapter: NewsListAdapter

    lateinit var countDownTimer: CountDownTimer

    private var titleList = mutableListOf<String>()
    private var descriptionList = mutableListOf<String>()
    private var imagelist = mutableListOf<String>()
    private var linksList = mutableListOf<String>()
    private var pubList = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
//        val navHostFragment = findNavController().findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        navcontroller = navHostFragment.navController
        binding.mytoolbar?.setOnMenuItemClickListener(this)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
// **********************************************************

// **************************************************************
        makeAPIRequest()

    }
    private fun setrecyclerview() {
        binding.recview.layoutManager = LinearLayoutManager(context)
        binding.recview.adapter = NewsListAdapter(titleList, descriptionList,imagelist,linksList,pubList)
    }


    private fun addToList(title:String,description:String,image:String,link:String,date:String){
        titleList.add(title)
        descriptionList.add(description)
        imagelist.add(image)
        linksList.add(link)
        pubList.add(date)
    }

private fun fadeInfromcolour(){
    v_colorscreen.animate().apply {
        alpha( 0f)
        duration = 3000

    }.start()
}

    private fun makeAPIRequest() {
        progressbar.visibility = View.VISIBLE
        // ******************************************************************
        var cache: okhttp3.Cache = okhttp3.Cache(context?.cacheDir, 10 * 1024 * 1024)
        var okHttpClient: okhttp3.OkHttpClient = okhttp3.OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(Interceptor { chain ->
                var request: Request = chain.request()
                request = if (CheckConnect.checkConnectivity(context)!!)
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 5
                    ).build()
                chain.proceed(request)
            })
            .build()
        // **********************************************************************

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(APIRequest::class.java)

        GlobalScope.launch (Dispatchers.IO){
            try {
                val response = api.getNews()
                for(article in response.articles){
                    Log.i("MainFragment","Results = $article")
                    addToList(article.title,article.description,article.urlToImage,article.url,article.publishedAt)
                }
                withContext(Dispatchers.Main){

                  setrecyclerview()
                    fadeInfromcolour()
                    progressbar.visibility = View.GONE

                }
            } catch (e: Exception){
                Log.e("MainFragment",e.toString())
                withContext(Dispatchers.Main){
                    attemptRequestAgain()
                }
            }
        }
        }

     fun attemptRequestAgain() {
        countDownTimer = object: CountDownTimer(5*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
               Log.i("MainFragment","Could not retrieve data... Trying again in ${millisUntilFinished/1000} seconds")
            }

            override fun onFinish() {
                makeAPIRequest()
                countDownTimer.cancel()
            }

        }
        countDownTimer.start()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.abtitem -> findNavController().navigate(R.id.action_mainFragment_to_aboutFragment2)
            R.id.licitem -> findNavController().navigate(R.id.action_mainFragment_to_licenseFragment2)
            R.id.lgn ->     findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
            R.id.probtn -> findNavController().navigate(R.id.action_mainFragment_to_profileFragment)

        }
        return true
    }


}




















































//    private fun fetchdata(){
////        val url = "https://saurav.tech/NewsAPI/top-headlines/category/general/in.json"
//        val url = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=a2dcdde82b024544b87dcdfc364f3658"
//        val jsonObjectRequest = JsonObjectRequest (
//            Request.Method.GET,
//            url,
//            null,
//            {
//                val newsJsonArray = it.getJSONArray("articles")
//                val newsArray = ArrayList<News>()
//                for(i in 0 until newsJsonArray.length()){
//                    val newsJsonObject = newsJsonArray.getJSONObject(i)
//                    val news = News(
//                        newsJsonObject.getString("title"),
//                        newsJsonObject.getString("author"),
//                        newsJsonObject.getString("url"),
//                        newsJsonObject.getString("urlToImage")
//                    )
//                    newsArray.add(news)
//                }
//                mAdapter.updateNews(newsArray)
//            },
//            {
//                Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
//            }
//        )
//        MySingleton.getInstance(context!!.applicationContext).addToRequestQueue(jsonObjectRequest)
//    }

//    override fun onItemClicked(item: News) {
//        Toast.makeText(context,"Bakchod bhosadike $item",Toast.LENGTH_SHORT).show()
////    }
////    class MySingleton constructor(context: Context) {
//        companion object {
//            @Volatile
//            private var INSTANCE: MySingleton? = null
//            fun getInstance(context: Context) =
//                INSTANCE ?: synchronized(this) {
//                    INSTANCE ?: MySingleton(context).also {
//                        INSTANCE = it
//                    }
//                }
//        }
//        val imageLoader: ImageLoader by lazy {
//            ImageLoader(requestQueue,
//                object : ImageLoader.ImageCache {
//                    private val cache = LruCache<String, Bitmap>(20)
//                    override fun getBitmap(url: String): Bitmap? {
//                        return cache.get(url)
//                    }
//                    override fun putBitmap(url: String, bitmap: Bitmap) {
//                        cache.put(url, bitmap)
//                    }
//                })
//        }
//        private val requestQueue: RequestQueue by lazy {
//            // applicationContext is key, it keeps you from leaking the
//            // Activity or BroadcastReceiver if someone passes one in.
//            Volley.newRequestQueue(context.applicationContext)
//        }
//        fun <T> addToRequestQueue(req: Request<T>) {
//            requestQueue.add(req)
//        }
//    }
//}
