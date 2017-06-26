//
//  FakeManager.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 14.06.17.
//  Copyright © 2017 GB. All rights reserved.
//
import UIKit
// поставщик фейковых данных для тестирования пока не появятся данные на сервере
class FakeManager:DataProvider{
    let sitesName: [String] = ["Facebook", "VK", "News", "NYT"]
    let personsName: [String] = ["Красная шапочка", "Серый волк", "Бабуля", "Лесоруб"]
    
    override func getTotalData(){
        var result: [SiteData] = []
        let count = sitesName.count
        for num in 0..<count{
            let item: SiteData = SiteData()
            item.date = Date()
            item.site = sitesName[num]
            for person in 0..<personsName.count{
                item.stats[personsName[person]] = Int(arc4random()) % 100
            }
            item.total = false
            result.append(item)
        }
        delegat.didCompliteRequestTotal(data: SiteDataArray(data: result), dataProvider: self)
    }
    override init() {
        super.init()
        self.type = .source
    }
    override func getOnRangeData(dateBegin: Date, dateEnd: Date){
        var result: [SiteData] = []
        let count = sitesName.count
        for num in 0..<count{
            let item: SiteData = SiteData()
            item.date = dateBegin
            item.site = sitesName[num]
            for person in 0..<personsName.count{
                item.stats[personsName[person]] = Int(arc4random()) % 100
            }
            item.total = true
            result.append(item)
        }
        delegat.didCompliteRequestOnRange(data: SiteDataArray(data: result), dateBegin: dateBegin, dateEnd: dateEnd, dataProvider: self)
        
    }
    
}
