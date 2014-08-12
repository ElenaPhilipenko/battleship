package com.github.elkurilina.seabattle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * GameGridValidator performs validation of ships provided by Player.
 *
 * @author Elena Kurilina
 */
public class GameGridValidator {

    public static boolean isShipLocationsValid(Collection<Collection<GridSquare>> shipLocation) {
        return isShipAmountValid(shipLocation) &&
                isShipSizesValid(shipLocation) &&
                isDistanceBetweenShipsValid(shipLocation);
    }

    private static boolean isShipSizesValid(Collection<Collection<GridSquare>> shipLocation) {
        final Collection<Integer> sizes = new ArrayList<>(Game.SHIP_SIZES);
        shipLocation.stream().forEach(ship -> sizes.remove(ship.size()));
        return sizes.size() == 0;
    }

    private static boolean isShipAmountValid(Collection<Collection<GridSquare>> ships) {
        return ships.size() == Game.SHIP_SIZES.size();
    }

    private static boolean isDistanceBetweenShipsValid(Collection<Collection<GridSquare>> ships) {
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

    private static double getMinDistanceBetweenShips(Collection<GridSquare> ship1, Collection<GridSquare> ship2) {
        double min = Game.GRID_SIZE;
        for (GridSquare p1 : ship1) {
            for (GridSquare p2 : ship2) {
                min = Math.min(min, p1.distance(p2));
            }
        }
        return min;
    }
}
