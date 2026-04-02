package strategy;

import action.Action;
import action.BasicAttack;
import combatant.Combatant;
import java.util.List;

/**
 * The only enemy action strategy required by the spec: always BasicAttack
 * the first living target.
 * OCP: if a smarter AI is needed later, implement EnemyActionStrategy — done.
 */
public class BasicAttackStrategy implements EnemyActionStrategy {


    @Override
    public Action chooseAction() {
        return new BasicAttack();
    }
}
