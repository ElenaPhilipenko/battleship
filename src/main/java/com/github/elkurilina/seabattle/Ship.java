package com.github.elkurilina.seabattle;

import java.awt.*;
import java.util.Collection;

/**
 * @author Elena Kurilina
 */
public class Ship {

    private final Collection<Point> parts;

    public Collection<Point> getParts() {
        return parts;
    }

    public Ship(Collection<Point> points) {
        this.parts = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship1 = (Ship) o;

        if (parts != null ? !parts.equals(ship1.parts) : ship1.parts != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return parts != null ? parts.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "parts=" + parts +
                '}';
    }
}
