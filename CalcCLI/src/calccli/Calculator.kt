package calccli

class Calculator {
    /** 0: Long, 1: Double */
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
            '+' -> {
                tt = 1; dd = AddOperation.op(a, b); dd
            }

            '-' -> {
                tt = 1; dd = SubtractOperation.op(a, b); dd
            }

            '*' -> {
                tt = 1; dd = MultiplyOperation.op(a, b); dd
            }

            '/' -> {
                tt = 1; dd = DivideOperation.op(a, b); dd
            }

            '%' -> mod(a, b)
            else -> throw IllegalArgumentException("error: 잘못된 연산자: $o")
        }

//    fun operate(a: Double, o: Char, b: Long): Double =
//        when (o) {
//            '+' -> AddOperation.op(a, b)
//            '-' -> subtract(a, b)
//            '*' -> multiply(a, b)
//            '/' -> divide(a, b)
//            '%' -> mod(a, b)
//            else -> throw IllegalArgumentException("error: 잘못된 연산자: $o")
//        }

//    fun operate(a: Long, o: Char, b: Double): Double =
//        when (o) {
//            '+' -> AddOperation.op(a, b)
//            '-' -> subtract(a, b)
//            '*' -> multiply(a, b)
//            '/' -> divide(a, b)
//            '%' -> mod(a, b)
//            else -> throw IllegalArgumentException("error: 잘못된 연산자: $o")
//        }

    fun operate(a: Long, o: Char, b: Long): Long =
        when (o) {
            '+' -> {
                tt = 0; ll = AddOperation.op(a, b); ll
            }

            '-' -> {
                tt = 0; ll = SubtractOperation.op(a, b); ll
            }

            '*' -> {
                tt = 0; ll = MultiplyOperation.op(a, b); ll
            }

            '/' -> {
                tt = 0; ll = DivideOperation.op(a, b); ll
            }

            '%' -> mod(a, b)
            else -> throw IllegalArgumentException("error: 잘못된 연산자: $o")
        }

    fun add(b: Double): Double {
        if (tt == 0) {
            tt = 1; dd = AddOperation.op(ll, b)
        } else dd = AddOperation.op(dd, b)
        return dd
    }

    fun addLongToLong(b: Long): Long {
        if (tt == 1) {
            println("warning: 기존값이 $dd Double입니다. 강제로 Long으로 캐스팅합니다.")
            tt = 0; ll = AddOperation.op(dd.toLong(), b)
        } else ll = AddOperation.op(ll, b)
        return ll
    }

    fun subtract(b: Double): Double {
        if (tt == 0) {
            tt = 1; dd = SubtractOperation.op(ll, b)
        } else dd = SubtractOperation.op(dd, b)
        return dd
    }

    fun subtractLongToLong(b: Long): Long {
        if (tt == 1) {
            println("warning: 기존값이 $dd Double입니다. 강제로 Long으로 캐스팅합니다.")
            tt = 0; ll = SubtractOperation.op(dd.toLong(), b)
        } else ll = SubtractOperation.op(ll, b)
        return ll
    }

    fun multiply(b: Double): Double {
        if (tt == 0) {
            tt = 1; dd = MultiplyOperation.op(ll, b)
        } else dd = MultiplyOperation.op(dd, b)
        return dd
    }

    fun multiplyLongToLong(b: Long): Long {
        if (tt == 1) {
            println("warning: 기존값이 $dd Double입니다. 강제로 Long으로 캐스팅합니다.")
            tt = 0; ll = MultiplyOperation.op(dd.toLong(), b)
        } else ll = MultiplyOperation.op(ll, b)
        return ll
    }

    fun divide(b: Double): Double {
        if (tt == 0) {
            tt = 1; dd = DivideOperation.op(ll, b)
        } else dd = DivideOperation.op(dd, b)
        return dd
    }

    fun divideLongToLong(b: Long): Long {
        if (tt == 1) {
            println("warning: 기존값이 $dd Double입니다. 강제로 Long으로 캐스팅합니다.")
            tt = 0; ll = DivideOperation.op(dd.toLong(), b)
        } else ll = DivideOperation.op(ll, b)
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
