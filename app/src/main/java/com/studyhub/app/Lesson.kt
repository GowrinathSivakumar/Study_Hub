package com.studyhub.app

import androidx.annotation.RawRes

data class Lesson(
    val title: String,
    @RawRes val videoResId: Int,
    val thumbnailResId: Int = R.drawable.subject_icon
)
