package org.littlewings.lyricsbot.artist

import org.littlewings.lyricsbot.LyricsBotFunSpecSupport

class LilyMyuBotSpec extends LyricsBotFunSpecSupport {
  describe("Lily.μ Lyrics Bot") {
    it(s"all lyrics <= Max Tweet Length(${LyricsBotFunSpecSupport.TWEET_MAX_LENGTH})") {
      fullLyrics("lilymyu")
    }
  }
}
