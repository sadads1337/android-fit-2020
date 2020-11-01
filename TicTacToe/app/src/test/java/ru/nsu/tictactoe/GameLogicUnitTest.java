package ru.nsu.tictactoe;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameLogicUnitTest {

    Game game = new Game();
    int nCells = 3;

    @Test
    public void firstColumn() {
        game.setGameState(new int[] { 1,  0,  1,
                                      1, -1, -1,
                                      1,  0,  0 });
        assertTrue(game.foundMatches(0, nCells, 1, nCells * nCells - nCells, nCells));
    }

    @Test
    public void secondColumn() {
        game.setGameState(new int[] { -1,  0,  1,
                                       1,  0, -1,
                                       1,  0,  0 });
        assertTrue(game.foundMatches(0, nCells, 1, nCells * nCells - nCells, nCells));
    }

    @Test
    public void thirdColumn() {
        game.setGameState(new int[] { -1,   0,  1,
                                       1,  -1,  1,
                                       1,   0,  1 });
        assertTrue(game.foundMatches(0, nCells, 1, nCells * nCells - nCells, nCells));
    }

    @Test
    public void firstRow() {
        game.setGameState(new int[] { 0,  0,  0,
                                      1, -1,  1,
                                      1,  0,  1 });
        assertTrue(game.foundMatches(0, nCells * nCells, nCells, nCells, 1));
    }

    @Test
    public void secondRow() {
        game.setGameState(new int[] { 0, 1,  0,
                                      1, 1,  1,
                                      1, 0,  1 });
        assertTrue(game.foundMatches(0, nCells * nCells, nCells, nCells, 1));
    }

    @Test
    public void thirdRow() {
        game.setGameState(new int[] { 0, 1,  0,
                                      1, 0,  1,
                                      1, 1,  1 });
        assertTrue(game.foundMatches(0, nCells * nCells, nCells, nCells, 1));
    }

    @Test
    public void crossLeft() {
        game.setGameState(new int[] {  0, 1,  0,
                                       1, 0,  1,
                                      -1, 1,  0 });
        assertTrue(game.foundMatches(0, nCells, nCells, nCells * nCells - 1, nCells + 1));
    }

    @Test
    public void crossRight() {
        game.setGameState(new int[] {  0, 1,  1,
                                       0, 1,  1,
                                       1, 1,  0 });
        assertTrue(game.foundMatches(nCells - 1, nCells, 1, nCells * nCells - nCells - 1, nCells - 1));
    }
}