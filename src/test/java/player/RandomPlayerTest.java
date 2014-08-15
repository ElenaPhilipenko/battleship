package player;

import com.github.elkurilina.seabattle.*;
import com.github.elkurilina.seabattle.player.RandomPlayer;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;

import static com.github.elkurilina.seabattle.Game.GRID_SIZE;
import static com.github.elkurilina.seabattle.Game.SHIP_SIZES;

/**
 * @author Elena Kurilina
 */
public class RandomPlayerTest extends AbstractTest {
    private RandomPlayer player;
    private final Logger LOG = Logger.getLogger(RandomPlayer.class);


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

    @BeforeMethod
    public void createPlayer() {
        player = new RandomPlayer();
    }

    @Test
    public void testPlayGame() {
        Game game = new Game();
        final Player p2 = new RandomPlayer();
        final Player p1 = new RandomPlayer();

        final WriteGameGrid p1GameGrid = WriteGameGrid.createGameGidWithShips(p1.getShips(), GRID_SIZE);
        final WriteGameGrid p2GameGrid = WriteGameGrid.createGameGidWithShips(p2.getShips(), GRID_SIZE);
        p1.initGrids(p1GameGrid, p2GameGrid.maskedGrid);
        p2.initGrids(p2GameGrid, p1GameGrid.maskedGrid);
        game.playGame(p1, p2);
    }

    @Test
    public void testSizeOfCreatesShips() {
        final Collection<Collection<GridSquare>> fleet = player.getShips();

        int sum = SHIP_SIZES.size();
        Assert.assertEquals(fleet.size(), sum);
    }

    @Test
    public void testCreatesShipsAreValid() {
        final Collection<Collection<GridSquare>> fleet = player.getShips();

        Assert.assertTrue(ShipsValidator.isShipLocationsValid(fleet));
    }

    @Test
    public void testMakeRightShotAfterOneHit() {
        final WriteGameGrid gameGrid = createGrid();
        gameGrid.applyShot(new GridSquare(0, 0));
        player.initGrids(null, gameGrid);

        GridSquare gridSquare = player.makeShot();

        Assert.assertTrue(gridSquare.equals(new GridSquare(1, 0)) || gridSquare.equals(new GridSquare(0, 1)));
    }

    @Test
    public void testShotNotIncludeAlreadyShotCell() {
        final WriteGameGrid gameGrid = createGrid();
        gameGrid.applyShot(new GridSquare(9, 3));
        gameGrid.applyShot(new GridSquare(9, 2));
        gameGrid.applyShot(new GridSquare(8, 3));
        player.initGrids(null, gameGrid);

        GridSquare gridSquare = player.makeShot();

        Assert.assertTrue(gridSquare.equals(new GridSquare(9, 4)));
    }

    @Test
    public void testMakeRightShotAfter2Hit() {
        final WriteGameGrid gameGrid = createGrid();
        //convert(new GridSquare(4, 1), new GridSquare(4, 2), new GridSquare(4, 3), new GridSquare(4, 4))
        gameGrid.applyShot(new GridSquare(4, 3));
        gameGrid.applyShot(new GridSquare(4, 2));
        player.initGrids(null, gameGrid);

        GridSquare gridSquare = player.makeShot();
        LOG.info("GridSquare was selected: " + gridSquare);
        Assert.assertTrue(gridSquare.equals(new GridSquare(4, 1)) || gridSquare.equals(new GridSquare(4, 4)));
    }

    @Test
    public void testMakeRightShotAfter3Hit() {
        final WriteGameGrid gameGrid = createGrid();
        gameGrid.applyShot(new GridSquare(4, 3));
        gameGrid.applyShot(new GridSquare(4, 2));
        gameGrid.applyShot(new GridSquare(4, 4));
        player.initGrids(null, gameGrid);

        GridSquare gridSquare = player.makeShot();
        LOG.info("GridSquare was selected: " + gridSquare);
        Assert.assertTrue(gridSquare.equals(new GridSquare(4, 1)) || gridSquare.equals(new GridSquare(4, 5)));

    }

}
