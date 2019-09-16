package ru.skillbranch.devintensive.extensions

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

/*Реализуй extension позволяющий очистить строку от html тегов и html escape последовательностей
("& < > ' ""), а так же удалить пустые символы (пробелы) между словами если их больше 1.
Необходимо вернуть модифицированную строку
Пример:
"<p class="title">Образовательное IT-сообщество <p>Skill</p> Branch</p>".stripHtml() //Образовательное IT-сообщество Skill Branch
"<p>Образовательное       IT-сообщество Skill Branch</p>".stripHtml() //Образовательное IT-сообщество Skill Branch*/

fun String.stripHtml(): String {
    //TODO maybe later
    return this.replace("<.*?>".toRegex(), "")
}