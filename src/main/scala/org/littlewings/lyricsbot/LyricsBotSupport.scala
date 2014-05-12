package org.littlewings.lyricsbot

trait LyricsBotSupport extends LyricsBot {
  lazy val artist: Artist = Artist.fromConfig(artistNameAlias)

  def formatLyrics(artist: Artist, album: Album, trackName: String, lyrics: String): String =
    LyricsFormatter.format(album, trackName, lyrics)

  def pickupLyrics(formatter: (Artist, Album, String, String) => String): String = {
    val album = Album.fromConfig(artist, artist.pickupAlbum)

    val track = album.pickupTrack
    val lyrics = album.pickupTrackLyrics(track)

    formatter(artist, album, track, lyrics)
  }
}

trait StandAloneLyricsBotSupport extends LyricsBotSupport {
  def main(args: Array[String]): Unit =
    tweetAction()
}

trait ConsoleLyricsBotSupport extends LyricsBotSupport {
  override def tweetAction(): Unit =
    println(s"[${new java.util.Date}]${System.lineSeparator}${pickupLyrics(formatLyrics)}")
}

trait TwitterLyricsBotSupport extends LyricsBotSupport {
  override def tweetAction(): Unit =
    LyricsTwitter.tweet(pickupLyrics(formatLyrics))
}
