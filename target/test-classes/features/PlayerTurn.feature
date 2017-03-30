Feature: Player's turn

  After drawn the tile
  place the draw
  and do the build action

  Scenario: Place the first tile

    Given The player get the randomly tile
    When The tile first place on board
    Then The tile will be placed in the center of the table

  Scenario: Place the subsequent tile
    Given The player get the randomly tile
    When The tile placed directly on the table by player
    Then At least one edge is touching any previously placed tile's edge

  Scenario: Place the subsequent tile
    Given The player get the randomly tile
    When The tile placed on top of other tiles
    And the hexes are at the same level
    And the hexes must be part of two or three different tile
    And can't completely wipeout a settlement
    And none of the hexes contains a Totoro sanctuary
    And none of the hexes contains a tiger playground
    And at least one of the hexes is a volcano
    And when the tile is placed, it will be positioned so as to completely cover the hexes
    And when the tile is placed, its volcano hex must cover a volcano hex
    Then The tile placed successful