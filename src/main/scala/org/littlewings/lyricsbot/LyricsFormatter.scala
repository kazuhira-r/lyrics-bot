package org.littlewings.lyricsbot

object LyricsFormatter {
  def format(album: Album, trackName: String, lyrics: String): String =
    if (album.name.isEmpty)
      s"""|$lyrics
          |
          |[$trackName]""".stripMargin
    else
      s"""|$lyrics
          |
          |[$trackName / ${album.name}]""".stripMargin
}
