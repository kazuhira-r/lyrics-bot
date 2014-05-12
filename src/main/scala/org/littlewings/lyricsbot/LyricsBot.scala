package org.littlewings.lyricsbot

trait LyricsBot {
  val artistNameAlias: String

  def tweetAction(): Unit
}
