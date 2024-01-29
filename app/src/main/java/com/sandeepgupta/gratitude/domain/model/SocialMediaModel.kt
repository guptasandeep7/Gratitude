package com.sandeepgupta.gratitude.domain.model

import com.sandeepgupta.gratitude.R

data class SocialMediaModel(
    val drawable: Int,
    val title: String,
    val packageName: String
)

val socialMediaList = listOf(
    SocialMediaModel(R.drawable.ic_whatsapp, "Whatsapp", "com.whatsapp"),
    SocialMediaModel(R.drawable.ic_instagram, "Instagram", "com.instagram.android"),
    SocialMediaModel(R.drawable.ic_facebook, "Facebook", "com.facebook.katana")
)
