package simulation;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

//public class StateTree<HashMap<String, Object>> {
//    public AbstractCard card;
//    StateTree<HashMap<String, Object>> parent;
//    List<StateTree<HashMap<String, Object>>> children;
//
//    public StateTree(HashMap<String, Object> gameState, AbstractCard card) {
//        this.card = card;
//    }
//
//}

public class StateTree {
    HashMap<String, Object> gameState;
    StateTree parent;
    List<StateTree> children;

    public StateTree(HashMap<String, Object> gameState, AbstractCard card) {
        this.gameState = gameState;
        this.children = new ArrayList<StateTree>();
    }

    public StateTree addChild(StateTree child) {
        child.parent = this;
        this.children.add(child);
        return child;
    }
}