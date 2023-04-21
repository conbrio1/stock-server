package com.example.stock.presentation.scheduler

import com.example.stock.application.ChartCollectService
import com.example.stock.infra.client.enums.ApiValidRange
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ChartCollectScheduler(
    private val chartCollectService: ChartCollectService
) {

    companion object {
        const val SAMSUNG = "005930.KS"
    }

    @Scheduled(cron = "\${schedule.daily-collect.cron}")
    @SchedulerLock(
        name = "\${schedule.daily-collect.name}",
        lockAtMostFor = "\${schedule.daily-collect.lock-most}",
        lockAtLeastFor = "\${schedule.daily-collect.lock-least}"
    )
    fun collect5DaysChart() {
        chartCollectService.collectDailyChart(SAMSUNG, ApiValidRange.FIVE_DAYS)
    }

    @Scheduled(cron = "\${schedule.today-collect.cron}")
    @SchedulerLock(
        name = "\${schedule.today-collect.name}",
        lockAtMostFor = "\${schedule.today-collect.lock-most}",
        lockAtLeastFor = "\${schedule.today-collect.lock-least}"
    )
    fun collectTodayChart() {
        chartCollectService.collectDailyChart(SAMSUNG, ApiValidRange.ONE_DAY)
    }
}
