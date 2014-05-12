package org.littlewings.lyricsbot

import org.scalatest.{FunSpec, GivenWhenThen}
import org.scalatest.Matchers._

object LyricsBotFunSpecSupport {
  val TWEET_MAX_LENGTH: Int = 140
}

trait LyricsBotFunSpecSupport extends FunSpec with GivenWhenThen {
  def fullLyrics(artistNameAlias: String): Unit = {
    Given(s"a artist[$artistNameAlias]")
    val artist = Artist.fromConfig(artistNameAlias)

    (0 until artist.albumSize).foreach { albumIndex =>
      val album = Album.fromConfig(artist, artist.album(albumIndex))

      When(s"Album [${album.name}]")

      (0 until album.trackSize).foreach { trackIndex =>
        val track = album.track(trackIndex)

        (0 until album.trackLyricsSize(track)).foreach { trackLyricsIndex =>
          val trackName = album.track(trackIndex)
          val lyrics = album.trackLyrics(trackName, trackLyricsIndex)
          val formatted = LyricsFormatter.format(album, trackName, lyrics).replace("\\r", "")

          Then(s"Track [$trackName]: No.${trackLyricsIndex}, size: ${formatted.size}")

          formatted.size should be <= (LyricsBotFunSpecSupport.TWEET_MAX_LENGTH)

          info(s"Track [$trackName]: No.${trackLyricsIndex} OK!!")
        }
      }
    }
  }
}
