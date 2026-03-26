package strategy;

import domain.action.Action;
import domain.action.BasicAttack;
import domain.combatant.Combatant;
import java.util.List;

/**
 * The only enemy action strategy required by the spec: always BasicAttack
 * the first living target.
 * OCP: if a smarter AI is needed later, implement EnemyActionStrategy — done.
 */
public class BasicAttackStrategy implements EnemyActionStrategy {

    @Override
    public Action chooseAction(Combatant enemy, List<Combatant> targets) {
        // Spec: enemies always perform BasicAttack. Target selection: first living target.
        return new BasicAttack();
    }
}
