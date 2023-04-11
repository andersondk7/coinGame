package org.dka.coingame.coins

import cats.syntax.all.*
import org.dka.coingame
import org.dka.coingame.Seed
import org.dka.coingame.coins._
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers.*

class CoinTossHistorySpec extends AnyFunSpec {
  describe("AlwaysHeadsUp") {
    it ("should always be heads up") {
      val tossCount = 100
      val lastState = tossCoins(tossCount)(AlwaysHeadsUpTossHistory.next)
      lastState.history.size shouldBe tossCount
      lastState.history.contains(Coin.TailsUp) shouldBe false
    }
  }
  describe("AlwaysTailsUp") {
    it ("should always be tails up") {
      val tossCount = 100
      val lastState = tossCoins(tossCount)(AlwaysHeadsUpTossHistory.next)
      lastState.history.size shouldBe tossCount
      lastState.history.contains(Coin.TailsUp) shouldBe false
    }
  }
  describe("MostlyHeads") {
    val tossCount = 100
    val ratio = 5
    val tosser = MostlyHeadsCoinTossHistory(ratio)
    it ("should mostly be tails up") {
      val lastState = tossCoins(tossCount)(tosser.next)
      val headsCount = lastState.history.filter(_ == Coin.HeadsUp).size
      val tailsCount = lastState.history.filter(_ == Coin.TailsUp).size
      lastState.history.size shouldBe tossCount
      headsCount should be > tailsCount
      tailsCount shouldBe tossCount/ratio
    }
  }
  describe("MostlyTails") {
    val tossCount = 100
    val ratio = 4
    val tosser = MostlyTailsCoinTossHistory(ratio)
    it ("should mostly be tails up") {
      val lastState = tossCoins(tossCount)(tosser.next)
      val headsCount = lastState.history.filter(_ == Coin.HeadsUp).size
      val tailsCount = lastState.history.filter(_ == Coin.TailsUp).size
      lastState.history.size shouldBe tossCount
      tailsCount should be > headsCount
      headsCount shouldBe tossCount/ratio
    }
  }
  describe("RandomToss") {
    val tossCount = 100
    val tosser = RandomCoinTossHistory
    val initialCriteria = CoinHistoryCriteria(Seed(15))
    val lastCriteria: CoinHistoryCriteria = (1 to tossCount).toList
      .foldLeft(initialCriteria) ( (criteria, i) => {
        val (nextCriteria, history) = tosser.next(criteria)
        nextCriteria.copy(seed = Seed(i)) // throw away calculated next seed and use the increment
      })
    lastCriteria.previous.get
    it ("should return the same newValue when using the same seed") {
      val lastState = tossCoins(tossCount)(tosser.next)
      val firstCoin = lastState.coin
      lastState.history.contains(firstCoin.flipSide) shouldBe false
    }
  }

  private def tossCoins(count: Int)
                       (next: CoinHistoryCriteria => (CoinHistoryCriteria, CoinHistory)): CoinHistory =
    val initialCriteria = CoinHistoryCriteria(Seed(0))
    val lastCriteria: CoinHistoryCriteria = (1 to count).toList
      .foldLeft(initialCriteria) ( (criteria, i) => {
        val (nextCriteria, history) = next(criteria)
        nextCriteria.copy(seed = Seed(i)) // throw away calculated next seed and use the increment
      })
    lastCriteria.previous.get
}
