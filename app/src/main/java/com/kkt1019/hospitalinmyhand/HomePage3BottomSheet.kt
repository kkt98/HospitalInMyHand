package com.kkt1019.hospitalinmyhand

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomePage1Binding
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomepage1BottomsheetBinding
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomepage2BottomsheetBinding
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomepage3BottomsheetBinding

class HomePage3BottomSheet : BottomSheetDialogFragment() {

    val recycler : RecyclerView by lazy { binding.recycler }

    var items = mutableListOf<ReviewItem>()

    lateinit var name : String
    lateinit var addr : String
    lateinit var tell : String



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return binding.root
    }

    override fun onResume() {
        super.onResume()

        datas()

        binding.title.text = name
        binding.address.text = addr
        binding.tell.text = tell


    }

    val binding: FragmentHomepage3BottomsheetBinding by lazy { FragmentHomepage3BottomsheetBinding.inflate(layoutInflater) }

    fun detail(name: String, addr:String, tell:String){

        this.name = name
        this.addr = addr
        this.tell = tell


    }



    fun datas(){

        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "asdasdasdasdasd\nasdasdasdasdasd\nasdasdasdasd\nasdasdasdasd"))
        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "후기 내용"))
        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "후기 내용"))
        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "후기 내용"))
        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "후기 내용"))

        recycler.adapter = ReviewAdapter(activity as Context, items)

    }
}