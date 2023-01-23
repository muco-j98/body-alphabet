package com.muco.bodyalphabet

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.muco.bodyalphabet.utils.Constants.RC_CAMERA
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestCameraPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.i("JHN", "onPermissionsGranted: ")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestCameraPermission()
        }
    }

    @AfterPermissionGranted(RC_CAMERA)
    private fun requestCameraPermission() {
        val perms = arrayOf(Manifest.permission.CAMERA)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this, getString(R.string.camera_rationale),
                RC_CAMERA, concat(*perms)
            )
        }
    }

    private fun concat(vararg strings: String): String {
        return strings.fold("") { acc, next -> "$acc$next" }
    }
}