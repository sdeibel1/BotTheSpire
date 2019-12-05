package simulation;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class SimCard {
    public AbstractCard card;

    public SimCard(AbstractCard c) {
        this.card = c;
    }

    public abstract void use();
}
