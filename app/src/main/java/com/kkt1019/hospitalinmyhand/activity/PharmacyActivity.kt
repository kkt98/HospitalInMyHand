package com.kkt1019.hospitalinmyhand.activity

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.kkt1019.hospitalinmyhand.HomePage3Adapter
import com.kkt1019.hospitalinmyhand.HomePage3Item
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.databinding.ActivityPharmacyBinding
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.*

class PharmacyActivity : AppCompatActivity() {
    val binding: ActivityPharmacyBinding by lazy { ActivityPharmacyBinding.inflate(layoutInflater) }

    val recycler: RecyclerView by lazy { binding.recycler }

    var items = mutableListOf<HomePage3Item>()
    var items2 = mutableListOf<HomePage3Item>()
    var allitems = mutableListOf<HomePage3Item>()

    var apiKey = "H7PvoIiO2D6%2BqVfe6kF2WAoJgdpbVUtJT52Wx7dL6%2BDLP4IEk5i5xqP%2BGZMDktix9xaYS03X6YP4JtLGSnuunw%3D%3D"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.title = "약국"

        recycler.adapter = HomePage3Adapter(this, items2, supportFragmentManager)

        binding.btn.setOnClickListener { spinner() }

        Mylocation()
    }

    fun loadData(x:Double, y:Double){
//        Toast.makeText(this, "$x : $y", Toast.LENGTH_SHORT).show()

        object : Thread() {
            override fun run() {

                val address = ("http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList" +
                        "?serviceKey=" + apiKey +
                        "&pageNo=1" +
                        "&numOfRows=1000" +
                        "&xPos=" + x +
                        "&yPos=" + y +
                        "&radius=10000")

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

                                }else if (tagName.equals("ykiho")){
                                    xpp.next()
                                    if (item != null) item.ykiho = xpp.text

                                }else if (tagName.equals("distance")){
                                    xpp.next()
                                    if (item != null) item.location = xpp.text

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

                    runOnUiThread {
                        items.addAll(allitems)
                        items2.addAll(items)
                        binding.recycler.adapter?.notifyDataSetChanged()
                    }

                }catch (e:Exception){ Log.i("abc", e.toString())}

            }

        }.start()

    }

    fun spinner(){

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog2, null)

        val spinner = mDialogView.findViewById<Spinner>(R.id.spinner)
        val spinner2 = mDialogView.findViewById<Spinner>(R.id.spinner2)
        val checkBox = mDialogView.findViewById<CheckBox>(R.id.check_my)

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
        var spinnerAdapter = ArrayAdapter(this as Activity, android.R.layout.simple_spinner_dropdown_item, city)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                when (p2){

                    0 -> {

                        val spinnerItems = resources.getStringArray(R.array.choice)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
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

                        for (HomePage3Item in allitems) {
                            if (HomePage3Item.addr.contains(city[1])) {
                                items.add(HomePage3Item)
                            }
                        }

                        binding.recycler.adapter?.notifyDataSetChanged()

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_seoul)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_seoul)

                                for (HomePage3Item in items) {
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        Log.i("ggg", "abc")
                                        items2.add(HomePage3Item)

                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    2 -> {

                        items.clear()

                        for (HomePage3Item in allitems) {
                            if (HomePage3Item.addr.contains(city[2])) {
                                items.add(HomePage3Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_busan)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_busan)

                                for (HomePage3Item in items) {
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items2.add(HomePage3Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    3 -> {

                        items.clear()

                        for (HomePage3Item in allitems) {
                            if (HomePage3Item.addr.contains(city[3])) {
                                items.add(HomePage3Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_daegu)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_seoul)

                                for (HomePage3Item in items) {

                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items2.add(HomePage3Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    4 -> {

                        items.clear()

                        for (HomePage3Item in allitems) {
                            if (HomePage3Item.addr.contains(city[4])) {
                                items.add(HomePage3Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_incheon)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_incheon)

                                for (HomePage3Item in items) {
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items2.add(HomePage3Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    5 -> {

                        items.clear()

                        for (HomePage3Item in allitems) {
                            if (HomePage3Item.addr.contains(city[5])) {
                                items.add(HomePage3Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_gwangju)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_gwangju)

                                for (HomePage3Item in items) {
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items2.add(HomePage3Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    6 -> {

                        items.clear()

                        for (HomePage3Item in allitems) {
                            if (HomePage3Item.addr.contains(city[6])) {
                                items.add(HomePage3Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_daejeon)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_daejeon)

                                for (HomePage3Item in items) {
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items2.add(HomePage3Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    7 -> {

                        items.clear()

                        for (HomePage3Item in allitems) {
                            if (HomePage3Item.addr.contains(city[7])) {
                                items.add(HomePage3Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_ulsan)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_ulsan)

                                for (HomePage3Item in items) {
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items2.add(HomePage3Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    8 -> {

                        items.clear()

                        for (HomePage3Item in allitems) {
                            if (HomePage3Item.addr.contains(city[8])) {
                                items.add(HomePage3Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_sejong)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_sejong)

                                for (HomePage3Item in items) {
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items2.add(HomePage3Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    9 -> {

                        items.clear()

                        for (HomePage3Item in allitems) {
                            if (HomePage3Item.addr.contains(city[9])) {
                                items.add(HomePage3Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_gyeonggi)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_gyeonggi)

                                for (HomePage3Item in items) {
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items2.add(HomePage3Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    10 -> {

                        items.clear()

                        for (HomePage3Item in allitems) {
                            if (HomePage3Item.addr.contains(city[10])) {
                                items.add(HomePage3Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_gangwon)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_gangwon)

                                for (HomePage3Item in items) {
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items2.add(HomePage3Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    11 -> {

                        items.clear()

                        for (HomePage3Item in allitems) {
                            if (HomePage3Item.addr.contains(city[11])) {
                                items.add(HomePage3Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_chung_buk)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_chung_buk)

                                for (HomePage3Item in items) {
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items2.add(HomePage3Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    12 -> {

                        items.clear()

                        for (HomePage3Item in allitems) {
                            if (HomePage3Item.addr.contains(city[12])) {
                                items.add(HomePage3Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_chung_nam)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_chung_nam)

                                for (HomePage3Item in items) {
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items2.add(HomePage3Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    13 -> {

                        items.clear()

                        for (HomePage3Item in allitems) {
                            if (HomePage3Item.addr.contains(city[13])) {
                                items.add(HomePage3Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_jeon_buk)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_jeon_buk)

                                for (HomePage3Item in items) {
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items2.add(HomePage3Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    14 -> {

                        items.clear()

                        for (HomePage3Item in allitems) {
                            if (HomePage3Item.addr.contains(city[14])) {
                                items.add(HomePage3Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_jeon_nam)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_jeon_nam)

                                for (HomePage3Item in items) {
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items2.add(HomePage3Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    15 -> {

                        items.clear()

                        for (HomePage3Item in allitems) {
                            if (HomePage3Item.addr.contains(city[15])) {
                                items.add(HomePage3Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_gyeong_buk)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_gyeong_buk)

                                for (HomePage3Item in items) {
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items2.add(HomePage3Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    16 -> {

                        items.clear()

                        for (HomePage3Item in allitems) {
                            if (HomePage3Item.addr.contains(city[16])) {
                                items.add(HomePage3Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_region_gyeong_nam)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_region_gyeong_nam)

                                for (HomePage3Item in items) {
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items2.add(HomePage3Item)
                                    }
                                }
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }
                        }

                    }
                    17 -> {

                        items.clear()

                        for (HomePage3Item in allitems) {
                            if (HomePage3Item.addr.contains(city[17])) {
                                items.add(HomePage3Item)
                            }
                        }

                        val spinnerItems = resources.getStringArray(R.array.spinner_jeju)
                        val madapter = ArrayAdapter(this@PharmacyActivity, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                        spinner2.adapter = madapter

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                                items2.clear()

                                val arr = resources.getStringArray(R.array.spinner_jeju)

                                for (HomePage3Item in items) {
                                    if (HomePage3Item.addr.contains(arr[p2])) {
                                        items2.add(HomePage3Item)
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

        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        mBuilder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
            binding.recycler.adapter?.notifyDataSetChanged()
        }
        mBuilder.setNegativeButton("취소", null)

        mBuilder.show()

        val items = resources.getStringArray(R.array.city)

        val madapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        spinner.adapter = madapter
    }

    lateinit var providerClient: FusedLocationProviderClient

    fun Mylocation(){

        //위치정보 제공자 객체얻어오기
        providerClient = LocationServices.getFusedLocationProviderClient(this)

        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY //높은 정확도 우선시..[gps]

        //내 위치 실시간 갱신 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) { return }
        providerClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper()
        )
    }

    var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            //파라미터로 전달된 위치정보결과 객체에게 위치정보를 얻어오기
            val location = locationResult.lastLocation
            val lat = location?.latitude
            val lng = location?.longitude

            loadData(lng!!, lat!!)

        }
    }
}