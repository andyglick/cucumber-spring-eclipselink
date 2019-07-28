Feature: Books

  @txn @accepted @completed
  Scenario: Save books

    Given "Walter Winchell" has contributed to the following titles:
      | The Cucumber Book |
      | Cucumber Recipes  |
    When someone fetches the books
    Then 2 titles named as above have been stored persistently
    And have "Walter Winchell" as an author


