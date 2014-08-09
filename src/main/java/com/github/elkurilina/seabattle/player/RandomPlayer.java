package com.github.elkurilina.seabattle.player;

import com.github.elkurilina.seabattle.GameGreed;
import com.github.elkurilina.seabattle.Player;
import com.github.elkurilina.seabattle.Ship;
import com.github.elkurilina.seabattle.ShotResult;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author Elena Kurilina
 */
public class RandomPlayer implements Player {
    private final Random random = new Random();
    private final RandomShipLocator shipLocator = new RandomShipLocator();

    private final Set<Point> pointsAroundShips = new HashSet<>();
    private final List<Point> nextShots = new ArrayList<>();
    private Point lastShot;
    private final GameGreed gameGreed;

    public RandomPlayer(Iterable<Integer> shipSizes, int fieldSize) {
        final Iterable<Ship> ships = shipLocator.createShips(shipSizes, fieldSize);
        this.gameGreed = new GameGreed(ships, fieldSize);
    }

    @Override
    public boolean makeShot(GameGreed greed) {
        final List<Point> notShotPoints = greed.findNotShotPoints();
        if (nextShots.isEmpty()) {
            notShotPoints.removeAll(pointsAroundShips);
            lastShot = notShotPoints.get(random.nextInt(notShotPoints.size()));
        } else {
            lastShot = nextShots.remove(0);
            if (!notShotPoints.remove(lastShot)) {
                makeShot(greed);
            }
        }
        return handleShotResult(greed.applyShot(lastShot), greed);
    }

    @Override
    public GameGreed getGameGrid() {
        return this.gameGreed;
    }

    @Override
    public String getName() {
        return "Bot Player";
    }

    private boolean handleShotResult(ShotResult result, GameGreed greed) {
        switch (result) {
            case MISSED:
                return false;
            case HIT:
                pointsAroundShips.add(lastShot);
                nextShots.addAll(greed.getSurroundingPoints(lastShot, false));
                return true;
            case SHIP_IS_DEAD:
                nextShots.clear();
                return true;
        }
        return false;
    }


}
