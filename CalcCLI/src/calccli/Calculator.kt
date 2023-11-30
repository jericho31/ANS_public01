package calccli

import kotlin.math.floor

class Calculator {
    // 0: Long, 1: Double
    var tt = 0
    var ll = 0L
    var dd = .0
    val operators = "+-*/%"
    fun prnval() = if (tt == 0) println(ll) else println(dd)
    fun add(a: Double, b: Double) {
        if (floor(a) == a && floor(b) == b) {
            tt = 0; ll = a.toLong() + b.toLong()
        }
        else{
            tt = 1; dd = a + b
        }
    }
    fun subtract(a: Double, b: Double) {}
    fun multiply(a: Double, b: Double) {}
    fun mod(a: Double, b: Double) {}

    fun subtract(a: Long, b: Long) {}
    fun multiply(a: Long, b: Long) {}
    fun divide(a: Double, b: Double) {}
    fun mod(a: Long, b: Long) {}
}
