package calccli

class Calculator {
    // 0: Long, 1: Double
    var tt = 0
    var ll = 0L
    var dd = .0
    val operators = "+-*/%"
    fun clear() {
        tt = 0; ll = 0L; dd = .0
    }

    fun prnval() = if (tt == 0) println(ll) else println(dd)
    fun operate(o: Char, b: Double): Double =
        when (o) {
            '+' -> add(b)
            '-' -> subtract(b)
            '*' -> multiply(b)
            '/' -> divide(b)
            '%' -> mod(b)
            else -> throw IllegalArgumentException("error: 잘못된 연산자: $o")
        }

    fun operate(o: Char, b: Long): Unit {
        if (tt == 1) {
            operate(o, b.toDouble())
            return
        }
        when (o) {
            '+' -> addLongToLong(b)
            '-' -> subtractLongToLong(b)
            '*' -> multiplyLongToLong(b)
            '/' -> divideLongToLong(b)
            '%' -> modLongToLong(b)
            else -> throw IllegalArgumentException("error: 잘못된 연산자: $o")
        }
    }

    fun operate(a: Double, o: Char, b: Double): Double =
        when (o) {
            '+' -> add(a, b)
            '-' -> subtract(a, b)
            '*' -> multiply(a, b)
            '/' -> divide(a, b)
            '%' -> mod(a, b)
            else -> throw IllegalArgumentException("error: 잘못된 연산자: $o")
        }

    fun operate(a: Double, o: Char, b: Long): Double =
        when (o) {
            '+' -> add(a, b)
            '-' -> subtract(a, b)
            '*' -> multiply(a, b)
            '/' -> divide(a, b)
            '%' -> mod(a, b)
            else -> throw IllegalArgumentException("error: 잘못된 연산자: $o")
        }

    fun operate(a: Long, o: Char, b: Double): Double =
        when (o) {
            '+' -> add(a, b)
            '-' -> subtract(a, b)
            '*' -> multiply(a, b)
            '/' -> divide(a, b)
            '%' -> mod(a, b)
            else -> throw IllegalArgumentException("error: 잘못된 연산자: $o")
        }

    fun operate(a: Long, o: Char, b: Long): Long =
        when (o) {
            '+' -> add(a, b)
            '-' -> subtract(a, b)
            '*' -> multiply(a, b)
            '/' -> divide(a, b)
            '%' -> mod(a, b)
            else -> throw IllegalArgumentException("error: 잘못된 연산자: $o")
        }

    fun add(b: Double): Double {
        if (tt == 0) {
            tt = 1; dd = b + ll
        } else dd += b
        return dd
    }

    fun addLongToLong(b: Long): Long {
        if (tt == 1) {
            println("warning: 기존값이 $dd Double입니다. 강제로 Long으로 캐스팅합니다.")
            tt = 0; ll = dd.toLong() + b
        } else ll += b
        return ll
    }

    fun subtract(b: Double): Double {
        if (tt == 0) {
            tt = 1; dd = ll - b
        } else dd -= b
        return dd
    }

    fun subtractLongToLong(b: Long): Long {
        if (tt == 1) {
            println("warning: 기존값이 $dd Double입니다. 강제로 Long으로 캐스팅합니다.")
            tt = 0; ll = dd.toLong() - b
        } else ll -= b
        return ll
    }

    fun multiply(b: Double): Double {
        if (tt == 0) {
            tt = 1; dd = b * ll
        } else dd *= b
        return dd
    }

    fun multiplyLongToLong(b: Long): Long {
        if (tt == 1) {
            println("warning: 기존값이 $dd Double입니다. 강제로 Long으로 캐스팅합니다.")
            tt = 0; ll = dd.toLong() * b
        } else ll *= b
        return ll
    }

    fun divide(b: Double): Double {
        if (tt == 0) {
            tt = 1; dd = ll / b
        } else dd /= b
        return dd
    }

    fun divideLongToLong(b: Long): Long {
        if (tt == 1) {
            println("warning: 기존값이 $dd Double입니다. 강제로 Long으로 캐스팅합니다.")
            tt = 0; ll = dd.toLong() / b
        } else ll /= b
        return ll
    }

    fun mod(b: Double): Double {
        if (tt == 0) {
            tt = 1; dd = ll % b
        } else dd %= b
        return dd
    }

    fun modLongToLong(b: Long): Long {
        if (tt == 1) {
            println("warning: 기존값이 $dd Double입니다. 강제로 Long으로 캐스팅합니다.")
            tt = 0; ll = dd.toLong() % b
        } else ll %= b
        return ll
    }


    fun add(a: Double, b: Double): Double {
        tt = 1; dd = a + b; return dd
    }

    fun add(a: Double, b: Long): Double {
        tt = 1; dd = a + b; return dd
    }

    fun add(a: Long, b: Double): Double {
        tt = 1; dd = a + b; return dd
    }

    fun add(a: Long, b: Long): Long {
        tt = 0; ll = a + b; return ll
    }

    fun subtract(a: Double, b: Double): Double {
        tt = 1; dd = a - b; return dd
    }

    fun subtract(a: Double, b: Long): Double {
        tt = 1; dd = a - b; return dd
    }

    fun subtract(a: Long, b: Double): Double {
        tt = 1; dd = a - b; return dd
    }

    fun subtract(a: Long, b: Long): Long {
        tt = 0; ll = a - b; return ll
    }

    fun multiply(a: Double, b: Double): Double {
        tt = 1; dd = a * b; return dd
    }

    fun multiply(a: Double, b: Long): Double {
        tt = 1; dd = a * b; return dd
    }

    fun multiply(a: Long, b: Double): Double {
        tt = 1; dd = a * b; return dd
    }

    fun multiply(a: Long, b: Long): Long {
        tt = 0; ll = a * b; return ll
    }

    fun divide(a: Double, b: Double): Double {
        tt = 1; dd = a / b; return dd
    }

    fun divide(a: Double, b: Long): Double {
        tt = 1; dd = a / b; return dd
    }

    fun divide(a: Long, b: Double): Double {
        tt = 1; dd = a / b; return dd
    }

    fun divide(a: Long, b: Long): Long {
        tt = 0; ll = a / b; return ll
    }

    fun mod(a: Double, b: Double): Double {
        tt = 1; dd = a / b; return dd
    }

    fun mod(a: Double, b: Long): Double {
        tt = 1; dd = a / b; return dd
    }

    fun mod(a: Long, b: Double): Double {
        tt = 1; dd = a / b; return dd
    }

    fun mod(a: Long, b: Long): Long {
        tt = 0; ll = a / b; return ll
    }
}
