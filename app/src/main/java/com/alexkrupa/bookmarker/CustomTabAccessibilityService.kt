package com.alexkrupa.bookmarker

import android.accessibilityservice.AccessibilityService

class CustomTabAccessibilityService : AccessibilityService() {

    private val controller = CustomTabController(this)

    override fun onAccessibilityEvent(event: Event) {
        log(event.format())
        controller.onStateChange(rootInActiveWindow)
    }

    override fun onInterrupt() = Unit
}