package com.github.elkurilina.seabattle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author Elena Kurilina
 */
public final class GameGreed {
    private final List<List<CellState>> grid;

    public GameGreed(Iterable<Ship> ships, int size) {
        grid = new ArrayList<>(size);
        createEmptyGrid(size);
        locateShips(ships);
    }

    public boolean hasAfloatShip() {
        for (List<CellState> row : grid) {
            if (row.contains(CellState.SHIP)) {
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        return grid.size();
    }

    public ShotResult applyShot(Point point) {
        CellState cell = grid.get(point.x).get(point.y);
        if (cell == CellState.SHIP) {
            setCellAtPoint(point, CellState.HIT_SHIP);
            if (hasShipCellAround(point, true)) {
                return ShotResult.SHIP_IS_DEAD;
            }
            return ShotResult.HIT;
        } else if (cell == CellState.EMPTY) {
            setCellAtPoint(point, CellState.MISSED_SHOT);
        }
        return ShotResult.MISSED;
    }

    public Collection<Point> getSurroundingPoints(Point p, boolean includeDiagonals) {
        Collection<Point> surroundingPoints = new HashSet<>();
        validateAndAdd(p.x + 1, p.y, surroundingPoints);
        validateAndAdd(p.x - 1, p.y, surroundingPoints);
        validateAndAdd(p.x, p.y + 1, surroundingPoints);
        validateAndAdd(p.x, p.y - 1, surroundingPoints);
        if (includeDiagonals) {
            validateAndAdd(p.x - 1, p.y - 1, surroundingPoints);
            validateAndAdd(p.x - 1, p.y + 1, surroundingPoints);
            validateAndAdd(p.x + 1, p.y - 1, surroundingPoints);
            validateAndAdd(p.x + 1, p.y + 1, surroundingPoints);
        }

        return surroundingPoints;
    }

    public List<Point> findNotShotPoints() {
        final List<Point> empty = new ArrayList<>();
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.size(); j++) {
                final Point current = new Point(i, j);
                if (getCellAtPoint(current) == CellState.EMPTY || getCellAtPoint(current) == CellState.SHIP) {
                    empty.add(current);
                }
            }
        }
        return empty;
    }

    public void print(boolean masked) {
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        int rowIndex = 0;
        for (Iterable<CellState> row : grid) {
            System.out.print(rowIndex + " ");
            rowIndex++;
            for (CellState cell : row) {
                switch (cell) {
                    case SHIP:
                        System.out.print(masked ? "?" : "s");
                        break;
                    case HIT_SHIP:
                        System.out.print("x");
                        break;
                    case MISSED_SHOT:
                        System.out.print(".");
                        break;
                    case EMPTY:
                        System.out.print("?");
                        break;
                }
                System.out.print(" ");
            }
            System.out.print("\n");
        }
        System.out.println("----------------");
    }

    private void validateAndAdd(int x, int y, Collection<Point> validPoints) {
        if (x >= 0 && x < grid.size() && y >= 0 && y < grid.size()) {
            validPoints.add(new Point(x, y));
        }
    }

    private boolean hasShipCellAround(Point point, boolean checkAround) {
        Iterable<Point> surroundPoints = getSurroundingPoints(point, false);
        for (Point p : surroundPoints) {
            final CellState state = getCellAtPoint(p);
            if (state == CellState.SHIP) {
                return false;
            } else if (state == CellState.HIT_SHIP && checkAround) {
                if (!hasShipCellAround(p, false)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void createEmptyGrid(int size) {
        for (int i = 0; i < size; i++) {
            grid.add(new ArrayList<>(size));
            for (int j = 0; j < size; j++) {
                grid.get(i).add(CellState.EMPTY);
            }

        }
    }

    private CellState getCellAtPoint(Point p) {
        return grid.get(p.x).get(p.y);
    }

    private void setCellAtPoint(Point p, CellState value) {
        grid.get(p.x).set(p.y, value);
    }

    private void locateShips(Iterable<Ship> ships) {
        for (Ship ship : ships) {
            final Iterable<Point> points = ship.getParts();
            for (Point p : points) {
                setCellAtPoint(p, CellState.SHIP);
            }
        }
    }
}
