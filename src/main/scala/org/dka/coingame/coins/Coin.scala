package org.dka.coingame.coins

import cats.Show
import cats.syntax.show.*

import Coin._
final case class Coin(face: Face) {
  val flipSide: Coin = if (face == Face.Tails) Coin.HeadsUp else Coin.TailsUp
}

object Coin {
  implicit val showCoin: Show[Coin] = Show.show(c => c.face.show)

  enum Face {
    case Heads, Tails
  }

  val HeadsUp: Coin = Coin(Face.Heads)
  val TailsUp: Coin = Coin(Face.Tails)

  def flipSide(coin: Coin): Coin = if (coin == HeadsUp) TailsUp else HeadsUp

  implicit val showFace: Show[Face] = Show.show {
    case Face.Heads => "Heads"
    case Face.Tails => "Tails"
  }

}
