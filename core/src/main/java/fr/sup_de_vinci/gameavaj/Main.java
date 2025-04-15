package fr.sup_de_vinci.gameavaj;

import com.badlogic.gdx.Game;
import fr.sup_de_vinci.gameavaj.screen.FirstScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends Game {

    @Override
    public void create() {
        setScreen(new FirstScreen());
    }
}