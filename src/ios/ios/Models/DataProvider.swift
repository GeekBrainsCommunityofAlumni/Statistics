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
    func didCompliteRequestOnRange(data: SiteDataArray, dateBegin: Date, dateEnd: Date, dataProvider: DataProvider)
    func didCompliteRequestTotal(data: SiteDataArray, dataProvider: DataProvider)
}

class DataProvider{
    var type: DataProviderType!
    var delegat: DataProviderProtocol!
    
    func getTotalData(){
    }
    
    func getOnRangeData(dateBegin: Date, dateEnd: Date){
    }
    
    func putData(data: SiteDataArray){
    }
}
