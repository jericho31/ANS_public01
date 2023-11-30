package calccli

fun main() {
    println("계산기 프로그램 입니다. 식을 입력하세요.")
    println("\"연산자 숫자\" 혹은 \"숫자 연산자 숫자\" 형태로 입력합니다.")
    println("예시1: + 5    예시2: 7.6 / 3")
    println("연산자는 + - * / % 를 지원합니다.")
    println("초기화 하려면 r 또는 R을 입력하세요.")
    println("종료하려면 q 또는 Q를 입력하세요.")
    println("==========================")

    //todo 루프만 메인에서 돌리고 입력값 처리 전체는 계산기로 옮겨야.

    val calc = Calculator()
    var s: String
    lateinit var sl: List<String>
    while (true) {
        s = readln()
        when (s) {
            "" -> continue
            "q", "Q", "ㅂ" -> return
            "r", "R", "ㄱ" -> {
                calc.clear()
                println('0')
                continue
            }
        }
        sl = s.split(" ")

        if (sl[0].toDoubleOrNull() != null) {
            if (sl[0].toLongOrNull() != null) {
                if (sl.size < 2) {
                    println("error: 숫자 뒤에 입력값이 없습니다.")
                    continue
                }
                val o = sl[1][0]
                if (o !in calc.operators) {
                    println("error: 지원하는 연산자가 아닙니다. o = $o")
                    continue
                }
                if (sl.size < 3) {
                    println("error: 연산자 뒤에 입력값이 없습니다.")
                    continue
                }
                if (sl[2].toDoubleOrNull() == null) {
                    println("error: 두 번째 숫자를 잘못 입력했습니다. ${sl[2]}")
                    continue
                }
                val b = sl[2].toLongOrNull()
                if (b == null) {
                    calc.operate(sl[0].toDouble(), o, sl[2].toDouble())
                } else {
                    calc.operate(sl[0].toLong(), o, b)
                }
            } else {
                val o = sl[1][0]
                if (o !in calc.operators) {
                    println("error: 지원하는 연산자가 아닙니다. o = $o")
                    continue
                }
                if (sl.size < 3) {
                    println("error: 연산자 뒤에 입력값이 없습니다.")
                    continue
                }
                val b = sl[2].toDoubleOrNull()
                if (b == null) {
                    println("error: 두 번째 숫자를 잘못 입력했습니다. ${sl[2]}")
                    continue
                }
                calc.operate(sl[0].toDouble(), o, b)
            }
        } else {  // 연산자 + 숫자
            val o = sl[0][0]
            if (o !in calc.operators) {
                println("error: 지원하는 연산자가 아닙니다. o = $o")
                continue
            }
            if (sl.size < 2) {
                println("error: 연산자 뒤에 입력값이 없습니다.")
                continue
            }
            if (sl[1].toDoubleOrNull() == null) {
                println("error: 숫자를 잘못 입력했습니다. ${sl[1]}")
                continue
            }
            val b = sl[1].toLongOrNull()
            if (b == null) {
                calc.operate(o, sl[1].toDouble())
            } else {
                calc.operate(o, b)
            }
        }
        calc.prnval()
    }
}
