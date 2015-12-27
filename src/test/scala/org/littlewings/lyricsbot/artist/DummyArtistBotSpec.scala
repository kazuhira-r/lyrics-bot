package org.littlewings.lyricsbot.artist

import org.littlewings.lyricsbot.LyricsBotFunSpecSupport

class DummyArtistBotSpec extends LyricsBotFunSpecSupport {
  describe("DummyArtist Lyrics Bot") {
    it(s"all lyrics <= Max Tweet Length(${LyricsBotFunSpecSupport.TWEET_MAX_LENGTH})") {
      fullLyrics("dummy_artist")
    }
  }
}

