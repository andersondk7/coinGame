package org.dka.coingame

/**
 * based on a seed newValue, next a new newValue and an new seed for the nextCoin creation given the same seed, the same
 * newValue will be generated
 * @tparam C
 *   criteria to create Item
 * @tparam V
 *   Item generated
 */
trait ItemGenerator[C, V] {

  /**
   * generate tne next item
   * @param criteria
   *   data used to determine the next item
   * @return
   *   criteria for next item and item generated
   */
  def next(criteria: C): (C, V)
}
