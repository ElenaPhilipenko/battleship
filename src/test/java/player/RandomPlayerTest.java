package player;

import com.github.elkurilina.seabattle.Cell;
import com.github.elkurilina.seabattle.Game;
import com.github.elkurilina.seabattle.Player;
import com.github.elkurilina.seabattle.WriteGameGrid;
import com.github.elkurilina.seabattle.player.RandomPlayer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;

import static com.github.elkurilina.seabattle.Game.FIELD_SIZE;
import static com.github.elkurilina.seabattle.Game.SHIP_SIZES;

/**
 * @author Elena Kurilina
 */
public class RandomPlayerTest {
    private RandomPlayer player;

    @BeforeMethod
    public void createPlayer() {
        player = new RandomPlayer(10);
    }

    @Test
    public void testPlayGame() {
        Game game = new Game();
        final Player p2 = new RandomPlayer(FIELD_SIZE);
        final Player p1 = new RandomPlayer(FIELD_SIZE);

        final WriteGameGrid p1GameGrid = new WriteGameGrid(p1.getShips(SHIP_SIZES), FIELD_SIZE);
        final WriteGameGrid p2GameGrid = new WriteGameGrid(p2.getShips(SHIP_SIZES), FIELD_SIZE);
        game.playGame(p1, p2, p1GameGrid, p2GameGrid);
    }

    @Test
    public void testSizeOfCreatesShips() {
        final Collection<Cell> fleet = player.getShips(Game.SHIP_SIZES);

        int sum = SHIP_SIZES.stream().reduce(0, (a, b) -> a + b);
        Assert.assertEquals(fleet.size(), sum);
    }

//    private double getMinDistanceBetweenShips(Ship s1, Ship s2) {
//        double min = 100;
//        for (Point p1 : s1.getParts()) {
//            for (Point p2 : s2.getParts()) {
//                double distance = Math.sqrt(Math.pow((p2.x - p1.x), 2) + Math.pow((p2.y - p1.y), 2));
//                min = Math.min(min, distance);
//
//            }
//        }
//        LOG.info(s1.toString() + " ---- " + s2.toString());
//        LOG.info("distance: " + min);
//        return min;
//    }
}
