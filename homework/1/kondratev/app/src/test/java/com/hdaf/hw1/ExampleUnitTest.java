package com.hdaf.hw1;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    Game g = Game.getInstance();

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void test1(){
        String[][] field = {
                {"X","O",""},
                {"","X","O"},
                {"","","X"}};
        assertEquals(true, g.checkForWin(field));
    }
    @Test
    public void test2(){
        String[][] field = {
                {"X","O",""},
                {"X","","O"},
                {"X","",""}};
        assertEquals(true, g.checkForWin(field));
    }
    @Test
    public void test3(){
        String[][] field = {
                {"","O","X"},
                {"","X","O"},
                {"X","",""}};
        assertEquals(true, g.checkForWin(field));
    }
    @Test
    public void test4(){
        String[][] field = {
                {"","","O"},
                {"","O",""},
                {"X","X","X"}};
        assertEquals(true, g.checkForWin(field));
    }
    @Test
    public void test5(){
        String[][] field = {
                {"","X","O"},
                {"","X",""},
                {"O","X",""}};
        assertEquals(true, g.checkForWin(field));
    }

    @Test
    public void test6(){
        String[][] field = {
                {"X","X",""},
                {"O","O","O"},
                {"","X",""}};
        assertEquals(true, g.checkForWin(field));
    }

    @Test
    public void test7(){
        String[][] field = {
                {"X","X","O"},
                {"","O",""},
                {"O","","X"}};
        assertEquals(true, g.checkForWin(field));
    }

    @Test
    public void test8(){
        String[][] field = {
                {"O","O","O"},
                {"X","X",""},
                {"","","X"}};
        assertEquals(true, g.checkForWin(field));
    }
    @Test
    public void test9(){
        String[][] field = {
                {"","O","X"},
                {"X","O",""},
                {"X","O",""}};
        assertEquals(true, g.checkForWin(field));
    }
    @Test
    public void test10(){
        String[][] field = {
                {"X","X","O"},
                {"X","O",""},
                {"O","",""}};
        assertEquals(true, g.checkForWin(field));
    }
    @Test
    public void test11(){
        String[][] field = {
                {"X","O",""},
                {"","","O"},
                {"","","X"}};
        assertEquals(false, g.checkForWin(field));
    }
    @Test
    public void test12(){
        String[][] field = {
                {"X","O",""},
                {"","O","O"},
                {"","X","X"}};
        assertEquals(false, g.checkForWin(field));
    }
    @Test
    public void test13(){
        String[][] field = {
                {"X","X","O"},
                {"O","O","X"},
                {"X","X","O"}};
        assertEquals(false, g.checkForWin(field));
    }
    @Test
    public void test14(){
        String[][] field = {
                {"","",""},
                {"","",""},
                {"","",""}};
        assertEquals(false, g.checkForWin(field));
    }
}