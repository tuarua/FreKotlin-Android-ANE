package com.mycompany.helloworldane

import android.content.Intent
import android.content.res.Configuration
import com.adobe.air.AndroidActivityWrapper.ActivityState
import com.adobe.air.AndroidActivityWrapper.ActivityState.*
import com.adobe.air.FreKotlinActivityResultCallback
import com.adobe.air.FreKotlinStateChangeCallback
import com.adobe.fre.FREContext
import com.adobe.fre.FREObject
import com.tuarua.frekotlin.*
import java.util.*

@Suppress("unused", "UNUSED_PARAMETER", "UNCHECKED_CAST")
class KotlinController : FreKotlinMainController, FreKotlinStateChangeCallback, FreKotlinActivityResultCallback {
    fun init(ctx: FREContext, argv: FREArgv): FREObject? {
        return null
    }

    fun sayHello(ctx: FREContext, argv: FREArgv): FREObject? {
        argv.takeIf { argv.size > 2 } ?: return FreArgException()
        val myString = String(argv[0]) ?: return FreArgException()
        val uppercase = Boolean(argv[1]) ?: return FreArgException()
        val numRepeats = Int(argv[2]) ?: return FreArgException()

        dispatchEvent("MY_EVENT", "ok")

        for (i in 0 until numRepeats) {
            trace("hello $i")
            // or
            // trace("Hello", i)
        }
        var ret = myString
        if (uppercase) {
            ret = ret.uppercase(Locale.getDefault())
        }
        return ret.toFREObject()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
    }

    override fun onConfigurationChanged(configuration: Configuration?) {
    }

    override fun onActivityStateChanged(activityState: ActivityState?) {
        when (activityState) {
            STARTED -> return
            RESTARTED -> return
            RESUMED -> return
            PAUSED -> return
            STOPPED -> return
            DESTROYED -> return
            else -> return
        }
    }

    override val TAG: String?
        get() = this::class.java.simpleName
    private var _context: FREContext? = null
    override var context: FREContext?
        get() = _context
        set(value) {
            _context = value
        }
}