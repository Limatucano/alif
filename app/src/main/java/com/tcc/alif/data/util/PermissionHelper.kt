package com.tcc.alif.data.util

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts

class PermissionHelper{

    private var fragment: Fragment
    private var permissions: Array<String>
    private var onPermissionResult: OnPermissionResult
    private lateinit var singlePermissionLauncher: ActivityResultLauncher<String>
    private lateinit var multiplePermissionLauncher: ActivityResultLauncher<Array<String>>

    constructor(
        fragment: Fragment,
        permission: String,
        onPermissionResult: OnPermissionResult
    ){
        this.fragment = fragment
        this.permissions = arrayOf(permission)
        this.onPermissionResult = onPermissionResult
        this.singlePermissionLauncher = fragment.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){
            if(it){
                onPermissionResult.onAllPermissionsGranted()
            } else{
                onPermissionResult.onPermissionsDenied(
                    deniedPermissions = listOf(permissions.first()),
                    deniedPermissionsWithNeverAskAgain = if(fragment.shouldShowRequestPermissionRationale(permissions.first()))
                        listOf(permissions.first()) else listOf()
                )
            }
        }
    }

    constructor(
        fragment: Fragment,
        permissions: Array<String>,
        onPermissionResult: OnPermissionResult
    ){
        this.fragment = fragment
        this.permissions = permissions
        this.onPermissionResult = onPermissionResult
        this.multiplePermissionLauncher = fragment.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){ checkMultiPermissions(it) }
    }

    private fun checkMultiPermissions(result: Map<String, Boolean>){
        if(areAllPermissionsGranted()){
            onPermissionResult.onAllPermissionsGranted()
        } else {
            onPermissionResult.onPermissionsDenied(
                deniedPermissions = getDeniedPermissions(result),
                deniedPermissionsWithNeverAskAgain = getDeniedPermissionsWithNeverAskAgain(getDeniedPermissions(result))
            )
        }
    }

    private fun getDeniedPermissions(result: Map<String,Boolean>) = result.filter { !it.value }.keys.toList()

    private fun getDeniedPermissionsWithNeverAskAgain(result: List<String>) =
        mutableListOf<String>().apply {
            result.forEach { permission ->
                if(fragment.shouldShowRequestPermissionRationale(permission)) add(permission)
            }
        }

    private fun checkPermissions(permission: String) : Boolean =
        ContextCompat.checkSelfPermission(
            fragment.requireActivity(),
            permission
        ) == PackageManager.PERMISSION_GRANTED

    private fun areAllPermissionsGranted() : Boolean = permissions.all { checkPermissions(it) }

    fun launch(){
        when{
            areAllPermissionsGranted() -> {
                onPermissionResult.onAllPermissionsGranted()
            }
            permissions.size > 1 -> {
                multiplePermissionLauncher.launch(permissions)
            }
            permissions.size == 1 -> {
                singlePermissionLauncher.launch(permissions.first())
            }
        }
    }


    interface OnPermissionResult {
        fun onAllPermissionsGranted()

        fun onPermissionsDenied(
            deniedPermissions: List<String> = listOf(),
            deniedPermissionsWithNeverAskAgain: List<String> = listOf()
        )
    }
}