package fr.sup_de_vinci.gameavaj.characters;

import com.badlogic.gdx.math.Vector2;

public interface CharacterController {
  State update(State current, Vector2 pos);
}
