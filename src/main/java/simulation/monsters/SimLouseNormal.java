package simulation.monsters;

import com.megacrit.cardcrawl.monsters.exordium.LouseNormal;
import simulation.actions.DamageAction;

import java.util.ArrayList;
import java.util.Random;

public class SimLouseNormal extends AbstractMonster{
    private boolean isOpen;
    private static final int BITE = 0;
    private static final int STRENGTHEN = 1;

    public SimLouseNormal(LouseNormal louse) {
        super(louse);
        this.isOpen = false;
    }

    private void getMove() {
        this.realMonster.rollMove();
    }

    @Override
    public void takeTurn() {
        //TODO: check random stuff
        Random r = new Random();
        float prob = r.nextFloat();
        if (prob <=.25) {
            if (lastTwoMoves(STRENGTHEN)) {
                this.moveHistory.add(BITE);
                //TODO: fix
                new DamageAction(BITE_DAMAGE, 1, this,player);
            } else {
                this.moveHistory.add(STRENGTHEN);
            }
        } else if (lastTwoMoves(BITE)){
            this.moveHistory.add(STRENGTHEN);
        } else {
            this.moveHistory.add(BITE);
        }
    }
}
