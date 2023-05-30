@file:JvmName("Log")

package android.util

// Взято с https://stackoverflow.com/a/46793567.
object Log {
    @JvmStatic
    fun d(tag: String, msg: String): Int {
        println("DEBUG: $tag: $msg")
        return 0
    }

}
