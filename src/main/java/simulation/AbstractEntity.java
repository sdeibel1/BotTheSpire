package simulation;

import java.util.HashMap;

public abstract class AbstractEntity {
    public int maxHP;
    public int currentHP;
    public int currentBlock;
    public int strength = 0;
    public int dexterity = 0;
    public HashMap<String, Boolean> debuffs;

    public AbstractEntity(int maxHP, int currentHP) {
        this.maxHP = maxHP;
        this.currentHP = currentHP;
        this.currentBlock = 0;
        this.debuffs = new HashMap<String, Boolean>();
    }

    public void takeDmg(int dmg) {
        this.currentHP -= dmg;
    };
    public void gainBlock(int block) {
        this.currentBlock += block;
    }
}
