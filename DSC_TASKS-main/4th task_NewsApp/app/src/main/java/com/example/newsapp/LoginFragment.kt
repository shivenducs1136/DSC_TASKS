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
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.newsapp.databinding.FragmentLoginBinding
import com.example.newsapp.ui.AuthListener
import com.example.newsapp.ui.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

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


            //configure profress dialoge
            progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Please Wait")
            progressDialog.setMessage("Logging in...")
            progressDialog.setCanceledOnTouchOutside(false)

            //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()



            //handle click open register fragment
            binding.Registerbtn.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

            }

            // handle click , begin login


    }

    private fun checkUser() {
        // if user is already logged in go to profile activity
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser!=null){
            // user is already logged in
            view?.let { Snackbar.make(it,"You are Logged In",Snackbar.LENGTH_SHORT).show() }

            findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
        }
        else{
            val imm: InputMethodManager =
                requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
            firebasLogin()
        }
    }
    override fun onStarted() {
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
            checkUser()
        }
    }
    private fun firebasLogin() {
        // show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            // login sucess
            progressDialog.dismiss()
            // getuserinfo
//            val firebaseUser = firebaseAuth.currentUser
//            val email =firebaseUser!!.email
            view?.let { Snackbar.make(it,"Logged In As ${email}", Snackbar.LENGTH_SHORT).show() }

            // open profile
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)

        }
            .addOnFailureListener{e->
                // login failed
                progressDialog.dismiss()
                view?.let { Snackbar.make(it,"You are not Registered yet",Snackbar.LENGTH_SHORT).show() }

            }
    }
    override fun onSuccess() {
        fragmentManager?.popBackStack()
    }

    override fun onFailure(message: String) {

    }






}