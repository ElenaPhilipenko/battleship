package com.github.elkurilina.seabattle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ShipsValidator performs validation of ships provided by Player.
 *
 * @author Elena Kurilina
 */
public class ShipsValidator {

    public static boolean isShipLocationsValid(Collection<Collection<GridSquare>> ships) {
        return isShipAmountValid(ships) &&
                isShipSizesValid(ships) &&
                isDistanceBetweenShipsValid(ships) &&
                ships.stream().filter(ShipsValidator::isShipValid).count() == ships.size();
    }

    public static boolean isDistanceBetweenShipsValid(Collection<Collection<GridSquare>> ships) {
        List<Collection<GridSquare>> list = new ArrayList<>(ships);
        while (!list.isEmpty()) {
            final Collection<GridSquare> ship = list.remove(0);
            if (list.stream()
                    .filter(ship2 -> getMinDistanceBetweenShips(ship, ship2) < 2)
                    .count() > 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isShipValid(Collection<GridSquare> ship) {
        return isShipOnTheSameLine(ship) && !shipContainGaps(ship);
    }

    private static boolean isShipOnTheSameLine(Collection<GridSquare> ship) {
        if (ship.size() == 1) return true;
        final GridSquare first = ship.iterator().next();
        long vertical = ship.stream().filter(s -> s.y == first.y).count();
        long horizontal = ship.stream().filter(s -> s.x == first.x).count();

        return vertical == ship.size() || horizontal == ship.size();
    }

    private static boolean shipContainGaps(Collection<GridSquare> ship) {
        List<GridSquare> sorted = ship.stream()
                .sorted(new GridSquare.SquareComparator())
                .collect(Collectors.toList());
        GridSquare previous = null;
        double maxDistance = 0;
        for (GridSquare s : sorted) {
            if (previous != null) {
                maxDistance = Math.max(maxDistance, s.distance(previous));
            }
            previous = s;
        }
        return maxDistance > 1;

    }

    private static boolean isShipSizesValid(Collection<Collection<GridSquare>> shipLocation) {
        final Collection<Integer> sizes = new ArrayList<>(Game.SHIP_SIZES);
        shipLocation.stream().forEach(ship -> sizes.remove(ship.size()));
        return sizes.size() == 0;
    }

    private static boolean isShipAmountValid(Collection<Collection<GridSquare>> ships) {
        return ships.size() == Game.SHIP_SIZES.size();
    }

    private static double getMinDistanceBetweenShips(Collection<GridSquare> ship1, Collection<GridSquare> ship2) {
        double min = Game.GRID_SIZE;
        for (GridSquare square : ship1) {
            for (GridSquare square1 : ship2) {
                min = Math.min(min, square.distance(square1));
            }
        }
        return min;
    }
}
