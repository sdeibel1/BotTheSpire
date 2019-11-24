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


import java.util.*;
import java.util.stream.Collectors;

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

      int intentDmg = -1 * player.currentBlock;
      for (AbstractMonster monster : monsters.monsters) {
//          if (monster.intent == AbstractMonster.Intent.ATTACK ||
//                  monster.intent == AbstractMonster.Intent.ATTACK_BUFF ||
//                  monster.intent == AbstractMonster.Intent.ATTACK_DEFEND) {
              intentDmg += monster.getIntentDmg();
//          }
      }
      logger.info("Intent damage is: " + intentDmg);

      if (player.hand.canUseAnyCard()) {
          List<AbstractCard> playable = player.hand.group
                  .stream().filter(c -> c.cost <= player.energy.energy)
                  .collect(Collectors.toList());
          AbstractCard cardToUse;
          if (intentDmg <= 3) {
              cardToUse = playable.stream().max(Comparator.comparingInt(c -> c.damage)).get();
          } else {
              cardToUse = playable.stream().max(Comparator.comparingInt(c -> c.block)).get();
          }
          logger.info("Card to use is: " + cardToUse.name + " with post-use damage intent of " + intentDmg);
          logger.info("This card has damage " + cardToUse.damage + " and block " + cardToUse.block);
//          for (AbstractCard card : player.hand.group) {
//              if (intentDmg <= 3) {
//              }
//              if (card.block > 0 && card.block <= intentDmg && card.cost <= player.energy.energy) {
//                  intentDmg -= card.block;
//              }
//          }
          AbstractMonster lowestMonster = monsters.monsters.stream().min(Comparator.comparingInt(m -> m.currentHealth)).get();
          player.useCard(cardToUse, lowestMonster, cardToUse.cost);
      }

//      if (player.hand.canUseAnyCard()) {
//          AbstractMonster lowestMonster = monsters.monsters.stream().min((m1, m2) -> Integer.compare(m1.currentHealth, m2.currentHealth)).get();
//          for (AbstractCard card : player.hand.group) {
//              if (card.block > 0 && card.block <= intentDmg && card.cost <= player.energy.energy) {
//                  player.useCard(card, Collections.min(monsters.monsters, AbstractMonster::currentHealth) card.cost);
//              }
//          }
//          logger.info(player.hand);
//          AbstractCard c = player.hand.getRandomCard(false);
//          logger.info(c);
//          logger.info(player.energy);
//          logger.info(player.energy.energy);
//          AbstractMonster randMonster = monsters.getRandomMonster(true);
//          logger.info(c.energyOnUse);
//          player.useCard(c, randMonster, c.energyOnUse);
//      }
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