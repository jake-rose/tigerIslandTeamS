 Feature: Setup

  Before the game
  We should prepared all 48 tiles
  so that player can pick the tile
  As the game started
  A player should pick a tile form all 48 tiles

   Scenario: Draw a random tile

    Given The player's turn
    When The tile is randomly created
    Then The player get the tile

