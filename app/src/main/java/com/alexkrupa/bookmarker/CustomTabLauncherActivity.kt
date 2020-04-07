package com.alexkrupa.bookmarker

import android.app.Activity
import android.content.Intent

class CustomTabLauncherActivity : Activity() {

    private var isFirstStart = true

    override fun onStart() {
        super.onStart()
        if (isFirstStart) {
            val sharedText = intent.sharedText() ?: return
            launchCustomTab(this, sharedText)
            isFirstStart = false
        } else {
            finish()
        }
    }
}

private fun Intent.sharedText(): String? {
    return if (isShareTextIntent()) {
        getStringExtra(Intent.EXTRA_TEXT)
    } else null
}

private fun Intent.isShareTextIntent() =
    action == Intent.ACTION_SEND && type == "text/plain"

private fun suicide() {
    Runtime.getRuntime().exit(0)
}