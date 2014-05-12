package org.littlewings.lyricsbot.servlet

import javax.servlet.http.HttpServlet

import org.quartz.{Job, JobExecutionContext}

import org.littlewings.lyricsbot.ScheduledLyricsBot
import org.littlewings.lyricsbot.artist

class ScheduledLyricsBotServlet extends HttpServlet {
  private val scheduledLyricsBot: ScheduledLyricsBot =
    artist.ScheduledConsoleLilyMyuBot
    //artist.ScheduledTwitterLilyMyuBot

  override def init(): Unit =
    scheduledLyricsBot.startJob()

  override def destroy(): Unit =
    scheduledLyricsBot.endJob()
}
