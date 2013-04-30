package org.tuenti.contest.input;

import java.util.*;

/**
 * User: robertcorujo
 */
public class TestCase {

    private LinkedHashMap<String,Scene> orderedScenes = new LinkedHashMap<String, Scene>();

    private Map<String,Scene> unlinkedScenes = new HashMap<String, Scene>();

    public void addOrderedScene(String name) {
        orderedScenes.put(name, new Scene(name,orderedScenes.size()));
    }

    public void addFlashforwardScene(String name) {
        Scene prevScene = getLastOrderdScene();
        Scene scene = unlinkedScenes.get(name);
        if (scene == null) {
            scene = new Scene(name);
            unlinkedScenes.put(name,scene);
        }
        prevScene.addForwardScene(scene);
        scene.addPreviousScene(prevScene);
    }

    public void addFlashbackScene(String name) {
        Scene forwardScene = getLastOrderdScene();
        Scene scene = unlinkedScenes.get(name);
        if (scene == null) {
            scene = new Scene(name);
            unlinkedScenes.put(name,scene);
        }
        forwardScene.addPreviousScene(scene);
        scene.addForwardScene(forwardScene);
    }

    private Scene getLastOrderdScene() {
        return orderedScenes.get(orderedScenes.size()-1);
    }

    public Scene getOrderedScene(String name) {
        return orderedScenes.get(name);
    }

    public LinkedHashMap<String, Scene> getOrderedScenes() {
        return orderedScenes;
    }

    public Map<String, Scene> getUnlinkedScenes() {
        return unlinkedScenes;
    }

    public static  class Scene {
        private String name;
        private List<Scene> previousScenes = new ArrayList<Scene>();
        private List<Scene> forwardScenes = new ArrayList<Scene>();
        private int initalOrder;


        public Scene(String name) {
            this.name = name;
        }
        public Scene(String name, int order) {
            this(name);
            initalOrder = order;
        }

        public void addPreviousScene(Scene scene) {
            previousScenes.add(scene);
        }

        public void addForwardScene(Scene scene) {
            forwardScenes.add(scene);
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (o != null && o instanceof Scene) {
                return name.equals(((Scene)o).name);
            }
            return false;
        }
    }

}
