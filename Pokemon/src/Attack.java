class Attack{
 
  private int cost;
  private int damage;
  private String name;
  private String special;
  
  //--------------------------------------------------------------------------------------------------------------------
  //constructor class to initialize the variables
  public Attack(String name, String cost, String damage, String special){
    this.name = name;
    this.cost = Integer.parseInt(cost);
    this.damage = Integer.parseInt(damage);
    
    if (special.equals(" ")){
      this.special = "no special";
    }
    else{
      this.special = special;
    }
  }
  //-------------------------------------------------------------------------------------------------------------------
  public static void delay(int n){ //used to set the delay between the printing
    try{ 
      Thread.sleep(n);
    }
    catch(InterruptedException ex){
      System.out.println(ex);
    }
  }
  
  //--------------------------------------------------------------------------------------------------------------------
  //getter methods to get the attack name, energy cost, damage and special
  public String getName(){
    return name;
  }
  
  public int getCost(){
    return cost;
  }
  
  public int getDamage(){
    return damage;
  }
  
  public String getSpecial(){
    return special;
  }
  //--------------------------------------------------------------------------------------------------------------------
  //calling the special attack methods
  
  public int doSpecial(Pokemon poke, Pokemon enemy){
    
    if (special.equals("stun")) {
      return specialStun(poke, enemy);
    }
  
    else if (special.equals("wild card")) {
      return specialWildCard(poke, enemy);
    }
  
    else if (special.equals("wild storm")) {
      return specialWildStorm(poke, enemy);
    }
  
    else if (special.equals("disable")) {
      return specialDisable(poke, enemy);
    }
  
    else if (special.equals("recharge")) {
      return specialRecharge(poke, enemy);
    }
    return 1; //return 1 incase the special is empty
  }
  
  //at the end of each of these methods, 1 is returned as a "damage multiplier" for specials that is applied
  //in the Attack method in the Pokemon class. The only ones that affect damage are Wild Card and Wild Storm
  //so all the other methods return 1.
  //--------------------------------------------------------------------------------------------------------------------
  public int specialStun(Pokemon poke, Pokemon enemy){
    
    System.out.printf("\n%s used Stun...",poke.getName());
    
    int chance = (int)(Math.random()*2);
    //50/50 chance of whether the stun occurs or not
    if (chance == 1){
      System.out.printf(" and it worked! %s was stunned.", enemy.getName());
      enemy.stun(); //if stun is successful, the enemy is stunned
    }
    else{
      System.out.print(" and it didn't work. Nothing happened...");
    }
    return 1;
  }
  
  public int specialWildCard (Pokemon poke, Pokemon enemy){
    System.out.printf("\n%s used Wild Card...", poke.getName());
  
    int x = (int) (Math.random()*2);
  
    if (x == 1) {
      System.out.println(" and the damage was unaffected by it.");
      return x; //if x is returned, then the damage is normal
    }
    
    else {
      System.out.println(" and was unlucky. The damage was cance1led...");
      
      return x; //otherwise, the damage is cancelled by multiplying it by 0
    }
  }
  
  public int specialWildStorm (Pokemon poke, Pokemon enemy){
    
    int count = 0; //this is used to display to the user how many times the attack occured
    System.out.printf("\n%s used Wild Storm...", poke.getName());
    
    int chance = (int)(Math.random()*2); //chance of whether the attack goes through or not (0 == didnt go through, 1 == went through)
    
    if (chance == 1){
      count += 1;
      boolean check = true; //used to loop through the wild storm since a random number is needed everytime
      //to check whether or not the attack will keep occuring
      
      while (check){
        
        chance = (int)(Math.random()*2); //generates a new random number everytime in the while loop
        if (chance == 0){ //if the random generated numbered is 0, then the wild storm ends and the count multiplier is returned
          check = false;
        }
        count += 1;
      }
    }
    if (count == 0){
      System.out.printf(" It hit 0 times... the damage was cancelled.");
    }
    else{
      System.out.printf(" It hit %d times!", count);
    }
    return count; //returns the count so the damage will be multiplied by this amount in the damage calculations
  }
  
  public int specialDisable(Pokemon poke, Pokemon enemy){
    if (enemy.isDisabled() == false){ //checks if the enemy is already disabled or not
      System.out.printf("\n%s used Disable. %s was disabled, so its attack will do 10 less damage!", poke.getName(),enemy.getName());
      enemy.disable();
    }
    else{
      System.out.printf("\n%s is already disabled, so nothing happened...", enemy.getName());
    }
    return 1;
  }
  
  public int specialRecharge(Pokemon poke, Pokemon enemy){
    System.out.printf("\n%s used Recharge! It regained 20 energy points!", poke.getName());
    poke.increaseEnergy(20);
    return 1;
  }
  //--------------------------------------------------------------------------------------------------------------------
}

