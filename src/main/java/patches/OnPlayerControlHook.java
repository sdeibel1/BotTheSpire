package patches;

import ExampleMod.ExampleMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

@SpirePatch(
  clz= GameActionManager.class,
  method="update"
)
public class OnPlayerControlHook {

  public static final Logger logger = LogManager.getLogger(ExampleMod.class.getName());

  @SpireInsertPatch(
    locator=Locator.class,
    localvars={}
  )
  public static void Insert(GameActionManager __instance) {
    AbstractRoom currRoom = AbstractDungeon.getCurrRoom();
    MonsterGroup monsters = AbstractDungeon.getMonsters();
    AbstractPlayer player = AbstractDungeon.player;
    logger.info(player);
    logger.info(monsters);
    // ArrayList<AbstractCard> playable = new ArrayList<>();
    // for (AbstractCard c : AbstractPlayer.hand.group) {
    //     if (c.energyOnUse <= AbstractPlayer.energy.energy) {
    //         playable.add(c.makeSameInstanceOf());
    //     }
    // }
    if (player.hand.canUseAnyCard()) {
        logger.info(player.hand);
        AbstractCard c = player.hand.getRandomCard(false);
        logger.info(c);
        logger.info(player.energy);
        logger.info(player.energy.energy);
        AbstractMonster randMonster = monsters.getRandomMonster(true);
        logger.info(c.energyOnUse);
        player.useCard(c, randMonster, c.energyOnUse);

//        AbstractCard c = player.hand.getRandomCard(false);
//        while (c.energyOnUse <= player.energy.energy) {
//            c = player.hand.getRandomCard(false);
//        }
//        player.useCard(c, monsters.getRandomMonster(true), c.energyOnUse);
    }
  }

  // ModTheSpire searches for a nested class that extends SpireInsertLocator
  // This class will be the Locator for the @SpireInsertPatch
  // When a Locator is not specified, ModTheSpire uses the default behavior for the @SpireInsertPatch
  private static class Locator extends SpireInsertLocator {
    // This is the abstract method from SpireInsertLocator that will be used to find the line
    // numbers you want this patch inserted at
    public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
      // finalMatcher is the line that we want to insert our patch before -
      // in this example we are using a `MethodCallMatcher` which is a type
      // of matcher that matches a method call based on the type of the calling
      // object and the name of the method being called. Here you can see that
      // we're expecting the `end` method to be called on a `SpireBatch`
      Matcher hasControl_fieldMatcher = new Matcher.FieldAccessMatcher(GameActionManager.class, "hasControl");

      // the `new ArrayList<Matcher>()` specifies the prerequisites before the line can be matched -
      // the LineFinder will search for all the prerequisites in order before it will match the finalMatcher -
      // since we didn't specify any prerequisites here, the LineFinder will simply find the first expression
      // that matches the finalMatcher.
      return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), hasControl_fieldMatcher);
    }
  }

}