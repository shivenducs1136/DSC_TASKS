package com.example.newsapp

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {

    var toolbar:Toolbar ? = null
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragments(MainFragment())
        toolbar = findViewById<Toolbar>(R.id.mytoolbar)
        toolbar?.setOnMenuItemClickListener(this)
        firebaseAuth = FirebaseAuth.getInstance()


        backbtn.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,MainFragment()).commit()
        }

    }


    fun loadFragments(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit()
        }

        return true
    }



    override fun onMenuItemClick(item: MenuItem?): Boolean {
        var fragment: Fragment? = null
        when (item?.itemId) {
            R.id.abtitem -> fragment = AboutFragment()
            R.id.licitem -> fragment = LicenseFragment()
            R.id.lgn -> {
                val firebaseUser = firebaseAuth.currentUser
                if(firebaseUser==null){
                    // user is already logged in
                    fragment = LoginFragment()
            }
            else{
                 Toast.makeText(this,"User Already LoggedIn/Registered",Toast.LENGTH_LONG).show()
                }
            }
            R.id.probtn -> fragment = ProfileFragment()
        }
        return loadFragments(fragment)
    }


}
//R.id.abtitem-> {
//    startActivity(Intent(this,AboutFragment::class.java))
//}
//R.id.licitem-> Toast.makeText(this,"Awesome2",Toast.LENGTH_SHORT).show()