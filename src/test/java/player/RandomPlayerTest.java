package player;

import com.github.elkurilina.seabattle.Cell;
import com.github.elkurilina.seabattle.Game;
import com.github.elkurilina.seabattle.Player;
import com.github.elkurilina.seabattle.WriteGameGrid;
import com.github.elkurilina.seabattle.player.RandomPlayer;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;

import static com.github.elkurilina.seabattle.Game.FIELD_SIZE;
import static com.github.elkurilina.seabattle.Game.SHIP_SIZES;

/**
 * @author Elena Kurilina
 */
public class RandomPlayerTest {
    private RandomPlayer player;
    private final Logger LOG = Logger.getLogger(RandomPlayer.class);

    @BeforeMethod
    public void createPlayer() {
        player = new RandomPlayer(10);
    }

    @Test
    public void testPlayGame() {
        Game game = new Game();
        final Player p2 = new RandomPlayer(FIELD_SIZE);
        final Player p1 = new RandomPlayer(FIELD_SIZE);

        final WriteGameGrid p1GameGrid = new WriteGameGrid(p1.getShips(SHIP_SIZES), FIELD_SIZE);
        final WriteGameGrid p2GameGrid = new WriteGameGrid(p2.getShips(SHIP_SIZES), FIELD_SIZE);
        game.playGame(p1, p2, p1GameGrid, p2GameGrid);
    }

    @Test
    public void testSizeOfCreatesShips() {
        final Collection<Cell> fleet = player.getShips(Game.SHIP_SIZES);

        int sum = SHIP_SIZES.stream().reduce(0, (a, b) -> a + b);
        Assert.assertEquals(fleet.size(), sum);
    }

    @Test
    public void testMakeRightShotAfterOneHit() {
        final WriteGameGrid gameGrid = new WriteGameGrid(Arrays.asList(new Cell(0, 0), new Cell(0, 1)), 10);
        gameGrid.applyShot(new Cell(0, 0));

        Cell cell = player.makeShot(gameGrid.maskedGrid);

        Assert.assertTrue(cell.equals(new Cell(1, 0)) || cell.equals(new Cell(0, 1)));
    }

    @Test
    public void testShotNotIncludeAlreadyShotCell() {
        final WriteGameGrid gameGrid = new WriteGameGrid(Arrays.asList(new Cell(3, 3), new Cell(3, 2)), 10);
        gameGrid.applyShot(new Cell(3, 3));
        gameGrid.applyShot(new Cell(3, 4));
        gameGrid.applyShot(new Cell(2, 3));
        gameGrid.applyShot(new Cell(4, 3));

        Cell cell = player.makeShot(gameGrid.maskedGrid);

        Assert.assertTrue(cell.equals(new Cell(3, 2)));
    }

    @Test
    public void testMakeRightShotAfter2Hit() {
        final WriteGameGrid gameGrid = new WriteGameGrid(Arrays.asList(new Cell(3, 3), new Cell(3, 2), new Cell(3, 1)), 10);
        gameGrid.applyShot(new Cell(3, 3));
        gameGrid.applyShot(new Cell(3, 2));

        Cell cell = player.makeShot(gameGrid.maskedGrid);
        LOG.info("Cell was selected: " + cell);
        Assert.assertTrue(cell.equals(new Cell(3, 1)) || cell.equals(new Cell(3, 4)));
    }

    @Test
    public void testMakeRightShotAfter3Hit() {
        final WriteGameGrid gameGrid = new WriteGameGrid(Arrays.asList(new Cell(4, 3), new Cell(2, 3), new Cell(1, 3), new Cell(3, 3)), 10);
        gameGrid.applyShot(new Cell(3, 3));
        gameGrid.applyShot(new Cell(2, 3));
        gameGrid.applyShot(new Cell(1, 3));

        Cell cell = player.makeShot(gameGrid.maskedGrid);

        Assert.assertTrue(cell.equals(new Cell(0, 3)) || cell.equals(new Cell(4, 3)));
    }

}
