package com.dilshayarana.app

object BloggerUtils {

    // 1. HTML Content me se Image URL nikalne ke liye
    fun extractImageUrl(htmlContent: String?): String? {
        if (htmlContent.isNullOrEmpty()) return null
        val regex = """<img[^>]+src=["']([^"']+)["']""".toRegex()
        val matchResult = regex.find(htmlContent)
        return matchResult?.groupValues?.get(1)
    }

    // 2. HTML Tags hata kar sirf pehle 100 characters (2 lines) dikhane ke liye
    fun extractSnippet(htmlContent: String?, maxLength: Int = 100): String {
        if (htmlContent.isNullOrEmpty()) return ""
        
        // Remove HTML tags and extra spacing
        val plainText = htmlContent
            .replace(Regex("<[^>]*>"), "")
            .replace("&nbsp;", " ")
            .trim()

        return if (plainText.length > maxLength) {
            plainText.substring(0, maxLength) + "..."
        } else {
            plainText
        }
    }
}
