package simulation.actions;

import simulation.AbstractEntity;
import simulation.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.HashMap;

public class DamageAction extends AbstractAction{
    private ArrayList<AbstractEntity> targets;
    private AbstractEntity source;
    public int damage;
    public int multi = 1;

    public DamageAction(int damage, int multi, AbstractEntity source, ArrayList<AbstractEntity> targets) {
        this.damage = damage;
        this.targets = targets;
        this.multi = multi;
        this.source = source;
    }

    @Override
    public void update() {
        for (AbstractEntity t : targets) {
            int finalDmg = this.damage;
            //TODO: fix floating point issues
            if (this.source.debuffs.get("weakened")) {
                finalDmg *= .75;
            }
            if (t.debuffs.get("vulnerable")) {
                finalDmg *= 1.5;
            }
            t.takeDmg(this.damage*multi);
        }
    }

    public enum DamageType {

    }
}


