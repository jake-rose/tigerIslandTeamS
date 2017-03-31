Feature: Player's turn

  After drawn the tile
  place the draw
  and do the build action

  Scenario: Active player's turn
    Given The player draw a tile
    When place the tile drawn
    Then complete a single build action
