package com.aura.enfocabita.utils

object FrasesMotivacionales {

    private val frases = listOf(
        "Un hábito a la vez. ¡Sigue así!",
        "No se trata de perfección, sino de progreso.",
        "Tu constancia crea tu éxito.",
        "Hoy es un buen día para mejorar.",
        "La disciplina supera la motivación.",
        "Pequeños pasos, grandes logros.",
        "Confía en el proceso.",
        "Hazlo por ti, por tu futuro.",
        "Incluso los días pequeños cuentan.",
        "No te rindas, ¡estás más cerca de lo que crees!",
        "Lo que hagas hoy, lo agradeceras mañana.",
        "Hacer un 1% es mejor que no hacer nada.",
        "Cada día es una nueva oportunidad para reconstruir la versión de ti mismo que deseas ser.",
        "No tienes que ser grande para empezar, pero tienes que empezar para ser grande.",
        "La disciplina es elegir entre lo que quieres ahora y lo que quieres más.",
        "Un pequeño paso hoy es un gran salto mañana.",
        "El primer paso no te lleva a donde quieres ir, pero te saca de donde estás.",
        "Tus hábitos de hoy crean tu futuro de mañana.",
        "No se trata de la motivación, sino de la acción. Actúa y la motivación te seguirá.",
        "La incomodidad temporal lleva a la libertad a largo plazo.",
        "No importa lo lento que vayas, siempre y cuando no te detengas.",
        "El éxito es la suma de pequeños esfuerzos repetidos día tras día."
    )

    fun obtenerAleatoria(): String = frases.random()
}