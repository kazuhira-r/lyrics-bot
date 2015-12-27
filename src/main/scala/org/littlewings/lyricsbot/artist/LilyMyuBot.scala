package org.littlewings.lyricsbot.artist

import scala.util.{Failure, Success, Try}

import java.util.Date

import org.quartz.{Job, JobExecutionContext}

import org.littlewings.lyricsbot._

abstract class LilyMyuBot extends LyricsBotSupport {
  val artistNameAlias: String = "lilymyu"
}

object ConsoleLilyMyuBot extends LilyMyuBot
                         with StandAloneLyricsBotSupport
                         with ConsoleLyricsBotSupport

object TwitterLilyMyuBot extends LilyMyuBot
                         with StandAloneLyricsBotSupport
                         with TwitterLyricsBotSupport

object ScheduledConsoleLilyMyuBot extends LilyMyuBot
                                  with ConsoleLyricsBotSupport
                                  with ScheduledLyricsBotSupport[ConsoleLilyMyuJob] {
  override protected def jobClass: Class[ConsoleLilyMyuJob] =
    classOf[ConsoleLilyMyuJob]

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

class ConsoleLilyMyuJob extends Job {
  override def execute(context: JobExecutionContext): Unit = {
    println(s"[${new Date}] execute tweetAction")
    ScheduledConsoleLilyMyuBot.tweetAction()
  }
}

object ScheduledTwitterLilyMyuBot extends LilyMyuBot
                                  with TwitterLyricsBotSupport
                                  with ScheduledLyricsBotSupport[TwitterLilyMyuJob]
                                  with ReductionLyricsBotSupport {
  override protected def jobClass: Class[TwitterLilyMyuJob] =
    classOf[TwitterLilyMyuJob]
}

class TwitterLilyMyuJob extends Job {
  override def execute(context: JobExecutionContext): Unit = {
    val action: () => Unit = () => ScheduledTwitterLilyMyuBot.tweetAction()

    (1 to 3).foldLeft(Try(action())) {
      case (s @ Success(_), _) =>
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
