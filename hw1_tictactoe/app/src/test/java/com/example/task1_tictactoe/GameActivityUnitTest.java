
package com.example.task1_tictactoe;

import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;


public class GameActivityUnitTest {
    GameActivity gameActivity;

    @Before
    public void init() {
        gameActivity = spy(GameActivity.class);
    }

    @Test
    public void playerTap() {
        doNothing().when(gameActivity).redrawUI((View) any());

        ImageView imageView = mock(ImageView.class);
        when(imageView.getTag()).thenReturn(0);

        ViewPropertyAnimator animator = mock(ViewPropertyAnimator.class);
        doReturn(animator).when(imageView).animate();
        doReturn(animator).when(animator).translationYBy(anyFloat());

        TextView textView = mock(TextView.class);
        View imView = mock(ImageView.class);
        doReturn(imView).when(gameActivity).findViewById(AdditionalMatchers.not(eq(R.id.status)));
        doReturn(textView).when(gameActivity).findViewById(eq(R.id.status));

        assertArrayEquals(gameActivity.gameState, new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2});

        gameActivity.playerTap(imageView);
        assertArrayEquals(gameActivity.gameState, new int[]{0, 2, 2, 2, 2, 2, 2, 2, 2});
        gameActivity.playerTap(imageView);
        assertArrayEquals(gameActivity.gameState, new int[]{0, 2, 2, 2, 2, 2, 2, 2, 2});

        // x o x
        // o x o - i < 6
        // x
        for (int i = 1; i < 6; i++) {
            when(imageView.getTag()).thenReturn(i);
            gameActivity.playerTap(imageView);
            assertEquals(i % 2, gameActivity.gameState[i]);
            assertEquals((i + 1) % 2, gameActivity.activePlayer);
        }

        when(imageView.getTag()).thenReturn(6);
        gameActivity.playerTap(imageView);
        assertEquals(0, gameActivity.gameState[6]);
        assertFalse(gameActivity.gameActive);
        assertFalse(gameActivity.isDraw);

        doNothing().when(gameActivity).replaceFieldFragment();
        gameActivity.playerTap(imageView);
        assertArrayEquals(gameActivity.gameState, new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2});

        // 0 1
        // x o . 0
        // x o . 1
        // x . . 2
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                int k = i * 3 + j;
                if (i + j == 3) break;
                when(imageView.getTag()).thenReturn(k);
                gameActivity.playerTap(imageView);
                assertEquals(j, gameActivity.gameState[k]);
                assertEquals((j + 1) % 2, gameActivity.activePlayer);
            }
        }
        assertArrayEquals(new int[]{0, 1, 2, 0, 1, 2, 0, 2, 2}, gameActivity.gameState);
        gameActivity.playerTap(imageView);
        assertArrayEquals(new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2}, gameActivity.gameState);
        // 0 1 2

        // . x o 0
        // . x o 1
        // . x . 2
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j < 3; j++) {
                int k = i * 3 + j;
                if (i + j == 4) break;
                when(imageView.getTag()).thenReturn(k);
                gameActivity.playerTap(imageView);
                assertEquals(j - 1, gameActivity.gameState[k]);
                assertEquals(j % 2, gameActivity.activePlayer);
            }
        }
        assertArrayEquals(new int[]{2, 0, 1, 2, 0, 1, 2, 0, 2}, gameActivity.gameState);
        gameActivity.playerTap(imageView);
        assertArrayEquals(new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2}, gameActivity.gameState);

        // 0 1 2
        // x x x 0
        // o o . 1
        // . . . 2
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 2; i++) {
                int k = j + i * 3;
                if (i + j == 3) break;
                when(imageView.getTag()).thenReturn(k);
                gameActivity.playerTap(imageView);
                assertEquals(i, gameActivity.gameState[k]);
                assertEquals((i + 1) % 2, gameActivity.activePlayer);
            }
        }
        assertArrayEquals(new int[]{0, 0, 0, 1, 1, 2, 2, 2, 2}, gameActivity.gameState);
        gameActivity.playerTap(imageView);
        assertArrayEquals(new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2}, gameActivity.gameState);

    }

}
