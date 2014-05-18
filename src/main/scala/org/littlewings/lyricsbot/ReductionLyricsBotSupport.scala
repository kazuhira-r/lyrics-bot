package org.littlewings.lyricsbot

import scala.annotation.tailrec
import scala.collection.immutable.TreeMap
import scala.util.Random

import java.security.SecureRandom
import java.util.Date

object ReductionLyricsBotSupport {
  private val RANDOM: Random = new Random(new SecureRandom)

  private def nextInt(n: Int): Int =
    RANDOM.nextInt(n)
}

trait ReductionLyricsBotSupport extends LyricsBotSupport {
  protected var lyricsStorage: Map[String, Lyrics] = TreeMap.empty

  override def pickupLyrics(formatter: (Artist, Album, String, String) => String): String = {
    if (lyricsStorage.isEmpty) {
      println(s"[${new Date}] storage is empty, load all tracks")

      (0 until artist.albumSize).foreach { albumIndex =>
        val album = Album.fromConfig(artist, artist.album(albumIndex))

        (0 until album.trackSize).foreach { trackIndex =>
          val track = album.track(trackIndex)

          (0 until album.trackLyricsSize(track)).foreach { trackLyricsIndex =>
            val trackName = album.track(trackIndex)
            val lyrics = album.trackLyrics(trackName, trackLyricsIndex)

            lyricsStorage += (s"${albumIndex}:${trackIndex}:${trackLyricsIndex}" -> Lyrics(album, trackName, lyrics))
            println(s"[${new Date}] load ${albumIndex}:${trackIndex}:${trackLyricsIndex}")
          }
        }
      }

      println(s"[${new Date}] all tracks loaded, size[$lyricsStorage.size]")
    }

    val targetIndex = ReductionLyricsBotSupport.nextInt(lyricsStorage.size)

    val (key, lyrics) = lyricsStorage.toIndexedSeq.apply(targetIndex)
    lyricsStorage -= key

    println(s"[new Date] choise[$key], remain size[${lyricsStorage.size}]")

    formatter(artist, lyrics.album, lyrics.track, lyrics.lyrics)
  }
}
