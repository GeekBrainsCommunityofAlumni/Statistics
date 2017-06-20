package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.entity.Person;
import com.gb.statistics.webservice.entity.Site;
import com.gb.statistics.webservice.repository.MockPersonRepository;
import com.gb.statistics.webservice.repository.MockSiteRepository;

public class InitTestModel {
    public static final int NOT_EXISTING_ID = 100500;
    public static final int EXISTING_ID = 1;
    public static final String EXISTING_PERSON_NAME = "Путин";
    public static final String NOT_EXISTING_PERSON_NAME = "Медведев";
    public static final String EXISTING_SITE_NAME = "lenta.ru";
    public static final String EXISTING_SITE_URL = "lenta.ru/index";
    public static final String NOT_EXISTING_SITE_NAME = "regnum.ru";
    public static final String NOT_EXISTING_SITE_URL = "regnum.ru/news";
    public static final String UPDATED_SITE_NAME = "LENTA.RU";
    public static final String UPDATED_PERSON_NAME = "Путин В.В";
    public static final String UPDATED_SITE_URL = "www.lenta.ru/index/news";



    private InitTestModel(){
        initPersonRepositoryMock();
        initSiteRepositoryMock();
    }


    public static InitTestModel init(){
        return new InitTestModel();
    }

    private void initPersonRepositoryMock() {
        MockPersonRepository.personsList.add(new Person(1, "Путин"));
        MockPersonRepository.personsList.add(new Person(2, "Навальный"));
        MockPersonRepository.personsList.add(new Person(3, "Зюганов"));
    }

    private void initSiteRepositoryMock() {
        MockSiteRepository.siteList.add(new Site(1, "lenta.ru", "lenta.ru/index"));
        MockSiteRepository.siteList.add(new Site(2, "ria.ru", "ria.ru/index"));
        MockSiteRepository.siteList.add(new Site(3, "newsru.com", "newsru.com"));
    }
}
