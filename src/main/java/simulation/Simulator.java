package simulation;

import ExampleMod.ExampleMod;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Simulator {
    public AbstractPlayer player;
    public CardGroup hand;
    public MonsterGroup monsters;
    public static final Logger logger = LogManager.getLogger(ExampleMod.class.getName());
    public static final GameActionManager originalActionStack = AbstractDungeon.actionManager;


    public Simulator(HashMap<String, Object> gameState) {
        this.player = (AbstractPlayer) gameState.get("player");
        this.hand = (CardGroup) gameState.get("hand");
        this.monsters = (MonsterGroup) gameState.get("monsters");
    }

    // Takes in card and target and advances one game state
    public HashMap<String, Object> playCard(AbstractCard card, AbstractMonster target) {
        player.useCard(card, target, card.cost);
        HashMap<String, Object> newState = new HashMap<String, Object>();
        newState.put("player", player);
        newState.put("hand", )
        newState.put(,)
    }


    // Steps the simulation of a gamestate by one turn.
    // Begins at the beginning of player's turn and returns a new game state at the
    // beginning of the start of the player's next turn (player makes one turn, monsters make one turn).
    private HashMap<String, Object> stepSimulation(HashMap<String, Object> oldState, AbstractCard cardToPlay) {
        player.useCard(cardToPlay);

        return new HashMap<String, Object>();
    }

    public saveState
}
