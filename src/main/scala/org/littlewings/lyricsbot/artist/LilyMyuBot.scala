package org.littlewings.lyricsbot.artist

import org.quartz.{Job, JobExecutionContext}

import org.littlewings.lyricsbot._

abstract class LilyMyuBot extends LyricsBotSupport {
  val artistNameAlias: String = "lily_myu"
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
    println(s"[${new java.util.Date}] execute tweetAction")
    ScheduledConsoleLilyMyuBot.tweetAction()
  }
}

object ScheduledTwitterLilyMyuBot extends LilyMyuBot
                                  with TwitterLyricsBotSupport
                                  with ScheduledLyricsBotSupport[TwitterLilyMyuJob] {
  override protected def jobClass: Class[TwitterLilyMyuJob] =
    classOf[TwitterLilyMyuJob]
}

class TwitterLilyMyuJob extends Job {
  override def execute(context: JobExecutionContext): Unit = {
    try {
      println(s"[${new java.util.Date}] execute tweetAction")
      ScheduledTwitterLilyMyuBot.tweetAction()
    } catch {
      case th: Throwable => th.printStackTrace()
    }
  }
}
