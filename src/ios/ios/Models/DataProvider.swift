//
//  DataProvider.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 14.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit

//  Type of data provider
enum DataProviderType {
    case db         // database. alow read and write
    case source     // network or etc. only read alow
}

protocol DataProviderDelegat {
    func didCompliteRequestOnRange(data: SiteDataArray, dateBegin: Date, dateEnd: Date, dataProvider: DataProvider)
    func didCompliteRequestTotal(data: SiteDataArray, dataProvider: DataProvider)
}

//  Abstract class for random data source and database. DataManager work only with subclass of DataProvider
class DataProvider {
    var type: DataProviderType!
    var delegat: DataProviderDelegat!
    //  Get total information
    func getTotalData() {
    }
    //  Get information on date range
    func getOnRangeData(dateBegin: Date, dateEnd: Date) {
    }
    //  Put information in database. In source DataProvider this funciton not work
    func putData(data: SiteDataArray) {
    }
}
