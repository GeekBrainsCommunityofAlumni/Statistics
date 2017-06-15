//
//  DataManager.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 14.06.17.
//  Copyright © 2017 GB. All rights reserved.
//
import UIKit

protocol DataManagerProtocol {
    func didCompliteRequestOnData(data: [SiteData], date: Date);
    func didCompliteRequestTotal(data: [SiteData], date: Date)
}

class DataManager: DataProviderProtocol{
    var dbManager: DataProvider?
    var networkManager: DataProvider?
    var delegat: DataManagerProtocol?
    
    func getSumaryData(date: Date){
        if let dbmanager = self.dbManager {
            dbmanager.getSumaryData(date: date)
        } else if let networkmanager = self.networkManager {
            networkmanager.getSumaryData(date: date)
        } else {
            // источника не существует. ошибка
            print("not have source")
            delegat?.didCompliteRequestTotal(data: [], date: date)
        }
        
    }
    
    func getDataOnDate(date: Date){
        if let dbmanager = self.dbManager {
            dbmanager.getDataOnDate(date: date)
        } else if let networkmanager = self.networkManager {
            networkmanager.getDataOnDate(date: date)
        } else {
            // источника не существует. ошибка
            print("not have source")
            delegat?.didCompliteRequestOnData(data: [], date: date)
        }
    }
    
    init(dbManager: DataProvider?, networkManager: DataProvider?) {
        self.dbManager = dbManager
        self.dbManager?.delegat = self
        self.networkManager = networkManager
        self.networkManager?.delegat = self
    }
    
    func didCompliteRequestOnData(data: [SiteData], date: Date, dataProvider: DataProvider){
        if data.isEmpty == true{
            if dataProvider.type == .db {
                self.networkManager?.getDataOnDate(date: date)
            } else {
                // нет данных
                delegat?.didCompliteRequestOnData(data: [], date: date)
            }
        } else {
            if dataProvider.type == .source {
                self.dbManager?.putData(data: data)
                delegat?.didCompliteRequestOnData(data: data, date: date)
            } else {
                delegat?.didCompliteRequestOnData(data: data, date: date)
            }
        }
    }
    
    
    func didCompliteRequestTotal(data: [SiteData], date: Date, dataProvider: DataProvider){
        if data.isEmpty == true{
            if dataProvider.type == .db {
                self.networkManager?.getSumaryData(date: date)
            } else {
                // нет данных
                delegat?.didCompliteRequestTotal(data: [], date: date)
            }
        } else {
            if dataProvider.type == .source {
                self.dbManager?.putData(data: data)
                delegat?.didCompliteRequestTotal(data: data, date: date)
            } else {
                delegat?.didCompliteRequestTotal(data: data, date: date)
            }
        }
    }
}
















































