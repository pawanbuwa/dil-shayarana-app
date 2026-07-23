package com.dilshayarana.app

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

object AdManager {

    // आपका Monetag Direct Link
    private const val MONETAG_DIRECT_LINK = "https://omg10.com/4/11372270"

    fun showMonetagAd(context: Context) {
        try {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, Uri.parse(MONETAG_DIRECT_LINK))
        } catch (e: Exception) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(MONETAG_DIRECT_LINK))
            context.startActivity(intent)
        }
    }
}
