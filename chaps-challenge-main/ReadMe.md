# Chaps Challenge

An imitation of the classics 90's game 'Chips Challenge'.

Gource video link:

https://youtu.be/LutDPATDmko

## Instructions
Run Main.java to begin the program.

### Game concept
Control Patrick Star to collect all Krabby Patties and unlock all locks with the conch shells to proceed to the next level. Avoid getting stung by the jellyfish in level 2 and use the info tiles to get hints.

### Controls
CTRL + X : exit the game, the current game state will be lost, the next time the game is started, it will resume from the last unfinished level

CTRL + S : exit the game, saves the game state, game will resume next time the application will be started

CTRL + R :  resume a saved game -- this will pop up a file selector to select a saved game to be loaded

CTRL + 1 : start a new game at level 1

CTRL + 2 : start a new game at level 2

SPACE : pause the game and display a “game is paused” dialog

ESC : close the “game is paused” dialog and resume the game

UP, DOWN, LEFT, RIGHT ARROW KEYS : move Chap within the maze

## Members & Responsiblities

| Name | GitName | Module |
| -------------- | --------- | ------------------------|
|Giancarlo Cigaral| cigaragian1 | App |
|Frank Irinco| irincofran | Persistancy |
|Vedaanth Kannan| kannanveda | Fuzz |
|Benjamin McEvoy| mcevoybenj | Renderer |
|Adam Roddick| roddicadam | Domain |
|Ethan Windle| windleetha | Recorder |

## Theme 

Despite the idea of being an iteration of Chip's Challenge, a theme must be imposed.



## Libraries 

Libraries to/may be used in this project are the following:

1. https://mvnrepository.com/artifact/com.google.guava/guava/30.1.1-jre and any library it
depends on (for general utilities)
2. https://mvnrepository.com/artifact/commons-io/commons-io/2.11.0 and any library it
depends on (for general utilities)
3. https://mvnrepository.com/artifact/org.jdom/jdom2/2.0.6 and any library it depends on
(for XML parsing and generating)
4. https://mvnrepository.com/artifact/org.dom4j/dom4j/2.1.3 and any library it depends on
(for XML parsing and generating)
5. JUnit4 or JUnit5 (junit3 is not acceptable) -- support for JUnit is available within IDEs ,
but you can also use libraries from these groups:
https://mvnrepository.com/artifact/org.junit.jupiter (JUnit5) or
https://mvnrepository.com/artifact/junit/junit (JUnit4)
6. https://mvnrepository.com/artifact/org.mockito -- any library within this group (for testing)
7. https://mvnrepository.com/artifact/org.json/json - for JSON processing (but note that
XML should be used!)
8. https://mvnrepository.com/artifact/com.google.code.gson/gson - for JSON processing
(but note that XML should be used!)
9. https://mvnrepository.com/artifact/com.fasterxml.jackson (any in this group) - for JSON
processing (but note that XML should be used!)
10. https://mvnrepository.com/artifact/com.alibaba/fastjson - for JSON processing (but note
that XML should be used!)



