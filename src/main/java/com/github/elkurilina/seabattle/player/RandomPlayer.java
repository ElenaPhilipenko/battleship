package com.github.elkurilina.seabattle.player;

import com.github.elkurilina.seabattle.*;
import com.github.elkurilina.seabattle.MaskedGameGrid;

import java.util.*;

/**
 * @author Elena Kurilina
 */
public class RandomPlayer implements Player {
    private final Random random = new Random();

    private final int size;

    private final Set<Cell> cellsAroundShips = new HashSet<>();
    private final List<Cell> nextShots = new ArrayList<>();
    private Cell lastShot;

    public RandomPlayer(int fieldSize) {
        this.size = fieldSize;
    }

    @Override
    public Cell makeShot(MaskedGameGrid grid) {
        final List<Cell> notShotCells = grid.findNotShotPoints();
        if (nextShots.isEmpty()) {
            notShotCells.removeAll(cellsAroundShips);
            lastShot = notShotCells.get(random.nextInt(notShotCells.size()));
        } else {
            lastShot = nextShots.remove(0);
            if (!notShotCells.remove(lastShot)) {
                return makeShot(grid);
            }
        }
        return lastShot;
    }

    @Override
    public boolean handleShotResult(ShotResult result) {
        switch (result) {
            case MISSED:
                return false;
            case HIT:
                cellsAroundShips.add(lastShot);
                nextShots.addAll(lastShot.findNeighborsOnField(size));
                return true;
            case SHIP_IS_DEAD:
                nextShots.clear();
                return true;
        }
        return false;
    }

    @Override
    public Iterable<Cell> getShips(Iterable<Integer> shipSizes) {
        return new RandomShipLocator().createShips(shipSizes, size);
    }

    @Override
    public String getName() {
        return "Bot Player";
    }

    private List<Cell> createPoints(int size) {
        final List<Cell> field = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                field.add(new Cell(x, y));
            }
        }
        return field;
    }


}
