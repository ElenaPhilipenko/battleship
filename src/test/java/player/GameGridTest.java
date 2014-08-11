package player;

import com.github.elkurilina.seabattle.Cell;
import com.github.elkurilina.seabattle.CellState;
import com.github.elkurilina.seabattle.MaskedGameGrid;
import com.github.elkurilina.seabattle.WriteGameGrid;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

/**
 * @author Elena Kurilina
 */

public class GameGridTest {
    private static final Logger LOG = Logger.getLogger(GameGridTest.class);

    @DataProvider(name = "shipSizes")
    public static Iterator<Object[]> getShipSizes() {
        Collection<Object[]> ships = new HashSet<>();
        ships.add(new Object[]{4});
        ships.add(new Object[]{3});
        ships.add(new Object[]{2});
        ships.add(new Object[]{1});
        return ships.iterator();
    }

    @Test
    public void testFindEmptyCells() {
        final MaskedGameGrid gameGrid = new WriteGameGrid(new ArrayList<>(), 10);
        Assert.assertEquals(gameGrid.findNotShotPoints().size(), 100);
    }

    @Test
    public void testCreateGameGridWithShips() {
        final WriteGameGrid gameGrid = getWriteGameGridWitShip(4);
        Assert.assertEquals(gameGrid.getCellState(new Cell(0, 0)), CellState.SHIP);
    }

    @Test
    public void testFindEmptyCellsWithOneShip() {
        final MaskedGameGrid gameGrid = getWriteGameGridWitShip(1);
        Assert.assertEquals(gameGrid.findNotShotPoints().size(), 100);
    }

    @Test
    public void testFindEmptyCellsWithShipsAndShots() {
        final WriteGameGrid gameGrid = getWriteGameGridWitShip(3);
        gameGrid.applyShot(new Cell(6, 9));
        gameGrid.applyShot(new Cell(0, 1));
        gameGrid.applyShot(new Cell(0, 2));
        Assert.assertEquals(gameGrid.findNotShotPoints().size(), 97);
    }

    @Test
    public void testFindEmptyCellsWithhitShip() {
        final WriteGameGrid gameGrid = getWriteGameGridWitShip(1);
        gameGrid.applyShot(new Cell(0, 9));
        gameGrid.applyShot(new Cell(0, 1));
        gameGrid.applyShot(new Cell(0, 2));
        gameGrid.applyShot(new Cell(0, 0));
        Assert.assertEquals(gameGrid.findNotShotPoints().size(), 96);
    }

    @Test(dataProvider = "shipSizes")
    public void testDetectAfloatShip(Integer size) {
        final WriteGameGrid gameGrid = getWriteGameGridWitShip(size);
        Assert.assertTrue(gameGrid.hasAfloatShip());
    }

    @Test
    public void testHasNoAfloatShip() {
        final WriteGameGrid gameGrid = getWriteGameGridWitShip(1);
        gameGrid.applyShot(new Cell(0, 0));
        Assert.assertFalse(gameGrid.hasAfloatShip());
    }

    @Test
    public void testMarkShipAsDead() {
        final WriteGameGrid gameGrid = getWriteGameGridWitShip(1);
        gameGrid.applyShot(new Cell(0, 0));
        Assert.assertEquals(gameGrid.findDeadShips().size(), 1);
    }

    @Test
    public void testNotMarkShipAsDead() {
        final WriteGameGrid gameGrid = getWriteGameGridWitShip(4);
        Assert.assertEquals(gameGrid.findDeadShips().size(), 0);
    }

    @Test
    public void testNotMarkHitShipAsDead() {
        final WriteGameGrid gameGrid = getWriteGameGridWitShip(4);
        gameGrid.applyShot(new Cell(0, 0));
        Assert.assertEquals(gameGrid.findDeadShips().size(), 0);
    }


    @Test(dataProvider = "shipSizes")
    public void testFindDeadShip(Integer shipSize) {
        LOG.info("test for ship size: " + shipSize);
        final WriteGameGrid gameGrid = getWriteGameGridWitShip(shipSize);
        int size = shipSize;
        while (size > 0) {
            gameGrid.applyShot(new Cell(size - 1, 0));
            size--;
        }

        Assert.assertEquals(gameGrid.findDeadShips().size(), shipSize.intValue());
    }

    private WriteGameGrid getWriteGameGridWitShip(int shipSize) {
        List<Cell> ships = new ArrayList<>();
        while (shipSize > 0) {
            ships.add(new Cell(shipSize - 1, 0));
            shipSize--;
        }
        return new WriteGameGrid(ships, 10);
    }

}
