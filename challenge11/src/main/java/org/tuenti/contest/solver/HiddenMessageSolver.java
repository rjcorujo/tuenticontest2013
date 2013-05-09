package org.tuenti.contest.solver;

import java.util.*;

/**
 * User: robertcorujo
 */
public class HiddenMessageSolver {

    private Map<String,Character> buildingResult;

    public String solve(List<String> pictures) {
        buildingResult = new HashMap<String,Character>();

        Map<String,Character> partialResult = transformPic(pictures.get(0));
        for (int i = 1; i < pictures.size(); i++) {
            Map<String,Character> secondPic = transformPic(pictures.get(i));
            merge("0",partialResult,secondPic);

            if (buildingResult == null) {
                return "error";
            }

            compactResult();
            //reuse memory
            Map<String,Character> aux = partialResult;
            partialResult = buildingResult;
            buildingResult = aux;
            buildingResult.clear();
        }

        return buildStr(partialResult);
    }


    private void merge(String prefix, Map<String, Character> pic1, Map<String, Character> pic2) {
        char left = pic1.get(prefix);
        char right = pic2.get(prefix);

        if (left == 'b' || right == 'b') {
            buildingResult.put(prefix, 'b');
        } else if (left == 'w' && right == 'w') {
            buildingResult.put(prefix,'w');
        } else if (left == 'p' && right == 'p') {
            buildingResult.put(prefix,'p');
            for (int i = 1; i <= 4; i++) {
                merge(prefix+i, pic1, pic2);
            }
        } else if (left =='p' && right == 'w') {
            copyValuesWithPrefix(prefix, pic1);
        } else if (left != 'p' && right == 'p') {
            copyValuesWithPrefix(prefix, pic2);
        } else {
            throw new Error("Error");
        }

    }

    private void copyValuesWithPrefix(String prefix, Map<String, Character> pic) {
        for (String key: pic.keySet()) {
            if (key.startsWith(prefix)) {
                buildingResult.put(key, pic.get(key));
            }
        }
    }


//    public String solve(List<String> pictures) {
//        buildingResult = new HashMap<String,Character>();
//
//        Map<String,Character> partialResult = transformPic(pictures.get(0));
//        for (int i = 1; i < pictures.size(); i++) {
//
//            merge(partialResult,pictures.get(i));
//            if (buildingResult == null) {
//                return "error";
//            }
//
//            compactResult();
//            //reuse memory
//            Map<String,Character> aux = partialResult;
//            partialResult = buildingResult;
//            buildingResult = aux;
//            buildingResult.clear();
//        }
//
//        return buildStr(partialResult);
//    }

    private void compactResult() {
        if (buildingResult.get("0") == 'p') {
            char compactForm = compactBranch("0");
            buildingResult.put("0",compactForm);
        }
    }

    private char compactBranch(String identifier) {
        boolean allSameLeaf = true;
        Character last = null;
        for (int i = 1; i <= 4 ; i++) {
            String currentId = identifier+i;
            char current = buildingResult.get(currentId);
            if (current == 'p') {
                current = compactBranch(currentId);
                buildingResult.put(currentId, current);
            }

            if (last != null) {
                allSameLeaf = allSameLeaf && (last.charValue() == current) && current != 'p';
            }

            last = current;
        }

        if (allSameLeaf) {
            for (int i = 1; i <= 4; i++) {
                buildingResult.remove(identifier+i);
            }
            return last.charValue();
        } else {
            return 'p';
        }

    }


    private String buildStr(Map<String, Character> result) {
        List<String> keys = new ArrayList<String>(result.keySet());
        Collections.sort(keys, new Comparator<String>() {
            @Override
            public int compare(String s, String s2) {
                if (s.length() < s2.length()) {
                    return -1;
                } else if (s.length() > s2.length()) {
                    return 1;
                } else {
                    return s.compareTo(s2);
                }
            }
        });
        StringBuilder strBuilder = new StringBuilder();
        for (String key : keys) {
            strBuilder.append(result.get(key));
        }
        return strBuilder.toString();
    }


    private void merge(Map<String,Character> nodes, String pic) {
        switch (pic.charAt(0)) {
            case 'b':
                buildingResult.put("0",'b');
                break;
            case 'w':
                buildingResult.putAll(nodes);
                break;
            case 'p':
                buildingResult.put("0",'p');
                iterate(Arrays.asList("0"), nodes, pic, 1, new ArrayList<String>(), new ArrayList<String>());
                break;
            default:
                buildingResult = null;
        }
    }

    private void iterate(List<String> prefixes, Map<String,Character> nodes, String picture, int startRight, List<String> justCopy, List<String> ignore) {
        List<String> nextIgnore = new ArrayList<String>();
        List<String> nextPrefixes = new ArrayList<String>();
        List<String> nextCopy = new ArrayList<String>();
        int index = startRight;
        for (String prefix : prefixes) {

            if (ignore.contains(prefix)) {
                for (int i = 1; i <= 4 ; i++) {
                    String currentPrefix = prefix + i;
                    if (picture.charAt(index) == 'p') { //ignore branches must go on ignoring
                        nextIgnore.add(currentPrefix);
                    }
                    index++;
                }
            } else {
                for (int i = 1; i <= 4; i++) {
                    String currentPrefix = prefix + i;

                    if (justCopy.contains(prefix)) {
                        char current = picture.charAt(index);
                        buildingResult.put(currentPrefix, current);
                        if (current == 'p') {
                            nextPrefixes.add(currentPrefix);
                            nextCopy.add(currentPrefix);
                        }
                        index++;
                    } else {
                        char left = nodes.get(currentPrefix);
                        char right = picture.charAt(index);

                        if (right == 'b') {
                            buildingResult.put(currentPrefix,'b');
                        } else if (right == 'w') {
                            if (left == 'b') {
                                buildingResult.put(currentPrefix,'b');
                            } else if (left == 'w') {
                                buildingResult.put(currentPrefix,'w');
                            } else { //p
                                copyBranch(nodes,currentPrefix);
                            }
                        } else if (right == 'p') {
                            if (left == 'b') {
                                buildingResult.put(currentPrefix,'b');
                                nextPrefixes.add(currentPrefix);
                                nextIgnore.add(currentPrefix);
                            } else if (left == 'w') {
                                buildingResult.put(currentPrefix,'p');
                                nextPrefixes.add(currentPrefix);
                                nextCopy.add(currentPrefix);
                            } else { //p
                                buildingResult.put(currentPrefix,'p');
                                nextPrefixes.add(currentPrefix);
                            }
                        }
                        index++;
                    }
                }
            }
        }
        if (!nextPrefixes.isEmpty()) {
            iterate(nextPrefixes, nodes, picture, index, nextCopy, nextIgnore);
        }
    }

    private void copyBranch(Map<String, Character> nodes, String identifier) {
        char result = nodes.get(identifier);
        buildingResult.put(identifier,result);
        for (int i = 1; i <= 4; i++) {
            String currentId = identifier+i;
            if (nodes.get(currentId) != 'p') {
                buildingResult.put(currentId,nodes.get(currentId));
            } else {
                copyBranch(nodes,currentId);
            }
        }
    }


    private Map<String,Character> transformPic(String pic) {
        Map<String,Character> nodes = new HashMap<String, Character>();
        if (pic.charAt(0) != 'p') {
            nodes.put("0",pic.charAt(0));
        } else {
            nodes.put("0",pic.charAt(0));
            transform(pic, 1, Arrays.asList("0"), nodes);
        }
        return nodes;
    }

    private void transform(String raw, int startIndex, List<String> prefixes, Map<String,Character> output) {
        List<String> newPrefixes = new ArrayList<String>();
        int start = startIndex;
        for (String prefix : prefixes) {
            for (int i = 1; i <= 4; i++) {
                char current = raw.charAt(start+i-1);
                String currentPrefix = prefix+i;
                output.put(currentPrefix,current);
                if (current == 'p') {
                    newPrefixes.add(currentPrefix);
                }
            }
            start += 4;
        }
        if (newPrefixes.size() > 0) {
            transform(raw,start,newPrefixes,output);
        }
    }

}
