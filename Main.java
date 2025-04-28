import java.util.*;


// RPGコマンドバトルゲーム
// Author: （あなたの名前）
// プレイヤーが武器を選び、ランダムな敵と戦うターン制バトルゲームです。
// 武器強化、スキル、毒、ガチャ要素、連勝記録などの機能があります。


// 敵キャラクターのクラス
// 名前、HP、攻撃力を持つ
class Enemy {
    String name;
    int hp;
    int maxHp;
    int attack;

    Enemy(String name,int hp,int attack){
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
    }
}


// 武器のクラス
// 武器名、攻撃力、レアリティ、攻撃力ボーナスを管理する
class Weapon {
    String name;
    int attack;
    String rarity;
    int attackBonus = 0;

    Weapon(String name,int attack,String rarity){
        this.name = name;
        this.attack = attack;
        this.rarity = rarity;
    }

    void showInfo(){
        System.out.println(name + "（攻撃力：" + getTotalAttack() + "/レア：" + rarity + "）");
    }

    int getTotalAttack(){
        return attack + attackBonus;
    }
}

// The main method must be in a class named "Main".
public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // RPGバトルゲームスタート・終了選択
        System.out.println("=== RPGバトルへようこそ！ ===");
        System.out.println("1：ゲームスタート");
        System.out.println("2：ゲーム終了");
        System.out.print("数字を入力してください：");
        int menuChoice = scanner.nextInt();
        while (menuChoice < 1 || menuChoice > 2) {
            System.out.println("無効な番号です。もう一度選んでください（１～２）：");
            menuChoice = scanner.nextInt();
        }

        if (menuChoice == 1) {
            System.out.println("ゲームスタート！");
            System.out.println("--------------------------------");
        } else {
            System.out.println("ゲームを終了します。");
            scanner.close();
            return; // mainメソッドを終了
        }


        // プレイヤー初期化
        Player player = new Player("勇者", 100, 30, 20, 10, 20);

        // 連勝数カウント用変数
        int winCount = 0;
        int turnCount = 1;
        // ガチャ再抽選チケットフラグ
        boolean hasRerollTicket = false;

        // 武器リスト作成
        Weapon[] allWeapons = {
            new Weapon("アイアンソード",10,"N"),
            new Weapon("シルバーソード",15,"R"),
            new Weapon("フレイムブレード",20,"SR"),
            new Weapon("ドラゴンスレイヤー",30,"SSR"),
            new Weapon("木の棒",5,"N"),
            new Weapon("アイスブレード",18,"R"),
            new Weapon("サンダーソード",22,"SR"),
            new Weapon("デモンズサイズ",35,"SSR"),
            new Weapon("聖剣エクスカリバー",40,"SSR"),
            new Weapon("さびた剣",3,"N")
        };

        // 武器ガチャ：ランダムに３本の武器を選出
        System.out.println("===武器ガチャ！ランダムに３つ出ます===");
        List<Weapon> weaponOptions = new ArrayList<>();
        while (weaponOptions.size() < 3){
            Weapon w = allWeapons[random.nextInt(allWeapons.length)];
            if (!weaponOptions.contains(w)){
                weaponOptions.add(w);
            }
        }
        for (int i = 0; i < weaponOptions.size();  i++){
            System.out.print((i + 1) + "：");
            weaponOptions.get(i).showInfo();
        }

        System.out.println("--------------------------------");

        // 武器を選択して装備する
        System.out.println("使う武器を選んでください（１～３）：");
        int weaponChoice = scanner.nextInt();
        while (weaponChoice < 1 || weaponChoice > 3) {
            System.out.println("無効な番号です。もう一度選んでください（１～３）：");
            weaponChoice = scanner.nextInt();
        }
        Weapon playerWeapon = weaponOptions.get(weaponChoice - 1);
        System.out.println("あなたは『" + playerWeapon.name + "』を装備した！");

        // 敵リスト作成
        Enemy[]enemies = {
            new Enemy("スライム",80,15),
            new Enemy("ゴブリン",100,20),
            new Enemy("ドラゴン",150,30)
        };
        
        // 戦う敵を選択
        System.out.println("===戦う敵を選んでください===");
        for (int i = 0; i < enemies.length; i++){
            System.out.println((i + 1) + "：" + enemies[i].name + "（HP：" + enemies[i].hp + "/攻撃力：" + enemies[i].attack + "）");
        }
        int enemyChoice = scanner.nextInt();
        while (enemyChoice < 1 || enemyChoice > enemies.length) {
            System.out.println("無効な番号です。もう一度選んでください（１～" + enemies.length + "）：");
            enemyChoice = scanner.nextInt();
        }
        Enemy enemy = enemies[enemyChoice - 1];

        System.out.println("--------------------------------");

        // 敵の出現表示
        System.out.println(enemy.name + "が現れた！");
        
        System.out.println("--------------------------------");

        // バトルスタート
        boolean isPlaying = true;

        while (isPlaying){
            while (player.hp > 0 && enemy.hp > 0){
                System.out.println("\n=== 第" + turnCount + "ターン ===");

                if (player.sufferPoison()){
                    System.out.println(player.name + "は毒で倒れた・・・ゲームオーバー");
                    isPlaying = false;
                    break;
                }

                // プレイヤーターン処理
                System.out.println("---プレイヤーターン---");
            
                // プレイヤーの行動（攻撃・回復・スキル使用）選択
                System.out.println("何をしますか？");
                System.out.println("１：こうげき");
                System.out.println("２：かいふく");
                System.out.println("３：スキル（パワースラッシュ）");

                System.out.println("--------------------------------");

                int command = scanner.nextInt();
                while (command < 1 || command > 3) {
                    System.out.println("無効な番号です。もう一度選んでください（１～３）：");
                    command = scanner.nextInt();
                }

                if (command == 1){
                    System.out.println(player.name + "のこうげき！");
                    int damage = player.attack(playerWeapon);
                    enemy.hp -= damage;
                    System.out.println(enemy.name + "に" + damage + "のダメージ！");
                }else if (command == 2){
                    player.heal();
                }else if (command == 3){
                    int skillDamage = player.useSkill(playerWeapon);
                    if (skillDamage > 0) {
                        enemy.hp -= skillDamage;
                        System.out.println(enemy.name + "に" + skillDamage + "の大ダメージ！");
                    }
                    
                }


                // 敵ターン処理
                int enemyCommand = random.nextInt(2) + 1;

                if (enemy.hp > 0){
                    System.out.println("---敵ターン---");
                    // 敵の行動（攻撃・スキル・回復）選択
                    if (enemyCommand == 1) {
                        int skillRate = 30;
                        int poisonRate = 20;
                        int skillRoll = random.nextInt(100);

                        if (skillRoll < poisonRate){
                            System.out.println(enemy.name + "はスキル『ポイズンブレス』を使った！");
                            System.out.println(player.name + "は毒状態になった！");
                            player.poisonTurns = 3;
                            player.isPoisoned = true;
                        }else if (skillRoll < poisonRate + skillRate){
                            System.out.println(enemy.name + "は強力なスキル『シャドウバースト』を使った！");
                            int skillDamage = enemy.attack * 2;
                            player.hp -= skillDamage;
                            System.out.println(player.name + "は" + skillDamage + "の大ダメージを受けた！");
                        }else{
                            System.out.println(enemy.name + "のこうげき！");
                            int damage = enemy.attack + random.nextInt(5);
                            if (random.nextInt(100) < player.criticalRate){
                                player.hp -= damage * 2;
                                System.out.println("クリティカルヒット！");
                                System.out.println(player.name + "は" + damage * 2 + "の大ダメージを受けた！");
                            }else{
                                player.hp -= damage;
                                System.out.println(player.name + "は" + damage + "のダメージを受けた！");
                            }
                        }
                    }else if (enemyCommand == 2) {
                        System.out.println(enemy.name + "は回復魔法を使った！");
                        enemy.hp += 20;
                        if (enemy.hp > enemy.maxHp){
                            enemy.hp = enemy.maxHp;
                        }
                        System.out.println(enemy.name + "のHPが20回復した！（現在のHP：" + enemy.hp + "）");
                    }
                
                }

                System.out.println("--------------------------------");

                System.out.println("【" + player.name + "HP：" + player.hp + "/MP：" + player.mp + "】" + "【" + enemy.name + "HP：" + enemy.hp + "】");

                System.out.println("================================");
                turnCount++;


                // バトル結果判定（敵撃破 or プレイヤー敗北）
                if (enemy.hp <= 0) {
                    System.out.println(enemy.name + "を倒した！");
                    winCount ++;
                
                    
                    System.out.println("===勝利報酬===");
                    System.out.println("１：武器強化（＋３ 攻撃力）");
                    System.out.println("２：ガチャ再抽選チケットを獲得");
                    System.out.println("３：何も受け取らずに次へ進む");
                    // 勝利報酬選択（武器強化 or ガチャ再抽選 or 何も選ばない）
                    int reward = scanner.nextInt();
                    while (reward < 1 || reward > 3) {
                        System.out.println("無効な番号です。もう一度選んでください（１～３）：");
                        reward = scanner.nextInt();
                    }

                    if (reward == 1){
                        playerWeapon.attackBonus += 3;
                        System.out.println("--------------------------------");
                        System.out.println(playerWeapon.name + "を強化した！（攻撃力＋３）");
                        System.out.println("--------------------------------");
                    }else if (reward == 2){
                        hasRerollTicket = true;
                        System.out.println("武器ガチャをやり直せるチケットを獲得！");
                    }else{
                        System.out.println("何も受け取らずに次の戦いへ。");
                    }

                    if (hasRerollTicket){
                        System.out.println("ガチャ再抽選チケットを使いますか？（１：はい  ２：いいえ）");
                        int rerollChoice = scanner.nextInt();
                        while (rerollChoice < 1 || rerollChoice > 2) {
                            System.out.println("無効な番号です。もう一度選んでください（１または２）：");
                            rerollChoice = scanner.nextInt();
                        }
                        if (rerollChoice == 1){
                            // ガチャ再抽選の処理
                            System.out.println("===再抽選！新しい武器を引き直します===");
                            weaponOptions.clear();
                            while (weaponOptions.size() < 3){
                                Weapon w = allWeapons[random.nextInt(allWeapons.length)];
                                if (!weaponOptions.contains(w)){
                                    weaponOptions.add(w);
                                }
                            }
                            for (int i = 0; i < weaponOptions.size();  i++){
                                System.out.print((i + 1) + "：");
                                weaponOptions.get(i).showInfo();
                            }

                            System.out.println("--------------------------------");

                            System.out.println("使う武器を選んでください（１～３）：");
                            weaponChoice = scanner.nextInt();
                            while (weaponChoice < 1 || weaponChoice > 3) {
                                System.out.println("無効な番号です。もう一度選んでください（１～３）：");
                                weaponChoice = scanner.nextInt();
                            }
                            playerWeapon = weaponOptions.get(weaponChoice - 1);
                            System.out.println("あなたは新たに『" + playerWeapon.name + "』を装備した！");

                            hasRerollTicket = false;
                        }
                    }
                    

                    // もう一度戦うか選択
                    System.out.println("もう一度戦いますか？（１：はい  ２：いいえ）");
                    int again = scanner.nextInt();
                    while (again < 1 || again > 2) {
                        System.out.println("無効な番号です。もう一度選んでください（１または２）：");
                        again = scanner.nextInt();
                    }
                    if (again == 1){
                        // プレイヤーHP/MP回復処理
                        int hpRecovery = (int)(player.maxHp * 0.5);
                        int mpRecovery = (int)(player.maxMp * 0.5);

                        player.hp += hpRecovery;
                        player.mp += mpRecovery;

                        if (player.hp > player.maxHp){
                            player.hp = player.maxHp;
                        }
                        if (player.mp > player.maxMp){
                            player.mp = player.maxMp;
                        }
                        System.out.println("--------------------------------");
                        System.out.println(player.name + "はHPを" + hpRecovery + "、MPを" + mpRecovery + "回復した！");
                        System.out.println("【現在HP：" + player.hp + "/MP:" + player.mp + "/攻撃力：" + playerWeapon.getTotalAttack() + "】");
                        System.out.println("--------------------------------");

                        // 敵の強化処理（次の戦闘に向けて敵を強化）
                        for (Enemy e : enemies){
                          e.maxHp = (int)(e.maxHp * 1.2);
                          e.hp = e.maxHp;
                          e.attack = (int)(e.attack * 1.2);
                        }

                        // 新しい敵の選択
                        System.out.println("===次の敵を選んでください===");
                        for (int i = 0; i < enemies.length; i++){
                            System.out.println((i + 1) + "：" + enemies[i].name + "（HP：" + enemies[i].hp + "/攻撃力：" + enemies[i].attack + "）");
                        }
                        enemyChoice = scanner.nextInt();
                        while (enemyChoice < 1 || enemyChoice > enemies.length) {
                            System.out.println("無効な番号です。もう一度選んでください（１～" + enemies.length + "）：");
                            enemyChoice = scanner.nextInt();
                        }
                        enemy = enemies[enemyChoice - 1];

                        System.out.println("--------------------------------");
                        System.out.println(enemy.name + "が現れた！");
                        System.out.println("【HP：" + enemy.hp + "/攻撃力：" + enemy.attack + "】");
                        System.out.println("--------------------------------");

                        //ターン数のリセット
                        turnCount = 1;

                        
                    }else{
                        // ゲーム終了時、連勝数を表示
                        System.out.println("ゲーム終了");
                        System.out.println("あなたの連勝数：" + winCount + "勝");
                        isPlaying = false;
                    }
            
                }else if (player.hp <= 0){
                    System.out.println(player.name + "は倒された・・・ゲームオーバー");
                    System.out.println("あなたの連勝数：" + winCount + "勝");
                    isPlaying = false;
                }
            
            }
            
        }
      scanner.close();
    }
}