Feature: Tiger
  Scenario: Build a tiger playground
    Given The player has at least one unplayed tiger
    When The player chooses any empty, non-volcano hex adjacent
    And level three or greater
    And a settlement does not already contain a tiger playground
    Then place a tiger on that hex and immediately score it