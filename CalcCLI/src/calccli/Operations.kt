package calccli

object AddOperation {
    fun op(a: Double, b: Double): Double = a + b
    fun op(a: Double, b: Long): Double = a + b
    fun op(a: Long, b: Double): Double = a + b
    fun op(a: Long, b: Long): Long = a + b
}

object SubtractOperation {
    fun op(a: Double, b: Double): Double = a - b
    fun op(a: Double, b: Long): Double = a - b
    fun op(a: Long, b: Double): Double = a - b
    fun op(a: Long, b: Long): Long = a - b
}

object MultiplyOperation {
    fun op(a: Double, b: Double): Double = a * b
    fun op(a: Double, b: Long): Double = a * b
    fun op(a: Long, b: Double): Double = a * b
    fun op(a: Long, b: Long): Long = a * b
}

object DivideOperation {
    fun op(a: Double, b: Double): Double = a / b
    fun op(a: Double, b: Long): Double = a / b
    fun op(a: Long, b: Double): Double = a / b
    fun op(a: Long, b: Long): Long = a / b
}
