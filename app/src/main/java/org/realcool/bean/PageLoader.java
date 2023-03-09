package org.realcool.bean;

import android.content.Context;

import org.realcool.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class PageLoader {
    public static List<Page> loadPage(Context context, String source) {
        //读取yaml构建 page对象
        Map<String, Object> map = FileUtils.loadAssetsYaml(context, source);
        Map pages = (Map) map.get("pages");
        Map relation = (Map) pages.get("relation");
        Map feature = (Map) pages.get("feature");
        String s = (String) relation.keySet().toArray()[0];
        Page page = new Page(s);
        buildPage(page, (Map) relation.get(s));
        List<Page> list = pageToList(page, new ArrayList<>());
        addToPage(list, feature);
        return list;
    }

    private static void buildPage(Page node, Map parentMap) {
        if (parentMap != null) {
            for (String o : (Set<String>) parentMap.keySet()) {
                Page page = new Page(o);
                node.add(page);
                buildPage(page, (Map) parentMap.get(o));
            }
        }
    }

    private static List<Page> pageToList(Page page, List<Page> list) {
        list.add(page);
        if (page.getChildren().size() > 0) {
            for (Page child : page.getChildren()) {
                pageToList(child, list);
            }
        }
        return list;
    }

    private static void addToPage(List<Page> list, Map page) {
        for (String o : (Set<String>) page.keySet()) {
            for (int i = 0; i < list.size(); i++) {
                Page p = list.get(i);
                if (Objects.equals(p.getName(), o)) {
                    Map map = (Map) page.get(o);
                    List featureText = get("featureText", map);
                    List featureImage = get("featureImage", map);
                    String enterText = get("enterText", map);
                    String enterImage = get("enterImage", map);
                    Integer tapOffsetX = get("tapOffsetX", map);
                    Integer tapOffsetY = get("tapOffsetY", map);
                    tapOffsetX = tapOffsetX == null ? 0 : tapOffsetX;
                    tapOffsetY = tapOffsetY == null ? 0 : tapOffsetY;
                    p.setEnterText(enterText);
                    p.setEnterImage(enterImage);
                    p.setTapOffsetX(tapOffsetX);
                    p.setTapOffsetY(tapOffsetY);
                    if (featureImage != null) {
                        Object o1 = featureImage.get(featureImage.size() - 1);
                        if (o1 instanceof Integer) {
                            p.setSuitFeatureImageNum((Integer) o1);
                            p.setFeatureImage(featureImage.subList(0, featureImage.size() - 1));
                        } else {
                            p.setFeatureImage(featureImage.subList(0, featureImage.size()));
                        }
                    }
                    if (featureText != null) {
                        p.setFeatureText(featureText.subList(0, featureText.size() - 1));
                        Object o1 = featureText.get(featureText.size() - 1);
                        if (o1 instanceof Integer) {
                            p.setSuitFeatureTextNum((Integer) o1);
                            p.setFeatureText(featureText.subList(0, featureText.size() - 1));
                        } else {
                            p.setFeatureText(featureText.subList(0, featureText.size()));
                        }
                    }
                }
            }
        }
    }

    private static <T> T get(String type, Map map) {
        if (map != null) {
            Object o = map.get(type);
            return o != null ? (T) o : null;
        }
        return null;
    }
}
