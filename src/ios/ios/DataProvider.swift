//
//  DataProvider.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 14.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit

// тип дата провайдера
enum DataProviderType {
    case db         // кеш (получени/запись данных)
    case source     //  сеть (только получение данных)
}

// протокол для возврата из источника данных. dataProvider - дает нам возможность в вызывающем методе получить информацию о типе провайдера (кеш/сеть) и т.д.
protocol DataProviderProtocol {
    func didCompliteRequestOnData(data: SiteDataArray, date1: Date, date2: Date, dataProvider: DataProvider)
    func didCompliteRequestTotal(data: SiteDataArray, dataProvider: DataProvider)
}

class DataProvider{
    var type: DataProviderType!
    //let priority: Int = 0    уже не вижу смысла в этом параметре
    var delegat: DataProviderProtocol!
    
    func getSumaryData(){
        
    }
    func getDataOnDate(date1: Date, date2: Date){
        
    }
    func putData(data: SiteDataArray){
        
    }
}
