package com.mycompany.helloworldane

import com.adobe.fre.FREContext
import com.adobe.fre.FREObject
import com.tuarua.frekotlin.*

@Suppress("unused", "UNUSED_PARAMETER", "UNCHECKED_CAST")
class KotlinController : FreKotlinMainController {
    fun init(ctx: FREContext, argv: FREArgv): FREObject? {
        return null
    }

    fun sayHello(ctx: FREContext, argv: FREArgv): FREObject? {
        argv.takeIf { argv.size > 2 } ?: return FreArgException("sayHello")

        val myString = String(argv[0])
        val uppercase = Boolean(argv[1])
        val numRepeats = Int(argv[2])

        if (myString != null && uppercase != null && numRepeats != null) {
            dispatchEvent("MY_EVENT", "ok")

            for (i in 0 until numRepeats) {
                trace("hello $i")
                // or
                // trace("Hello", i)
            }
            var ret = myString
            if (uppercase) {
                ret = ret.toUpperCase()
            }
            return ret.toFREObject()
        }
        return null
    }

    override fun onStarted() {
        super.onStarted()
    }

    override fun onRestarted() {
        super.onRestarted()
    }

    override fun onResumed() {
        super.onResumed()
    }

    override fun onPaused() {
        super.onPaused()
    }

    override fun onStopped() {
        super.onStopped()
    }

    override fun onDestroyed() {
        super.onDestroyed()
    }

    override val TAG: String
        get() = this::class.java.simpleName
    private var _context: FREContext? = null
    override var context: FREContext?
        get() = _context
        set(value) {
            _context = value
        }
}