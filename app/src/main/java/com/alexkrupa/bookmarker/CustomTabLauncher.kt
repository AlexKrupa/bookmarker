package com.alexkrupa.bookmarker

import android.app.Activity
import android.webkit.URLUtil
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

private val URL_REGEX =
    """(http|ftp|https)://([\w_-]+(?:(?:\.[\w_-]+)+))([\w.,@?^=%&:/~+#-]*[\w@?^=%&/~+#-])?"""
        .toRegex()

fun launchCustomTab(activity: Activity, urlCandidate: String) {
    val toast = activity::toast

    val url: String? = urlCandidate.extractUrl()
    if (url == null) {
        toast("No valid URL found.")
        return
    }

    toast("Bookmarking...")
    CustomTabsIntent.Builder()
        .addDefaultShareMenuItem()
        .setStartAnimations(activity, 0, 0)
        .setExitAnimations(activity, 0, 0)
        .build()
        .launchUrl(activity, url.toUri())
}

private fun String.extractUrl(): String? {
    return if (this.isValidUrl()) {
        this
    } else {
        val extractedUrl = URL_REGEX.find(this)?.value
        extractedUrl?.takeIf { it.isValidUrl() }
    }
}

private fun String.isValidUrl() = URLUtil.isValidUrl(this)