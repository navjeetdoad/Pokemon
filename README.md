# Pokemon
A text-based Pokemon battle simulator creaed in Java that reads Pokemon from a text file and lets users choose a group of Pokemon and battle against the computer. 
This application was mostly created using Object Oriented Programming. The Pokemon were put into a text file - as displayed below - in a format where I could break
up the input into the Pokemon name, HP, type, weakness, resistance, attacks, and one special move. Special moves all have different effects; Disable can disable
an enemy Pokemon, which causes all of its attacks to have their damage decreased by 10 points. Stun can stun an enemy Pokemon, which renders the enemy Pokemon
unable to move for the current turn.

![alt text](https://github.com/navjeetdoad/Pokemon/blob/master/textfile.png)

The information about the Pokemon was then used to turn each line in the .txt file into a Pokemon object, and each Pokemon had a list of Attack objects which 
represented the possible attacks it could use. 
