package com.kkt1019.hospitalinmyhand

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomePage3Binding
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HomePage3Fragment:Fragment() {


    var items = mutableListOf<HomePage3Item>()
    var allitems = mutableListOf<HomePage3Item>()

    var apiKey = "H7PvoIiO2D6%2BqVfe6kF2WAoJgdpbVUtJT52Wx7dL6%2BDLP4IEk5i5xqP%2BGZMDktix9xaYS03X6YP4JtLGSnuunw%3D%3D"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.recycler.adapter = childFragmentManager?.let { HomePage3Adapter(activity as Context, items, it) }

        binding.btn.setOnClickListener { spinner() }

//        datas()

        NetworkThread().start()



        return binding.root
    }

    val binding:FragmentHomePage3Binding by lazy { FragmentHomePage3Binding.inflate(layoutInflater) }

    inner class NetworkThread:Thread(){

        override fun run() {

            val address = ("http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList"
                    + "?serviceKey=" + apiKey
                    + "&pageNo=1&numOfRows=100")

            Log.i("abc", "nnn")

            try {
                val url = URL(address)

                val conn = url.openConnection() as HttpURLConnection
                conn.doInput = true
                Log.i("abc", "fff")
                val ips = conn.inputStream

                val isr = InputStreamReader(ips)

                val factory = XmlPullParserFactory.newInstance()
                val xpp = factory.newPullParser()
                xpp.setInput(isr)

                var eventType = xpp.eventType

                var item: HomePage3Item? = null
                Log.i("abc", "bbb")

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    Log.i("abc", "aaa")

                    when (eventType) {

                        XmlPullParser.START_DOCUMENT -> {
                        }
                        XmlPullParser.START_TAG -> {
                            val tagName = xpp.name

                            if (tagName.equals("item")) {
                                item = HomePage3Item()

                            }else if (tagName.equals("addr")){
                                xpp.next()
                                if (item != null) item.addr = xpp.text

                            }else if (tagName.equals("yadmNm")){
                                xpp.next()
                                if (item != null) item.yadmNm = xpp.text

                            }else if (tagName.equals("telno")){
                                xpp.next()
                                if (item != null) item.telno = xpp.text

                            }else if (tagName.equals("XPos")){
                                xpp.next()
                                if (item != null) item.xPos = xpp.text

                            }else if (tagName.equals("YPos")){
                                xpp.next()
                                if (item != null) item.yPos = xpp.text

                            }
                        }
                        XmlPullParser.END_TAG -> {
                            val tagName2: String = xpp.name
                            if (tagName2 == "item") {
                                if (item != null) {
                                    allitems.add(item)
                                }
                            }
                        }
                    }
                    eventType = xpp.next()
                }

                activity?.runOnUiThread {
                    Toast.makeText(context, "aaaa"+items.size, Toast.LENGTH_SHORT).show()
                    items.addAll(allitems)
                    binding.recycler.adapter?.notifyDataSetChanged()
                }

            }catch (e:Exception){ Log.i("abc", e.toString())}


        }

    }



//    fun datas(){
//
////        items.add( HomePage3Item("약국 이름", "약국 주소", "약국 전화번호"))
////        items.add( HomePage3Item("약국 이름", "약국 주소", "야국 전화번호"))
////        items.add( HomePage3Item("약국 이름", "약국 주소", "야국 전화번호"))
////        items.add( HomePage3Item("약국 이름", "약국 주소", "야국 전화번호"))
////        items.add( HomePage3Item("약국 이름", "약국 주소", "야국 전화번호"))
////        items.add( HomePage3Item("약국 이름", "약국 주소", "야국 전화번호"))
//
//
//
//    }

    fun spinner(){

        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog2, null)

        var spinner = mDialogView.findViewById<Spinner>(R.id.spinner)
        var spinner2 = mDialogView.findViewById<Spinner>(R.id.spinner2)
        var checkBox = mDialogView.findViewById<CheckBox>(R.id.check_my)

        checkBox.setOnCheckedChangeListener { compoundButton, b ->

            if (checkBox.isChecked) {
                spinner.visibility = View.GONE
                spinner2.visibility = View.GONE
            }
            else {
                spinner.visibility = View.VISIBLE
                spinner2.visibility = View.VISIBLE
            }

        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                when (p2){

                    0 -> {

                        val spinnerItems = resources.getStringArray(R.array.choice)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                    }
                    1 -> {

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_seoul)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items.clear()

                                if (p2 == 0) {
                                    items.addAll(allitems)
                                    binding.recycler.adapter?.notifyDataSetChanged()
                                    return
                                }

                                val arr = resources.getStringArray(R.array.spinner_region_seoul)
                                Log.i("qwe", "hhh"+allitems.size)

                                for (HomePage3Item in allitems) {
                                    Log.i("qwe", "ooo")
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items.add(HomePage3Item)
                                        Log.i("qwe", "rrr")
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    2 -> {

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_busan)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items.clear()

                                if (p2 == 0) {
                                    items.addAll(allitems)
                                    binding.recycler.adapter?.notifyDataSetChanged()
                                    return
                                }

                                val arr = resources.getStringArray(R.array.spinner_region_busan)
                                Log.i("qwe", "hhh"+allitems.size)

                                for (HomePage3Item in allitems) {
                                    Log.i("qwe", "ooo")
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items.add(HomePage3Item)
                                        Log.i("qwe", "rrr")
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    3 -> {

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_daegu)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items.clear()

                                if (p2 == 0) {
                                    items.addAll(allitems)
                                    binding.recycler.adapter?.notifyDataSetChanged()
                                    return
                                }

                                val arr = resources.getStringArray(R.array.spinner_region_daegu)
                                Log.i("qwe", "hhh"+allitems.size)

                                for (HomePage3Item in allitems) {
                                    Log.i("qwe", "ooo")
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items.add(HomePage3Item)
                                        Log.i("qwe", "rrr")
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    4 -> {

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_incheon)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items.clear()

                                if (p2 == 0) {
                                    items.addAll(allitems)
                                    binding.recycler.adapter?.notifyDataSetChanged()
                                    return
                                }

                                val arr = resources.getStringArray(R.array.spinner_region_incheon)
                                Log.i("qwe", "hhh"+allitems.size)

                                for (HomePage3Item in allitems) {
                                    Log.i("qwe", "ooo")
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items.add(HomePage3Item)
                                        Log.i("qwe", "rrr")
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    5 -> {

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_gwangju)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items.clear()

                                if (p2 == 0) {
                                    items.addAll(allitems)
                                    binding.recycler.adapter?.notifyDataSetChanged()
                                    return
                                }

                                val arr = resources.getStringArray(R.array.spinner_region_gwangju)
                                Log.i("qwe", "hhh"+allitems.size)

                                for (HomePage3Item in allitems) {
                                    Log.i("qwe", "ooo")
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items.add(HomePage3Item)
                                        Log.i("qwe", "rrr")
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    6 -> {

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_daejeon)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items.clear()

                                if (p2 == 0) {
                                    items.addAll(allitems)
                                    binding.recycler.adapter?.notifyDataSetChanged()
                                    return
                                }

                                val arr = resources.getStringArray(R.array.spinner_region_daejeon)
                                Log.i("qwe", "hhh"+allitems.size)

                                for (HomePage3Item in allitems) {
                                    Log.i("qwe", "ooo")
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items.add(HomePage3Item)
                                        Log.i("qwe", "rrr")
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    7 -> {

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_ulsan)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items.clear()

                                if (p2 == 0) {
                                    items.addAll(allitems)
                                    binding.recycler.adapter?.notifyDataSetChanged()
                                    return
                                }

                                val arr = resources.getStringArray(R.array.spinner_region_ulsan)
                                Log.i("qwe", "hhh"+allitems.size)

                                for (HomePage3Item in allitems) {
                                    Log.i("qwe", "ooo")
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items.add(HomePage3Item)
                                        Log.i("qwe", "rrr")
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    8 -> {

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_sejong)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items.clear()

                                if (p2 == 0) {
                                    items.addAll(allitems)
                                    binding.recycler.adapter?.notifyDataSetChanged()
                                    return
                                }

                                val arr = resources.getStringArray(R.array.spinner_region_sejong)
                                Log.i("qwe", "hhh"+allitems.size)

                                for (HomePage3Item in allitems) {
                                    Log.i("qwe", "ooo")
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items.add(HomePage3Item)
                                        Log.i("qwe", "rrr")
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    9 -> {

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_gyeonggi)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items.clear()

                                if (p2 == 0) {
                                    items.addAll(allitems)
                                    binding.recycler.adapter?.notifyDataSetChanged()
                                    return
                                }

                                val arr = resources.getStringArray(R.array.spinner_region_gyeonggi)
                                Log.i("qwe", "hhh"+allitems.size)

                                for (HomePage3Item in allitems) {
                                    Log.i("qwe", "ooo")
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items.add(HomePage3Item)
                                        Log.i("qwe", "rrr")
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    10 -> {

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_gangwon)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items.clear()

                                if (p2 == 0) {
                                    items.addAll(allitems)
                                    binding.recycler.adapter?.notifyDataSetChanged()
                                    return
                                }

                                val arr = resources.getStringArray(R.array.spinner_region_gangwon)
                                Log.i("qwe", "hhh"+allitems.size)

                                for (HomePage3Item in allitems) {
                                    Log.i("qwe", "ooo")
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items.add(HomePage3Item)
                                        Log.i("qwe", "rrr")
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    11 -> {

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_chung_buk)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items.clear()

                                if (p2 == 0) {
                                    items.addAll(allitems)
                                    binding.recycler.adapter?.notifyDataSetChanged()
                                    return
                                }

                                val arr = resources.getStringArray(R.array.spinner_region_chung_buk)
                                Log.i("qwe", "hhh"+allitems.size)

                                for (HomePage3Item in allitems) {
                                    Log.i("qwe", "ooo")
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items.add(HomePage3Item)
                                        Log.i("qwe", "rrr")
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    12 -> {

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_chung_nam)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items.clear()

                                if (p2 == 0) {
                                    items.addAll(allitems)
                                    binding.recycler.adapter?.notifyDataSetChanged()
                                    return
                                }

                                val arr = resources.getStringArray(R.array.spinner_region_chung_nam)
                                Log.i("qwe", "hhh"+allitems.size)

                                for (HomePage3Item in allitems) {
                                    Log.i("qwe", "ooo")
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items.add(HomePage3Item)
                                        Log.i("qwe", "rrr")
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    13 -> {

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_jeon_buk)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items.clear()

                                if (p2 == 0) {
                                    items.addAll(allitems)
                                    binding.recycler.adapter?.notifyDataSetChanged()
                                    return
                                }

                                val arr = resources.getStringArray(R.array.spinner_region_jeon_buk)
                                Log.i("qwe", "hhh"+allitems.size)

                                for (HomePage3Item in allitems) {
                                    Log.i("qwe", "ooo")
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items.add(HomePage3Item)
                                        Log.i("qwe", "rrr")
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    14 -> {

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_jeon_nam)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items.clear()

                                if (p2 == 0) {
                                    items.addAll(allitems)
                                    binding.recycler.adapter?.notifyDataSetChanged()
                                    return
                                }

                                val arr = resources.getStringArray(R.array.spinner_region_jeon_nam)
                                Log.i("qwe", "hhh"+allitems.size)

                                for (HomePage3Item in allitems) {
                                    Log.i("qwe", "ooo")
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items.add(HomePage3Item)
                                        Log.i("qwe", "rrr")
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    15 -> {

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_gyeong_buk)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items.clear()

                                if (p2 == 0) {
                                    items.addAll(allitems)
                                    binding.recycler.adapter?.notifyDataSetChanged()
                                    return
                                }

                                val arr = resources.getStringArray(R.array.spinner_region_gyeong_buk)
                                Log.i("qwe", "hhh"+allitems.size)

                                for (HomePage3Item in allitems) {
                                    Log.i("qwe", "ooo")
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items.add(HomePage3Item)
                                        Log.i("qwe", "rrr")
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    16 -> {

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_gyeong_nam)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items.clear()

                                if (p2 == 0) {
                                    items.addAll(allitems)
                                    binding.recycler.adapter?.notifyDataSetChanged()
                                    return
                                }

                                val arr = resources.getStringArray(R.array.spinner_region_gyeong_nam)
                                Log.i("qwe", "hhh"+allitems.size)

                                for (HomePage3Item in allitems) {
                                    Log.i("qwe", "ooo")
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items.add(HomePage3Item)
                                        Log.i("qwe", "rrr")
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    17 -> {

                        val spinnerItems = resources.getStringArray(R.array.spinner_jeju)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items.clear()

                                if (p2 == 0) {
                                    items.addAll(allitems)
                                    binding.recycler.adapter?.notifyDataSetChanged()
                                    return
                                }

                                val arr = resources.getStringArray(R.array.spinner_jeju)
                                Log.i("qwe", "hhh"+allitems.size)

                                for (HomePage3Item in allitems) {
                                    Log.i("qwe", "ooo")
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items.add(HomePage3Item)
                                        Log.i("qwe", "rrr")
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d("MyTag", "onNothingSelected")
            }

        }

        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)

        mBuilder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
            binding.recycler.adapter?.notifyDataSetChanged()
        }
        mBuilder.setNegativeButton("취소", null)

        mBuilder.show()

        val items = resources.getStringArray(R.array.city)

        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)

        spinner.adapter = madapter
    }
}