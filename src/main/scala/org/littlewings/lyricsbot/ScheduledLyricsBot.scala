package org.littlewings.lyricsbot

trait ScheduledLyricsBot extends LyricsBot {
  def startJob(): Unit

  def endJob(): Unit
}
