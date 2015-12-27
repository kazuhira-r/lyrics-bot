package org.littlewings.lyricsbot.servlet

import java.io.IOException
import java.net.{HttpURLConnection, URL}
import java.util.{Date, TimeZone}
import javax.servlet.ServletException
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import com.typesafe.config.{Config, ConfigFactory}
import org.quartz.CronScheduleBuilder._
import org.quartz.JobBuilder._
import org.quartz.TriggerBuilder._
import org.quartz.impl.StdSchedulerFactory
import org.quartz.{Job, JobExecutionContext, Scheduler}

class PingServlet extends HttpServlet {
  private val pingJob: PingJob = new PingJob

  @throws(classOf[IOException])
  @throws(classOf[ServletException])
  override protected def doGet(req: HttpServletRequest, res: HttpServletResponse): Unit =
    res.getWriter.println("OK")

  override def init(): Unit =
    pingJob.startJob()

  override def destroy(): Unit =
    pingJob.endJob()
}

object PingJob {
  private val config: Config = ConfigFactory.load("data/ping").getConfig("ping")

  val targetUrl: String = config.getString("target-url")

  val executeSchedule: String = config.getString("execute-schedule")

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

class PingJob extends Job {
  override def execute(context: JobExecutionContext): Unit = {
    val url = new URL(PingJob.targetUrl)

    println(s"[${new Date}] Ping")

    val conn = url.openConnection.asInstanceOf[HttpURLConnection]
    try {
      println(s"Ping Result = ${conn.getResponseCode}")
    } finally {
      conn.disconnect()
    }
  }

  def startJob(): Unit = {
    val job =
      newJob(getClass)
        .withIdentity("Ping-Job")
        .build

    val trigger =
      newTrigger
        .withIdentity("Ping-Trigger")
        .withSchedule {
          cronSchedule(PingJob.executeSchedule)
            .inTimeZone(TimeZone.getTimeZone("Asia/Tokyo"))
        }
        .forJob(job)
        .startNow
        .build

    PingJob.scheduler.scheduleJob(job, trigger)
  }

  def endJob(): Unit =
    PingJob.shutdown()
}
