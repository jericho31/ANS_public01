package calccli

fun main() {
    val calc = Calculator()
    val a = 10
    val b = 3
    println("$a + $b = ${calc.add(a, b)}")
    println("$a + $b = ${calc.subtract(a, b)}")
    println("$a + $b = ${calc.multiply(a, b)}")
    println("$a + $b = ${calc.divide(a, b)}")
}
