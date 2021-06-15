package com.example.myapplication.businessschema

import java.io.File

interface OnDataReceived {
    fun onDataReceivedListener(data: File?)
}