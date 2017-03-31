Feature: Totoro

  Scenario: Build a Totoro Sanctuary
    Given The player has at least one unplayed Totoro
    When The player chooses any empty, non-volcano hex adjacent
    And a settlement of size 5 or greater
    And does not contain a Totoro sanctuary
    Then place a Totoro on that hex and immediately score it
