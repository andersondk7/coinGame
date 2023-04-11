package org.dka.coingame.coins

import cats.syntax.all.*
import org.dka.coingame.coins.*
import org.dka.coingame.{ItemGenerator, Seed}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers.*

class CoinTossSpec extends AnyFunSpec {
  describe("AlwaysHeadsUp") {
    it ("should always be heads up") {
      val results = tossCoins(100)( AlwaysHeadsUpToss.next)
      results.contains(Coin.TailsUp) shouldBe false
    }
  }
  describe("AlwaysTailsUp") {
    it ("should always be tails up") {
      val results = tossCoins(100)( AlwaysTailsUpToss.next)
      results.contains(Coin.HeadsUp) shouldBe false
    }
  }
  describe("MostlyHeads") {
    val tossCount = 100
    val ratio = 5 // meaning 1 of every 5 is NOT heads
    val tosser = MostlyHeadsCoinToss(ratio)
    it ("should mostly be heads") {
      val results: List[Coin] = tossCoins(tossCount)(tosser.next)
      val tailsCount = results.filter(_ == Coin.TailsUp).size
      tailsCount shouldBe tossCount/ratio
    }
  }
  describe("MostlyTails") {
    val tossCount = 100
    val ratio = 4 // meaning 1 of every 4 is NOT tails
    val tosser = MostlyTailsCoinToss(ratio)
    it ("should mostly be tails") {
      val results: List[Coin] = tossCoins(tossCount)(tosser.next)
      val headsCount = results.filter(_ == Coin.HeadsUp).size
      headsCount shouldBe tossCount/ratio
    }
  }
  describe("RandomToss") {
    val tossCount = 101
    val tosser: ItemGenerator[Seed, Coin] = RandomCoinToss
    it ("should return the same newValue when using the same seed") {
      val seed = Seed(15)
      val expected = tosser.next(seed)._2
      val results: List[Coin] = (0 to tossCount)
        .foldLeft(List.empty) (( history, _) => tosser.next(seed)._2 :: history )

      results.contains(expected.flipSide) shouldBe false

    }
  }

  private def tossCoins(count: Int)(nextCoin: (s: Seed) => (Seed, Coin)): List[Coin] =
    (1 to count).toList.map(i => {
      nextCoin(Seed(i))._2
    })
}
