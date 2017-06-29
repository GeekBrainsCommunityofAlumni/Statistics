//
//  DataManager.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 14.06.17.
//  Copyright © 2017 GB. All rights reserved.
//
import UIKit

protocol DataManagerProtocol {
    func didCompliteRequestOnRange(data: SiteDataArray, dateBegin: Date, dateEnd: Date)
    func didCompliteRequestTotal(data: SiteDataArray)
}

//  DataManager - class for get info in network source or database source(if present), save network info in database (if present). Return on level up resource requirements
class DataManager: DataProviderProtocol{
    private var dbManager: DataProvider?
    private var networkManager: DataProvider?
    var delegat: DataManagerProtocol?
    
    func getTotalData(){
        if let dbmanager = self.dbManager {
            dbmanager.getTotalData()
        } else if let networkmanager = self.networkManager {
            networkmanager.getTotalData()
        } else {
            print("not have source")
            delegat?.didCompliteRequestTotal(data: SiteDataArray())
        }
    }
    
    func getOnRangeData(dateBegin: Date, dateEnd: Date){
        if let dbmanager = self.dbManager {
            dbmanager.getOnRangeData(dateBegin: dateBegin, dateEnd: dateEnd)
        } else if let networkmanager = self.networkManager {
            networkmanager.getOnRangeData(dateBegin: dateBegin, dateEnd: dateEnd)
        } else {
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
        if data.isEmpty == true{
            if dataProvider.type == .db {
                self.networkManager?.getOnRangeData(dateBegin: dateBegin, dateEnd: dateEnd)
            } else {
                delegat?.didCompliteRequestOnRange(data: SiteDataArray(), dateBegin: dateBegin, dateEnd: dateEnd)
            }
        } else {
            if dataProvider.type == .source {
                self.dbManager?.putData(data: data)
                delegat?.didCompliteRequestOnRange(data: data, dateBegin: dateBegin, dateEnd: dateEnd)
            } else {
                delegat?.didCompliteRequestOnRange(data: data, dateBegin: dateBegin, dateEnd: dateEnd)
            }
        }
    }
    
    func didCompliteRequestTotal(data: SiteDataArray, dataProvider: DataProvider){
        if data.isEmpty == true{
            if dataProvider.type == .db {
                self.networkManager?.getTotalData()
            } else {
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
    //  Init class with NetworkManager
    static func initWithNetworkManager() -> DataManager{
        let nm = NetworkManager()
        let dm = DataManager(dbManager: nil, networkManager: nm)
        return dm
    }
    //  Init class with FakeManager
    static func initWithFakeManager() -> DataManager{
        let fm = FakeManager()
        let dm = DataManager(dbManager: nil, networkManager: fm)
        return dm
    }
}

