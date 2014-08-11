package com.github.elkurilina.seabattle.player;

import com.github.elkurilina.seabattle.Cell;

import java.util.*;

/**
 * @author Elena Kurilina
 */
public class RandomShipLocator {
    private final Random random = new Random();

    public Collection<Cell> createShips(Iterable<Integer> shipSizes, int gridSize) {
        final Collection<Cell> shipLocations = new HashSet<>();
        final List<Cell> field = createPossiblePoints(gridSize);
        for (Integer shipSize : shipSizes) {
            shipLocations.addAll(createShip(field, shipSize, gridSize));
        }
        return shipLocations;
    }

    private Collection<Cell> createShip(List<Cell> field, int shipSize, int size) {
        final Set<Cell> shipLocation = findShipLocation(field, shipSize);
        field.removeAll(shipLocation);
        removeAllPointsAround(field, shipLocation, size);
        return shipLocation;
    }

    private Set<Cell> findShipLocation(List<Cell> field, int shipSize) {
        final Cell head = field.get(random.nextInt(field.size()));
        final boolean horizontal = random.nextBoolean();
        Set<Cell> shipLocation = new HashSet<>(shipSize);
        for (int i = 0; i < shipSize; i++) {
            final Cell option = horizontal ? new Cell(head.x, head.y + i) : new Cell(head.x + i, head.y);
            if (field.contains(option)) {
                shipLocation.add(option);
            } else {
                shipLocation = findShipLocation(field, shipSize);
            }
        }
        return shipLocation;
    }

    private void removeAllPointsAround(Collection<Cell> field, Collection<Cell> shipLocation, int size) {
        for (Cell cell : shipLocation) {
            Iterable<Cell> points = cell.findNeighborsOnFieldWithDiagonals(size);
            for (Cell point : points) {
                field.remove(point);
            }
        }
    }

    private List<Cell> createPossiblePoints(int size) {
        final java.util.List<Cell> field = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                field.add(new Cell(x, y));
            }
        }
        return field;
    }
}
