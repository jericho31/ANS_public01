package calccli

fun main() {
    println("계산기 프로그램 입니다. 식을 입력하세요.")
    println("\"연산자 숫자\" 혹은 \"숫자 연산자 숫자\" 형태로 입력합니다.")
    println("예시1: + 5    예시2: 7.6 / 3")
    println("연산자는 + - * / % 를 지원합니다.")
    println("초기화 하려면 r 또는 R을 입력하세요.")
    println("종료하려면 q 또는 Q를 입력하세요.")
    println("==========================")

    val calc = Calculator()
    var s: String
    lateinit var sl: List<String>
    while (true) {
        s = readln()
        when (s) {
            "" -> continue
            "q", "Q" -> return
            "r", "R" -> {
                calc.clear()
                println('0')
                continue
            }
        }
        sl = s.split(" ")
        when {
            sl[0].toLongOrNull() != null -> {

            }

            sl[0].toDoubleOrNull() != null -> {

            }

            else -> {
                val o = sl[0][0]
                if (o !in calc.operators) {
                    println("error: 지원하는 연산자가 아닙니다. o = $o")
                    continue
                }
                if (sl.size < 2) {
                    println("error: 연산자 뒤에 입력값이 없습니다.")
                    continue
                }
                if (sl[1].toLongOrNull() != null) {
                    calc.operate(o, sl[1].toLong())
                } else if (sl[1].toDoubleOrNull() != null) {
                    calc.operate(o, sl[1].toDouble())
                }

                calc.prnval()
            }
        }
    }
}
