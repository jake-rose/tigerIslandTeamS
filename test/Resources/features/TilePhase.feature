Feature: Tile Phase

  The tile picked by player should contains 3 Hex, and one of them should be volcano


  Scenario:
    Given The player get the randomly tile
    When A tile is picked
    Then The tile is initialed with three hex and one hex is volcano
