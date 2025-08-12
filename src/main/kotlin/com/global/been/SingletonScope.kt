package com.global.been

import com.domain.system.system.controller.SystemController
import com.domain.wiseSaying.wiseSaying.controller.WiseSayingController
import com.domain.wiseSaying.wiseSaying.repository.WiseSayingFileRepository
import com.domain.wiseSaying.wiseSaying.repository.WiseSayingRepository
import com.domain.wiseSaying.wiseSaying.service.WiseSayingService

object SingletonScope {
    val wiseSayingController by lazy { WiseSayingController() }
    val wiseSayingService by lazy { WiseSayingService() }
    val wiseSayingRepository: WiseSayingRepository by lazy { WiseSayingFileRepository() }
    val wiseSayingFileRepository by lazy { WiseSayingFileRepository() }
    val systemController by lazy { SystemController() }
}