package com.github.elkurilina.seabattle.player;

import com.github.elkurilina.seabattle.GameGreed;
import com.github.elkurilina.seabattle.Player;
import com.github.elkurilina.seabattle.Ship;
import com.github.elkurilina.seabattle.ShotResult;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Elena Kurilina
 */
public class ConsolePlayer implements Player {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final RandomShipLocator shipLocator = new RandomShipLocator();

    private final GameGreed gameGreed;
    private final String name;

    public ConsolePlayer(Iterable<Integer> shipSizes, int fieldSize) throws IOException {
        System.out.println("Please, enter your name: ");
        this.name = br.readLine();
        final Iterable<Ship> ships;
        System.out.println("Do you want create random game grid? (y/n)");
        if (br.readLine().contains("n")) {
            ships = createSips(shipSizes, fieldSize);
        } else {
            ships = shipLocator.createShips(shipSizes, fieldSize);
        }
        this.gameGreed = new GameGreed(ships, fieldSize);

    }


    @Override
    public boolean makeShot(GameGreed greed) {
        System.out.println("You game grid: ");
        gameGreed.print(false);
        System.out.println("Please enter coordinates of you shot in format: x y");
        try {
            Point p = parsePoint(greed.getSize());
            ShotResult result = greed.applyShot(p);
            greed.print(true);
            return (result == ShotResult.HIT || result == ShotResult.SHIP_IS_DEAD);
        } catch (Exception e) {
            System.out.print("Can not parse move.");
            return makeShot(greed);
        }
    }

    @Override
    public GameGreed getGameGrid() {
        return this.gameGreed;
    }

    @Override
    public String getName() {
        return this.name;
    }

    private Iterable<Ship> createSips(Iterable<Integer> shipSizes, int size) {
        final Collection<Ship> ships = new HashSet<>();
        for (Integer shipSize : shipSizes) {
            final Set<Point> shipLocation = new HashSet<>();
            System.out.println("Please, input location points for ship with size: " + shipSize);
            for (int i = 0; i < shipSize; i++) {
                addParsedPoint(shipLocation, i, size);
            }
            ships.add(new Ship(shipLocation));

        }
        return ships;
    }


    private Point parsePoint(int max) throws Exception {
        System.out.println("Please enter in format: x y ");
        final String shot = br.readLine().replace(" ", "");
        final Integer x = Integer.parseInt(String.valueOf(shot.charAt(0)));
        final Integer y = Integer.parseInt(String.valueOf(shot.charAt(1)));
        if (x < 0 || x > max || y < 0 || y > max) {
            throw new IllegalArgumentException("Wring point");
        }
        return new Point(x, y);
    }

    private void addParsedPoint(Collection<Point> shipLocation, int i, int size) {
        System.out.println("point " + (i + 1));
        try {
            shipLocation.add(parsePoint(size));
        } catch (Exception e) {
            e.printStackTrace();
            addParsedPoint(shipLocation, i, size);
        }
    }
}
