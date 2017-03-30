Feature: Settlement

  Scenario: Found a settlement
    Given The player
    When the player have at least one unplayed villager
    And chose any empty, level-1, non-volcano hex
    Then Places a villager on it and immediately score the placement