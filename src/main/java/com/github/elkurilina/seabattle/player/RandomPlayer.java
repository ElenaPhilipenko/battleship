package com.github.elkurilina.seabattle.player;

import com.github.elkurilina.seabattle.Game;
import com.github.elkurilina.seabattle.GameGrid;
import com.github.elkurilina.seabattle.GridSquare;
import com.github.elkurilina.seabattle.Player;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.elkurilina.seabattle.GridSquare.SquareComparator;

/**
 * @author Elena Kurilina
 */
public class RandomPlayer implements Player {
    private final SecureRandom random = new SecureRandom();

    private GameGrid opponentGrid;

    @Override
    public Collection<Collection<GridSquare>> getShips() {
        return new RandomShipLocator().createShips(Game.SHIP_SIZES, Game.GRID_SIZE);
    }

    @Override
    public void initGrids(GameGrid ownGrid, GameGrid opponentGrid) {
        this.opponentGrid = opponentGrid;
    }

    @Override
    public GridSquare makeShot() {
        final Collection<GridSquare> hitShip = opponentGrid.findHitShip();
        final Collection<GridSquare> notShotGridSquares = opponentGrid.findNotShotSquares();
        final List<GridSquare> candidates;
        if (!hitShip.isEmpty()) {
            candidates = findBestCandidatesAround(hitShip).stream().filter(notShotGridSquares::contains).collect(Collectors.toList());
        } else {
            notShotGridSquares.removeAll(findSquaresAround(opponentGrid.findDeadShips()));
            candidates = new ArrayList<>(notShotGridSquares);
        }
        return candidates.get(random.nextInt(candidates.size()));
    }

    @Override
    public void handleResult(boolean victory) {
    }

    private Collection<GridSquare> findSquaresAround(Collection<GridSquare> deadShips) {
        final Collection<GridSquare> allGridSquares = new HashSet<>();
        deadShips.stream().forEach(s -> allGridSquares.addAll(s.findNeighborsOnGridWithDiagonals(Game.GRID_SIZE)));
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
            return hitShip.iterator().next().findNeighborsOnGrid(Game.GRID_SIZE);
        }
    }

}
