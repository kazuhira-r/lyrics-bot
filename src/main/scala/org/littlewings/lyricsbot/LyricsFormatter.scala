package org.littlewings.lyricsbot

object LyricsFormatter {
  def format(album: Album, trackName: String, lyrics: String): String =
    s"""|$lyrics
        |
        |[$trackName / ${album.name}]""".stripMargin
}
