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
    var s: String  // lateinit을 쓰나 안쓰나 똑같나?
    while (true) {
        s = readln()
        when (s) {
            "" -> continue
            "q", "Q", "ㅂ" -> return
            "r", "R", "ㄱ" -> {
                calc.clear()
                calc.prnval()
                continue
            }
        }
        println(calc.implement(s))
    }
}
