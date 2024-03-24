package org.bspoones.chloris.command.annotations.choices

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class LongChoices(
    val choices: LongArray = [],
)