package com.ch2ps075.talenthub.ui.profile.helpcenter

data class Question(
    val title: String,
    val answer: String,
    var isExpandable: Boolean = false
)
