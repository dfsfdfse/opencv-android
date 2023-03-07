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
                    p.setFeatureText((List<String>) map.get("featureText"));
                    p.setFeatureImage((List<String>) map.get("featureImage"));
                }
            }
        }
    }
}
