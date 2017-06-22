package com.gb.statistics.features.ai.interfaces;

import com.gb.statistics.features.ai.model.Site;
import javafx.collections.ObservableList;

public interface SiteListInterface {

    void refreshSiteList();

    boolean addSite(Site site);

    boolean updateSite(Site site);

    boolean deleteSite(Site site);

    ObservableList<Site> getSiteList();
}
