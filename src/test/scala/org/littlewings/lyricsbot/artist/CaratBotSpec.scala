package org.littlewings.lyricsbot.artist

import org.littlewings.lyricsbot.LyricsBotFunSpecSupport

class CaratBotSpec extends LyricsBotFunSpecSupport {
  describe("Carat Lyrics Bot") {
    it(s"all lyrics <= Max Tweet Length(${LyricsBotFunSpecSupport.TWEET_MAX_LENGTH})") {
      fullLyrics("carat")
    }
  }
}
