package player;

import com.github.elkurilina.seabattle.Game;
import com.github.elkurilina.seabattle.player.RandomPlayer;
import org.testng.annotations.Test;

import static com.github.elkurilina.seabattle.SeaBattle.FIELD_SIZE;
import static com.github.elkurilina.seabattle.SeaBattle.SHIP_SIZES;

/**
 * @author Elena Kurilina
 */
public class RandomPlayerTest {


    @Test
    public void testPlayGame() {
        Game game = new Game();

        game.playGame(new RandomPlayer(SHIP_SIZES, FIELD_SIZE), new RandomPlayer(SHIP_SIZES, FIELD_SIZE));
    }

//    @Test
//    public void testCreatedShipDontIntersect() {
//        List<Ship> fleet = new ArrayList<>(player.getShips(Game.SHIP_SIZES, 10));
//        while (!fleet.isEmpty()) {
//            final Ship s1 = fleet.remove(0);
//            for (Ship s2 : fleet) {
//                Assert.assertTrue(getMinDistanceBetweenShips(s1, s2) > 1);
//
//            }
//
//        }
//    }
//
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
