package org.littlewings.lyricsbot.servlet

import java.security.Security
import java.util.Date

import javax.servlet.http.HttpServlet

import org.quartz.{Job, JobExecutionContext}

import org.littlewings.lyricsbot.ScheduledLyricsBot
import org.littlewings.lyricsbot.artist

class ScheduledLyricsBotServlet extends HttpServlet {
  private val scheduledLyricsBot: ScheduledLyricsBot =
    //artist.ScheduledConsoleLilyMyuBot
    artist.ScheduledTwitterLilyMyuBot

  override def init(): Unit = {
    scheduledLyricsBot.startJob()
    println(s"[${new Date}] bot Startup.")
  }

  override def destroy(): Unit =
    scheduledLyricsBot.endJob()
}
