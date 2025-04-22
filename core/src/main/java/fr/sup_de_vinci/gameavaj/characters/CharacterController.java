package fr.sup_de_vinci.gameavaj.characters;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents a controller responsible for updating the state of a character
 * This interface allows decoupling character logic from input or AI behaviors
 */
public interface CharacterController {
  /**
   * Determines the new state of the character based on its current state and
   * position
   *
   * @param current The current {@link State} of the character
   * @param pos     The current render position of the character in pixels
   * @return A new {@link State} instance
   */
  State update(State current, Vector2 pos);
}
