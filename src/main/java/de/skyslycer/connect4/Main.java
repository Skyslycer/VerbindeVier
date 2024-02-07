package de.skyslycer.connect4;

import de.skyslycer.connect4.frontend.console.ConsoleFrontend;

public class Main {

    public static void main(String[] args) {
        var game = new Connect4(new ConsoleFrontend());
        game.init();
        while (true) {
            game.play();
        }
    }

}
