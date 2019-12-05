package ExampleMod;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import basemod.interfaces.OnCardUseSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

@SpireInitializer
public class ExampleMod implements PostExhaustSubscriber, 
                                    PostBattleSubscriber, 
                                    PostDungeonInitializeSubscriber,
        OnCardUseSubscriber {

    private int count, totalCount;
    public static final Logger logger = LogManager.getLogger(ExampleMod.class.getName());

    public ExampleMod() {
        logger.info("the mod is starting!");
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        @SuppressWarnings("unused")
        ExampleMod AIMod = new ExampleMod();
        logger.info("TESTING TESTING TESTING");
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        logger.info("You exhausted " + count + " cards this battle. So far, you've exhausted " +
                totalCount + " cards so far this run.");
        count = 0;
    }

    @Override
    public void receivePostDungeonInitialize() {
        count = 0;
        totalCount = 0;
    }

    @Override
    public void receivePostExhaust(AbstractCard abstractCard) {
        count++;
        totalCount++;
        logger.info("You exhausted a card!");

        logger.info("Can I change AbstractDungeon's player?");
        logger.info("The current player is " + AbstractDungeon.player);
        AbstractPlayer newPlayer = AbstractDungeon.player.newInstance();
        AbstractPlayer oldPlayer = AbstractDungeon.player;
//        AbstractDungeon.player = newPlayer;
//        logger.info("Now the current player is " + AbstractDungeon.player);
        logger.info(AbstractDungeon.player.masterDeck);
        logger.info(oldPlayer.getClass().getFields().length);
        for (Field f : oldPlayer.getClass().getFields()) {
            logger.info("here's a field");
            logger.info(f.getName());
            logger.info(f);
            logger.info("accessible? " + f.isAccessible());
        }
        for (Field f : oldPlayer.getClass().getFields()) {
            if (f.isAccessible()) {
                try {
                    f.set(this, f.get(oldPlayer));
                    logger.info("set field: " + f.getName() + " to value " + f.get(oldPlayer));
                } catch(Exception e) {
                    logger.info("caught the exception");
                }
            }
        }
        logger.info("New player " +  newPlayer);
        logger.info("Old player deck pile " + AbstractDungeon.player.drawPile);
        logger.info("New player master deck " + newPlayer.drawPile);


        AbstractDungeon.player = oldPlayer;
    }

    @Override
    public void receiveCardUsed(AbstractCard var1) {
        logger.info("You played a card!");
    }

}
// public class PlayRandom extends ConsoleCommand {
//     public PlayRandom() {
//         maxExtraTokens = 0;
//         requiresPlayer = true;
//     }

//     @Override
//     public void execute(String[] tokens, int depth) {
//         logger.info("Test");
//     }
// }