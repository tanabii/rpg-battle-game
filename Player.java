import java.util.Random;

// プレイヤーキャラクターのクラス
// 名前、HP、MP、攻撃力、クリティカル率、状態異常などを管理する
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

  // プレイヤーの初期ステータスを設定するコンストラクタ
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


  // HPを回復する
  void heal() {
    this.hp += 20;
    if (this.hp > this.maxHp){
      this.hp = this.maxHp;
    }
    System.out.println(name + "のHPが20回復した！（現在のHP：" + hp + "）");
  }

  // 武器を使ってスキル攻撃を行う（MP消費）
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

  // 毒ダメージを受ける処理（毒状態ならダメージ＋ターン減少）
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

  // 通常攻撃を行う（クリティカル判定あり）
  int attack(Weapon weapon){
    int damage = (this.attack + weapon.getTotalAttack()) + random.nextInt(7) - 3;
    
    if (random.nextInt(100) < this.criticalRate){
      damage *= 2;
      System.out.println("クリティカルヒット！");
    }
    return damage;
  }


}
