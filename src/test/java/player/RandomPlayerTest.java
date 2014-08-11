package player;

import com.github.elkurilina.seabattle.*;
import com.github.elkurilina.seabattle.player.RandomPlayer;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;

import static com.github.elkurilina.seabattle.Game.FIELD_SIZE;
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
        player = new RandomPlayer(10);
    }

    @Test
    public void testPlayGame() {
        Game game = new Game();
        final Player p2 = new RandomPlayer(FIELD_SIZE);
        final Player p1 = new RandomPlayer(FIELD_SIZE);

        final WriteGameGrid p1GameGrid = WriteGameGrid.createGameGidWithShips(p1.getShips(SHIP_SIZES), FIELD_SIZE);
        final WriteGameGrid p2GameGrid = WriteGameGrid.createGameGidWithShips(p2.getShips(SHIP_SIZES), FIELD_SIZE);
        game.playGame(p1, p2, p1GameGrid, p2GameGrid);
    }

    @Test
    public void testSizeOfCreatesShips() {
        final Collection<Collection<Cell>> fleet = player.getShips(Game.SHIP_SIZES);

        int sum = SHIP_SIZES.size();
        Assert.assertEquals(fleet.size(), sum);
    }

    @Test
    public void testCreatesShipsAreValid() {
        final Collection<Collection<Cell>> fleet = player.getShips(Game.SHIP_SIZES);

        Assert.assertTrue(GameGridValidator.isShipLocationsValid(fleet));
    }

    @Test
    public void testMakeRightShotAfterOneHit() {
        final WriteGameGrid gameGrid = createGrid();
        gameGrid.applyShot(new Cell(0, 0));

        Cell cell = player.makeShot(gameGrid.maskedGrid);

        Assert.assertTrue(cell.equals(new Cell(1, 0)) || cell.equals(new Cell(0, 1)));
    }

    @Test
    public void testShotNotIncludeAlreadyShotCell() {
        final WriteGameGrid gameGrid = createGrid();
        gameGrid.applyShot(new Cell(9, 3));
        gameGrid.applyShot(new Cell(9, 2));
        gameGrid.applyShot(new Cell(8, 3));

        Cell cell = player.makeShot(gameGrid.maskedGrid);

        Assert.assertTrue(cell.equals(new Cell(9, 4)));
    }

    @Test
    public void testMakeRightShotAfter2Hit() {
        final WriteGameGrid gameGrid = createGrid(); //convert(new Cell(4, 1), new Cell(4, 2), new Cell(4, 3), new Cell(4, 4))
        gameGrid.applyShot(new Cell(4, 3));
        gameGrid.applyShot(new Cell(4, 2));

        Cell cell = player.makeShot(gameGrid.maskedGrid);
        LOG.info("Cell was selected: " + cell);
        Assert.assertTrue(cell.equals(new Cell(4, 1)) || cell.equals(new Cell(4, 4)));
    }

    @Test
    public void testMakeRightShotAfter3Hit() {
        final WriteGameGrid gameGrid = createGrid();
        gameGrid.applyShot(new Cell(4, 3));
        gameGrid.applyShot(new Cell(4, 2));
        gameGrid.applyShot(new Cell(4, 4));

        Cell cell = player.makeShot(gameGrid.maskedGrid);
        LOG.info("Cell was selected: " + cell);
        Assert.assertTrue(cell.equals(new Cell(4, 1)) || cell.equals(new Cell(4, 5)));

    }

}
