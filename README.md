# OTM-assignment

Ekonomista is game/simulation made by Sami Saukkonen for Otm-course.
In the game, the goal of the player is to amass wealth by trading **badly** simulated stocks.

## Documentation;

[requirement specification](https://github.com/TerriFin/otm-harjoitustyo/blob/master/documentation/requirement_specification.md)

[hour accounting](https://github.com/TerriFin/otm-harjoitustyo/blob/master/documentation/hour_accounting.md)

[manual](https://github.com/TerriFin/otm-harjoitustyo/blob/master/documentation/manual)

[architecture](https://github.com/TerriFin/otm-harjoitustyo/blob/master/documentation/arkkitehtuuri.md)

[release](https://github.com/TerriFin/otm-harjoitustyo/releases)

**Some quick terminal commands;**

**mvn test** runs tests

**mvn test jacoco:report** generates test coverage report

**mvn jxr:jxr checkstyle:checkstyle** generates checkstyle coverage report

**mvn package** generates two new executable jar files, you want the one without the word *"original"* in its name

*All generated files can be founds in **target** directory*
