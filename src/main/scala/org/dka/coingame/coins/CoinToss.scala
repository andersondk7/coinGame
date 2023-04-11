package org.dka.coingame.coins

import org.dka.coingame._

import Coin._

object CoinTossers {

  enum TossType {
    case
    AlwaysHeads, AlwaysTails, Random, MostlyHeads, MostlyTails
  }
}

object AlwaysHeadsUpToss extends ItemGenerator[Seed, Coin] {
  override def next(seed: Seed): (Seed, Coin) = (seed.next, HeadsUp)
}

object AlwaysTailsUpToss extends ItemGenerator[Seed, Coin] {
  override def next(seed: Seed): (Seed, Coin) = (seed.next, TailsUp)
}

object RandomCoinToss extends ItemGenerator[Seed, Coin] {
  override def next(seed: Seed): (Seed, Coin) = {
    val next = seed.next
    (
      seed.next,
      if (seed.value > 0) HeadsUp else TailsUp
    )
  }
}

abstract class BiasedCoinToss(
                               prefered: Coin, splitRatio: Int = 3) extends ItemGenerator[Seed, Coin] {
  private val notPreferred = if (prefered == Coin.HeadsUp) Coin.TailsUp else Coin.HeadsUp
  def next(seed: Seed): (Seed, Coin) = {
    (
      seed.next,
      if (seed.value % splitRatio == 0) notPreferred
      else prefered
    )
  }
}
final case class MostlyHeadsCoinToss(splitRatio: Int = 3) extends BiasedCoinToss(Coin.HeadsUp, splitRatio)

final case class MostlyTailsCoinToss(splitRatio: Int = 4) extends BiasedCoinToss(Coin.TailsUp, splitRatio)
