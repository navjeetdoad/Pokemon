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
0. Stats: shows all of your current available Pokemon, their HP, energy, resistance, and weakness.
1. Attack: choose one of your attacks and use it against the enemy Pokemon.
2. Pass: switches your current Pokemon to the next available Pokemon that you possess.
3. Retreat: allows you to switch your current Pokemon for any of your other Pokemon

Markup : 0. Stats: shows all of your current available Pokemon, their HP, energy, resistance, and weakness.
         1. Attack: choose one of your attacks and use it against the enemy Pokemon.
         2. Pass: switches your current Pokemon to the next available Pokemon that you possess.
         3. Retreat: allows you to switch your current Pokemon for any of your other Pokemon


![alt text](https://github.com/navjeetdoad/Pokemon/blob/master/battle.png)

The battle interface is display in the picture above.
