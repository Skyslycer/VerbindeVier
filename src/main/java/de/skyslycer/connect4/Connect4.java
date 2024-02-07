package de.skyslycer.connect4;

import de.skyslycer.connect4.frontend.Frontend;

import java.util.Arrays;

public class Connect4 {

    // false = player 1 X
    // true = player 2 0
    private boolean player = false;
    private boolean won = false;

    private int[][] grid; // x y
    private int sizeX;
    private int sizeY;
    private final Frontend frontend;

    public Connect4(Frontend frontend) {
        this.frontend = frontend;
    }

    public void init() {
        if (!frontend.init()) {
            System.out.println("An error occurred while loading the frontend! The program will exit now.");
            System.exit(1);
        }
        sizeX = sizeDialogue(true);
        sizeY = sizeDialogue(false);
    }

    private int sizeDialogue(boolean horizontal) {
        var sizeStr = frontend.getSize(horizontal);
        int size;
        try {
            size = Integer.parseInt(sizeStr);
        } catch (NumberFormatException ignored) {
            frontend.printError(Frontend.ConnectFourError.NOT_A_NUMBER);
            return sizeDialogue(horizontal);
        }
        if (size < 4) {
            frontend.printError(Frontend.ConnectFourError.TOO_SMALL);
            return sizeDialogue(horizontal);
        }
        return size;
    }

    public void play() {
        won = false;
        grid = new int[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            var arr = new int[sizeY];
            Arrays.fill(arr, 0);
            grid[i] = arr;
        }
        frontend.printGrid(sizeX, sizeY, grid);
        while (!won) {
            if (throwChip()) {
                frontend.printGrid(sizeX, sizeY, grid);
                checkWin();
                if (!won) {
                    player = !player;
                }
            }
        }
        frontend.playerWon(mapPlayer());
    }

    private void checkWin() {
        if (checkHorizontal() || checkVertical() || checkDiagonalTopBottom() || checkDiagonalBottomTop()) won = true;
    }

    private boolean checkHorizontal() {
        for (int y = 0; y < sizeY; y++) {
            var count = 0;
            for (int x = 0; x < sizeX; x++) {
                if (grid[x][y] == mapPlayer()) count++; else count = 0;
                if (count >= 4) return true;
            }
        }
        return false;
    }

    private boolean checkVertical() {
        for (int x = 0; x < sizeX; x++) {
            var count = 0;
            for (int y = 0; y < sizeY; y++) {
                if (grid[x][y] == mapPlayer()) count++; else count = 0;
                if (count >= 4) return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalTopBottom() {
        for (int y = sizeY - 4; y >= 0; y--) {
            var count = 0;
            for (int x = 0; x < sizeX; x++) {
                int diagY = y + x;
                if (diagY >= sizeY) break;
                if (grid[x][diagY] == mapPlayer()) count++; else count = 0;
                if (count >= 4) return true;
            }
        }
        for (int x = 1; x < sizeX; x++) {
            var count = 0;
            for (int y = 0; y < sizeY; y++) {
                int diagX = x + y;
                if (diagX >= sizeX) break;
                if (grid[diagX][y] == mapPlayer()) count++; else count = 0;
                if (count >= 4) return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalBottomTop() {
        for (int y = 3; y < sizeY; y++) {
            var count = 0;
            for (int x = 0; x < sizeX; x++) {
                int diagY = y - x;
                if (diagY < 0) break;
                if (grid[x][diagY] == mapPlayer()) count++; else count = 0;
                if (count >= 4) return true;
            }
        }
        for (int x = 1; x < sizeX; x++) {
            var count = 0;
            for (int y = (sizeY - 1); y >= 0; y--) {
                var diagX = x + sizeY - 1 - y;
                if (diagX >= sizeX) break;
                if (grid[diagX][y] == mapPlayer()) count++; else count = 0;
                if (count >= 4) return true;
            }
        }
        return false;
    }

    private boolean throwChip() {
        var slot = frontend.getChipLocation(mapPlayer());
        int slotNum;
        try {
            slotNum = Integer.parseInt(slot);
        } catch (NumberFormatException exception) {
            frontend.printError(Frontend.ConnectFourError.NOT_A_NUMBER);
            return false;
        }
        if (slotNum < 1 || slotNum > 7) {
            frontend.printError(Frontend.ConnectFourError.NOT_IN_FIELD);
            return false;
        }
        slotNum--;
        if (grid[slotNum][0] != 0) {
            frontend.printError(Frontend.ConnectFourError.COLUMN_FULL);
            return false;
        }
        var freeY = 0;
        for (int y = 1; y < grid[slotNum].length; y++) {
            if (grid[slotNum][y] == 0) freeY = y; else break;
        }
        grid[slotNum][freeY] = mapPlayer();
        return true;
    }

    private int mapPlayer() {
        return player ? 2 : 1;
    }
    
}
