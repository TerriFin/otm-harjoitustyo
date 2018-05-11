### Test document

Ekonomista is tested using automated JUnit tests. The tests used are single unit tests, and also some integration tests.

## Game logic

Integration tests simulate the interactions different game logic classes can have.

There are also some single unit tests for simple methods.

## DAO-classes

All DAO-classes (except dummyCompanyDao, because it does not have long term memory) use some sort of test file/database for testing.

## Testing line coverage is ~70%

### What still needs to be done in regards with testing?

GameService-class still does not have any tests. I am unsure how one would go about making tests for it, as it has some random elements inside it.
