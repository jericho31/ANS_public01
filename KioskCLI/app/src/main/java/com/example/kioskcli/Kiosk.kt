package com.example.kioskcli

import com.example.kioskcli.Cashcard

class Kiosk {
    var itemListList = ArrayList<ItemList>()
    var isTerminate = false
    var orderList = ArrayList<Item>()

    init {
        initItemListList()
    }

    fun initItemListList() {
        itemListList.add(ItemList("버거류", "맘스터치 버거는 다 맛있습니다.", createBurgers()))
        itemListList.add(ItemList("치킨류", "맘스터치 치킨은 다 맛있습니다.", createChickens()))
        itemListList.add(ItemList("음료류", "맘스터치 음료는 다 맛있습니다.", createDrinks()))
        itemListList.add(ItemList("사이드류", "맘스터치 사이드는 다 맛있습니다.", createSides()))
    }

    fun run() {
        while (!isTerminate) page_1()
        println("프로그램을 종료합니다.")
    }

    fun page_1() {
        println("=================================")
        println("SHAKESHACK 키오스크입니다.")
        println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.")
        println("[ 맘스터치 메뉴 ]")
        itemListList.forEachIndexed { index, itemList ->
            println("${index + 1}. ${itemList.name}\t| ${itemList.detail}")
        }
        if (orderList.isNotEmpty()) {
            println("[ 주문 메뉴 ]")
            println("${itemListList.size + 1}. 주문\t\t| 장바구니를 확인 후 주문합니다.")
            println("${itemListList.size + 2}. 취소\t\t| 진행중인 주문을 취소합니다.")
        }
        println("0. 종료\t\t| 프로그램 종료")

        while (true) {
            val s = readln()
            when (val n = s.toIntOrNull() ?: -1) {
                0 -> {
                    isTerminate = true
                    break
                }

                in 1..itemListList.size -> {
                    page_2(itemListList[n - 1])
                    break
                }

                itemListList.size + 1 -> {
                    if (orderList.isEmpty()) {
                        println("잘못된 입력입니다: $s")
                    } else {
                        page_order()
                        break
                    }
                }

                itemListList.size + 2 -> {
                    if (orderList.isEmpty()) {
                        println("잘못된 입력입니다: $s")
                    } else {
                        orderList.clear()
                        println("주문을 취소합니다.")
                        delay(1)
                        break
                    }
                }

                else -> println("잘못된 입력입니다: $s")
            }
        }
    }

    fun page_2(itemList: ItemList) {
        println("[ ${itemList.name} 메뉴 ]")
        itemList.lst.forEachIndexed { index, item ->
            println("${index + 1}. ${item.info()}")
        }
        println("0. 뒤로가기")

        while (true) {
            val s = readln()
            when (val n = s.toIntOrNull() ?: -1) {
                0 -> break
                in 1..itemList.size() -> {
                    page_AddBasket(itemList[n - 1])
                    break
                }

                else -> println("잘못된 입력입니다: $s")
            }
        }
    }

    fun page_AddBasket(item: Item) {
        println("\"${item.info()}\"")
        println("위 메뉴를 장바구니에 추가하시겠습니까?")
        println("현재 합계: ${orderList.sumOf { it.price }}")
        println("1. 확인    2. 취소")

        while (true) {
            val s = readln()
            when (s.toIntOrNull() ?: -1) {
                1 -> {
                    orderList.add(item)
                    println("${item.name} 가 장바구니에 추가되었습니다.")
                    println("현재 합계: ${orderList.sumOf { it.price }}")
                    delay(1)
                    break
                }

                2 -> {
                    println("취소되었습니다.")
                    delay(1)
                    break
                }

                else -> println("잘못된 입력입니다: $s")
            }
        }
    }

    fun page_order() {
        val total = orderList.fold(0) { acc, item -> acc + item.price }

        println("아래와 같이 주문 하시겠습니까?")
        println("[ 주문 목록 ]")
        orderList.sortBy { it.name }
        orderList.forEachIndexed { index, item ->
            println("${index + 1}. ${item.info()}")
        }
        println("합계: $total 원")
        println()
        println("1. 주문    2 또는 0. 뒤로가기")

        while (true) {
            val s = readln()
            when (s.toIntOrNull() ?: -1) {
                1 -> {
                    if (Cashcard.money < total) {
                        println("현재 잔액은 ${Cashcard.money}원으로 ${total - Cashcard.money}원이 부족해서 주문할 수 없습니다.")
                        // TODO: 주문 취소
                        println("처음부터 다시 시도해주십시오.")
                        orderList.clear()
                    } else {
                        Cashcard.money -= total
                        println("주문되었습니다. 현재 잔액은 ${Cashcard.money}원입니다.")
                        orderList.clear()
                    }
                    delay(3)
                    break
                }

                2, 0 -> break

                else -> println("잘못된 입력입니다: $s")
            }
        }
    }

    fun delay(sec: Int) {
        print("... ")
        (sec downTo 1).forEach {
            print("$it ")
            Thread.sleep(999)
        }
        println()
    }
}
