package simulation.actions;

import simulation.AbstractEntity;

public class DebuffAction extends AbstractAction {
    public String debuffName;
    public AbstractEntity source;
    public AbstractEntity target;

    public DebuffAction(String debuffName, AbstractEntity source, AbstractEntity target) {
        this.debuffName = debuffName;
        this.source = source;
        this.target = target;
    }

    @Override
    public void update() {
        this.target.debuffs.put(debuffName, true);
    }
}
