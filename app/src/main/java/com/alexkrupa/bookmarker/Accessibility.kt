package com.alexkrupa.bookmarker

import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

typealias Node = AccessibilityNodeInfo
typealias Event = AccessibilityEvent

fun Node.clickViewId(id: String) =
    findNodeByViewId(id)
        ?.takeIf { it.isClickable }
        ?.click()
        ?: false

fun Node.clickText(text: String) =
    findNodeByText(text)
        ?.takeIf { it.isClickable }
        ?.click()
        ?: false

fun Node.findNodeByViewId(id: String) =
    findAccessibilityNodeInfosByViewId(id).firstOrNull()

fun Node.findNodeByText(text: String) =
    findAccessibilityNodeInfosByText(text).firstOrNull()
        ?.takeIf { text == it.contentDescription }

fun Node.click() =
    performAction(Node.ACTION_CLICK)

fun Event.format(): String {
    return this.toString()
        .split(';')
        .filter { line ->
            arrayOf(
                "EventType: ",
                "PackageName: ",
                "ClassName: ",
                "Text: [",
                "ContentDescription: "
            ).any { it in line }
        }
        .joinToString(" | ") { it.trim() }
}