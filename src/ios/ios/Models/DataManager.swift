//
//  DataManager.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 14.06.17.
//  Copyright © 2017 GB. All rights reserved.
//
import UIKit

protocol DataManagerProtocol {
    func didCompliteRequestOnRange(data: SiteDataArray, dateBegin: Date, dateEnd: Date) // возвращает статистику за период
    func didCompliteRequestTotal(data: SiteDataArray) // возвращает общую статистику
}

//  задача класса: выбрать источник данных (кеш, сеть и т.п.) и передать в него запрос на информацию
//  получить данные от источника, сохранить их в кеше и отдать на уровень выше
class DataManager: DataProviderProtocol{
    private var dbManager: DataProvider? // класс обслуживающий кеш
    private var networkManager: DataProvider? // класс работающий с сетью
    var delegat: DataManagerProtocol?
    
    // запрос общей статистики
    func getTotalData(){
        if let dbmanager = self.dbManager { // если используется кеш
            dbmanager.getTotalData() // ищем данные в кеше
        } else if let networkmanager = self.networkManager { // если есть сетевой менеджер
            networkmanager.getTotalData() // делаем запрос к сети
        } else {
            // источника не существует. ошибка
            print("not have source")
            delegat?.didCompliteRequestTotal(data: SiteDataArray()) // отдаем пустую таблицу
        }
        
    }
    // запрос статистики на дату
    func getOnRangeData(dateBegin: Date, dateEnd: Date){
        if let dbmanager = self.dbManager {
            dbmanager.getOnRangeData(dateBegin: dateBegin, dateEnd: dateEnd)
        } else if let networkmanager = self.networkManager {
            networkmanager.getOnRangeData(dateBegin: dateBegin, dateEnd: dateEnd)
        } else {
            // источника не существует. ошибка
            print("not have source")
            delegat?.didCompliteRequestOnRange(data: SiteDataArray(), dateBegin: dateBegin, dateEnd: dateEnd)
        }
    }
    
    init(dbManager: DataProvider?, networkManager: DataProvider?) {
        self.dbManager = dbManager
        self.dbManager?.delegat = self
        self.networkManager = networkManager
        self.networkManager?.delegat = self
    }
    func didCompliteRequestOnRange(data: SiteDataArray, dateBegin: Date, dateEnd: Date, dataProvider: DataProvider){
        if data.isEmpty == true{ // если поиск вернул пустую таблицу
            if dataProvider.type == .db { //и это было обращение в кеш
                self.networkManager?.getOnRangeData(dateBegin: dateBegin, dateEnd: dateEnd) //тянем данные из сети
            } else {
                // нет данных, возвращаем пустую таблицу
                delegat?.didCompliteRequestOnRange(data: SiteDataArray(), dateBegin: dateBegin, dateEnd: dateEnd)
            }
        } else {//данные получили
            if dataProvider.type == .source { // если источником является сеть или что то подобное
                self.dbManager?.putData(data: data) // сохраняем данные в локальный кеш
                delegat?.didCompliteRequestOnRange(data: data, dateBegin: dateBegin, dateEnd: dateEnd) // отдаем их на уровень выше
            } else { // если данные вытянули из кеша сразу отдаем их на уровень выше
                delegat?.didCompliteRequestOnRange(data: data, dateBegin: dateBegin, dateEnd: dateEnd)
            }
        }
    }
    func didCompliteRequestTotal(data: SiteDataArray, dataProvider: DataProvider){
        if data.isEmpty == true{
            if dataProvider.type == .db {
                self.networkManager?.getTotalData()
            } else {
                // нет данных
                delegat?.didCompliteRequestTotal(data: SiteDataArray())
            }
        } else {
            if dataProvider.type == .source {
                self.dbManager?.putData(data: data)
                delegat?.didCompliteRequestTotal(data: data)
            } else {
                delegat?.didCompliteRequestTotal(data: data)
            }
        }
    }
    // статический метод возвращающий DataManager c NetworkManager
    static func initWithNetworkManager() -> DataManager{
        let nm = NetworkManager()
        let dm = DataManager(dbManager: nil, networkManager: nm)
        return dm
    }
    // статический метод возвращающий DataManager c FakeManager
    static func initWithFakeManager() -> DataManager{
        let fm = FakeManager()
        let dm = DataManager(dbManager: nil, networkManager: fm)
        return dm
    }
}
















































