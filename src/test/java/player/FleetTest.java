package player;

import com.github.elkurilina.seabattle.Cell;
import com.github.elkurilina.seabattle.MaskedGameGrid;
import com.github.elkurilina.seabattle.WriteGameGrid;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Elena Kurilina
 */

public class FleetTest {

    @Test
    public void testFindEmptyCells() {
        final MaskedGameGrid gameGrid = new WriteGameGrid(new ArrayList<>(), 10);
        Assert.assertEquals(gameGrid.findNotShotPoints().size(), 100);
    }

    @Test
    public void testFindEmptyCellsWithOneShip() {
        List<Cell> ships = new ArrayList<>();
        ships.add(new Cell(0, 0));
        final MaskedGameGrid gameGrid = new WriteGameGrid(ships, 10);
        Assert.assertEquals(gameGrid.findNotShotPoints().size(), 100);
    }

    @Test
    public void testFindEmptyCellsWithOneShipAndShots() {
        List<Cell> ships = new ArrayList<>();
        ships.add(new Cell(0, 0));
        final WriteGameGrid gameGrid = new WriteGameGrid(ships, 10);
        gameGrid.applyShot(new Cell(0, 9));
        gameGrid.applyShot(new Cell(0, 1));
        gameGrid.applyShot(new Cell(0, 2));
        Assert.assertEquals(gameGrid.findNotShotPoints().size(), 97);
    }

    @Test
    public void testFindEmptyCellsWithhitShip() {
        List<Cell> ships = new ArrayList<>();
        ships.add(new Cell(0, 0));
        final WriteGameGrid gameGrid = new WriteGameGrid(ships, 10);
        gameGrid.applyShot(new Cell(0, 9));
        gameGrid.applyShot(new Cell(0, 1));
        gameGrid.applyShot(new Cell(0, 2));
        gameGrid.applyShot(new Cell(0, 0));
        Assert.assertEquals(gameGrid.findNotShotPoints().size(), 96);
    }


    @Test
    public void testDetectAfloatShip() {
        List<Cell> ships = new ArrayList<>();
        ships.add(new Cell(0, 0));
        final WriteGameGrid gameGrid = new WriteGameGrid(ships, 10);
        Assert.assertTrue(gameGrid.hasAfloatShip());
    }

    @Test
    public void testDetectAfloat3PartShip() {
        List<Cell> ships = new ArrayList<>();
        ships.addAll((Arrays.asList(new Cell(0, 0), new Cell(0, 1), new Cell(0, 2))));
        final WriteGameGrid gameGrid = new WriteGameGrid(ships, 10);
        gameGrid.applyShot(new Cell(0, 1));
        gameGrid.applyShot(new Cell(0, 2));
        Assert.assertTrue(gameGrid.hasAfloatShip());
    }

    @Test
    public void testDetectSunkShip() {
        List<Cell> ships = new ArrayList<>();
        ships.add(new Cell(0, 0));
        final WriteGameGrid gameGrid = new WriteGameGrid(ships, 10);
        gameGrid.applyShot(new Cell(0, 0));
        Assert.assertFalse(gameGrid.hasAfloatShip());
    }


}
