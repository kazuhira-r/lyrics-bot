package org.littlewings.lyricsbot.artist

import java.util.Date

import org.littlewings.lyricsbot._
import org.quartz.{Job, JobExecutionContext}

import scala.util.{Failure, Success, Try}

abstract class CaratBot extends LyricsBotSupport {
  val artistNameAlias: String = "carat"
}

object ConsoleCaratBot
  extends CaratBot
  with StandAloneLyricsBotSupport
  with ConsoleLyricsBotSupport

object TwitterCaratBot
  extends CaratBot
  with StandAloneLyricsBotSupport
  with TwitterLyricsBotSupport

object ScheduledConsoleCaratBot
  extends CaratBot
  with ConsoleLyricsBotSupport
  with ScheduledLyricsBotSupport[ConsoleCaratJob] {
  override protected def jobClass: Class[ConsoleCaratJob] =
    classOf[ConsoleCaratJob]

  def main(args: Array[String]): Unit = {
    val sleepTime = args.toList match {
      case n :: Nil => n.toInt
      case _ => 5
    }

    startJob()
    Thread.sleep(sleepTime * 1000L)
    endJob()
  }
}

class ConsoleCaratJob extends Job {
  override def execute(context: JobExecutionContext): Unit = {
    println(s"[${new Date}] execute tweetAction")
    ScheduledConsoleCaratBot.tweetAction()
  }
}

object ScheduledTwitterCaratBot
  extends CaratBot
  with TwitterLyricsBotSupport
  with ScheduledLyricsBotSupport[TwitterCaratJob]
  with ReductionLyricsBotSupport {
  override protected def jobClass: Class[TwitterCaratJob] =
    classOf[TwitterCaratJob]
}

class TwitterCaratJob extends Job {
  override def execute(context: JobExecutionContext): Unit = {
    val action: () => Unit = () => ScheduledTwitterCaratBot.tweetAction()

    (1 to 3).foldLeft(Try(action())) {
      case (s@Success(_), _) =>
        s
      case (Failure(e), i) =>
        println(s"[${new Date}] Failure[$i]: $e")
        e.printStackTrace()
        Thread.sleep(500L)
        Try(action())
    }

    println(s"[${new Date}] execute tweetAction")
  }
}
