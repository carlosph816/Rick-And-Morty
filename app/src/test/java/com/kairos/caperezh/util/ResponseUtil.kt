package com.kairos.caperezh.util

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

object ResponseUtil {
    fun MockWebServer.enqueueEntity(entity: String, code: Int) {
        enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(entity)
        )
    }
}
