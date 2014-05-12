package org.littlewings.lyricsbot

import twitter4j.{Twitter, TwitterFactory}

object LyricsTwitter {
  private val TWITTER_SINGLETON: Twitter = TwitterFactory.getSingleton

  def tweet(lyrics: String): Unit =
    TWITTER_SINGLETON.updateStatus(lyrics)
}
