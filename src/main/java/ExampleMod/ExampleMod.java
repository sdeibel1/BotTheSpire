package ExampleMod;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostEnergyRechargeSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import basemod.interfaces.OnCardUseSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

@SpireInitializer
public class ExampleMod implements PostExhaustSubscriber, 
                                    PostBattleSubscriber, 
                                    PostDungeonInitializeSubscriber,
        PostEnergyRechargeSubscriber,
        OnCardUseSubscriber {

    private int count, totalCount;
    public static final Logger logger = LogManager.getLogger(SampleMod.class.getName());

    public ExampleMod() {
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        new ExampleMod();
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

    @Override
    public void receivePostEnergyRecharge() {
        AbstractRoom currRoom = AbstractDungeon.getCurrRoom();
        MonsterGroup monsters = AbstractDungeon.getMonsters();
        AbstractPlayer player = AbstractDungeon.player;
        // ArrayList<AbstractCard> playable = new ArrayList<>();
        // for (AbstractCard c : AbstractPlayer.hand.group) {
        //     if (c.energyOnUse <= AbstractPlayer.energy.energy) {
        //         playable.add(c.makeSameInstanceOf());
        //     }
        // }
        while (player.hand.canUseAnyCard()) {
            logger.info(player.hand);
            AbstractCard c = player.hand.getRandomCard(false);
            while (c.energyOnUse <= player.energy.energy) {
                c = player.hand.getRandomCard(false);
            }
            player.useCard(c, monsters.getRandomMonster(true), c.energyOnUse);
        }
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