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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        datas()

        return binding.root
    }

    val binding: FragmentHomepage1BottomsheetBinding by lazy { FragmentHomepage1BottomsheetBinding.inflate(layoutInflater) }



    @SuppressLint("SetTextI18n")
    fun datas(){

//        arguments.let {
//
//            binding.title.text = "name"
//            binding.address.text = "addr"
//            binding.tell.text = "tell"
////            binding.address.text = "addr"
//
//        }

        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "asdasdasdasdasd\nasdasdasdasdasd\nasdasdasdasd\nasdasdasdasd"))
        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "후기 내용"))
        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "후기 내용"))
        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "후기 내용"))
        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "후기 내용"))

        recycler.adapter = ReviewAdapter(activity as Context, items)

    }
}