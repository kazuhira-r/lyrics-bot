package org.littlewings.lyricsbot

import java.security.SecureRandom

import com.typesafe.config.{Config, ConfigFactory}

import scala.util.Random

object Album {
  private val RANDOM: Random = new Random(new SecureRandom)

  private def nextInt(n: Int): Int =
    RANDOM.nextInt(n)

  def fromConfig(artist: Artist, albumNameAlias: String): Album =
    new Album(artist, albumNameAlias)
}

class Album private(artist: Artist, configName: String) {
  val escapeTargetChars: Set[Char] = Set(':', ',', '&', '.', '!')

  val config: Config =
    ConfigFactory.load(s"data/${artist.alias}/albums/${configName}").getConfig("album")

  def name: String =
    config.getString("name")

  def numbering: String =
    config.getString("numbering")

  def trackList: java.util.List[String] =
    config.getStringList("track-list")

  def trackSize: Int =
    trackList.size

  def track(n: Int): String =
    trackList.get(n)

  def pickupTrack: String =
    trackList.get(Album.nextInt(trackSize))

  def trackLyricsSize(trackName: String): Int =
    config
      .getStringList(escapeReserveChar(trackName))
      .size

  def trackLyrics(trackName: String, n: Int): String =
    config
      .getStringList(escapeReserveChar(trackName))
      .get(n)
      .stripMargin

  def pickupTrackLyrics(trackName: String): String =
    config
      .getStringList(escapeReserveChar(trackName))
      .get(Album.nextInt(trackLyricsSize(trackName)))
      .stripMargin

  private def escapeReserveChar(target: String): String =
    target
      .flatMap { c =>
        if (escapeTargetChars.contains(c))
          "\"" + c + "\""
        else
          c.toString
      }
}
