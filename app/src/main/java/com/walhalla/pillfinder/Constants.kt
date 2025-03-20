package com.walhalla.pillfinder

interface Constants {

    companion object {
        //samsungapps
        //amazon
        val JSONKey: Array<String> = arrayOf("id", "ndc11", "name", "labeler", "imageUrl")


        //String ENDPOINT_RX_IMAGE = "https://rximage.nlm.nih.gov/api/";
        //String ENDPOINT_RX_IMAGE = "https://www.mobixed.com/";
        const val ENDPOINT_FDA_GOV: String = "https://api.fda.gov/"

        const val URL_PRIVACY_POLICY: String =
            "https://pillidentifier-dfb05.firebaseapp.com/privacy_policy.html"

        //Pill Image Recognition Challenge
        const val FIELD_PAGE: String = "rPage"

        const val KEY_TIMEOUT: Long = 60
    }
}
