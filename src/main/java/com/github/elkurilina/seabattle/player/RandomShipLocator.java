package com.github.elkurilina.seabattle.player;

import com.github.elkurilina.seabattle.Cell;

import java.util.*;

/**
 * @author Elena Kurilina
 */
public class RandomShipLocator {
    private final Random random = new Random();

    public Collection<Collection<Cell>> createShips(Iterable<Integer> shipSizes, int gridSize) {
        final Collection<Collection<Cell>> shipLocations = new HashSet<>();
        final List<Cell> field = createPossiblePoints(gridSize);
        shipSizes.forEach(size -> shipLocations.add(createShip(field, size, gridSize)));
        return shipLocations;
    }

    private Collection<Cell> createShip(List<Cell> grid, int shipSize, int size) {
        final Collection<Cell> shipLocation = locateShip(grid, shipSize);
        grid.removeAll(shipLocation);
        shipLocation.forEach(cell -> grid.removeAll(cell.findNeighborsOnGridWithDiagonals(size)));
        return shipLocation;
    }

    private Collection<Cell> locateShip(List<Cell> grid, int shipSize) {
        final Cell head = grid.get(random.nextInt(grid.size()));
        final boolean horizontal = random.nextBoolean();
        Collection<Cell> shipLocation = new HashSet<>(shipSize);
        for (int i = 0; i < shipSize; i++) {
            final Cell option = head.translate(i, horizontal);
            if (grid.contains(option)) {
                shipLocation.add(option);
            } else {
                shipLocation = locateShip(grid, shipSize);
            }
        }
        return shipLocation;
    }

    private List<Cell> createPossiblePoints(int size) {
        final List<Cell> field = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                field.add(new Cell(x, y));
            }
        }
        return field;
    }
}
