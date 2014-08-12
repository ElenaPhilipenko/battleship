package player;

import com.github.elkurilina.seabattle.Game;
import com.github.elkurilina.seabattle.GridSquare;
import com.github.elkurilina.seabattle.SquareState;
import com.github.elkurilina.seabattle.WriteGameGrid;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Elena Kurilina
 */

public class GameGridTest extends AbstractTest {
    private WriteGameGrid gameGrid;

    @BeforeMethod
    public void creatGrid() {
        gameGrid = createGrid();
    }

    //          0 1 2 3 4 5 6 7 8 9
//            0 s s - - - - - s - - 0
//            1 - - - - - s - s - - 1
//            2 - - - - - - - s - - 2
//            3 - - - - - - - - - - 3
//            4 - s s s s - - - - - 4
//            5 - - - - - - - s - - 5
//            6 - - s s s - - - - s 6
//            7 - - - - - - - - - - 7
//            8 - s - - - - - - - s 8
//            9 - s - s s - - - - - 9
//              0 1 2 3 4 5 6 7 8 9

    @Test
    public void testFindEmptySquares() {
        Assert.assertEquals(gameGrid.findNotShotSquares().size(), 100);
    }

    @Test
    public void testFindEmptySquaresAfterShots() {
        gameGrid.applyShot(new GridSquare(6, 9));
        gameGrid.applyShot(new GridSquare(0, 1));
        gameGrid.applyShot(new GridSquare(0, 2));

        Assert.assertEquals(gameGrid.findNotShotSquares().size(), 97);
    }

    @Test
    public void testCreateGameGridWithShips() {
        Assert.assertEquals(gameGrid.getSquareState(new GridSquare(0, 0)), SquareState.SHIP);
    }

    @Test
    public void testDetectAfloatShip() {
        Assert.assertTrue(gameGrid.hasAfloatShip());
    }

    @Test
    public void testHasNoAfloatShip() {
        fleet.forEach(ship -> ship.forEach(gameGrid::applyShot));

        Assert.assertFalse(gameGrid.hasAfloatShip());
    }

    @Test
    public void testMarkShipAsDead() {
        gameGrid.applyShot(new GridSquare(0, 0));
        gameGrid.applyShot(new GridSquare(0, 1));

        Assert.assertEquals(gameGrid.findDeadShips().size(), 2);
    }

    @Test
    public void testNotMarkShipAsDead() {
        Assert.assertEquals(gameGrid.findDeadShips().size(), 0);
    }

    @Test
    public void testNotMarkHitShipAsDead() {
        gameGrid.applyShot(new GridSquare(0, 0));

        Assert.assertEquals(gameGrid.findDeadShips().size(), 0);
    }


    @Test
    public void testFindAllDeadShip() {
        fleet.forEach(ship -> ship.forEach(gameGrid::applyShot));
        int amount = Game.SHIP_SIZES.stream().reduce(0, (a, b) -> a + b);

        Assert.assertEquals(gameGrid.findDeadShips().size(), amount);
    }


}
