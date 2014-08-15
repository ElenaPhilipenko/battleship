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

    private static final Map<SquareState, String> printValues = new HashMap<>();
    static {
        printValues.put(SquareState.MISS, ".");
        printValues.put(SquareState.EMPTY, "-");
        printValues.put(SquareState.HIDDEN, "?");
        printValues.put(SquareState.HIT, "!");
        printValues.put(SquareState.DEAD_SHIP, "x");
        printValues.put(SquareState.SHIP, "s");
    }

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final String name;
    private WriteGameGrid myGreed;

    public ConsolePlayer() throws IOException {
        System.out.println("Please, enter your name: ");
        this.name = br.readLine();
    }

    @Override
    public GridSquare makeShot(GameGrid grid) {
        return printAndMakeShot(grid, true);
    }

    private GridSquare printAndMakeShot(GameGrid grid, boolean print) {
        if (print) printCurrentStates(grid);
        try {
            final GridSquare shot = parseSquare(Game.GRID_SIZE);
            if (grid.findNotShotSquares().contains(shot)) {
                return shot;
            } else {
                System.out.println("Shot is not valid.");
                return printAndMakeShot(grid, false);
            }
        } catch (Exception e) {
            System.out.println("Can not parse move.");
            return makeShot(grid);
        }
    }

    @Override
    public Collection<Collection<GridSquare>> getShips() {
        final Collection<Collection<GridSquare>> ship;
        System.out.println("Do you want create random game values? (y/n)");
        try {
            if (br.readLine().contains("n")) {
                ship = createShips();
            } else {
                ship = new RandomShipLocator().createShips(Game.SHIP_SIZES, Game.GRID_SIZE);
            }
        } catch (IOException e) {
            System.out.println("Try again.");
            return getShips();
        }
        return ship;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setGrid(WriteGameGrid myGreed) {
        this.myGreed = myGreed;
    }

    private void printCurrentStates(GameGrid grid) {
        printGrid(grid);
        System.out.println("You game grid: ");
        printGrid(myGreed);
    }

    private Collection<Collection<GridSquare>> createShips() {
        final Collection<Collection<GridSquare>> ships = new HashSet<>();
        for (Integer shipSize : Game.SHIP_SIZES) {
            createShip(ships, shipSize);
        }
        return ships;
    }

    private void createShip(Collection<Collection<GridSquare>> ships, Integer shipSize) {
        final Set<GridSquare> ship = new HashSet<>();
        System.out.println("Please, input location points for ship with size: " + shipSize);
        for (int i = 0; i < shipSize; i++) {
            System.out.println("point " + (i + 1));
            addParsedShipPart(ship, ships);
        }
        ships.add(ship);
        validateShip(ships, shipSize, ship);
    }

    private void validateShip(Collection<Collection<GridSquare>> ships, Integer shipSize, Set<GridSquare> ship) {
        if (!ShipsValidator.isDistanceBetweenShipsValid(ships) || !ShipsValidator.isShipValid(ship)) {
            System.out.println("Location of the last ship is wrong. Please recreate it.");
            ships.remove(ship);
            createShip(ships, shipSize);
        }
    }

    private GridSquare parseSquare(int max) throws Exception {
        System.out.println("Please enter your shot in format: x y ");
        final String shot = br.readLine().replace(" ", "");
        final Integer x = Integer.parseInt(String.valueOf(shot.charAt(0)));
        final Integer y = Integer.parseInt(String.valueOf(shot.charAt(1)));
        final GridSquare square = new GridSquare(x, y);
        if (!square.isInside(max)) {
            throw new IllegalArgumentException("Wrong square.");
        }
        return square;
    }

    private void addParsedShipPart(Collection<GridSquare> ship, Collection<Collection<GridSquare>> ships) {
        try {
            final GridSquare parsed = parseSquare(Game.GRID_SIZE);
            if (!ship.contains(parsed) && ships.stream().filter(s -> s.contains(parsed)).count() == 0) {
                ship.add(parsed);
            } else {
                System.out.println("This square is already used.");
                addParsedShipPart(ship, ships);
            }
        } catch (Exception e) {
            addParsedShipPart(ship, ships);
        }
    }

    public void printGrid(GameGrid grid) {
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < Game.GRID_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < Game.GRID_SIZE; j++) {
                System.out.print(printValues.get(grid.getSquareState(new GridSquare(i, j))));
                System.out.print(" ");
            }
            System.out.print(i + "\n");
        }
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
    }

}
