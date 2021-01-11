# Pokemon
A text-based Pokemon battle simulator creaed in Java that reads Pokemon from a text file and lets users choose a group of Pokemon and battle against the AI. 
This application was mostly created using Object Oriented Programming. The Pokemon were put into a text file - as displayed below - in a format where I could break
up the input easily. The first line of the .txt file has the total number of Pokemon in the file, and the rest of the lines contain Pokemon. Each line was broken up into the Pokemon name, energy, HP, type, weakness, resistance, attacks, and one special move. 

![alt text](https://github.com/navjeetdoad/Pokemon/blob/master/textfile.png)

Special moves all have different effects; Disable can disable
an enemy Pokemon, which causes all of its attacks to have their damage decreased by 10 points. Stun can stun an enemy Pokemon, which renders the enemy Pokemon
unable to move for the current turn. Wild Storm allows a random chance for the user to hit the enemy a random amount of times; however, this could also result in the Pokemon missing the attack entirely. Lastly, Wild Card is an ability that has a 50% chance for the attack to hit, and a 50% chance for the attack to cause 0 damage. 

<br />

Now, when the user starts up the program, they are first prompted to enter their name and are then given a list of Pokemon they can use. The user can choose up to 4 Pokemon, while the rest of the Pokemon go to the AI. During battle, you can press any of the following numbers to do one of the following:
<br />
* Press 0 for Stats: shows all of your current available Pokemon, their HP, energy, resistance, and weakness.
* Press 1 for Attack: choose one of your attacks and use it against the enemy Pokemon.
* Press 2 for Pass: switches your current Pokemon to the next available Pokemon that you possess.
* Press 3 for Retreat: allows you to switch your current Pokemon for any of your other Pokemon

The battle interface is displayed in the picture above.

![alt text](https://github.com/navjeetdoad/Pokemon/blob/master/battle.png)

If you defeat all of the enemies Pokemon, the games over and you've won! You've officially become the Pokemon Master! :)
