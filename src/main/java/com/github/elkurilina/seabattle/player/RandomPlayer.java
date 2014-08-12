package com.github.elkurilina.seabattle.player;

import com.github.elkurilina.seabattle.GridSquare;
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
    public GridSquare makeShot(GameGrid grid) {
        final Collection<GridSquare> hitShip = grid.findHitShip();
        final Collection<GridSquare> notShotGridSquares = grid.findNotShotSquares();
        final List<GridSquare> candidates;
        if (!hitShip.isEmpty()) {
            candidates =  findBestCandidatesAround(hitShip).stream().filter(notShotGridSquares::contains).collect(Collectors.toList());
        } else {
            notShotGridSquares.removeAll(findSquaresAround(grid.findDeadShips()));
            candidates = new ArrayList<>(notShotGridSquares);
        }
        return candidates.get(random.nextInt(candidates.size()));
    }

    @Override
    public Collection<Collection<GridSquare>> getShips(Iterable<Integer> shipSizes) {
        return new RandomShipLocator().createShips(shipSizes, size);
    }

    @Override
    public String getName() {
        return BOT_PLAYER;
    }

    private Collection<GridSquare> findSquaresAround(Collection<GridSquare> deadShips) {
        final Collection<GridSquare> allGridSquares = new HashSet<>();
        deadShips.stream().forEach(s -> allGridSquares.addAll(s.findNeighborsOnGridWithDiagonals(size)));
        allGridSquares.addAll(deadShips);
        return allGridSquares;
    }

    private Collection<GridSquare> findBestCandidatesAround(Collection<GridSquare> hitShip) {
        if (hitShip.size() > 1) {
            final Collection<GridSquare> candidates = new HashSet<>();
            final GridSquare min = hitShip.stream().min(new SquareComparator()).get();
            final GridSquare max = hitShip.stream().max(new SquareComparator()).get();
            final boolean horizontal = max.x - min.x != 0;
            candidates.add(max.translate(1, horizontal));
            candidates.add(min.translate(-1, horizontal));
            return candidates;
        } else {
            return hitShip.iterator().next().findNeighborsOnGrid(size);
        }
    }

    private static class SquareComparator implements Comparator<GridSquare> {

        @Override
        public int compare(GridSquare o1, GridSquare o2) {
            if (o1.x == o2.x && o1.y == o2.y) {
                return 0;
            } else {
                return o1.x > o2.x || o1.y > o2.y ? 1 : -1;
            }
        }
    }

}
