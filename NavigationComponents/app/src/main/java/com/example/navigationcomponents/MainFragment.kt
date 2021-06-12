package com.example.navigationcomponents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.navigationcomponents.databinding.FragmentMainBinding


class MainFragment : Fragment() , View.OnClickListener{

    lateinit var binding: FragmentMainBinding

    var navControler: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater,container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
       binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControler = Navigation.findNavController(view)
//        view.findViewById<Button>(R.id.view_transactions_btn).setOnClickListener(this)
//        view.findViewById<Button>(R.id.send_money_btn).setOnClickListener(this)
//        view.findViewById<Button>(R.id.view_balance_btn).setOnClickListener(this)
        binding.viewTransactionsBtn.setOnClickListener{
            Toast.makeText(context,"done",Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_mainFragment_to_viewTransactionFragment2)
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.view_transactions_btn -> navControler!!.navigate(R.id.action_mainFragment_to_viewTransactionFragment2)
            R.id.send_money_btn -> navControler!!.navigate(R.id.action_mainFragment_to_chooseReciptentFragment)
            R.id.view_balance_btn -> navControler!!.navigate(R.id.action_mainFragment_to_viewBalanceFragment2)
        }
    }
}