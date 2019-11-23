package ExampleMod;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import basemod.interfaces.OnCardUseSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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