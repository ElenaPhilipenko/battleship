package com.github.elkurilina.seabattle;

import java.util.*;
import java.util.concurrent.*;

/**
 * Game contains rules of playing SeaBattle game.
 *
 * @author Elena Kurilina
 */
public class Game {
    public static final int GRID_SIZE = 10;
    public static final Collection<Integer> SHIP_SIZES = Collections.unmodifiableList(
            Arrays.asList(4, 3, 3, 2, 2, 2, 1, 1, 1, 1));
    private final ExecutorService executorService = new ThreadPoolExecutor(2, 2, 3, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

    public void playGame(Player p1, Player p2) throws ExecutionException, InterruptedException {
        final Map<Player, WriteGameGrid> playerToGrid = initGrids(p1, p2);

        Player current = p1;
        Player opponent = p2;
        while (!killEveryBody(current, playerToGrid.get(opponent))) {
            Player t = current;
            current = opponent;
            opponent = t;
        }
        current.handleResult(true);
        opponent.handleResult(false);
    }

    private Map<Player, WriteGameGrid> initGrids(Player p1, Player p2) {
        final Map<Player, WriteGameGrid> playerToGrid = new HashMap<>();

        final Future<WriteGameGrid> futureGrid1 = initGridInTread(p1, playerToGrid);
        final Future<WriteGameGrid> futureGrid2 = initGridInTread(p2, playerToGrid);

        final WriteGameGrid grid1;
        final WriteGameGrid grid2;
        try {
            grid2 = futureGrid2.get();
            grid1 = futureGrid1.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        p2.initGrids(grid2, grid1.maskedGrid);
        p1.initGrids(grid1, grid2.maskedGrid);
        return playerToGrid;
    }

    private Future<WriteGameGrid> initGridInTread(Player p1, Map<Player, WriteGameGrid> playerToGrid) {
        final Callable<WriteGameGrid> callable = () -> {
            final WriteGameGrid grid = WriteGameGrid.createGameGidWithShips(p1.getShips(), GRID_SIZE);
            playerToGrid.put(p1, grid);
            return grid;
        };
        return executorService.submit(callable);
    }

    private boolean killEveryBody(Player p1, WriteGameGrid opponent) {
        boolean victory;
        boolean hit;
        do {
            hit = opponent.applyShot(p1.makeShot());
            victory = !opponent.hasAfloatShip();
        }
        while (hit && !victory);

        return victory;
    }


}
