//Navjeet Doad
//ISC4U - 02 
//December 12th, 2018

//A text-based Pokemon game where a list of Pokemon are read from a file and the user
//is allowed to choose 4 of these Pokemon and the Enemy AI uses the rest of the Pokemon 
//in the file. The user is allowed to Attack, Retreat, or Pass and the Enemy AI is only
//allowed to Attack and Pass. There are Resistances and Weaknesses like a usual Pokemon 
//game and there is also Energy, which is used to make moves. The objective of the game
//is to use your 4 Pokemon to defeat the enemy and become Trainer Supreme!


import java.util.*;
import java.io.*;

class PokemonArena{
  
  private static ArrayList <Pokemon> allPokes = new ArrayList <Pokemon> (); //list of all the pokemon loaded from the file
  private static ArrayList <Pokemon> team = new ArrayList <Pokemon> (); //list of the 4 selected by the user
  private static ArrayList <Pokemon> enemies = new ArrayList <Pokemon> (); //leftover pokemon used as enemies
  private static int current; //the index of the current pokemon chosen
  private static String playerName; //the name the user inputs
  private static String title; //this is the ASCII art that will be displayed on the opening screen
  //--------------------------------------------------------------------------------------------------------------------
  public static void load(String fileName){ //loads the pokemon
    try{
      Scanner fin = new Scanner(new BufferedReader(new FileReader(fileName)));
      int numOfPokes = fin.nextInt();
      fin.nextLine(); // dump the newline char
      for (int i = 0; i < numOfPokes; i++){
        String info = fin.nextLine();
        allPokes.add(new Pokemon(info)); //takes the loaded info and creates it into pokemon objects
      }
    }      
    catch(IOException ex){
      System.out.println("This file doesn't exist.");
    }
  }
 
  public static void loadArt(String fileName){ //the art for the intro screen is loaded here 
    try{
      Scanner fin = new Scanner(new BufferedReader(new FileReader(fileName)));
      int length = fin.nextInt();
      for (int i = 0; i < length; i++){
        title += fin.nextLine() + "\n";
      }
      String extra = "null"; //for some reason, a line with "null" showed up at the top when the file is read so it is replaced with a blank space here
      title = title.replace(extra, "");
    }
    catch(IOException ex){
      System.out.println("This file doesn't exist.");
    }  
  }
  
  public static void delay(int n){ //used to set the delay between the printing
    try{ 
      Thread.sleep(n);
    }
      
    catch(InterruptedException ex){
      System.out.println(ex);
    }
  }
  //--------------------------------------------------------------------------------------------------------------------
  public static void intro(){ //used to display the opening text
    System.out.println(title); 
    delay(1500);
    System.out.println("Welcome to Pokemon Arena, a place where you can compete to become the Trainer Supreme! \n");
    delay(1250);
    Scanner kb = new Scanner (System.in);
    System.out.println("My bad, I don't think I caught your name on your way in. What was it again? \n");
    delay(1000);
    playerName = kb.nextLine();
    System.out.printf("%s, huh. Welcome! I am the Trainer Supreme Lance, and today will be the day you lose to me. Let's get started. \n",playerName);
    delay(1500);
  }
 
  //--------------------------------------------------------------------------------------------------------------------
  public static void chooseTeam(){ //used at the beginning to choose the team and seperates the leftover Pokemon into enemies
    HashSet <Integer> teamNums = new HashSet <Integer> (); //a list with the indexs of the Pokemon that have been chosen
    System.out.println("I have some Pokemon that you can choose from... take your pick of any 4! \n");
    delay(500);
    
    System.out.println("\n---------------------------------------------------------");
    for (int i = 0; i < allPokes.size(); i++){
      System.out.printf("%d: %s \n", i + 1, allPokes.get(i).getName());
      delay(50);
    }
    System.out.println("\n---------------------------------------------------------");
    
    Scanner kb = new Scanner(System.in);
    while (team.size() < 4){
      System.out.println("\n Enter the number of the Pokemon you wish to choose: \n");
      int pokeIndex = kb.nextInt() - 1; //// -1 because the listing of pokemon starts at "1" but the actual corresponding index is 0
      if (pokeIndex >= 0 && pokeIndex < allPokes.size()){ //makes sure the input is valid
        if (teamNums.contains(pokeIndex) == false){ //checks if the input has already been entered
          team.add(allPokes.get(pokeIndex)); //if these conditions are satisfied, the pokemon is added to the team
          teamNums.add(pokeIndex); //the index is then added to the list of indexs
        }
        else{
          System.out.println("You already chose this Pokemon. Try again. \n");
        }
      }
      else{
        System.out.println("Oops! This Pokemon is not available! \n");
      }
    }
    System.out.println("Your team consists of: \n");
    for (int player:teamNums){
      System.out.println(player+1 + ". " + allPokes.get(player));
      delay(75);
    }                       
    for (int i = 1; i < allPokes.size(); i++){
      if (teamNums.contains(i) == false){
        enemies.add(allPokes.get(i));
        //here, the list of selected pokemon indexs are used and looped through the teamNums list to determine
        //if the index is in there or not. If it is, it is kept the way it is and if it is not in the list, the Pokemon
        //in that index is put into the enemies list.
      }
    }
    Collections.shuffle(enemies); //randomizes the enemies list
    
    System.out.println("\nYour enemies are: \n");
    for (Pokemon enemy:enemies){
      System.out.println(enemy);
      delay(75);
    }
    delay(1000);
  }
  //--------------------------------------------------------------------------------------------------------------------
  public static void choose(){
    
    Scanner kb = new Scanner (System.in);
    for (int i = 0; i < team.size(); i++){
      System.out.printf("\n %d. %s\n", i + 1, team.get(i).getName());
      delay(500);
    }
    System.out.println("\n Enter the number of what Pokemon you want: \n");
   
    boolean check = true; //this is used as a while loop to determine whether the input is valid or not
    int selection = -1; //if selection isnt initialized here (set equal to some value) it doesnt run
    
    while (check){
      selection = kb.nextInt();   
      if (selection > 0 && selection <= team.size()){ //checks if the input is valid and if so, the loop is broken
        check = false;
      }
      else{
        System.out.printf("Not a valid number. Please enter a number between 1 and %d. \n",team.size());
      }  
    }
    current = selection - 1; //sets the current index equal to the selected number subtract 1 (list index)
  }
  //--------------------------------------------------------------------------------------------------------------------
  public static String chooseMove (Pokemon poke) {
    
    Scanner kb = new Scanner (System.in);
    int count = 0; //counts the amount of attacks possible by going through the energycosts and comparing it to the available energy
    
    if (team.contains(poke)) { //checks if the battling pokemon is "awake" and in the team
      
      for (int i = 0; i < poke.getAttacks().size(); i ++) {
        if (poke.getAttacks().get(i).getCost() <= poke.getEnergy()) { //gets the attack and sees if they can be used
          count+=1;
        }
      }
    
      boolean retreat = false;
      if (team.size() > 1) { //checks if more than 1 pokemon is left awake
        retreat = true; 
      }
      
      System.out.println("\n--------------------------------------------------------------------------------------------");
      System.out.println("(Enter 0 to show the stats of your team)");
      System.out.printf("Your Pokemon: %s", poke);
      System.out.printf("Enemy: %s", enemies.get(0));
      System.out.println("1. Attack");
      System.out.println("2. Pass");
      
      if (retreat){
        System.out.println("3. Retreat");
        System.out.println("\n--------------------------------------------------------------------------------------------");
      }
      if (retreat == false){
        System.out.println("\n--------------------------------------------------------------------------------------------");
      }
      
      boolean check = true;
      int selection;
      
      while (check){ //boolean is used to loop through until the condition is satisfied
        selection = kb.nextInt();
        
        //here, depending on the input, a string value with the corresponding command is returned.
        //ex. if attack (1) is selected, a string containing "attack" is returned. These returned
        //the string values are used later on to call the corresponding functions.
        
        if (selection == 0){ //lets the user see the stats of their team
          stats();
        }
        else if (count == 0 && selection == 1){ //if the user cannot attack, this message is displayed
          System.out.printf("%s can not attack, it doesn't have enough energy!", poke.getName());
        }
        else if (count > 0 && selection == 1){ //if attacks are available and selection is 1
          return "attack";
        }
        else if (selection == 2){ //if pass is selected
          return "pass";
        }
        else if (selection == 3 && retreat){ //if retreat is selected AND the retreat condition above is true, then retreat is allowed to be called
          return "retreat";
        }
        else{
          System.out.println("Not a valid option; try again. \n");
        }
      }
    }
 
    else{ //checks the case where the team doesn't contain the pokemon that is entered as a parameter (therefore, an enemy)
      boolean canAttack = false; //checks if the enemy is able to attack since thats the only move they can make (besides passing)
      for (int i = 0; i < poke.getAttacks().size(); i ++) {
        if (poke.getAttacks().get(i).getCost() <= poke.getEnergy()) { //gets the attack and sees if they can be used
          canAttack = true;
          break;
        }
      }
    
      if (canAttack){ //if the enemies can attack, then they must attack
        return "attack";
      }
      else{ //otherwise, they just pass
        return "pass";
      }
    }
    return ""; //just to satisfy the "missing return statement" error, a blank string is returned even though 
    //this part of the code will never be reached, it'll always be returning "pass" or "attack"
  }
  
  public static void pass(Pokemon poke){ //if the user passes, nothing occurs except the following message
    System.out.printf("%s could not make a move, so it passed. \n",team.get(current).getName());
    delay(500);
  }
  
  public static void retreat(Pokemon poke){
    
    if (team.size() > 1){ //checks if the team is large enough to allow retreat
      for (int i = 0; i < team.size(); i ++) {
        System.out.printf("%d: %s", i + 1, team.get(i)); 
      }
      
      System.out.println("Enter the number corresponding to the Pokemon you wish to switch to: \n");
      
      boolean check = true; //again, a boolean is used as a while loop to check if the conditions are satisfied
      int selection; //the number the user selects
      Scanner kb = new Scanner(System.in);
      
      while(check){
        selection = kb.nextInt();
        
        if (selection - 1 == current){
          System.out.printf("You can't retreat into %s. It's already in battle! \n", team.get(current).getName());
        }
        else if (selection > 0 && selection <= team.size()) {
          current = selection - 1;
          check = false;
        }
        else {
          System.out.printf("Sorry, that is not a valid option. Please enter a number between 1 and %d. \n", team.size());
        }
      }
      System.out.printf("You switched to %s.\n", team.get(current).getName());
    }
    else {
      System.out.printf("All your other Pokemon are fainted! You must finish the battle with %s. \n",team.get(current).getName());
    }
  }
  
  public static void stats(){ //lets the user display their team's full stats (Typing, Weaknesses, etc)
    for (int i = 0; i < team.size(); i ++){
      System.out.printf("%s HP: %d/%d Energy: %d Type: %s Resistance: %s Weakness: %s\n", 
          team.get(i).getName(), team.get(i).getHP(), team.get(i).getStartHP(), team.get(i).getEnergy(),
          team.get(i).getType(), team.get(i).getResistance(), team.get(i).getWeakness());
    }
  }
  
  //--------------------------------------------------------------------------------------------------------------------
  public static Attack chooseAttack(Pokemon poke){
    
    ArrayList <Attack> availableAttacks = new ArrayList <Attack> ();
    
    for (int i = 0; i < poke.getAttacks().size(); i ++) {
      if (poke.getAttacks().get(i).getCost() <= poke.getEnergy()) { 
       //goes through the cost of the Pokemons attacks and sees if it has the energy to perform the move and if so, adds them into availableAttacks
        availableAttacks.add(poke.getAttacks().get(i));
      }
    }
        
    if (team.contains(poke)){ 
      if (availableAttacks.size() == 0){ //if no attacks are available to be used, then null is returned
        return null;
      }
      
      else{
        for (int i = 0; i < availableAttacks.size(); i++){ //prints out the available attacks that the user can make
          System.out.printf("%d. %s (Cost : %d, Damage = %d, Special = %s)\n",
          i+1, availableAttacks.get(i).getName(), availableAttacks.get(i).getCost(), 
          availableAttacks.get(i).getDamage(), availableAttacks.get(i).getSpecial());
        }
      }

      System.out.println("Enter the number corresponding to the move you wish to use: \n");
      
      Scanner kb = new Scanner(System.in);
      boolean check = true;
      int selection = 1; //variable is needed to be initialized here (assigned a value) so it can be 
      //used in the while loop and then be used in the return statement later on
      
      while (check){
        selection = kb.nextInt();
        if (selection > 0 && selection <= availableAttacks.size()){ //used to make sure the input is within bounds
          check = false;
        } 
        else{
          System.out.println("Not a valid move. Enter again. \n");
        }
      }
      
      return availableAttacks.get(selection - 1); //returns the corresponding index of the move
 
    }
    else{ 
      if (availableAttacks.size() > 0) {
        int x = (int) (Math.random()*availableAttacks.size()); //since all the enemy can do is attack, it just returns a random attack that they can make
        return availableAttacks.get(x);
      }
      else{
        return null;
      }
    }
  }
  //--------------------------------------------------------------------------------------------------------------------
  public static void round(Pokemon poke1, Pokemon poke2){ 
  //passes in 2 pokemon, however, the order is determined random later on in battle()
    if (poke1.isStunned() == false){
      
      String command = chooseMove(poke1); //the chooseMove method is used here to get the players input (attack, retreat, pass)
      
      //previously made methods are called here to enact the attacking, passing and retreating sequences
      if (command.equals("attack")){
        poke1.attack(chooseAttack(poke1), poke2); 
      }
      else if (command.equals("pass")){
        pass(poke1); 
      }
      else if (command.equals("retreat")){
        retreat(poke1);
        poke1 = team.get(current); //retreating changes which pokemon is currently being used, so current must be changed
      }
    }
    else{
      System.out.printf("%s is stunned. It cannot attack!\n", poke1.getName());
      poke1.unStun(); //after the pokemon has been stunned for the turn, it is unstunned
    }
    
    if (poke2.isDead() == false){ 
    //since the pokemon that goes second CAN be knocked out in this turn, we must be checked to see that it's "awake"
      if (poke2.isStunned() == false){
        String command = chooseMove(poke2);
        //same basic logic of calling the methods is used here
        if (command.equals("attack")){
          poke2.attack(chooseAttack(poke2),poke1);
        }
        else if (command.equals("pass")){
          pass(poke2);
        }
        else if (command.equals("retreat")){
          retreat(poke2);
          poke2 = team.get(current);
        }
      }
      else{
        System.out.printf("%s was stunned, and couldn't do anything.\n", poke2.getName());
        poke2.unStun(); 
        delay(500);
      }
    }
    
    //at the end of each ROUND, all pokemon regain 10 energy (up to 50)
    for (int i = 0; i < team.size(); i++){
      team.get(i).increaseEnergy(10);
    }
    for (int i = 0; i < enemies.size(); i++){
      enemies.get(i).increaseEnergy(10);
    }
    
    delay(1000);
    
  }
  //--------------------------------------------------------------------------------------------------------------------
  public static void battle (){
    
    int first = (int) (Math.random()*2);
    //a coin flip occurs here, where the random number generated is 0 or 1 and if
    //it's 0, the user goes first and if it's 1, then the enemy goes first
    if (first == 0){
      System.out.println("It's your turn!");
    }
    if (first == 1){
      System.out.println("It's your opponents turn!");
    }
    delay(500);
    
    System.out.printf("Your opponent sent out %s!\n", enemies.get(0).getName());
    
    delay(500);
    
    choose();
    
    delay(250);
    
    System.out.printf("You chose %s! \n", team.get(current).getName());
    
    team.get(current).fullEnergy();
    enemies.get(0).fullEnergy(); //each pokemons energy is fully replenished at the start of each battle
    
    delay(1000);
    
    boolean check = true;
    while (check){

      if (team.get(current).isDead()){ //checks if your pokemon got knocked out
        System.out.printf("%s knocked out %s! \n", enemies.get(0).getName(), team.get(current).getName());
        System.out.println("\n--------------------------------------------------------------------------------------------");
        delay(500);
        team.remove(current); //removes the Pokemon that was knocked out from the team list so it cannot be used anymore
        choose(); //allows the player to choose another pokemon after the previous one is knocked out
      }
      if (enemies.get(0).isDead()){ //checks the case if the enemy is dead and if so, the battle ends
        System.out.printf("%s knocked out %s! \n", team.get(current).getName(), enemies.get(0).getName());
        System.out.println("\n--------------------------------------------------------------------------------------------");
        delay(500);
        enemies.remove(0); //removes the current enemy from the enemies list so it cannot reoccur
        check = false; //battle ends when an opponent faints so the loop must break when this occurs
      }
    
      else{ //if no pokemon have been knocked out, the rounds occur over and over 
        if (first == 0){ //depending on the random number generator, either the user goes first or the enemy does 
          round(team.get(current), enemies.get(0)); 
        }
        else{
          round(enemies.get(0), team.get(current));
        }
      }
    }
    
    //at the end of each battle, each pokemon has their health increased by 20
    for (int i = 0; i < team.size(); i++){
      team.get(i).increaseHP(20);
    }
    for (int i = 0; i < enemies.size(); i++){
      enemies.get(i).increaseHP(20);
    }
    
    //at the end of each battle, ALL pokemon are undisabled
    for (int i = 0; i < team.size(); i++){
      team.get(i).unDisable();
    }
    for (int i = 0; i < enemies.size(); i++){
      enemies.get(i).unDisable();
    }
  }
  //--------------------------------------------------------------------------------------------------------------------
  public static void game(){
    
    boolean running = true;
    String winner = "";
    while (running){
  
      battle(); //battles are run over and over until the game ends (all of your Pokemon or the enemies Pokemon are knocked out)
  
      if (team.size() == 0){ //checking if the user lost
        running = false; //if so, then the loop breaks
        winner = "enemy";
      }
      if (enemies.size() == 0){ //checking if the user won
        running = false;
        winner = "user";
      }  
    }
    
    if (winner == "enemy"){
      System.out.printf("%s, you lost. Maybe if you played a bit smarter, you would have won. But I am still the Trainer Supreme! \n", playerName);
    }
    if (winner == "user"){
      System.out.printf("Impressive... you beat me, %s. I guess I have to hand you the title of Trainer Supreme. You deserve it. \n", playerName);
    }
  }
  //--------------------------------------------------------------------------------------------------------------------
  public static void main(String[]args){
    load(".idea/pokemon.txt");
    loadArt(".idea/start.txt");
    intro();
    chooseTeam();  
    game();
  }
}