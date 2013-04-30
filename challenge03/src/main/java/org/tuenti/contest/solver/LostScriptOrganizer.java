package org.tuenti.contest.solver;

import org.apache.commons.lang3.StringUtils;
import org.tuenti.contest.input.TestCase;
import org.tuenti.contest.input.TestCase.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: robertcorujo
 */
public class LostScriptOrganizer {

    public List<String> organize(List<TestCase> testCases) {

        List<String> scripts = new ArrayList<String>();
        for (TestCase test : testCases) {
            if (isValid(test)) {
                scripts.add(solve(test));
            } else {
                scripts.add("invalid");
            }
        }
        return scripts;
    }

    private String solve(TestCase test) {
        //clean not significant dependencies
        simplifyDependencies(test);

        int totalScripts = test.getTotalScenes();

        List<String> order = new ArrayList<String>();
        for (Scene scene : test.getOrderedScenes().values()) {
            if (scene.getPreviousScenes().size() > 0) {
                if (scene.getPreviousScenes().size() > 1) {
                    return "valid";
                }
                Scene previous = scene.getPreviousScenes().get(0);
                if (!order.contains(previous.getName())) {
                    order.add(previous.getName());
                }
            }

            order.add(scene.getName());

            if (scene.getForwardScenes().size() > 0) {
                if (scene.getForwardScenes().size() > 1) {
                    return "valid";
                }
                Scene forwardCandidate = scene.getForwardScenes().get(0);
                if (forwardCandidate.getForwardScenes().size() > 0) {
                    int nextOrder = forwardCandidate.getForwardScenes().get(0).getOrder();
                    if (scene.getOrder() + 1 == nextOrder) { //only one place to put it
                        order.add(forwardCandidate.getName());
                    } else {
                        return "valid";
                    }
                } else if (totalScripts - order.size() > 1) {
                    return "invalid";
                } else {
                    order.add(forwardCandidate.getName());
                }
            }
        }

        return StringUtils.join(order,",");
    }

    private void simplifyDependencies(TestCase test) {
        for (Scene scene : test.getUnlinkedScenes().values()) {
            scene.simplifyDependencies();
        }
    }

    private boolean isValid(TestCase test) {
        if (test.getUnlinkedScenes().size() == 0) {
            return true;
        }
        //check with known fixed scenes
        for (Scene scene : test.getUnlinkedScenes().values()) {
            Scene orderedScene = test.getOrderedScene(scene.getName());
            if (orderedScene != null) {
                if (isSceneAfterScenes(orderedScene,scene.getForwardScenes())) {
                    return false;
                }
                if (isSceneBeforeScenes(orderedScene, scene.getPreviousScenes())) {
                    return false;
                }
            }
            if (scene.getPreviousScenes().size() > 0 && scene.getForwardScenes().size() > 0) {
                int maxPrevious = getMaxOrder(scene.getPreviousScenes());
                int minForward = getMinOrder(scene.getForwardScenes());
                if (minForward < maxPrevious) {
                   return false;
                }
            }
        }
        return true;
    }

    private int getMinOrder(List<Scene> scenes) {
        int min = Integer.MAX_VALUE;
        for (Scene scene : scenes) {
            min = Math.min(min,scene.getOrder());
        }
        return min;
    }

    private int getMaxOrder(List<Scene> scenes) {
        int max = Integer.MIN_VALUE;
        for (Scene scene : scenes) {
            max = Math.max(max, scene.getOrder());
        }
        return max;
    }

    private boolean isSceneAfterScenes(Scene scene, List<Scene> scenes) {
        for (Scene sceneLinked : scenes) {
            if (scene.getOrder() > sceneLinked.getOrder())
                return true;
        }
        return false;
    }

    private boolean isSceneBeforeScenes(Scene scene, List<Scene> scenes) {
        for (Scene sceneLinked : scenes) {
            if (scene.getOrder() < sceneLinked.getOrder())
                return true;
        }
        return false;
    }


}
