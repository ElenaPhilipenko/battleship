package com.github.elkurilina.seabattle.player;

import com.github.elkurilina.seabattle.GridSquare;

import java.security.SecureRandom;
import java.util.*;

/**
 * @author Elena Kurilina
 */
public class RandomShipLocator {
    private final SecureRandom random = new SecureRandom();

    public Collection<Collection<GridSquare>> createShips(Iterable<Integer> shipSizes, int gridSize) {
        final Collection<Collection<GridSquare>> shipLocations = new HashSet<>();
        final List<GridSquare> grid = createPossiblePoints(gridSize);
        shipSizes.forEach(size -> shipLocations.add(createShip(grid, size, gridSize)));
        return shipLocations;
    }

    private Collection<GridSquare> createShip(List<GridSquare> grid, int shipSize, int size) {
        final Collection<GridSquare> shipLocation = locateShip(grid, shipSize);
        grid.removeAll(shipLocation);
        shipLocation.forEach(square -> grid.removeAll(square.findNeighborsOnGridWithDiagonals(size)));
        return shipLocation;
    }

    private Collection<GridSquare> locateShip(List<GridSquare> grid, int shipSize) {
        final GridSquare head = grid.get(random.nextInt(grid.size()));
        final boolean horizontal = random.nextBoolean();
        Collection<GridSquare> shipLocation = new HashSet<>(shipSize);
        for (int i = 0; i < shipSize; i++) {
            final GridSquare option = head.translate(i, horizontal);
            if (grid.contains(option)) {
                shipLocation.add(option);
            } else {
                shipLocation = locateShip(grid, shipSize);
            }
        }
        return shipLocation;
    }

    private List<GridSquare> createPossiblePoints(int size) {
        final List<GridSquare> field = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                field.add(new GridSquare(x, y));
            }
        }
        return field;
    }
}
