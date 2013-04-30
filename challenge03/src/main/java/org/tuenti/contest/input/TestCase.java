package org.tuenti.contest.input;

import java.util.*;

/**
 * User: robertcorujo
 */
public class TestCase {

    private LinkedHashMap<String,Scene> orderedScenes = new LinkedHashMap<String, Scene>();

    private Map<String,Scene> unlinkedScenes = new HashMap<String, Scene>();

    public int getTotalScenes() {
        return orderedScenes.size() + unlinkedScenes.size();
    }

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
        return new ArrayList<Scene>(orderedScenes.values()).get(orderedScenes.size()-1);
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

        public int getOrder() {
            return initalOrder;
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

        public List<Scene> getPreviousScenes() {
            return previousScenes;
        }

        public List<Scene> getForwardScenes() {
            return forwardScenes;
        }

        public void simplifyDependencies() {
            simplifyPreviousDep();
            simplifyForwardDep();
        }

        private void simplifyForwardDep() {
            Scene minScene = forwardScenes.size() > 0 ? forwardScenes.get(0) : null;
            for (Scene scene : forwardScenes) {
                if (scene.getOrder() < minScene.getOrder()) {
                    minScene = scene;
                }
                scene.getPreviousScenes().remove(this);
            }
            if (minScene != null) {
                forwardScenes.clear();
                forwardScenes.add(minScene);
                minScene.addPreviousScene(this);
            }

        }

        private void simplifyPreviousDep() {
            TestCase.Scene maxScene = previousScenes.size() > 0 ? previousScenes.get(0) : null;
            for (TestCase.Scene scene : previousScenes) {
                if (scene.getOrder() > maxScene.getOrder()) {
                    maxScene = scene;
                }
                scene.getForwardScenes().remove(this);
            }
            if (maxScene != null) {
                previousScenes.clear();
                previousScenes.add(maxScene);
                maxScene.addForwardScene(this);
            }
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
