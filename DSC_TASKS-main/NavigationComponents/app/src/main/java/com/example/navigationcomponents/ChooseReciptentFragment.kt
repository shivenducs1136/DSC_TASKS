package com.example.navigationcomponents

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController


class ChooseReciptentFragment : Fragment(), View.OnClickListener {

     var Nav_Controller : NavController? = null

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_reciptent, container, false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Nav_Controller = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.next_btn).setOnClickListener(this)
        view.findViewById<Button>(R.id.cancel_btn).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

//    override fun onClick(v: View?) {
//        when (v!!.id){
//            R.id.next_btn -> {
//                if(!TextUtils.isEmpty(input_recipient.text.toString())){
//                    val bundle = bundleOf("recipient" to input_recipient.text.toString())
//                    Nav_Controller!!.navigate(
//                        R.id.action_chooseReciptentFragment_to_specifyAmountFragment,
//                        bundle
//                    )
//                }
//                else {
//                    Toast.makeText(activity,"Enter an Admikamount", Toast.LENGTH_SHORT).show()
//                }
//            }
//            R.id.cancel_btn -> requireActivity().onBackPressed()
//
//        }
////
//    }

            }