/*
 * Copyright (C) 2021 Chaldeaprjkt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.chaldeaprjkt.gamespace.widget.tiles

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import io.chaldeaprjkt.gamespace.R

class BoostMemoryTile @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : BaseTile(context, attrs) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        title?.text = context.getString(R.string.memory_boost)
        summary?.text = context.getString(R.string.memory_boost_summary)
        icon?.setImageResource(R.drawable.ic_gear)
    }

    override fun onClick(v: View?) {
        isSelected = true
        releaseMemory()
        postDelayed({
            Toast.makeText(context, R.string.boost_memory, Toast.LENGTH_SHORT).show()
            isSelected = false
        }, 500)
    }

    private fun releaseMemory() {
        try {
            android.app.ActivityManager.getService().releaseMemory(606, 60, false, false)
        } catch (e: Exception) {}
    }
}
