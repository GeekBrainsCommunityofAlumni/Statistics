//
//  DataProvider.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 14.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit

enum DataProviderType {
    case db, source
}

protocol DataProviderProtocol {
    func didCompliteRequestOnData(data: [SiteData], date: Date, dataProvider: DataProvider)
    func didCompliteRequestTotal(data: [SiteData], date: Date, dataProvider: DataProvider)
}

class DataProvider{
    var type: DataProviderType!
    let priority: Int = 0
    var delegat: DataProviderProtocol!
    func getSumaryData(date: Date){
        
    }
    func getDataOnDate(date: Date){
        
    }
    func putData(data: [SiteData]){
        
    }
}
