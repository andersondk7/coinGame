package org.dka.coingame.coins

import cats.syntax.all.*
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers.*

class CoinSpec  extends AnyFunSpec {

  describe("newValue faces") {
    it ("should have a heads and a tails") {
      Coin.Face.Heads.show shouldBe "Heads"
      Coin.Face.Tails.show shouldBe "Tails"
    }
  }
}
