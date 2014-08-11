package com.github.elkurilina.seabattle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Elena Kurilina
 */
public class GameGridValidator {

    public static boolean isShipLocationsValid(Collection<Collection<Cell>> shipLocation) {
        return isShipAmountValid(shipLocation) &&
                isShipSizesValid(shipLocation) &&
                isDistanceBetweenShipsValid(shipLocation);
    }

    private static boolean isShipSizesValid(Collection<Collection<Cell>> shipLocation) {
        final Collection<Integer> sizes = new ArrayList<>(Game.SHIP_SIZES);
        shipLocation.stream().forEach(ship -> sizes.remove(ship.size()));
        return sizes.size() == 0;
    }

    private static boolean isShipAmountValid(Collection<Collection<Cell>> ships) {
        return ships.size() == Game.SHIP_SIZES.size();
    }

    private static boolean isDistanceBetweenShipsValid(Collection<Collection<Cell>> ships) {
        List<Collection<Cell>> list = new ArrayList<>(ships);
        while (!list.isEmpty()) {
            final Collection<Cell> ship = list.remove(0);
            if (list.stream()
                    .filter(ship2 -> getMinDistanceBetweenShips(ship, ship2) < 2)
                    .count() > 0) {
                return false;
            }
        }
        return true;
    }

    private static double getMinDistanceBetweenShips(Collection<Cell> ship1, Collection<Cell> ship2) {
        double min = 100;
        for (Cell p1 : ship1) {
            for (Cell p2 : ship2) {
                min = Math.min(min, p1.distance(p2));
            }
        }
        return min;
    }
}
