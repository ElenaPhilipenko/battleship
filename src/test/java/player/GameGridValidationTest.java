package player;

import com.github.elkurilina.seabattle.Cell;
import com.github.elkurilina.seabattle.GameGridValidator;
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
        Assert.assertTrue(GameGridValidator.isShipLocationsValid(fleet));
    }

    @Test
    public void testDetectWrongAmountOfShips() {
        final Collection<Collection<Cell>> invalid = new ArrayList<>();
        invalid.add(convert(new Cell(0, 0), new Cell(0, 1)));
        Assert.assertFalse(GameGridValidator.isShipLocationsValid(invalid));
    }

    @Test
    public void testDetectWrongShipSizes() {
        final Collection<Collection<Cell>> invalid = new ArrayList<>();
        invalid.add(convert(new Cell(0, 0)));
        invalid.add(convert(new Cell(0, 2)));
        invalid.add(convert(new Cell(0, 4)));
        invalid.add(convert(new Cell(0, 6)));
        invalid.add(convert(new Cell(0, 8)));
        invalid.add(convert(new Cell(2, 8)));
        invalid.add(convert(new Cell(2, 6)));
        invalid.add(convert(new Cell(2, 4)));
        invalid.add(convert(new Cell(2, 2)));
        invalid.add(convert(new Cell(2, 0)));
        Assert.assertFalse(GameGridValidator.isShipLocationsValid(invalid));
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
        final Collection<Collection<Cell>> fleet = new ArrayList<>();

        fleet.add(convert(new Cell(1, 5)));
        fleet.add(convert(new Cell(3, 6)));
        fleet.add(convert(new Cell(6, 9)));
        fleet.add(convert(new Cell(8, 9)));
        fleet.add(convert(new Cell(0, 0), new Cell(0, 1)));
        fleet.add(convert(new Cell(9, 3), new Cell(9, 4)));
        fleet.add(convert(new Cell(9, 1), new Cell(8, 1)));
        fleet.add(convert(new Cell(0, 7), new Cell(1, 7), new Cell(2, 7)));
        fleet.add(convert(new Cell(6, 2), new Cell(6, 3), new Cell(6, 4)));
        fleet.add(convert(new Cell(4, 1), new Cell(4, 2), new Cell(4, 3), new Cell(4, 4)));

        Assert.assertFalse(GameGridValidator.isShipLocationsValid(fleet));
    }
}
