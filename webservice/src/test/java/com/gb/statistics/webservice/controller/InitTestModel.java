package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.entity.Person;
import com.gb.statistics.webservice.entity.Site;
import com.gb.statistics.webservice.repository.MockPersonRepository;
import com.gb.statistics.webservice.repository.MockSiteRepository;

public class InitTestModel {
    public static final int NOT_EXISTING_TEST_ID = 100500;

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
