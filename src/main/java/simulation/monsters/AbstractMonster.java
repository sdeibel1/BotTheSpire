package simulation.monsters;

import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import simulation.AbstractEntity;
import simulation.actions.AbstractAction;
import simulation.info.IntentInfo;

import java.util.ArrayList;

public abstract class AbstractMonster extends AbstractEntity {
    private int currentHealth;
    private int maxHealth;
    private IntentInfo.Intent intent;
    protected com.megacrit.cardcrawl.monsters.AbstractMonster realMonster;
    private boolean alive;
    private EnemyMoveInfo move;
    protected ArrayList<Integer> moveHistory = new ArrayList<Integer>();

    public AbstractMonster(com.megacrit.cardcrawl.monsters.AbstractMonster monster) {
        super(monster.maxHealth, monster.currentHealth);
        this.alive = true;
        this.maxHealth = monster.maxHealth;
        this.currentHealth = monster.currentHealth;
        this.realMonster = monster;
    }

    public void takeDmg(int dmg) {
        this.currentHealth -= dmg;
        if (this.currentHealth <= 0) {
            this.alive = false;
        }
    }

    //TODO: check int vs Integer equality chekcing, maybe that's why STS uses byte and Byte?
    protected boolean lastTwoMoves(int move) {
        if (this.moveHistory.size() < 2) {
            return false;
        } else {
            return this.moveHistory.get(this.moveHistory.size() - 1) == move && this.moveHistory.get(this.moveHistory.size() - 2) == move;
        }
    }

    public abstract void takeTurn();
}
