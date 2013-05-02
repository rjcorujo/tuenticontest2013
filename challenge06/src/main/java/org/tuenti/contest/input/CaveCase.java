package org.tuenti.contest.input;

import java.util.List;

/**
 * User: robertcorujo
 */
public class CaveCase {
    private int width;
    private int height;
    private float speed;
    private float secondsBeforeMove;
    private CaveMap map;


    public CaveCase(int width, int height, float speed, float secondsBeforeMove, CaveMap map) {
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.secondsBeforeMove = secondsBeforeMove;
        this.map = map;
    }

    public CaveMap getMap() {
        return map;
    }

    public float getSpeed() {
        return speed;
    }

    public float getSecondsBeforeMove() {
        return secondsBeforeMove;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static class CaveMap {
        private List<Obstacle> obstacles;
        private Location startLocation;
        private Location exitLocation;

        public CaveMap(Location startLocation, Location exitLocation, List<Obstacle> obstacles) {
            this.obstacles = obstacles;
            this.startLocation = startLocation;
            this.exitLocation = exitLocation;
        }

        public List<Obstacle> getObstacles() {
            return obstacles;
        }

        public Location getStartLocation() {
            return startLocation;
        }

        public Location getExitLocation() {
            return exitLocation;
        }
    }

    public static class Location implements Comparable<Location>{
        private int x;
        private int y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int computeOptimisticDistance(Location other) {
            return Math.abs(x - other.x) + Math.abs(y - other.y);
        }

        @Override
        public int hashCode() {
            return x+y;
        }

        @Override
        public boolean equals(Object o) {
            if (o != null && o instanceof Location) {
                Location other = (Location)o;
                return x == other.x && y == other.y;
            }
            return false;
        }

        @Override
        public int compareTo(Location location) {
            if (x < location.x) {
                return -1;
            } else if (x > location.x) {
                return 1;
            } else {
                if (y < location.y) {
                    return -1;
                } else if (y > location.y) {
                    return 1;
                }
                return 0;
            }
        }

        @Override
        public String toString() {
            return "("+x+","+y+")";
        }
    }

    public static class Obstacle extends Location{

        public Obstacle(int x, int y) {
            super(x, y);
        }
    }
}
