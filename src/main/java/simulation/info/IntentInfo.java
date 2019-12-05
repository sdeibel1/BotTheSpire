package simulation.info;

import simulation.actions.AbstractAction;
import java.util.ArrayList;

public class IntentInfo {
    public ArrayList<AbstractAction> actions;
    public Intent intent;
    public int totalDmg;

    public IntentInfo(ArrayList<AbstractAction> actions, Intent intent) {
        this.actions = actions;
        this.intent = intent;
        this.totalDmg = 0;
        actions.stream().map(a -> this.totalDmg += a.damage);
    }

    public static enum Intent {
        ATTACK,
        ATTACK_BUFF,
        ATTACK_DEBUFF,
        ATTACK_DEFEND,
        BUFF,
        DEBUFF,
        STRONG_DEBUFF,
        DEBUG,
        DEFEND,
        DEFEND_DEBUFF,
        DEFEND_BUFF,
        ESCAPE,
        MAGIC,
        NONE,
        SLEEP,
        STUN,
        UNKNOWN;

        private Intent() {
        }
    }
}
