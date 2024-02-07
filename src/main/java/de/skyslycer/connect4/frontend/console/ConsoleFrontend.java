package de.skyslycer.connect4.frontend.console;

import de.skyslycer.connect4.frontend.Frontend;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleFrontend implements Frontend {

    private static final TranslationHandler.Language LANGUAGE = TranslationHandler.Language.EN;

    private final Scanner scanner = new Scanner(System.in);
    private TranslationHandler handler;

    @Override
    public boolean init() {
        handler = new TranslationHandler();
        try {
            handler.loadMessages(LANGUAGE);
        } catch (IOException exception) {
            System.out.println("Couldn't load translations!");
            System.out.println(exception.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void playerWon(int player) {
        handler.print(TranslationHandler.Message.PLAYER_WON, String.valueOf(player));
    }

    @Override
    public String getChipLocation(int player) {
        handler.print(TranslationHandler.Message.CHIP_LOCATION, String.valueOf(player));
        return scanner.next();
    }

    @Override
    public void printError(ConnectFourError error) {
        var str = switch (error) {
            case NOT_A_NUMBER -> handler.getMessage(TranslationHandler.Message.ERR_NOT_A_NUMBER);
            case NOT_IN_FIELD -> handler.getMessage(TranslationHandler.Message.ERR_NOT_IN_FIELD);
            case COLUMN_FULL -> handler.getMessage(TranslationHandler.Message.ERR_COLUMN_FULL);
            case TOO_SMALL -> handler.getMessage(TranslationHandler.Message.ERR_TOO_SMALL);
        };
        System.out.println(str);
    }

    @Override
    public void printGrid(int sizeX, int sizeY, int[][] grid) {
        for (int i = 1; i <= sizeX; i++) {
            System.out.print(i + " ");
        }
        for (int y = 0; y < sizeY; y++) {
            System.out.println();
            for (int x = 0; x < sizeX; x++) {
                System.out.print(mapField(grid[x][y]) + " ");
            }
        }
        System.out.println();
    }

    @Override
    public String getSize(boolean horizontal) {
        if (horizontal) {
            handler.print(TranslationHandler.Message.FIELD_SIZE_HORIZONTAL);
        } else {
            handler.print(TranslationHandler.Message.FIELD_SIZE_VERTICAL);
        }
        return scanner.next();
    }

    private String mapField(int field) {
        return switch (field) {
            case 1 -> "X";
            case 2 -> "0";
            default -> "_";
        };
    }

}
