package org.littlewings.lyricsbot

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class ArtistSpec extends FunSpec {
  describe("Dummy Artist") {
    it("read fromConfig") {
      val artist = Artist.fromConfig("dummy_artist")

      artist.name should be("Dummy Artist")
      artist.tweetScheduleFromAlbum should be("0 */3 * * * ?")

      artist.albumSize should be(2)

      artist.album("album1") should not be (None)

      artist.album(1) should be("album2")
    }
  }
}
