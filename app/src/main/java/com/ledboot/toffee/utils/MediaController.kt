package com.ledboot.toffee.utils

import java.nio.ByteBuffer

/**
 * Created by Gwynn on 17/10/31.
 */
class MediaController private constructor(){

    companion object {
        init {
//            System.loadLibrary("toffee")
        }

        private var mInstance: MediaController? = null

        val instance:MediaController
        get() {
            if (mInstance == null) {
                synchronized(MediaController::class.java) {
                    if (mInstance== null)
                        mInstance = MediaController()
                }
            }
            return mInstance as MediaController
        }
    }

    external fun startRecord(path: String): Int
    external fun writeFrame(frame: ByteBuffer, len: Int): Int
    external fun stopRecord()
    external fun openOpusFile(path: String): Int
    external fun seekOpusFile(position: Float): Int
    external fun isOpusFile(path: String): Int
    external fun closeOpusFile()
    external fun testFun(args: IntArray)
    external fun readOpusFile(buffer: ByteBuffer, capacity: Int, args: IntArray)
    external fun getTotalPcmDuration(): Long
    external fun getWaveform(path: String): ByteArray
    external fun getWaveform2(array: ShortArray, length: Int): ByteArray

}