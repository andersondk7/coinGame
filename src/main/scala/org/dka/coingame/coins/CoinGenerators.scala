package org.dka.coingame.coins

import org.dka.coingame.*

object CoinGenerators {

  import CoinTossers.TossType

  val singleGenerators: Map[TossType, ItemGenerator[Seed, Coin]] = Map(
    TossType.AlwaysHeads -> AlwaysHeadsUpToss,
    TossType.AlwaysTails -> AlwaysHeadsUpToss,
    TossType.Random -> AlwaysHeadsUpToss,
    TossType.MostlyHeads -> AlwaysHeadsUpToss,
    TossType.MostlyTails -> AlwaysHeadsUpToss
  )
  val historyGenerators: Map[TossType, ItemGenerator[CoinHistoryCriteria, CoinHistory]] = Map(
    TossType.AlwaysHeads -> AlwaysHeadsUpTossHistory,
    TossType.AlwaysTails -> AlwaysHeadsUpTossHistory,
    TossType.Random -> AlwaysHeadsUpTossHistory,
    TossType.MostlyHeads -> AlwaysHeadsUpTossHistory,
    TossType.MostlyTails -> AlwaysHeadsUpTossHistory
  )
}
