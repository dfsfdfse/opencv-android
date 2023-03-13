package org.realcool.bean;

import android.annotation.SuppressLint;
import android.content.Context;

import org.realcool.game.WorldMode;
import org.realcool.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class PageLoader {
    private List<Page> pageList;

    private Context ctx;

    private String source;

    public List<Page> getPageList() {
        if (pageList == null) return loadPage(ctx, source);
        return pageList;
    }

    public static class PageLoaderHolder {
        public static PageLoader INSTANCE = new PageLoader();
    }

    public static PageLoader getInstance() {
        return PageLoaderHolder.INSTANCE;
    }

    public WorldMode getWorldMode() {
        for (int i = 0; i < pageList.size(); i++) {
            Page page = pageList.get(i);
            if (page instanceof WorldMode) {
                return (WorldMode) page;
            }
        }
        return null;
    }

    public List<Page> loadPage(Context context, String source) {
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
        this.pageList = list;
        this.ctx = context;
        this.source = source;
        return list;
    }

    private void buildPage(Page node, Map parentMap) {
        if (parentMap != null) {
            for (String o : (Set<String>) parentMap.keySet()) {
                Page page = null;
                if (Objects.equals(o, "世界模式")){
                    page = new WorldMode();
                } else {
                    page = new Page(o);
                }
                node.add(page);
                buildPage(page, (Map) parentMap.get(o));
            }
        }
    }

    private List<Page> pageToList(Page page, List<Page> list) {
        list.add(page);
        if (page.getChildren().size() > 0) {
            for (Page child : page.getChildren()) {
                pageToList(child, list);
            }
        }
        return list;
    }

    private void addToPage(List<Page> list, Map page) {
        for (String o : (Set<String>) page.keySet()) {
            for (int i = 0; i < list.size(); i++) {
                Page p = list.get(i);
                if (Objects.equals(p.getName(), o)) {
                    Map map = (Map) page.get(o);
                    p.setTapOffsetX(getValue("tapOffsetX", map, 0));
                    p.setTapOffsetY(getValue("tapOffsetY", map, 0));
                    p.setFeatureText(get("featureText", map));
                    p.setFeatureImage(get("featureImage", map));
                    p.setSuitFeatureTextNum(getValue("featureTextNum", map, 1));
                    p.setSuitFeatureImageNum(getValue("featureImageNum", map, 1));
                    p.setEnterText(get("enterText", map));
                    p.setEnterImage(get("enterImage", map));
                    p.setOutText(get("outText", map));
                    p.setOutImage(get("outImage", map));
                }
            }
        }
    }

    private <T> T get(String type, Map map) {
        if (map != null) {
            Object o = map.get(type);
            return o != null ? (T) o : null;
        }
        return null;
    }

    private Integer getValue(String type, Map map, int value) {
        if (map != null) {
            Integer o = (Integer) map.get(type);
            return o == null ? value : o;
        }
        return value;
    }
}
