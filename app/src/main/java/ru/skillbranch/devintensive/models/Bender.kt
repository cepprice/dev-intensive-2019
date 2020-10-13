package ru.skillbranch.devintensive.models

class Bender(
    var status: Status = Status.NORMAL,
    var question: Question = Question.NAME
) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        if (question == Question.IDLE) {
            return "На этом все, вопросов больше нет" to status.color
        }

        val isInvalid =
            if (answer.isEmpty())
                null
            else
                question.hasInputError(answer)

        return if (isInvalid == null) {
            if (question.answers.contains(answer.toLowerCase())) {
                question = question.nextQuestion()
                "Отлично - ты справился\n${question.question}" to status.color
            } else {
                if (status == Status.CRITICAL) {
                    status = status.nextStatus()
                    question = Question.NAME
                    "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
                } else {
                    status = status.nextStatus()
                    "Это неправильный ответ\n${question.question}" to status.color
                }
            }
        } else {
            "$isInvalid\n${question.question}" to status.color
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)) ,
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus() : Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal+1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION

            override fun hasInputError(answer: String): String? {
                if (answer[0].isLowerCase())
                    return "Имя должно начинаться с заглавной буквы"
                return null
            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL

            override fun hasInputError(answer: String): String? {
                if (answer[0].isUpperCase())
                    return "Профессия должна начинаться со строчной буквы"
                return null
            }
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY

            override fun hasInputError(answer: String): String? {
                if (answer.contains(Regex("[0-9]")))
                    return "Материал не должен содержать цифр"
                return null
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL

            override fun hasInputError(answer: String): String? {
                if (answer.contains(Regex("[^0-9]")))
                    return "Год моего рождения должен содержать только цифры"
                return null
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE

            override fun hasInputError(answer: String): String? {
                if (answer.length != 7 || answer.contains(Regex("[^0-9]")))
                    return "Серийный номер содержит только цифры, и их 7"
                return null
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE

            override fun hasInputError(answer: String): String? {
                return null
            }
        };

        abstract fun nextQuestion() : Question
        abstract fun hasInputError(answer: String) : String?
    }
}