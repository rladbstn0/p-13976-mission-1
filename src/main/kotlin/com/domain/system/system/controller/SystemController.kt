package com.domain.system.system.controller

import com.global.rq.Rq

class SystemController {
    fun actionExit(rq: Rq) {
        println("프로그램을 종료합니다.")
        System.exit(0)
    }
}
