package com.github.elkurilina.seabattle.player;

import com.github.elkurilina.seabattle.Cell;
import com.github.elkurilina.seabattle.GameGrid;
import com.github.elkurilina.seabattle.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Elena Kurilina
 */
public class RandomPlayer implements Player {
    public static final String BOT_PLAYER = "Bot Player";
    private final Random random = new Random();

    private final int size;

    public RandomPlayer(int fieldSize) {
        this.size = fieldSize;
    }

    @Override
    public Cell makeShot(GameGrid grid) {
        final Collection<Cell> hitShip = grid.findHitShip();
        final Collection<Cell> notShotCells = grid.findNotShotPoints();
        final List<Cell> candidates;
        if (!hitShip.isEmpty()) {
            candidates =  findBestCandidatesAround(hitShip).stream().filter(notShotCells::contains).collect(Collectors.toList());
        } else {
            notShotCells.removeAll(findCellsAround(grid.findDeadShips()));
            candidates = new ArrayList<>(notShotCells);
        }
        return candidates.get(random.nextInt(candidates.size()));
    }

    @Override
    public Collection<Collection<Cell>> getShips(Iterable<Integer> shipSizes) {
        return new RandomShipLocator().createShips(shipSizes, size);
    }

    @Override
    public String getName() {
        return BOT_PLAYER;
    }

    private Collection<Cell> findCellsAround(Collection<Cell> deadShips) {
        final Collection<Cell> allCells = new HashSet<>();
        deadShips.stream().forEach(cell -> allCells.addAll(cell.findNeighborsOnGridWithDiagonals(size)));
        allCells.addAll(deadShips);
        return allCells;
    }

    private Collection<Cell> findBestCandidatesAround(Collection<Cell> hitShip) {
        if (hitShip.size() > 1) {
            final Collection<Cell> candidates = new HashSet<>();
            final Cell min = hitShip.stream().min(new CellComparator()).get();
            final Cell max = hitShip.stream().max(new CellComparator()).get();
            final boolean horizontal = max.x - min.x != 0;
            candidates.add(max.translate(1, horizontal));
            candidates.add(min.translate(-1, horizontal));
            return candidates;
        } else {
            return hitShip.iterator().next().findNeighborsOnGrid(size);
        }
    }

    private static class CellComparator implements Comparator<Cell> {

        @Override
        public int compare(Cell o1, Cell o2) {
            if (o1.x == o2.x && o1.y == o2.y) {
                return 0;
            } else {
                return o1.x > o2.x || o1.y > o2.y ? 1 : -1;
            }
        }
    }

}
