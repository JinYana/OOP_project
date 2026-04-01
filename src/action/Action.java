package action;

import combatant.Combatant;

public interface Action {
    void execute(Combatant actor, Combatant target);
}