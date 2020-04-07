package com.alexkrupa.bookmarker

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK
import android.app.ActivityManager
import android.content.Context
import androidx.core.content.getSystemService

private const val MENU_BUTTON_ID = "com.android.chrome:id/menu_button"
private const val BACK_BUTTON_ID = "com.android.chrome:id/back_button"
private const val CUSTOM_TAB_ACTIVITY =
    "org.chromium.chrome.browser.customtabs.CustomTabActivity"
private const val BOOKMARK = "Bookmark"
private const val EDIT_BOOKMARK = "Edit bookmark"
private const val CLOSE_TAB = "Close tab"

class CustomTabController(
    private val service: AccessibilityService
) {
    private var shouldTryToBookmark = false

    fun onStateChange(root: Node) {
        if (!isCustomTab()) return

        if (shouldTryToBookmark) {
            if (isMenuOpen(root)) {
                if (!addBookmark(root)) {
                    pressBack()
                }
                shouldTryToBookmark = false
            } else {
                openMenu(root)
            }
        } else {
            if (isMenuOpen(root)) {
                pressBack()
            } else {
                closeTab(root)
                shouldTryToBookmark = true
            }
        }
    }

    private fun addBookmark(root: Node) = root.clickText(BOOKMARK).also { Thread.sleep(200) }
    private fun closeTab(root: Node) = root.clickText(CLOSE_TAB)
    private fun openMenu(root: Node) = root.clickViewId(MENU_BUTTON_ID)
    private fun pressBack() = service.performGlobalAction(GLOBAL_ACTION_BACK)

    private fun isMenuOpen(root: Node) =
        root.findNodeByText(BOOKMARK) ?: root.findNodeByText(EDIT_BOOKMARK) != null

    private fun isCustomTab() = topActivityClass(service.applicationContext) == CUSTOM_TAB_ACTIVITY
}

private fun topActivityClass(context: Context): String? {
    return context.getSystemService<ActivityManager>()
        ?.getRunningTasks(1)
        ?.get(0)
        ?.topActivity
        ?.className
}
