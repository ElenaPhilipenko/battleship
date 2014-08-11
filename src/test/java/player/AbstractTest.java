package player;

import com.github.elkurilina.seabattle.Cell;
import com.github.elkurilina.seabattle.WriteGameGrid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Elena Kurilina
 */
public class AbstractTest {
    public static final Collection<Collection<Cell>> fleet = new ArrayList<>();

    protected WriteGameGrid createGrid() {
        return WriteGameGrid.createGameGidWithShips(fleet, 10);
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

    static {
        fleet.add(convert(new Cell(1, 5)));
        fleet.add(convert(new Cell(5, 7)));
        fleet.add(convert(new Cell(6, 9)));
        fleet.add(convert(new Cell(8, 9)));
        fleet.add(convert(new Cell(0, 0), new Cell(0, 1)));
        fleet.add(convert(new Cell(9, 3), new Cell(9, 4)));
        fleet.add(convert(new Cell(9, 1), new Cell(8, 1)));
        fleet.add(convert(new Cell(0, 7), new Cell(1, 7), new Cell(2, 7)));
        fleet.add(convert(new Cell(6, 2), new Cell(6, 3), new Cell(6, 4)));
        fleet.add(convert(new Cell(4, 1), new Cell(4, 2), new Cell(4, 3), new Cell(4, 4)));
    }


    protected static Collection<Cell> convert(Cell... c) {
        final Collection<Cell> cells = new ArrayList<>();
        Collections.addAll(cells, c);
        return cells;
    }
}
