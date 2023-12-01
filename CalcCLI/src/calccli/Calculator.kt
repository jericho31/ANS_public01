package calccli

class Calculator {
    /** 0: Long, 1: Double */
    var curtype = 0
    var ll = 0L
    var dd = .0
    val operators = "+-*/%"

    fun clear() {
        curtype = 0; ll = 0L; dd = .0
    }

    fun prnval() = println(if (curtype == 0) ll else dd)

    fun implement(s: String): String {
        val sl = s.split(" ")

        if (sl[0].toDoubleOrNull() != null) {  // 숫자1 + 연산자 + 숫자2
            if (sl[0].toLongOrNull() != null) {  // 정수1
                if (sl.size < 2) return "error: 숫자 뒤에 입력값이 없습니다."
                val o = sl[1][0]
                if (o !in operators) return "error: 지원하는 연산자가 아닙니다. o = $o"
                if (sl.size < 3) return "error: 연산자 뒤에 입력값이 없습니다."
                if (sl[2].toDoubleOrNull() == null) return "error: 두 번째 숫자를 잘못 입력했습니다. ${sl[2]}"
                val b = sl[2].toLongOrNull()
                if (b == null) {  // 소수2
                    curtype = 1
                    dd = operate(sl[0].toDouble(), o, sl[2].toDouble())
                } else {  // 정수2
                    curtype = 0
                    ll = operate(sl[0].toLong(), o, b)
                }
            } else {  // 소수1
                if (sl.size < 2) return "error: 숫자 뒤에 입력값이 없습니다."
                val o = sl[1][0]
                if (o !in operators) return "error: 지원하는 연산자가 아닙니다. o = $o"
                if (sl.size < 3) return "error: 연산자 뒤에 입력값이 없습니다."
                val b = sl[2].toDoubleOrNull() ?: return "error: 두 번째 숫자를 잘못 입력했습니다. ${sl[2]}"
                curtype = 1
                dd = operate(sl[0].toDouble(), o, b)
            }
        } else {  // 연산자 + 숫자
            val o = sl[0][0]
            if (o !in operators) return "error: 지원하는 연산자가 아닙니다. o = $o"
            if (sl.size < 2) return "error: 연산자 뒤에 입력값이 없습니다."
            if (sl[1].toDoubleOrNull() == null) return "error: 숫자를 잘못 입력했습니다. ${sl[1]}"
            if (curtype == 0) {
                val b = sl[1].toLongOrNull()
                if (b == null) {
                    curtype = 1
                    dd = operate(ll.toDouble(), o, sl[1].toDouble())
                } else {
                    ll = operate(ll, o, b)
                }
            } else {
                dd = operate(dd, o, sl[1].toDouble())
            }
        }
        return if (curtype == 0) ll.toString() else dd.toString()
    }

    // implement에서 현재 타입도 확인해서 들어오기 때문에 DoD 혹은 LoL 밖에 없다...가 아니라?
    // 추가 계산 LoD는 있네;; 그럼 이건 impl쪽에서 현재를 바꿔서 DoD 형태로 불러주면? 될듯!
    // 추가 계산도 다 aob 형태로 호출하고.
    // 현재 타입 바꾸고 그런 것도 다.
    // operate는 "연산만" 하니까 코드 확 정리되네... ㅠㅠ
    fun operate(a: Double, o: Char, b: Double): Double =
        when (o) {
            '+' -> AddOperation.op(a, b)
            '-' -> SubtractOperation.op(a, b)
            '*' -> MultiplyOperation.op(a, b)
            '/' -> DivideOperation.op(a, b)
            '%' -> mod(a, b)
            else -> throw IllegalArgumentException("error: 잘못된 연산자: $o")
        }

    fun operate(a: Long, o: Char, b: Long): Long =
        when (o) {
            '+' -> AddOperation.op(a, b)
            '-' -> SubtractOperation.op(a, b)
            '*' -> MultiplyOperation.op(a, b)
            '/' -> DivideOperation.op(a, b)
            '%' -> mod(a, b)
            else -> throw IllegalArgumentException("error: 잘못된 연산자: $o")
        }

    fun mod(a: Double, b: Double): Double = a % b
    fun mod(a: Double, b: Long): Double = a % b
    fun mod(a: Long, b: Double): Double = a % b
    fun mod(a: Long, b: Long): Long = a % b
}
