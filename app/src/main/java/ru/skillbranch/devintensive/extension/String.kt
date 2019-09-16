package ru.skillbranch.devintensive.extension

/*Реализуй extension усекающий исходную строку до указанного числа символов (по умолчанию 16) и
возвращающий усеченную строку с заполнителем "..." (если строка была усечена) если последний символ
усеченной строки является пробелом - удалить его и добавить заполнитель
Пример:
"Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate() //Bender Bending R...
"Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate(15) //Bender Bending...
"A     ".truncate(3) //A*/
fun String.truncate(size: Int = 16): String {
    require(size >= 0) { "Size must be non-negative, was $size" }

    val trimmed = this.trimEnd()
    if (trimmed.length <= size)
        return trimmed
    return "${this.subSequence(0, size).trimEnd()}..."
}