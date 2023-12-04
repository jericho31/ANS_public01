package kioskcli

class Kiosk {
    var itemListList = ArrayList<ItemList>()
    var isTerminate = false

    init {
        initItemListList()
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
        println("0. 종료\t\t| 프로그램 종료")

        while (true) {
            val s = readln()
            when (val n = s.toIntOrNull()) {
                null -> println("잘못된 입력입니다: $s")
                0 -> {
                    isTerminate = true
                    break
                }

                in 1..itemListList.size -> {
                    page_2(itemListList[n - 1])
                    break
                }

                else -> println("잘못된 입력입니다: $s")
            }
        }
    }

    fun page_2(itemList: ItemList) {
        println("[ ${itemList.name} 메뉴 ]")
        itemList.lst.forEachIndexed { index, item ->
            println("${index + 1}. ${item.name}\t| ${item.detail}")
        }
        println("0. 뒤로가기\t\t| 뒤로가기")

        while (true) {
            val s = readln()
            when (val n = s.toIntOrNull()) {
                null -> println("잘못된 입력입니다: $s")
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
        //todo Lv4 장바구니에 추가할까요
        println("${item.name} 주문되었습니다.")
    }

    fun initItemListList() {
        itemListList.add(ItemList("버거류", "맘스터치 버거는 다 맛있습니다.", createBurgers()))
        itemListList.add(ItemList("치킨류", "맘스터치 치킨은 다 맛있습니다.", createChickens()))
        itemListList.add(ItemList("음료류", "맘스터치 음료는 다 맛있습니다.", createDrinks()))
        itemListList.add(ItemList("사이드류", "맘스터치 사이드는 다 맛있습니다.", createSides()))
    }
}
