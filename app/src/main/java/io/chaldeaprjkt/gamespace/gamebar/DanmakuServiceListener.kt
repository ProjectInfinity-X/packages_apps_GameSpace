/*
 * Copyright (C) 2020 The exTHmUI Open Source Project
 * Copyright (C) 2021 AOSP-Krypton Project
 * Copyright (C) 2022 Nameless-AOSP Project
 * Copyright (C) 2023 the risingOS android Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.chaldeaprjkt.gamespace.gamebar

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class DanmakuServiceListener : NotificationListenerService() {

    private val postedNotifications = mutableSetOf<String>()

    var danmakuServiceInterface: DanmakuServiceInterface? = null

    override fun onListenerConnected() {
        super.onListenerConnected()
        getActiveNotifications()?.forEach { sbn ->
            if (sbn.isClearable && !sbn.isOngoing) {
                val title = sbn.notification.extras.getCharSequence(Notification.EXTRA_TITLE)?.toString()
                    ?: sbn.notification.extras.getCharSequence(Notification.EXTRA_TITLE_BIG)?.toString()
                val text = sbn.notification.extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()

                val danmakuText = "[$title] $text".trim()
                if (danmakuText.isNotBlank()) {
                    postedNotifications.add(danmakuText)
                }
            }
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (!(danmakuServiceInterface?.danmakuNotificationMode ?: false) || !sbn.isClearable || sbn.isOngoing) return

        val title = sbn.notification.extras.getCharSequence(Notification.EXTRA_TITLE)?.toString()
            ?: sbn.notification.extras.getCharSequence(Notification.EXTRA_TITLE_BIG)?.toString()
        val text = sbn.notification.extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()

        val danmakuText = "[$title] $text".trim()

        if (danmakuText.isNotBlank() && !postedNotifications.contains(danmakuText)) {
            danmakuServiceInterface?.showNotificationAsOverlay(danmakuText)
            insertPostedNotification(danmakuText)
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        val title = sbn.notification.extras.getCharSequence(Notification.EXTRA_TITLE)?.toString()
            ?: sbn.notification.extras.getCharSequence(Notification.EXTRA_TITLE_BIG)?.toString()
        val text = sbn.notification.extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()

        val danmakuText = "[$title] $text".trim()

        if (danmakuText.isNotBlank()) {
            postedNotifications.remove(danmakuText)
        }
    }

    private fun insertPostedNotification(danmakuText: String) {
        postedNotifications.add(danmakuText)
    }

    companion object {
        private const val NOTIFICATIONS_MAX_CACHED = 99
    }
}
