package com.kkt1019.hospitalinmyhand

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomePage2Binding
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder
import kotlin.math.*

class HomePage2Fragment:Fragment() {

    var items = mutableListOf<HomePage2Item>()
    var items2 = mutableListOf<HomePage2Item>()
    var allitems = mutableListOf<HomePage2Item>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.recycler.adapter = childFragmentManager?.let { HomePage2Adapter(activity as Context, items2, it) }

        binding.btn.setOnClickListener { spinner() }

        NetworkThread().start()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

//        Mylocation()

    }

    val binding:FragmentHomePage2Binding by lazy { FragmentHomePage2Binding.inflate(layoutInflater) }

    inner class NetworkThread:Thread(){

        override fun run() {

            val address = ("http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytBassInfoInqire?" +
                    "serviceKey=H7PvoIiO2D6%2BqVfe6kF2WAoJgdpbVUtJT52Wx7dL6%2BDLP4IEk5i5xqP%2BGZMDktix9xaYS03X6YP4JtLGSnuunw%3D%3D" +
                    "&pageNo=1&numOfRows=3000")

            Log.i("abc", "nnn")

            try {
                val url = URL(address)

                val conn = url.openConnection() as HttpURLConnection
                conn.doInput = true
                Log.i("abc", "ccc")
                val ips = conn.inputStream

                val isr = InputStreamReader(ips)

                val factory = XmlPullParserFactory.newInstance()
                val xpp = factory.newPullParser()
                xpp.setInput(isr)

                var eventType = xpp.eventType

                var item: HomePage2Item? = null
                Log.i("abc", "bbb")

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    Log.i("abc", "aaa")

                    when (eventType) {

                        XmlPullParser.START_DOCUMENT -> {
                        }
                        XmlPullParser.START_TAG -> {
                            val tagName = xpp.name

                            if (tagName.equals("item")) {
                                item = HomePage2Item()

                            }else if (tagName.equals("dutyAddr")){
                                xpp.next()
                                if (item != null) item.dutyAddr = xpp.text

                            }else if (tagName.equals("dutyName")){
                                xpp.next()
                                if (item != null) item.dutyName = xpp.text

                            }else if (tagName.equals("dutyTel1")){
                                xpp.next()
                                if (item != null) item.dutyTel1 = xpp.text

                            }else if (tagName.equals("dutyTel3")){
                                xpp.next()
                                if (item != null) item.dutyTel3 = xpp.text

                            }else if (tagName.equals("wgs84Lat")){
                                xpp.next()
                                if (item != null) item.wgs84Lat = xpp.text

                            }else if (tagName.equals("wgs84Lon")){
                                xpp.next()
                                if (item != null) item.wgs84Lon = xpp.text

                            }else if (tagName.equals("hpid")){
                                xpp.next()
                                if (item != null) item.hpid = xpp.text

                            }

                            if (item != null) {
                                Log.i("aaa", "${item.wgs84Lat}, ${item.wgs84Lon}")
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
                    items.addAll(allitems)
                    items2.addAll(items)
                    binding.recycler.adapter?.notifyDataSetChanged()
                }

            }catch (e:Exception){ Log.i("abc", e.toString())}


        }

    }

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

        val city = resources.getStringArray(R.array.city)
        var spinnerAdapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, city)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                when (p2){

                    0 -> {

                        val spinnerItems = resources.getStringArray(R.array.choice)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        items.clear()

                        if (p2 == 0) {
                            items.addAll(allitems)
                            binding.recycler.adapter?.notifyDataSetChanged()
                            return
                        }


                    }
                    1 -> {

                        items.clear()

                        for (HomePage2Item in allitems) {
                            if (HomePage2Item.dutyAddr.contains(city[1])) {
                                items.add(HomePage2Item)
                            }
                        }

                        binding.recycler.adapter?.notifyDataSetChanged()

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_seoul)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_seoul)

                                for (HomePage2Item in items) {
                                    if (HomePage2Item.dutyAddr.contains(arr[p2])) {
                                        Log.i("ggg", "abc")
                                        items2.add(HomePage2Item)

                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    2 -> {

                        items.clear()

                        for (HomePage2Item in allitems) {
                            if (HomePage2Item.dutyAddr.contains(city[2])) {
                                items.add(HomePage2Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_busan)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_busan)

                                for (HomePage2Item in items) {
                                    if (HomePage2Item.dutyAddr.contains(arr[p2])) {
                                        items2.add(HomePage2Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    3 -> {

                        items.clear()

                        for (HomePage2Item in allitems) {
                            if (HomePage2Item.dutyAddr.contains(city[3])) {
                                items.add(HomePage2Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_daegu)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_seoul)

                                for (HomePage2Item in items) {

                                    if (HomePage2Item.dutyAddr.contains(arr[p2])) {
                                        items2.add(HomePage2Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    4 -> {

                        items.clear()

                        for (HomePage2Item in allitems) {
                            if (HomePage2Item.dutyAddr.contains(city[4])) {
                                items.add(HomePage2Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_incheon)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_incheon)

                                for (HomePage2Item in items) {
                                    if (HomePage2Item.dutyAddr.contains(arr[p2])) {
                                        items2.add(HomePage2Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    5 -> {

                        items.clear()

                        for (HomePage2Item in allitems) {
                            if (HomePage2Item.dutyAddr.contains(city[5])) {
                                items.add(HomePage2Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_gwangju)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_gwangju)

                                for (HomePage2Item in items) {
                                    if (HomePage2Item.dutyAddr.contains(arr[p2])) {
                                        items2.add(HomePage2Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    6 -> {

                        items.clear()

                        for (HomePage2Item in allitems) {
                            if (HomePage2Item.dutyAddr.contains(city[6])) {
                                items.add(HomePage2Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_daejeon)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_daejeon)

                                for (HomePage2Item in items) {
                                    if (HomePage2Item.dutyAddr.contains(arr[p2])) {
                                        items2.add(HomePage2Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    7 -> {

                        items.clear()

                        for (HomePage2Item in allitems) {
                            if (HomePage2Item.dutyAddr.contains(city[7])) {
                                items.add(HomePage2Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_ulsan)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_ulsan)

                                for (HomePage2Item in items) {
                                    if (HomePage2Item.dutyAddr.contains(arr[p2])) {
                                        items2.add(HomePage2Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    8 -> {

                        items.clear()

                        for (HomePage2Item in allitems) {
                            if (HomePage2Item.dutyAddr.contains(city[8])) {
                                items.add(HomePage2Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_sejong)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_sejong)

                                for (HomePage2Item in items) {
                                    if (HomePage2Item.dutyAddr.contains(arr[p2])) {
                                        items2.add(HomePage2Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    9 -> {

                        items.clear()

                        for (HomePage2Item in allitems) {
                            if (HomePage2Item.dutyAddr.contains(city[9])) {
                                items.add(HomePage2Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_gyeonggi)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_gyeonggi)

                                for (HomePage2Item in items) {
                                    if (HomePage2Item.dutyAddr.contains(arr[p2])) {
                                        items2.add(HomePage2Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    10 -> {

                        items.clear()

                        for (HomePage2Item in allitems) {
                            if (HomePage2Item.dutyAddr.contains(city[10])) {
                                items.add(HomePage2Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_gangwon)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_gangwon)

                                for (HomePage2Item in items) {
                                    if (HomePage2Item.dutyAddr.contains(arr[p2])) {
                                        items2.add(HomePage2Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    11 -> {

                        items.clear()

                        for (HomePage2Item in allitems) {
                            if (HomePage2Item.dutyAddr.contains(city[11])) {
                                items.add(HomePage2Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_chung_buk)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_chung_buk)

                                for (HomePage2Item in items) {
                                    if (HomePage2Item.dutyAddr.contains(arr[p2])) {
                                        items2.add(HomePage2Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    12 -> {

                        items.clear()

                        for (HomePage2Item in allitems) {
                            if (HomePage2Item.dutyAddr.contains(city[12])) {
                                items.add(HomePage2Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_chung_nam)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_chung_nam)

                                for (HomePage2Item in items) {
                                    if (HomePage2Item.dutyAddr.contains(arr[p2])) {
                                        items2.add(HomePage2Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    13 -> {

                        items.clear()

                        for (HomePage2Item in allitems) {
                            if (HomePage2Item.dutyAddr.contains(city[13])) {
                                items.add(HomePage2Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_jeon_buk)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_jeon_buk)

                                for (HomePage2Item in items) {
                                    if (HomePage2Item.dutyAddr.contains(arr[p2])) {
                                        items2.add(HomePage2Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    14 -> {

                        items.clear()

                        for (HomePage2Item in allitems) {
                            if (HomePage2Item.dutyAddr.contains(city[14])) {
                                items.add(HomePage2Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_jeon_nam)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_jeon_nam)

                                for (HomePage2Item in items) {
                                    if (HomePage2Item.dutyAddr.contains(arr[p2])) {
                                        items2.add(HomePage2Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    15 -> {

                        items.clear()

                        for (HomePage2Item in allitems) {
                            if (HomePage2Item.dutyAddr.contains(city[15])) {
                                items.add(HomePage2Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_gyeong_buk)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_gyeong_buk)

                                for (HomePage2Item in items) {
                                    if (HomePage2Item.dutyAddr.contains(arr[p2])) {
                                        items2.add(HomePage2Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    16 -> {

                        items.clear()

                        for (HomePage2Item in allitems) {
                            if (HomePage2Item.dutyAddr.contains(city[16])) {
                                items.add(HomePage2Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_gyeong_nam)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_gyeong_nam)

                                for (HomePage2Item in items) {
                                    if (HomePage2Item.dutyAddr.contains(arr[p2])) {
                                        items2.add(HomePage2Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    17 -> {

                        items.clear()

                        for (HomePage2Item in allitems) {
                            if (HomePage2Item.dutyAddr.contains(city[17])) {
                                items.add(HomePage2Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_jeju)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_jeju)

                                for (HomePage2Item in items) {
                                    if (HomePage2Item.dutyAddr.contains(arr[p2])) {
                                        items2.add(HomePage2Item)
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

        mBuilder.setPositiveButton("??????") { dialogInterface: DialogInterface, i: Int ->
            binding.recycler.adapter?.notifyDataSetChanged()
        }
        mBuilder.setNegativeButton("??????", null)

        mBuilder.show()

        val items = resources.getStringArray(R.array.city)

        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)

        spinner.adapter = madapter
    }

}