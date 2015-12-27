package org.littlewings.lyricsbot

import java.security.SecureRandom

import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConverters._
import scala.util.Random

object Artist {
  private val RANDOM: Random = new Random(new SecureRandom)

  private def nextInt(n: Int): Int =
    RANDOM.nextInt(n)

  def fromConfig(artistNameAlias: String): Artist =
    new Artist(artistNameAlias)
}

class Artist private(configName: String) {
  private val config: Config = ConfigFactory.load(s"data/$configName").getConfig("artist")

  def name: String =
    config.getString("name")

  def alias: String =
    configName

  def tweetScheduleFromAlbum: String =
    config.getString("tweet-schedule-from-album")

  private def albums: java.util.List[String] =
    config.getStringList("albums")

  def albumSize: Int =
    albums.size

  def album(name: String): Option[String] =
    albums.asScala.find(_ == name)

  def album(n: Int): String =
    albums.get(n)

  def pickupAlbum: String =
    albums.get(Artist.nextInt(albumSize))
}
