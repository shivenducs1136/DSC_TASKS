package com.example.newsapp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.newsapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_register.*
import okhttp3.internal.cache.DiskLruCache
import java.lang.ref.PhantomReference

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    lateinit var navController: NavController
    lateinit var user:FirebaseUser
    lateinit var database: DatabaseReference
    lateinit var userID:String
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.heartrot.animate().apply {
            duration = 100000
            rotationYBy(360f)
        }.start()

            //inti firebase
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        // handle click logout
        binding.logoutbutton.setOnClickListener{
            firebaseAuth.signOut()
            Toast.makeText(context,"Logged Out please Login / Register",Toast.LENGTH_LONG).show()
            checkUser()
        }

    }
//    private fun checkUser (name: String,email:String,age: String, phonnum:String,address:String,bio:String){
//        val user = User(name,age,phonnum,address,bio,email)
//
//    }


    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser!=null){
            userID = firebaseUser!!.uid
            database = FirebaseDatabase.getInstance().getReference("Users")
            database.child(userID).get().addOnSuccessListener {
                if(it != null){
                    binding.nametxtview.text = it.child("fullname").value.toString()
                    binding.agetxtview.text = it.child("age").value.toString()
                    binding.emailtxtview.text = it.child("email").value.toString()
                    binding.addresstxtview.text = it.child("address").value.toString()
                    binding.biotxtview.text = it.child("bio").value.toString()
                    binding.phonetxtview.text = it.child("phonenum").value.toString()

                }
                else {
                    Toast.makeText(context,"You may Have not entered it properly",Toast.LENGTH_SHORT).show()
                }
            }
                .addOnFailureListener {
                    Toast.makeText(context,"Error Failed ",Toast.LENGTH_SHORT).show()

                }
        }
        else {
            Toast.makeText(context,"First Login/Register ",Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.loginFragment)

        }

        }
    }

//        val firebaseUser = firebaseAuth.currentUser
//        if(firebaseUser!=null){
//            reference = FirebaseDatabase.getInstance().getReference("Users")
//            userID = user.uid
//         reference.child(userID).addValueEventListener(ValueEventListener {
//             override fun onDataChange(dataSnapshot: DataSnapshot) {
//                 // Get Post object and use the values to update the UI
//                 val post = dataSnapshot.getValue(User.class)
//                 // ...
//             }
//
//             override fun onCancelled(databaseError: DatabaseError) {
//                 // Getting Post failed, log a message
//                 Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
//             }
//         }
//                 postReference.addValueEventListener(postListener))
//        }
//        else{
//            // user is null , go to loginn fragment
//            navController.navigate(R.id.loginFragment)
//
//        }


