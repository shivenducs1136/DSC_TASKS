package com.example.navigationcomponents

import android.os.Bundle
import android.os.Message
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import java.math.BigDecimal

class SpecifyAmountFragment : Fragment() , View.OnClickListener{

    var Navi_control:  NavController? = null
    var recipient : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipient = requireArguments().getString("recipient")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_specify_amount, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Navi_control = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.send_btn).setOnClickListener(this)
        view.findViewById<Button>(R.id.cancel_btn).setOnClickListener(this)
    val message: String = "Sending money to $recipient"
        view.findViewById<TextView>(R.id.recipient).text = message
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

//    override fun onClick(v: View?) {
//        when (v!!.id){
//            R.id.send_btn -> {
//                if(!TextUtils.isEmpty(input_amount.text.toString())){
//                    val amount=Money (BigDecimal(input_amount.text.toString()))
//                    val bundle = bundleOf(
//                        "recipient" to recipient,
//                    "amount" to amount
//                    )
//                    Navi_control!!.navigate(R.id.action_specifyAmountFragment_to_configurationFragment,
//                    bundle)
//                }
//                else {
//                Toast.makeText(activity,"Enter an amount",Toast.LENGTH_SHORT).show()
//                }
//
//            }
//            R.id.cancel_btn -> requireActivity().onBackPressed()
//
//        }
//
//    }


}
