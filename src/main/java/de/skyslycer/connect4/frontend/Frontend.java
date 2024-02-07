package de.skyslycer.connect4.frontend;

public interface Frontend {

    boolean init();

    void playerWon(int player);

    String getChipLocation(int player);

    void printError(ConnectFourError error);

    void printGrid(int sizeX, int sizeY, int[][] grid);

    String getSize(boolean horizontal);

    enum ConnectFourError {
        NOT_A_NUMBER,
        NOT_IN_FIELD,
        COLUMN_FULL,
        TOO_SMALL;
    }

}
