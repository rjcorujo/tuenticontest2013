package org.tuenti.contest.solver;

import org.tuenti.contest.input.CaveCase;

import java.util.*;

import static org.tuenti.contest.input.CaveCase.Location;
import static org.tuenti.contest.input.CaveCase.Obstacle;

/**
 * User: robertcorujo
 */
public class CaveSolver {
    Map<Integer,List<Obstacle>> rowOrganizedObstacles;
    Map<Integer,List<Obstacle>> columnOrganizedObstacles;

    private CaveCase cave;


    public int computeExitTime(CaveCase cave) {
        this.cave = cave;
        preComputeObstacles(cave);
        double time =  computeExitTime(cave.getMap().getStartLocation(), cave.getMap().getExitLocation());
        return (int)(Math.ceil(time));
    }

    private double computeExitTime(Location startLocation, Location exitLocation) {
        List<Location> visited = new LinkedList<Location>();
        List<Location> toEvaluate = new LinkedList<Location>();
        Map<Location,Location> path = new HashMap<Location, Location>();

        toEvaluate.add(startLocation);

        Map<Location,Double> estimatedScore = new HashMap<Location, Double>();
        Map<Location,Double> realScores = new HashMap<Location,Double>();


        realScores.put(startLocation, 0.0);
        estimatedScore.put(startLocation, computeOptimisticTimeBetweenLocation(startLocation,exitLocation));


        while (!toEvaluate.isEmpty()) {
            Location current = getCandidate(toEvaluate,realScores);
            if (current.equals(exitLocation)) {
                return computeTimeFromPath(path, startLocation, exitLocation, realScores.get(current));
            }

            toEvaluate.remove(current);
            visited.add(current);

            for (Location neighbour : getNeighboursForLocation(current)) {
                double aproxScore = realScores.get(current) + computeTimeBetweenLocations(current,neighbour);
                if (visited.contains(neighbour)) {
                    if (aproxScore >= realScores.get(neighbour)) {
                        continue;
                    }
                }

                if (!toEvaluate.contains(neighbour) || aproxScore < realScores.get(neighbour)) {
                    path.put(neighbour,current);
                    realScores.put(neighbour, aproxScore);
                    estimatedScore.put(neighbour, aproxScore + computeOptimisticTimeBetweenLocation(neighbour,exitLocation));
                    if (!toEvaluate.contains(neighbour)) {
                        toEvaluate.add(neighbour);
                    }
                }
            }
        }
        throw new Error("An exit path could not be found");
    }

    private double computeTimeFromPath(Map<Location, Location> path, Location startLocation, Location exitLocation, Double score) {
        Location last = exitLocation;

        int nodes = 0;
        while (!last.equals(startLocation)) {
            last = path.get(last);
            nodes++;
        }

        double steps = (score - nodes*cave.getSecondsBeforeMove());
        return nodes*cave.getSecondsBeforeMove() + steps/cave.getSpeed();
    }

    private List<Location> getNeighboursForLocation(Location current) {
        Location goal = cave.getMap().getExitLocation();
        List<Location> candidates = new ArrayList<Location>();
        //up && down
        List<Obstacle> sameColumn = columnOrganizedObstacles.get(current.getY());
        candidates.addAll(getCandidatesMovingUpOrDown(current));
        candidates.addAll(getCandidatesMovingLeftOrRight(current));
        if (isGoalANeighbour(current, goal)) {
            if (!candidates.contains(goal)) {
                candidates.add(goal);
            }
        }
        return candidates;
    }

    private boolean isGoalANeighbour(Location current, Location goal) {
        if (current.getX() == goal.getX()) {
            //No obstacle between them
            int minCol = Math.min(current.getY(), goal.getY());
            int maxCol = Math.max(current.getY(), goal.getY());
            for (int col = minCol+1; col < maxCol; col++) {
                if (rowOrganizedObstacles.get(current.getX()).contains(new Location(current.getX(), col))) {
                    return false;
                }
            }
            return true;
        } else if (current.getY() == goal.getY()) {
            int minRow = Math.min(current.getX(), goal.getX());
            int maxRow = Math.max(current.getX(), goal.getX());
            for (int row = minRow+1; row < maxRow; row++) {
                if (columnOrganizedObstacles.get(current.getY()).contains(new Location(row, current.getY()))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private List<Location> getCandidatesMovingUpOrDown(Location current) {
        List<Obstacle> obstacles = columnOrganizedObstacles.get(current.getY());
        //up

        Location upLocation = null;
        //obstacles right up me -> dont care
        for (int row = current.getX()-1; row >= 0 && upLocation == null; row--) {
            int index = obstacles.indexOf(new Location(row,current.getY()));
            if (index >= 0) {
                upLocation = new Location(row+1,current.getY());
            }
        }
        Location downLocation = null;
        //obstacles right down to me -> dont care
        for (int row = current.getX()+1; row < cave.getHeight() && downLocation == null; row++) {
            int index = obstacles.indexOf(new Location(row,current.getY()));
            if (index >= 0) {
                downLocation = new Location(row-1,current.getY());
            }
        }

        List<Location> destinations = new ArrayList<Location>();
        if (upLocation != null && !upLocation.equals(current)) {
            destinations.add(upLocation);
        }
        if (downLocation != null && !downLocation.equals(current)) {
            destinations.add(downLocation);
        }
        return destinations;
    }

    private List<Location> getCandidatesMovingLeftOrRight(Location current) {
        List<Obstacle> obstacles = rowOrganizedObstacles.get(current.getX());
        //up

        Location leftLocation = null;
        //obstacles right left to me -> dont care
        for (int col = current.getY()-1; col >= 0 && leftLocation == null; col--) {
            int index = obstacles.indexOf(new Location(current.getX(),col));
            if (index >= 0) {
                leftLocation = new Location(current.getX(),col+1);
            }
        }

        Location rightLocation = null;
        //obstacles right right to me -> dont care
        for (int col = current.getY()+1; col < cave.getWidth() && rightLocation == null; col++) {
            int index = obstacles.indexOf(new Location(current.getX(),col));
            if (index >= 0) {
                rightLocation = new Location(current.getX(),col-1);
            }
        }

        List<Location> destinations = new ArrayList<Location>();
        if (rightLocation != null && !rightLocation.equals(current)) {
            destinations.add(rightLocation);
        }
        if (leftLocation != null && !leftLocation.equals(current)) {
            destinations.add(leftLocation);
        }
        return destinations;
    }


    private Location getCandidate(List<Location> locations, final Map<Location,Double> costs) {
        Collections.sort(locations, new Comparator<Location>() {
            @Override
            public int compare(Location location, Location location2) {
               double leftCost = costs.get(location);
               double rightCost = costs.get(location2);
                if (leftCost == rightCost) {
                    return 0;
                } else {
                    return leftCost < rightCost ? -1 : 1;
                }
            }
        });
        return locations.get(0);
    }

    private double computeTimeBetweenLocations(Location start, Location end) {
        int distance = start.computeOptimisticDistance(end);
        return cave.getSecondsBeforeMove() + distance;
    }

    private double computeOptimisticTimeBetweenLocation(Location start, Location end) {
        int distance = start.computeOptimisticDistance(end);
        return cave.getSecondsBeforeMove() + distance;
    }

    private void preComputeObstacles(CaveCase cave) {
        CaveCase.CaveMap map = cave.getMap();
        rowOrganizedObstacles = new HashMap<Integer, List<Obstacle>>();
        columnOrganizedObstacles = new HashMap<Integer, List<Obstacle>>();
        for (Obstacle obstacle : map.getObstacles()) {
            putObstacleInMap(obstacle, obstacle.getX(), rowOrganizedObstacles);
            putObstacleInMap(obstacle, obstacle.getY(), columnOrganizedObstacles);
        }
        sortObstacles();
    }

    private void sortObstacles() {
        for (int key : rowOrganizedObstacles.keySet()) {
            List<Obstacle> obstaclesInRow = rowOrganizedObstacles.get(key);
            if (obstaclesInRow != null) {
                Collections.sort(obstaclesInRow);
            }
        }
    }

    private void putObstacleInMap(Obstacle obstacle, int key, Map<Integer, List<Obstacle>> map) {
        List<Obstacle> obs = map.get(key);
        if (obs == null) {
            obs = new ArrayList<Obstacle>();
            map.put(key,obs);
        }
        obs.add(obstacle);
    }



}
