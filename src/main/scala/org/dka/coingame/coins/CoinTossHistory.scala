package org.dka.coingame.coins

import org.dka.coingame.*
import org.dka.coingame.coins.Coin._

final case class CoinHistoryCriteria(seed: Seed, previous: CoinHistory)

object CoinHistoryTossers {
  
}

abstract class CoinTossHistory extends ItemGenerator[CoinHistoryCriteria, CoinHistory] {
  protected def toss(seed: Seed): (Seed, Coin)

  override def next(criteria: CoinHistoryCriteria): (CoinHistoryCriteria, CoinHistory) = {
    val (nextSeed: Seed, coin: Coin) = toss(criteria.seed)
    val coinHistory = CoinHistory(coin, criteria.previous)
    (CoinHistoryCriteria(nextSeed, coinHistory), coinHistory)
  }
}

object AlwaysHeadsUpTossHistory extends CoinTossHistory {
  override def toss(seed: Seed): (Seed, Coin) = (seed.next, HeadsUp)
}

object AlwaysTailsUpTossHistory extends CoinTossHistory {
  override def toss(seed: Seed): (Seed, Coin) = (seed.next, TailsUp)
}

object RandomCoinTossHistory extends CoinTossHistory {
  override def toss(seed: Seed): (Seed, Coin) = {
    val next = seed.next
    (
      seed.next,
      if (seed.value > 0) HeadsUp else TailsUp
    )
  }
}

abstract class BiasedCoinTossHistory(
                               prefered: Coin, splitRatio: Int = 3) extends CoinTossHistory {
  private val notPreferred = if (prefered == Coin.HeadsUp) Coin.TailsUp else Coin.HeadsUp
  def toss(seed: Seed): (Seed, Coin) = {
    (
      seed.next,
      if (seed.value % splitRatio == 0) notPreferred
      else prefered
    )
  }
}

final case class MostlyHeadsCoinTossHistory(splitRatio: Int = 3) extends BiasedCoinTossHistory(Coin.HeadsUp, splitRatio)

final case class MostlyTailsCoinTossHistory(splitRatio: Int = 4) extends BiasedCoinTossHistory(Coin.TailsUp, splitRatio)
