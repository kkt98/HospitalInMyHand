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

class HomePage1BottomSheet : BottomSheetDialogFragment() {

    val recycler : RecyclerView by lazy { binding.recycler }

    var items = mutableListOf<ReviewItem>()

    lateinit var name : String
    lateinit var addr : String
    lateinit var tell : String
    lateinit var timeS : String
    lateinit var timeC : String

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
        binding.tell.text = tell
        binding.address.text = addr

    }

    val binding: FragmentHomepage1BottomsheetBinding by lazy { FragmentHomepage1BottomsheetBinding.inflate(layoutInflater) }

    fun detail(name: String, addr:String, tell:String, timeS:String, timeC:String){

        this.name = name
        this.addr = addr
        this.tell = tell
        this.timeS = timeS
        this.timeC = timeC

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