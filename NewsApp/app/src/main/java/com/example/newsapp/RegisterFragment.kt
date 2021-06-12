package com.example.newsapp

import android.app.ProgressDialog
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.newsapp.databinding.FragmentRegisterBinding
import com.example.newsapp.ui.RegAuthViewModel
import com.example.newsapp.ui.RegAuthlistener
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class RegisterFragment:Fragment (R.layout.fragment_register), RegAuthlistener {

    lateinit var binding:FragmentRegisterBinding
    lateinit var navController: NavController
    lateinit var progressDialog:ProgressDialog
    lateinit var firebaseAuth: FirebaseAuth

    lateinit var email:String
    lateinit var name:String
    lateinit var password:String
    lateinit var cnfpassword:String
    lateinit var age:String
    lateinit var phonenum:String
    lateinit var address:String
    lateinit var bio:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_register,container,false)
        val regauthviewModel= ViewModelProviders.of(this).get(RegAuthViewModel::class.java)
        binding.registerdetails= RegAuthViewModel()
        regauthviewModel.regauthListener= this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)
        binding.earthnewsRegscreen.animate().apply {
            duration = 10000
            rotationYBy(360f)

        }.start()
        //configure profress dialoge
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firbese auth
        firebaseAuth = FirebaseAuth.getInstance()


        // handle click , beign signup
        binding.registeryoureslf.setOnClickListener {
            // validate data
            on_Started()
        }
    }

    private fun firebaseSignup() {
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            // sign up sucess
            progressDialog.dismiss()
                //get current usert\
            val firebaseAuth = firebaseAuth.currentUser
//            val email = firebaseAuth!!.email
//            Toast.makeText(context,"Account created with email $email",Toast.LENGTH_SHORT).show()
            var user = User( name, age, phonenum,address,bio,email)
//            FirebaseDatabase.getInstance().getReference("Users").setValue( user ).child(FirebaseAuth.getInstance().currentUser!!.uid)
            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
                .setValue(user).addOnCompleteListener{
                    Toast.makeText(context,"Ho gaye Register",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context,"Kuch Gadbad kardiye ho firse karo register",Toast.LENGTH_SHORT).show()

                }
            navController.navigate(R.id.profileFragment)

        }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(context,"Signup failed due to ${e.message}",Toast.LENGTH_SHORT).show()

            }
    }

    override fun on_Started() {
        name = binding.nametxt.text.toString().trim()
        email = binding.editTextTextEmailAddress.text.toString().trim()
        password = binding.editTextTextPassword.text.toString().trim()
        cnfpassword = binding.confirmPassword.text.toString().trim()
            age = binding.age.text.toString().trim()
            phonenum = binding.editTextPhone.text.toString().trim()
            address = binding.PostalAddress.text.toString().trim()
            bio = binding.bioedttxt.text.toString().trim()

        //  validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            // invalid email format
            binding.editTextTextEmailAddress.error = "Invalid Email"

        }
        else if(TextUtils.isEmpty(password)){
            binding.editTextTextPassword.error ="Please enter password"
        }
        else if(TextUtils.isEmpty(cnfpassword)){
            binding.confirmPassword.error = "Please Confirm Your Password"
        }

        else if(inValidPassword(password)){
            binding.editTextTextPassword.error="Minimum length is 8 characters and have at least on special char"

        }
        else if(cnfpassword != password){
            binding.confirmPassword.error = "Password and confirm password should be same."

        }
        else if(TextUtils.isEmpty(name)){
            binding.nametxt.error = "Please Enter Full Name"
        }
        else if(TextUtils.isEmpty(age)){
            binding.age.error = "Please Enter Your age"
        }
        else if( age.length >2 ){
            binding.age.error = "Please Enter a Valid Age"
        }
        else if(TextUtils.isEmpty(phonenum) ){
            binding.editTextPhone.error = "Please Enter your Phone number"
        }

//        else if(phonenum.length >12 ){
//            binding.editTextPhone.error = "Please Enter A Valid Phone number"
//        }

        else if(TextUtils.isEmpty(address) ){
            binding.editTextTextEmailAddress.error = "Please Enter your Address"
        }

        else if(TextUtils.isEmpty(bio) ){
            binding.bioedttxt.error = "Please Enter Something About you"
        }
        else{
            firebaseSignup()
        }

    }

    private fun inValidPassword(password: String): Boolean {

        var valid = true

        // Password policy check
        // Password should be minimum minimum 8 characters long
        if (password.length < 8) {
            valid = false
        }
        // Password should contain at least one number
        var exp = ".*[0-9].*"
        var pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        var matcher = pattern.matcher(password)
        if (!matcher.matches()) {
            valid = false
        }

        // Password should contain at least one capital letter
        exp = ".*[A-Z].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(password)
        if (!matcher.matches()) {
            valid = false
        }

        // Password should contain at least one small letter
        exp = ".*[a-z].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(password)
        if (!matcher.matches()) {
            valid = false
        }

        // Password should contain at least one special character
        // Allowed special characters : "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
        exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(password)
        if (!matcher.matches()) {
            valid = false
        }

        return (!valid)

    }

    override fun on_Success() {
    Toast.makeText(context,"User Registered",Toast.LENGTH_SHORT).show()

    }

    override fun on_Failure(message: String) {
//        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()

    }

}