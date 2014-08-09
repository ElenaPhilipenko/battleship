package com.github.elkurilina.seabattle.player;

import com.github.elkurilina.seabattle.Ship;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author Elena Kurilina
 */
public class RandomShipLocator {
    private final Random random = new Random();

    public Collection<Ship> createShips(Iterable<Integer> shipSizes, int size) {
        final Collection<Ship> ships = new HashSet<>();
        final List<Point> field = createPossiblePoints(size);
        for (Integer shipSize : shipSizes) {
            ships.add(createShip(field, shipSize));
        }
        return ships;
    }

    private Ship createShip(List<Point> field, int shipSize) {
        final Set<Point> shipLocation = findShipLocation(field, shipSize);
        field.removeAll(shipLocation);
        removeAllPointsAround(field, shipLocation);
        return new Ship(shipLocation);
    }

    private Set<Point> findShipLocation(java.util.List<Point> field, int shipSize) {
        final Point head = field.get(random.nextInt(field.size()));
        final boolean horizontal = random.nextBoolean();
        Set<Point> shipLocation = new HashSet<>(shipSize);
        for (int i = 0; i < shipSize; i++) {
            final Point option = horizontal ? new Point(head.x, head.y + i) : new Point(head.x + i, head.y);
            if (field.contains(option)) {
                shipLocation.add(option);
            } else {
                shipLocation = findShipLocation(field, shipSize);
            }
        }
        return shipLocation;
    }

    private void removeAllPointsAround(Collection<Point> field, Collection<Point> shipLocation) {
        for (Point p : shipLocation) {
            Iterable<Point> points = getSurroundingPoints(p);
            for (Point point : points) {
                field.remove(point);
            }
        }
    }

    private Collection<Point> getSurroundingPoints(Point p) {
        Collection<Point> surroundingPoints = new HashSet<>();
        surroundingPoints.add(new Point(p.x + 1, p.y + 1));
        surroundingPoints.add(new Point(p.x - 1, p.y - 1));
        surroundingPoints.add(new Point(p.x + 1, p.y - 1));
        surroundingPoints.add(new Point(p.x - 1, p.y + 1));
        surroundingPoints.add(new Point(p.x + 1, p.y));
        surroundingPoints.add(new Point(p.x - 1, p.y));
        surroundingPoints.add(new Point(p.x, p.y + 1));
        surroundingPoints.add(new Point(p.x, p.y - 1));
        return surroundingPoints;
    }

    private java.util.List<Point> createPossiblePoints(int size) {
        final java.util.List<Point> field = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                field.add(new Point(x, y));
            }
        }
        return field;
    }
}
