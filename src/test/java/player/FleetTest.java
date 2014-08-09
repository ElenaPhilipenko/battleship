package player;

import com.github.elkurilina.seabattle.GameGreed;
import com.github.elkurilina.seabattle.Ship;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Elena Kurilina
 */

public class FleetTest {

    @Test
    public void testFindEmptyCells() {
        final GameGreed gameGreed = new GameGreed(new ArrayList<>(), 10);
        Assert.assertEquals(gameGreed.findNotShotPoints().size(), 100);

    }

    @Test
    public void testFindEmptyCellsWithOneShip() {
        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship(Arrays.asList(new Point(0, 0))));
        final GameGreed gameGreed = new GameGreed(ships, 10);
        Assert.assertEquals(gameGreed.findNotShotPoints().size(), 100);
    }

    @Test
    public void testFindEmptyCellsWithOneShipAndShots() {
        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship(Arrays.asList(new Point(0, 0))));
        final GameGreed gameGreed = new GameGreed(ships, 10);
        gameGreed.applyShot(new Point(0, 9));
        gameGreed.applyShot(new Point(0, 1));
        gameGreed.applyShot(new Point(0, 2));
        Assert.assertEquals(gameGreed.findNotShotPoints().size(), 97);
    }

    @Test
    public void testFindEmptyCellsWithhitShip() {
        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship(Arrays.asList(new Point(0, 0))));
        final GameGreed gameGreed = new GameGreed(ships, 10);
        gameGreed.applyShot(new Point(0, 9));
        gameGreed.applyShot(new Point(0, 1));
        gameGreed.applyShot(new Point(0, 2));
        gameGreed.applyShot(new Point(0, 0));
        Assert.assertEquals(gameGreed.findNotShotPoints().size(), 96);
    }


    @Test
    public void testDetectAfloatShip() {
        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship(Arrays.asList(new Point(0, 0))));
        final GameGreed gameGreed = new GameGreed(ships, 10);
        Assert.assertTrue(gameGreed.hasAfloatShip());
    }

    @Test
    public void testDetectAfloat3PartShip() {
        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship(Arrays.asList(new Point(0, 0), new Point(0, 1), new Point(0, 2))));
        final GameGreed gameGreed = new GameGreed(ships, 10);
        gameGreed.applyShot(new Point(0, 1));
        gameGreed.applyShot(new Point(0, 2));
        Assert.assertTrue(gameGreed.hasAfloatShip());
    }

    @Test
    public void testDetectSunkShip() {
        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship(Arrays.asList(new Point(0, 0))));
        final GameGreed gameGreed = new GameGreed(ships, 10);
        gameGreed.applyShot(new Point(0, 0));
        Assert.assertFalse(gameGreed.hasAfloatShip());
    }


}
