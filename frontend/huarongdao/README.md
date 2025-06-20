# Java Implementation of HuaRongDao

This module provides a console version of the classic puzzle game HuaRong Dao.
You can choose from four preset levels taken from the original Python version
and undo or reset moves during play. Empty spaces are shown as blank gaps so the
pieces can slide around the board.
Each piece is labeled with a unique numeric ID from 0–9 and the exit is shown
below the board with vertical boundary lines. Run with:

```bash
javac -d out src/main/java/huarongdao/*.java
java -cp out huarongdao.Game
```

During the game you may enter commands like:
* `n wsad` &ndash; move piece `n` multiple steps (letters `W` `A` `S` `D`)
* `B` &ndash; undo last move
* `R` &ndash; reset current level
* `C` &ndash; choose another level
* `Q` &ndash; exit the program

The game counts your moves and reports the total when you solve a level.