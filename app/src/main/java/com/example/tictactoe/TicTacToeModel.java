package com.example.tictactoe;

import com.google.gson.Gson;

public class TicTacToeModel {

    private final WhoseTurn[][] grid = new WhoseTurn[3][3];
    WhoseTurn currPlayer = WhoseTurn.X;

    TicTacToeModel() {
        restart();
    }

    public boolean isValidClick(int row, int col) {
        return grid[row][col].equals(WhoseTurn.None);
    }

    public void cellClick(int row, int col, WhoseTurn turn) {
        if (isValidClick(row, col)) {
            grid[row][col] = turn;
            currPlayer = (currPlayer.equals(WhoseTurn.X)) ? WhoseTurn.O : WhoseTurn.X;
        }
        isWin();
    }

    public boolean isWin() {
        return (isRowWinner() || isColWinner() || isDiagonalTopLeftWinner() || isDiagonalTopRightWinner());
    }

    private boolean isDiagonalTopRightWinner() {
        WhoseTurn currentDiagonalValue;
        boolean isDiagonalWinner=false;

        int shortestRowLength = getShortestRowLength();

        currentDiagonalValue = grid[0][shortestRowLength-1];
        if (!currentDiagonalValue.equals(WhoseTurn.None)) {
            // assume diagonal will be all the same, for now
            isDiagonalWinner = true;
            for (int i = 1; i < grid.length; i++) {
                if (!grid[i][shortestRowLength - 1 - i].equals(currentDiagonalValue)) {
                    isDiagonalWinner = false;
                    break;
                }
            }
        }
        return isDiagonalWinner;
    }

    private boolean isDiagonalTopLeftWinner() {
        WhoseTurn currentDiagonalValue;
        boolean isDiagonalWinner = false;

        currentDiagonalValue = grid[0][0];
        if (!currentDiagonalValue.equals(WhoseTurn.None)) {
            // assume diagonal will be all the same, for now
            isDiagonalWinner = true;
            for (int i = 1; i < grid.length; i++) {
                if (!grid[i][i].equals(currentDiagonalValue)) {
                    isDiagonalWinner = false;
                    break;
                }
            }
        }
        return isDiagonalWinner;
    }

    private boolean isRowWinner() {
        WhoseTurn currentRowValue;
        boolean currentRowWinner;
        for (int i = 0; i < grid.length; i++) {
            currentRowValue = grid[i][0];
            if (!currentRowValue.equals(WhoseTurn.None)) {
                // assume row will be all the same, for now
                currentRowWinner = true;

                // check row starting from the second cell
                for (int j = 1; j < grid[i].length; j++) {
                    if (!grid[i][j].equals(currentRowValue)) {
                        currentRowWinner = false;
                        break;
                    }

                }
                // by the end of the row, if we haven't changed to false then the row matches!
                if (currentRowWinner)
                    return true;
                // else - check the next row, if any
            }
        }
        return false;
    }

    private boolean isColWinner() {
        WhoseTurn currentColValue;
        boolean currentColWinner;

        int shortestRowLength = getShortestRowLength();

        for (int j = 0; j < shortestRowLength; j++) {
            currentColValue = grid[0][j];
            if (!currentColValue.equals(WhoseTurn.None)) {
                // assume col will be all the same, for now
                currentColWinner = true;

                // check col starting from the second cell
                for (int i = 1; i < grid.length; i++) {
                    if (!grid[i][j].equals(currentColValue)) {
                        currentColWinner = false;
                        break;
                    }
                }
                // by the end of the col, if we haven't changed to false then the col matches!
                if (currentColWinner)
                    return true;
                // else - check the next col, if any
            }
        }
        return false;
    }

    private int getShortestRowLength() {
        int shortestRowLength = Integer.MAX_VALUE;
        for (int i = 0; i < grid.length; i++) {
            shortestRowLength = Math.min(grid[i].length, shortestRowLength);
        }
        return shortestRowLength;
    }

    public void restart() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = WhoseTurn.None;
            }
        }
    }

    public String getText(int row, int col) {
        return grid[row][col].toString();
    }

    enum WhoseTurn {
        None, X, O;

        @Override
        public String toString() {
            if (this == WhoseTurn.X)
                return "X";
            if (this == WhoseTurn.O)
                return "O";
            return "";
        }
    }

    /**
     * Reverses the game object's serialization as a String
     * back to a ThirteenStones game object
     *
     * @param json The serialized String of the game object
     * @return The game object
     */
    public static TicTacToeModel getGameFromJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, TicTacToeModel.class);
    }

    /**
     * Serializes the game object to a JSON-formatted String
     *
     * @param obj Game Object to serialize
     * @return JSON-formatted String
     */
    public static String getJSONFromGame(TicTacToeModel obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public String getJSONFromCurrentGame() {
        return getJSONFromGame(this);
    }
}
