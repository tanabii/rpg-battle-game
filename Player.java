import java.util.Random;

class Player {
  String name;
  int hp;
  int maxHp;
  int mp;
  int maxMp;
  int attack;
  int skillCost;
  boolean isPoisoned;
  int poisonTurns;
  int criticalRate;

  Random random = new Random();

  // コンストラクタ（初期化するやつ）
  Player(String name, int hp, int mp, int attack, int skillCost, int criticalRate) {
      this.name = name;
      this.hp = hp;
      this.maxHp = hp;
      this.mp = mp;
      this.maxMp = mp;
      this.attack = attack;
      this.skillCost = skillCost;
      this.criticalRate = criticalRate;
      this.isPoisoned = false;
      this.poisonTurns = 0;
  }


  void heal() {
    this.hp += 20;
    if (this.hp > this.maxHp){
      this.hp = this.maxHp;
    }
    System.out.println(name + "のHPが20回復した！（現在のHP：" + hp + "）");
  }

  int useSkill(Weapon weapon) {
    if (this.mp >= this.skillCost){
      this.mp -= this.skillCost;
      int damage = (this.attack + weapon.getTotalAttack()) * 2;
      System.out.println(this.name + "はスキル『パワースラッシュ』を使った！");
      System.out.println("MPを" + this.skillCost + "消費した（残りMP：" + this.mp + "）");
      return damage;
    }else{
      System.out.println("MPが足りない！");
      return 0;
    }
  }

  boolean sufferPoison() {
    if (this.isPoisoned){
      this.hp -= 5;
      this.poisonTurns --;
      System.out.println(this.name + "は毒で５ダメージ受けた！（残り毒ターン：" + this.poisonTurns + "）" );

      if (this.poisonTurns <= 0){
        this.isPoisoned = false;
        System.out.println(this.name + "の毒が治った！");
      }
      if (this.hp <= 0){
        return true;
      }
    }
    return false;
  }

  int attack(Weapon weapon){
    int damage = (this.attack + weapon.getTotalAttack()) + random.nextInt(7) - 3;
    
    if (random.nextInt(100) < this.criticalRate){
      damage *= 2;
      System.out.println("クリティカルヒット！");
    }
    return damage;
  }


}
