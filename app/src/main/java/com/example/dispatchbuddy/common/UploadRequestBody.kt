package com.example.dispatchbuddy.common

import android.os.Handler
import android.os.Looper
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.ProfileFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream

class UploadRequestBody(
    private val file: File,
    private val contentType:String,
    private val callBack: ProfileFragment
): RequestBody(){
    interface UploadCallBack {
        fun onProgressUpdate(percentage:Int)
    }

    override fun contentType() = "$contentType/*".toMediaTypeOrNull()
    override fun contentLength() = file.length()
    override fun writeTo(sink: BufferedSink) {
        val length = file.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val fileInputStream = FileInputStream(file)
        var uploaded = 0L
        fileInputStream.use {inputStream ->
            var read:Int
            val handler = Handler(Looper.getMainLooper())
            while (inputStream.read(buffer).also { read = it } != -1){
//                handler.post(ProgressUpdate(uploaded, length))
                uploaded += read
                sink.write(buffer,0,read)
            }
        }
    }
    companion object{
        private const val DEFAULT_BUFFER_SIZE = 1048
    }
}