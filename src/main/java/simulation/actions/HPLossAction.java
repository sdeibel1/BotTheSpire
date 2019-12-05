package simulation.actions;

import simulation.AbstractEntity;

public class HPLossAction extends AbstractAction {
    public int amount;
    public AbstractEntity target;

    public HPLossAction(int amount, AbstractEntity target) {
        this.amount = amount;
        this.target = target;
    }

    @Override
    public void update() {
        this.target.currentHP -= this.amount;
    }
}
