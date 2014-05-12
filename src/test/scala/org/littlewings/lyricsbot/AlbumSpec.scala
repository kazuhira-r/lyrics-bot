package org.littlewings.lyricsbot

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class AlbumSpec extends FunSpec {
  describe("Album") {
    it("read fromConfig") {
      val artist = Artist.fromConfig("dummy_artist")
      val album = Album.fromConfig(artist, "album1")

      album.name should be ("album1")
      album.numbering should be ("1st Al")

      album.trackSize should be (3)
      album.track(0) should be ("track1")

      album.trackLyricsSize("track2") should be (2)
      album.trackLyrics("track2", 0) should be ("hoge3")
      album.trackLyrics("track2", 1) should be ("hoge4")
    }
  }
}
