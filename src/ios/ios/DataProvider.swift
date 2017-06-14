//
//  DataProvider.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 14.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit

enum DataProviderType {
    case db
    case source
}

protocol DataProviderProtocol {
    func didCompliteRequestOnData(data: [SiteData]);
    func didCompliteRequestTotal(data: [SiteData])
}

class DataProvider{
    let type: DataProviderType
    let priority: Int
    func getSumaryData(){
        
    }
    func getDataOnDate(date: Date){
        
    }
}
