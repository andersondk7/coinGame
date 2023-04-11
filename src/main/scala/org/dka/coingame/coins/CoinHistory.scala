package org.dka.coingame.coins


// used as the state of coin tosses
final case class CoinHistory(coin: Coin, history: List[Coin]) { }

object CoinHistory {
  def initial(coin: Coin): CoinHistory = CoinHistory(coin, List(coin))
  def apply(coin: Coin, previous: CoinHistory): CoinHistory = CoinHistory(coin, coin :: previous.history)
}
