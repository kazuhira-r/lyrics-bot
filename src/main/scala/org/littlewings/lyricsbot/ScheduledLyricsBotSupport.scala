package org.littlewings.lyricsbot

import java.util.TimeZone

import org.quartz.CronScheduleBuilder._
import org.quartz.JobBuilder._
import org.quartz.TriggerBuilder._
import org.quartz.impl.StdSchedulerFactory
import org.quartz.{Job, Scheduler}

object ScheduledLyricsBotSupport {
  private lazy val SCHEDULER: Scheduler = {
    val s = StdSchedulerFactory.getDefaultScheduler
    s.start()
    s
  }

  def scheduler(): Scheduler = SCHEDULER

  def shutdown(): Unit = {
    if (!scheduler.isShutdown) {
      scheduler.shutdown()
    }
  }
}

trait ScheduledLyricsBotSupport[T <: Job] extends ScheduledLyricsBot
with LyricsBotSupport {
  protected def jobClass: Class[T]

  def startJob(): Unit = {
    val job =
      newJob(jobClass)
        .withIdentity(s"${artistNameAlias}-Job")
        .build

    val trigger =
      newTrigger
        .withIdentity(s"${artistNameAlias}-Trigger")
        .withSchedule {
          cronSchedule(artist.tweetScheduleFromAlbum)
            .inTimeZone(TimeZone.getTimeZone("Asia/Tokyo"))
        }
        .forJob(job)
        .startNow
        .build

    ScheduledLyricsBotSupport.scheduler.scheduleJob(job, trigger)
  }

  def endJob(): Unit =
    ScheduledLyricsBotSupport.shutdown()
}
