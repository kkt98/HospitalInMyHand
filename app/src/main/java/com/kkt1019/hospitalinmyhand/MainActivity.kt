package com.kkt1019.hospitalinmyhand

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kkt1019.hospitalinmyhand.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val fragment:MutableList<Fragment> by lazy { mutableListOf() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fragment.add(HomeFragment())
        fragment.add(BookMarkFragment())
        fragment.add(MedicalFragment())
        fragment.add(ReviewFragment())
        fragment.add(MapFragment())

        supportFragmentManager.beginTransaction().add(R.id.container, fragment[0]).commit()

        binding.bnv.setOnItemSelectedListener {

            supportFragmentManager.fragments.forEach {
                supportFragmentManager.beginTransaction().hide(it).commit()
            }

            val tran =  supportFragmentManager.beginTransaction()

            when(it.itemId){
                R.id.bnv_home -> {
                    tran.show(fragment[0])
                }
                R.id.bnv_bookmark -> {
                    if ( !supportFragmentManager.fragments.contains(fragment[1]))
                        tran.add(R.id.container, fragment[1])
                    tran.show(fragment[1])
                }
                R.id.bnv_medical -> {
                    if (!supportFragmentManager.fragments.contains(fragment[2]))
                        tran.add(R.id.container, fragment[2])
                    tran.show(fragment[2])
                }
                R.id.bnv_review -> {
                    if (!supportFragmentManager.fragments.contains(fragment[3]))
                        tran.add(R.id.container, fragment[3])
                    tran.show(fragment[3])
                }
                R.id.bnv_map -> {
                    if (!supportFragmentManager.fragments.contains(fragment[4]))
                        tran.add(R.id.container, fragment[4])
                    tran.show(fragment[4])
                }
            }
            tran.commit()

            true
        }
    }
}