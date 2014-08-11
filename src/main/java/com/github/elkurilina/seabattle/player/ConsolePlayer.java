package com.github.elkurilina.seabattle.player;

import com.github.elkurilina.seabattle.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Elena Kurilina
 */
public class ConsolePlayer implements Player {

    private static final Map<CellState, String> printValues = new HashMap<>();
    static {
        printValues.put(CellState.MISSED_SHOT, ".");
        printValues.put(CellState.EMPTY, "-");
        printValues.put(CellState.HIDDEN, "?");
        printValues.put(CellState.HIT_SHIP, "!");
        printValues.put(CellState.DEAD_SHIP, "x");
        printValues.put(CellState.SHIP, "s");
    }

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final String name;
    private final int size;
    private WriteGameGrid myGreed;

    public ConsolePlayer(int fieldSize) throws IOException {
        this.size = fieldSize;
        System.out.println("Please, enter your name: ");
        this.name = br.readLine();
    }

    @Override
    public Cell makeShot(GameGrid grid) {
        printGrid(grid);
        System.out.println("You game values: ");
        printGrid(myGreed);
        System.out.println("Please enter coordinates of you shot in format: x y");
        try {
            return parseCell(size);
        } catch (Exception e) {
            System.out.print("Can not parse move.");
            return makeShot(grid);
        }
    }

    @Override
    public Collection<Collection<Cell>> getShips(Iterable<Integer> shipSizes) {
        final Collection<Collection<Cell>> shipCells;
        System.out.println("Do you want create random game values? (y/n)");
        try {
            if (br.readLine().contains("n")) {
                shipCells = createSips(shipSizes, size);
            } else {
                shipCells = new RandomShipLocator().createShips(shipSizes, size);
            }
        } catch (IOException e) {
            System.out.println("Try again.");
            return getShips(shipSizes);
        }
        return shipCells;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setGreeds(WriteGameGrid myGreed) {
        this.myGreed = myGreed;
    }

    private Collection<Collection<Cell>> createSips(Iterable<Integer> shipSizes, int size) {
        final Collection<Collection<Cell>> shipCells = new HashSet<>();
        for (Integer shipSize : shipSizes) {
            final Set<Cell> shipLocation = new HashSet<>();
            System.out.println("Please, input location points for ship with size: " + shipSize);
            for (int i = 0; i < shipSize; i++) {
                addParsedCell(shipLocation, i, size);
            }
            shipCells.add(shipLocation);

        }
        return shipCells;
    }


    private Cell parseCell(int max) throws Exception {
        System.out.println("Please enter in format: x y ");
        final String shot = br.readLine().replace(" ", "");
        final Integer x = Integer.parseInt(String.valueOf(shot.charAt(0)));
        final Integer y = Integer.parseInt(String.valueOf(shot.charAt(1)));
        if (x < 0 || x > max || y < 0 || y > max) {
            throw new IllegalArgumentException("Wring point");
        }
        return new Cell(x, y);
    }

    private void addParsedCell(Collection<Cell> shipLocation, int i, int size) {
        System.out.println("point " + (i + 1));
        try {
            shipLocation.add(parseCell(size));
        } catch (Exception e) {
            addParsedCell(shipLocation, i, size);
        }
    }

    public void printGrid(GameGrid grid) {
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < size; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < size; j++) {
                System.out.print(printValues.get(grid.getCellState(new Cell(i, j))));
                System.out.print(" ");
            }
            System.out.print(i + "\n");
        }
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
    }

}
