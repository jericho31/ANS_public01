package calccli

import java.io.StreamTokenizer

fun main() {
    println("계산기 프로그램 입니다. 식을 입력하세요.")
    println("\"숫자 연산자 숫자\" 형태로 입력합니다.")
    println("예시1: 2 + 3")
    println("예시2: 7 / 2.5")
    println("연산자는 + - * / % 를 지원합니다.")
    println("초기화 하려면 r 또는 R을 입력하세요.")
    println("종료하려면 q 또는 Q를 입력하세요.")
    println("==========================")

    val calc = Calculator()
//    var s = ""
    lateinit var st: StreamTokenizer
    var tk: Int
    while (true) {
        st = StreamTokenizer(System.`in`.bufferedReader())
        tk = st.nextToken()
        when (tk) {
            StreamTokenizer.TT_EOF, StreamTokenizer.TT_EOL -> continue
            StreamTokenizer.TT_NUMBER -> {
                val a = st.nval
                tk = st.nextToken()
                if (tk != 43) {  // 왠지 모르겠는데 43으로 찍히네...
                    println("error: 숫자 다음 연산자가 아님. tk = $tk")
                    continue
                }
                val sss = st.sval
                println("sss: $sss")
                val o = st.sval[0]
                if (o !in calc.operators) {
                    println("error: 지원하는 연산자가 아닙니다. o = $o")
                    continue
                }
                tk = st.nextToken()
                if (tk != StreamTokenizer.TT_NUMBER) {
                    println("error: 문자 다음 숫자가 아님. tk = $tk")
                    continue
                }
                val b = st.nval
                when (o) {
                    '+' -> {
                        calc.add(a, b)
                    }
                    //todo - * / %
                }
                calc.prnval()
            }

            43 -> {  // 왠지 모르겠는데 43으로 찍히네...
                when (st.sval) {
                    "q", "Q" -> return
                    "r", "R" -> {
                        println("0\n")
                        continue
                    }

                    "+" -> {
                        //Todo +
                    }
                    //Todo - * / %
                    else -> {
                        println("잘못된 입력입니다.")
                        continue
                    }
                }

                val s = st.sval
                if (s == "q" || s == "Q") return
                if (s == "r" || s == "R") {
                    println("0\n")
                } else println("잘못된 입력입니다.")
                continue
            }

            StreamTokenizer.TT_WORD -> println("error: TT_WORD")
            else -> {
                println("error: 입력이 뭔가 잘못됐습니다. token: $tk")
            }
        }
    }
}
