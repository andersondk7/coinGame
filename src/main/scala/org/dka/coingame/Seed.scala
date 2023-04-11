package org.dka.coingame

final case class Seed(value: Long) {
  def next = Seed(value * 6364136223846793005L + 1442695040888963407L)
}
