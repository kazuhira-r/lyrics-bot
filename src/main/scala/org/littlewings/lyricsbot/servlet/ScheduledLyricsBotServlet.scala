package org.littlewings.lyricsbot.servlet

import java.util.Date
import javax.servlet.http.HttpServlet

import org.littlewings.lyricsbot.{ScheduledLyricsBot, artist}

class ScheduledLyricsBotServlet extends HttpServlet {
  private val scheduledLyricsBot: ScheduledLyricsBot =
  //artist.ScheduledConsoleLilyMyuBot
  // artist.ScheduledTwitterLilyMyuBot
  artist.ScheduledTwitterCaratBot

  override def init(): Unit = {
    scheduledLyricsBot.startJob()
    println(s"[${new Date}] bot Startup.")
  }

  override def destroy(): Unit =
    scheduledLyricsBot.endJob()
}
