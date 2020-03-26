package com.example.swipequiz

data class Question(
    var questionText: String,
    var answer: Boolean
) {
    //assigns values to the predefined variables questionText and answer
    companion object {
        val QUESTIONS = arrayOf(
            "A 'val' and 'var' are the same.",
            "Mobile Application Development grants 12 ECTS.",
            "A Unit in Kotlin corresponds to a void in Java.",
            "In Kotlin 'when' replaces the 'switch' operator in Java."
        )

        val ANSWERS = arrayOf(
            false,
            true,
            false,
            true
        )
    }
}
