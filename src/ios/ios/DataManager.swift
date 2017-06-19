//
//  DataManager.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 14.06.17.
//  Copyright © 2017 GB. All rights reserved.
//
import UIKit

protocol DataManagerProtocol {
    func didCompliteRequestOnData(data: [SiteData], date1: Date, date2: Date) // возвращает статистику за период
    func didCompliteRequestTotal(data: [SiteData]) // возвращает общую статистику
}

//  задача класса: выбрать источник данных (кеш, сеть и т.п.) и передать в него запрос на информацию
//  получить данные от источника, сохранить их в кеше и отдать на уровень выше
class DataManager: DataProviderProtocol{
    private var dbManager: DataProvider? // класс обслуживающий кеш
    private var networkManager: DataProvider? // класс работающий с сетью
    var delegat: DataManagerProtocol?
    
    // запрос общей статистики
    func getSumaryData(date: Date){
        if let dbmanager = self.dbManager { // если используется кеш
            dbmanager.getSumaryData() // ищем данные в кеше
        } else if let networkmanager = self.networkManager { // если есть сетевой менеджер
            networkmanager.getSumaryData() // делаем запрос к сети
        } else {
            // источника не существует. ошибка
            print("not have source")
            delegat?.didCompliteRequestTotal(data: []) // отдаем пустую таблицу
        }
        
    }
    // запрос статистики на дату
    func getDataOnDate(date1: Date, date2: Date){
        if let dbmanager = self.dbManager {
            dbmanager.getDataOnDate(date1: date1,date2: date2)
        } else if let networkmanager = self.networkManager {
            networkmanager.getDataOnDate(date1: date1, date2: date2)
        } else {
            // источника не существует. ошибка
            print("not have source")
            delegat?.didCompliteRequestOnData(data: [], date1: date1, date2: date2)
        }
    }
    
    init(dbManager: DataProvider?, networkManager: DataProvider?) {
        self.dbManager = dbManager
        self.dbManager?.delegat = self
        self.networkManager = networkManager
        self.networkManager?.delegat = self
    }
    
    func didCompliteRequestOnData(data: [SiteData], date1: Date, date2: Date, dataProvider: DataProvider){
        if data.isEmpty == true{ // если поиск вернул пустую таблицу
            if dataProvider.type == .db { //и это было обращение в кеш
                self.networkManager?.getDataOnDate(date1: date1, date2: date2) //тянем данные из сети
            } else {
                // нет данных, возвращаем пустую таблицу
                delegat?.didCompliteRequestOnData(data: [], date1: date1, date2: date2)
            }
        } else {//данные получили
            if dataProvider.type == .source { // если источником является сеть или что то подобное
                self.dbManager?.putData(data: data) // сохраняем данные в локальный кеш
                delegat?.didCompliteRequestOnData(data: data, date1: date1, date2: date2) // отдаем их на уровень выше
            } else { // если данные вытянули из кеша сразу отдаем их на уровень выше
                delegat?.didCompliteRequestOnData(data: data, date1: date1, date2: date2)
            }
        }
    }
    
    
    func didCompliteRequestTotal(data: [SiteData], dataProvider: DataProvider){
        if data.isEmpty == true{
            if dataProvider.type == .db {
                self.networkManager?.getSumaryData()
            } else {
                // нет данных
                delegat?.didCompliteRequestTotal(data: [])
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
    // статический метод возвращающий DataManager под данную ситуацию.
    static func initWithNetworkManager() -> DataManager{
        let nm = NetworkManager()
        let dm = DataManager(dbManager: nil, networkManager: nm)
        return dm
    }
}
















































