Feature: Settlement

  Scenario: Found a settlement
    Given The player have at least one unplayed villager
    When the player chooses any empty, level-1, non-volcano hex
    Then Places a villager on it and immediately score the villagers

  Scenario: Expand a settlement
    Given the player chooses one of her settlement and specifies a non-volcano terrain hex type
    When there must be at least at least one empty hex of the specified type adjacent to that settlement
    And in each empty hex adjacent
    Then add as many villagers as the hex's level and immediately score the villagers