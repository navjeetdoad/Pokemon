import java.util.*;

class Pokemon{
  private String name; 
  private String type,resistance,weakness;
  private int hp, startHP; //starting HP and HP are tracked seperately for display purposes and to make sure that health doesn't go over starting HP when health is increased
  private int energy = 50; //all energy starts at 50
  private ArrayList <Attack> attacks = new ArrayList <Attack> (); 
  private int numOfAttacks; //a number to keep track of how many attacks a pokemon has
  private boolean stunned, disabled = false; //booleans to keep check of the Pokemon's status in terms of disable and stun

  public Pokemon(String info){
    String[] infoList = info.split(","); //splitting up the list so values can be assigned from the info
    
    name = infoList[0];
    startHP = Integer.parseInt(infoList[1]);
    hp = startHP;
    type = infoList[2];
    resistance = infoList[3];
    weakness = infoList[4];
    
    numOfAttacks = Integer.parseInt(infoList[5]);
    for (int i = 0; i < numOfAttacks; i++){
      //depending on the number of attacks, it goes into the info given and extracts the attack name,cost,damage and special respectively
      attacks.add(new Attack(infoList[5 + 4 * i + 1], infoList[5 + 4 * i + 2], infoList[5 + 4 * i + 3], infoList[5 + 4* i + 4]));
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
  //the main attack method where damage is done and resistance/weaknesses are taken into account,
  //any special effects/damage is calculated and energy is subtracted
  public void attack(Attack att, Pokemon enemy){
    
    double damage = 1; //damage multiplier used for super effective/not very effective moves
    System.out.println("\n--------------------------------------------------------------------------------------------");
    System.out.printf("%s used %s against %s!",this.name, att.getName(), enemy.getName());
    delay(300);
    
    if (enemy.resistance.equals(this.type)){
      System.out.println("\nIt was not very effective...the attack only did half the damage.");
      delay(300);
      damage = 0.5; //if the move used is resisted, damage is halved
    }
    
    else if (enemy.weakness.equals(this.type)){
      System.out.println("\nIt was super effective! The attack did double the damage!!!");
      delay(300);
      damage = 2; //if the move used is the pokemons weakness, damage is double
    }      
    
    damage *= att.doSpecial(this, enemy); //since all the special move methods return an integer, then the damage is just 
    //multiplied by this amount. The ones that return 1 don't influence damage so the only ones that matter are wildStorm and wildCard
    //since they are the only ones that can actually influence damage at all
    
    if (isDisabled()){
      damage *= Math.max(att.getDamage()-10, 0); 
      //if the pokemon is disabled, its damage is decreased by 10. Some moves do less than 10 damage and we
      //don't want it to do negative damage, so a maximum of 0 is implemented
    }
    else{
      damage *= att.getDamage();
      //if no special cases (disabled/weaknesses) are present, then the damage multiplier is just multiplied
      //by the attackDamage
    }
    
    energy -= att.getCost(); //energy cost is subtracted from the Pokemon energy
    enemy.loseHP((int) damage); //damage is converted back to an int to fulfull the parameters of loseHP
    System.out.printf("\n%s lost %d HP.", enemy.getName(), (int) damage); //damage must be converted to an int here because it is a double
    delay(500);
  }
  
  //--------------------------------------------------------------------------------------------------------------------
  //Methods used to increase/decrease health based on attacks
  public void increaseHP(int x){
    hp = Math.min(hp + x, startHP); //min is used here to make sure the HP doesnt go over the starting hp
  }
  
  public void loseHP(int x){
    hp = Math.max(hp - x, 0); //when losing hp, max is also used to make sure the HP doesnt become negative
  }
  //--------------------------------------------------------------------------------------------------------------------
  //Used at the start of the battle to fully energize everyone
  public void fullEnergy(){
    energy = 50;
  }
  //used in between rounds to increase energy
  public void increaseEnergy(int x){
    energy = Math.min(this.energy + x, 50); //again, min is used to make sure energy doesnt go over 50
  }
  //--------------------------------------------------------------------------------------------------------------------
  //Getter and setter methods to obtain name,type,attacks and energy
  public String getName(){
    return name;
  }
  public String getType(){
    return type;
  }
  public int getStartHP(){
    return startHP;
  }
  public int getHP(){
    return hp;
  }
  public int getEnergy(){
    return energy;
  }
  public String getResistance(){
    return resistance;
  }
  public String getWeakness(){
    return weakness;
  }
  public ArrayList <Attack> getAttacks(){
    return attacks;
  }
  //--------------------------------------------------------------------------------------------------------------------
  //Used in the battle to check if pokemon is alive
  public boolean isDead(){
    if (hp <= 0){
      return true; 
    }
    else{
      return false;
    }
  }
  //--------------------------------------------------------------------------------------------------------------------
  //used to check if a pokemon is disabled and disable/undisable a pokemon
  public void disable(){
    disabled = true;
  }
  
  public void unDisable(){
    disabled = false;
  }
  
  public boolean isDisabled(){
    return disabled;
  }
  //--------------------------------------------------------------------------------------------------------------------
  //used to check if a pokemon is stunned and stun/unstun a pokemon
  public void stun(){
    stunned = true;
  }
  
  public void unStun(){
    stunned = false;
  }
  
  public boolean isStunned(){
    return stunned;
  }
  //--------------------------------------------------------------------------------------------------------------------
  public String toString(){
    return String.format("%s: HP: %d , Energy: %d \n", name, hp, energy);
  }
}
  
  




