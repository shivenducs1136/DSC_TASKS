package com.example.newsapp

import android.app.ActionBar
import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.newsapp.databinding.FragmentLoginBinding
import com.example.newsapp.ui.AuthListener
import com.example.newsapp.ui.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginFragment: Fragment(R.layout.fragment_login), AuthListener {


    lateinit var binding: FragmentLoginBinding

    lateinit var navcontroller:NavController


    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog
    private var email =""
    private var password =""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        val viewModel= ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.logindetails= viewModel
        viewModel.authListener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navcontroller = Navigation.findNavController(view)

        binding.earthnews.animate().apply {

                         duration = 100000
                         rotationYBy(360f)



                 }.start()
            //configure profress dialoge
            progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Please Wait")
            progressDialog.setMessage("Logging in...")
            progressDialog.setCanceledOnTouchOutside(false)

            //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
//              checkUser()
//        firebasLogin()

            //handle click open register fragment
            binding.Registerbtn.setOnClickListener {
                navcontroller.navigate(R.id.registerFragment)
            }

            // handle click , begin login
            binding.loginbtn.setOnClickListener{
                // before login validate data
                checkUser()
                firebasLogin()
            }


    }


    override fun onStarted() {
//    Toast.makeText(context,"Login Started",Toast.LENGTH_SHORT).show()
        //get data
        email = binding.editTextTextEmailAddress.text.toString().trim()
        password = binding.editTextTextPassword.text.toString().trim()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            // invalid email format
            binding.editTextTextEmailAddress.setError("Invalid Email")
        }
        else if(TextUtils.isEmpty(password)){
            // enter password
            binding.editTextTextPassword.error="Please enter the password"

        }
        else
        {
            // data validates, begin login
//                checkUser()
            firebasLogin()
        }
    }

    override fun onSuccess() {
//        Toast.makeText(context,"Login Success",Toast.LENGTH_SHORT).show()
        fragmentManager?.popBackStack()
    }

    override fun onFailure(message: String) {
//        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()

    }

    private fun checkUser() {
        // if user is already logged in go to profile activity
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser!=null){
            // user is already logged in
            navcontroller.navigate(R.id.profileFragment)

        }
        else if(firebaseUser == null){
            navcontroller.navigate(R.id.registerFragment)
        }
    }

    private fun firebasLogin() {
        // show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            // login sucess
            progressDialog.dismiss()
            // getuserinfo
            val firebaseUser = firebaseAuth.currentUser
            val email =firebaseUser!!.email
//            Toast.makeText(context,"LoggedIn as $email",Toast.LENGTH_SHORT).show()
            // open profile
            navcontroller.navigate(R.id.profileFragment)



        }
            .addOnFailureListener{e->
                // login failed
                progressDialog.dismiss()
                Toast.makeText(context,"Login Failed due to user not registered",Toast.LENGTH_SHORT).show()
                navcontroller.navigate(R.id.registerFragment)
            }
    }


}