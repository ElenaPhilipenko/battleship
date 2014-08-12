package player;

import com.github.elkurilina.seabattle.GridSquare;
import com.github.elkurilina.seabattle.ShipsValidator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Elena Kurilina
 */
public class GameGridValidationTest extends AbstractTest {

    @Test
    public void testFleetValid() {
        Assert.assertTrue(ShipsValidator.isShipLocationsValid(fleet));
    }

    @Test
    public void testDetectWrongAmountOfShips() {
        final Collection<Collection<GridSquare>> invalid = new ArrayList<>();
        invalid.add(convert(new GridSquare(0, 0), new GridSquare(0, 1)));
        Assert.assertFalse(ShipsValidator.isShipLocationsValid(invalid));
    }

    @Test
    public void testDetectWrongShipSizes() {
        final Collection<Collection<GridSquare>> invalid = new ArrayList<>();
        invalid.add(convert(new GridSquare(0, 0)));
        invalid.add(convert(new GridSquare(0, 2)));
        invalid.add(convert(new GridSquare(0, 4)));
        invalid.add(convert(new GridSquare(0, 6)));
        invalid.add(convert(new GridSquare(0, 8)));
        invalid.add(convert(new GridSquare(2, 8)));
        invalid.add(convert(new GridSquare(2, 6)));
        invalid.add(convert(new GridSquare(2, 4)));
        invalid.add(convert(new GridSquare(2, 2)));
        invalid.add(convert(new GridSquare(2, 0)));
        Assert.assertFalse(ShipsValidator.isShipLocationsValid(invalid));
    }

    //          0 1 2 3 4 5 6 7 8 9
//            0 s s - - - - - s - - 0
//            1 - - - - - s - s - - 1
//            2 - - - - - - - s - - 2
//            3 - - - - - - s - - - 3 - invalid
//            4 - s s s s - - - - - 4
//            5 - - - - - - - - - - 5
//            6 - - s s s - - - - s 6
//            7 - - - - - - - - - - 7
//            8 - s - - - - - - - s 8
//            9 - s - s s - - - - - 9
//              0 1 2 3 4 5 6 7 8 9
    @Test
    public void testDetectWrongDistance() {
        final Collection<Collection<GridSquare>> fleet = new ArrayList<>();

        fleet.add(convert(new GridSquare(1, 5)));
        fleet.add(convert(new GridSquare(3, 6)));
        fleet.add(convert(new GridSquare(6, 9)));
        fleet.add(convert(new GridSquare(8, 9)));
        fleet.add(convert(new GridSquare(0, 0), new GridSquare(0, 1)));
        fleet.add(convert(new GridSquare(9, 3), new GridSquare(9, 4)));
        fleet.add(convert(new GridSquare(9, 1), new GridSquare(8, 1)));
        fleet.add(convert(new GridSquare(0, 7), new GridSquare(1, 7), new GridSquare(2, 7)));
        fleet.add(convert(new GridSquare(6, 2), new GridSquare(6, 3), new GridSquare(6, 4)));
        fleet.add(convert(new GridSquare(4, 1), new GridSquare(4, 2), new GridSquare(4, 3), new GridSquare(4, 4)));

        Assert.assertFalse(ShipsValidator.isShipLocationsValid(fleet));
    }

    @Test
    public void testShipWithGap(){
        final Collection<GridSquare> ship = convert(new GridSquare(0,3), new GridSquare(0,1), new GridSquare(0,4));

        Assert.assertFalse(ShipsValidator.isShipValid(ship));

    }

    @Test
    public void testShipWithBreak(){
        final Collection<GridSquare> ship = convert(new GridSquare(0,3), new GridSquare(1,3), new GridSquare(0,4));

        Assert.assertFalse(ShipsValidator.isShipValid(ship));

    }

    @Test
    public void testShipWithDiagonalGap(){
        final Collection<GridSquare> ship = convert(new GridSquare(0,3), new GridSquare(1,4), new GridSquare(0,4));

        Assert.assertFalse(ShipsValidator.isShipValid(ship));

    }

    @Test
    public void testValidShip(){
        final Collection<GridSquare> ship = convert(new GridSquare(0,3), new GridSquare(0,2), new GridSquare(0,4));

        Assert.assertTrue(ShipsValidator.isShipValid(ship));

    }
}
