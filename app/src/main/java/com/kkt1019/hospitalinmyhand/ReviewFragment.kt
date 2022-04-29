package com.kkt1019.hospitalinmyhand

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.databinding.FragmentBookmarkBinding
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomepage1BottomsheetBinding
import com.kkt1019.hospitalinmyhand.databinding.FragmentReviewBinding

class ReviewFragment : Fragment() {

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

    val binding:FragmentReviewBinding by lazy { FragmentReviewBinding.inflate(layoutInflater) }

    fun datas(){

        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "asdasdasdasdasd\nasdasdasdasdasd\nasdasdasdasd\nasdasdasdasd"))
        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "후기 내용"))
        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "후기 내용"))
        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "후기 내용"))
        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "후기 내용"))

        recycler.adapter = ReviewAdapter(activity as Context, items)

    }

}