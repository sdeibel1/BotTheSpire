package simulation.monsters;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.monsters.exordium.LouseNormal;
import simulation.Simulator;
import simulation.actions.DamageAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SimLouseNormal extends AbstractMonster{
    private boolean isOpen;
    private final int BITE = 0;
    private final int STRENGTHEN = 1;
    private int biteDamage;

    public SimLouseNormal(LouseNormal louse) {
        super(louse);
        this.isOpen = false;
        this.biteDamage = (int) ReflectionHacks.getPrivate(louse, louse.getClass(), "biteDamage");
    }

    private void getMove() {
        this.realMonster.rollMove();
    }

    @Override
    public void takeTurn() {
        Random r = new Random();
        float prob = r.nextFloat();
        if (prob <=.25) {
            if (lastTwoMoves(STRENGTHEN)) {
                this.moveHistory.add(BITE);
                new DamageAction(this.biteDamage, 1, this, Arrays.asList(Simulator.player)).update();
            } else {
                this.moveHistory.add(STRENGTHEN);
                this.strength += 3;
            }
        } else if (lastTwoMoves(BITE)){
            this.moveHistory.add(STRENGTHEN);
            this.strength += 3;
        } else {
            this.moveHistory.add(BITE);
            new DamageAction(this.biteDamage, 1, this, Arrays.asList(Simulator.player)).update();
        }
    }
}
