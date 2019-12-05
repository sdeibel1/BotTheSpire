package simulation.actions;

import simulation.AbstractEntity;

public class BlockAction extends AbstractAction{
    public int block;
    public int multi;
    public AbstractEntity target;
    public AbstractEntity source;

    public BlockAction(int block, int multi, AbstractEntity source, AbstractEntity target) {
        this.block = block;
        this.multi = multi;
        this.target = target;
        this.source = source;
    }

    @Override
    public void update() {
        int finalBlock = this.block;
        //TODO: check for null source?
        //TODO: fix floating point issues
        if (this.source != null && this.source.debuffs.get("frail")) {
            finalBlock *= .75;
        }
        target.gainBlock(finalBlock*multi);
    }
}
