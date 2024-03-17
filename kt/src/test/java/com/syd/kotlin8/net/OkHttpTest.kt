package com.syd.kotlin8.net

import okhttp3.*
import org.junit.jupiter.api.Test
import java.io.IOException

class OkHttpTest {
    @Test
    fun testBaidu() {
        val client = OkHttpClient()
        val req: Request = Request.Builder().get()
            .url("https://www.baidu.com")
            .build()
        val call: Call = client.newCall(req)
//        call.enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            @Throws(IOException::class)
//            override fun onResponse(call: Call, response: Response) {
//                println(response.body.string())
//            }
//        })
        println(call.execute().body.string())
    }
}