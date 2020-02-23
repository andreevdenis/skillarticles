package ru.skillbranch.skillarticles.extensions

fun String.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> {
    val result = mutableListOf<Int>()
    val substrLen = substr.length
    var  startindex = 0
    do {
        val index = this.indexOf(substr, startindex, ignoreCase)
        if (index != -1) {
            result.add(index)
            startindex = index + substrLen
        }
    } while (index != -1)
    return result
}