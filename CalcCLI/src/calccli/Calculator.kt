package calccli

class Calculator {
    /**
     * 0: Long, 1: Double
     * 현재 결과값이 정수인지 소수인지 기억하는 변수.
     * 추가 계산 시 정수로 계산할지 소수로 계산할지 판단에 필요.
     */
    var curtype = 0

    var resultLong = 0L
    var resultDouble = .0

    /** 지원하는 연산자 목록 */
    val operators = "+-*/%"

    fun clear() {
        curtype = 0; resultLong = 0L; resultDouble = .0
    }

    fun prnval() = println(if (curtype == 0) resultLong else resultDouble)

    /**
     * 3 + 5 형태 혹은 + 5 형태로 들어오는 문자열 입력을 처리하는 함수.
     * 정수 + 정수 연산이면 정수로 계산하여 정수를 반환하고
     * 그 외의 정수 + 소수, 소수 + 소수 등은 모두 소수로 연산하여 소수를 반환한다.
     * 정수와 소수에 따른 반환형을 나눌 수 없으므로 String으로 반환.
     *
     * 추가 계산의 경우에도 현재 값이 정수인 상태에서 추가 연산이 정수로 들어오면 정수 계산을 한다.
     */
    fun implement(s: String): String {
        val stringList = s.split(" ")

        if (stringList[0].toDoubleOrNull() != null) {  // 숫자1 + 연산자 + 숫자2
            if (stringList[0].toLongOrNull() != null) {  // 정수1
                if (stringList.size < 2) return "error: 숫자 뒤에 입력값이 없습니다."
                val o = stringList[1][0]
                if (o !in operators) return "error: 지원하는 연산자가 아닙니다. o = $o"
                if (stringList.size < 3) return "error: 연산자 뒤에 입력값이 없습니다."
                if (stringList[2].toDoubleOrNull() == null) return "error: 두 번째 숫자를 잘못 입력했습니다. ${stringList[2]}"
                val b = stringList[2].toLongOrNull()
                if (b == null) {  // 소수2
                    curtype = 1
                    resultDouble = operate(stringList[0].toDouble(), o, stringList[2].toDouble())
                } else {  // 정수2
                    curtype = 0
                    resultLong = operate(stringList[0].toLong(), o, b)
                }
            } else {  // 소수1
                if (stringList.size < 2) return "error: 숫자 뒤에 입력값이 없습니다."
                val o = stringList[1][0]
                if (o !in operators) return "error: 지원하는 연산자가 아닙니다. o = $o"
                if (stringList.size < 3) return "error: 연산자 뒤에 입력값이 없습니다."
                val b = stringList[2].toDoubleOrNull()
                    ?: return "error: 두 번째 숫자를 잘못 입력했습니다. ${stringList[2]}"
                curtype = 1
                resultDouble = operate(stringList[0].toDouble(), o, b)
            }
        } else {  // 연산자 + 숫자2
            val o = stringList[0][0]
            if (o !in operators) return "error: 지원하는 연산자가 아닙니다. o = $o"
            if (stringList.size < 2) return "error: 연산자 뒤에 입력값이 없습니다."
            if (stringList[1].toDoubleOrNull() == null) return "error: 숫자를 잘못 입력했습니다. ${stringList[1]}"
            if (curtype == 0) {
                val b = stringList[1].toLongOrNull()
                if (b == null) {
                    curtype = 1
                    resultDouble = operate(resultLong.toDouble(), o, stringList[1].toDouble())
                } else {
                    resultLong = operate(resultLong, o, b)
                }
            } else {
                resultDouble = operate(resultDouble, o, stringList[1].toDouble())
            }
        }
        return if (curtype == 0) resultLong.toString() else resultDouble.toString()
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
