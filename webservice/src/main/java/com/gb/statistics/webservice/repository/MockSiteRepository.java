package com.gb.statistics.webservice.repository;

import com.gb.statistics.webservice.entity.Site;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MockSiteRepository implements SiteRepository{

    private static int count;
    public static List<Site> siteList = new LinkedList<Site>(); // Public for test. Because it is Mock!!!

    @Override
    public Site get(int id) {
        for (Site s : siteList) {
            if (s.getId()== id) return s;
        }
        return null;
    }

    @Override
    public List<Site> getAll() {
        return siteList;
    }

    @Override
    public Site add(Site s) {
        s.setId(++count);
        siteList.add(s);
        return s;
    }

    @Override
    public Site update(Site site) {
        for (Site s : siteList) {
            if (s.getId()==site.getId()){
                s.setName(site.getName());
                return s;
            }
        }
        return null;
    }

    @Override
    public boolean delete(Site site) {
        if(siteList.contains(site)){
            siteList.remove(site);
            return true;
        }
        return false;
    }

    @Override
    public boolean isExists(Site site) {
        return siteList.contains(site);
    }
}
