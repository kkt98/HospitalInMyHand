package com.kkt1019.hospitalinmyhand

import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

object Permission {

     fun requestPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {

                //권한이 허용됐을 때
                override fun onPermissionGranted() {
                }

                //권한이 거부됐을 때
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                }
            })
            .setDeniedMessage("위치 권한을 허용해주세요")// 권한이 없을 때 띄워주는 Dialog Message
            .setPermissions(
                android.Manifest.permission.ACCESS_COARSE_LOCATION
                ,android.Manifest.permission.ACCESS_FINE_LOCATION)// 얻으려는 권한(여러개 가능)
            .check()
    }

}