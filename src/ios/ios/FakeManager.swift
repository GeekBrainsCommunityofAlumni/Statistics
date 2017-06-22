//
//  FakeManager.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 14.06.17.
//  Copyright © 2017 GB. All rights reserved.
//
import UIKit

class FakeManager:DataProvider{
    let sitesName: [String] = ["Facebook", "VK", "News", "NYT"]
    let personsName: [String] = ["Красная шапочка", "Серый волк", "Бабуля", "Лесоруб"]
    
    override func getSumaryData(){
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
    override func getDataOnDate(date1: Date, date2: Date){
        var result: [SiteData] = []
        let count = sitesName.count
        for num in 0..<count{
            let item: SiteData = SiteData()
            item.date = date1
            //item.site = sitesName[Int(arc4random()) % count]
            item.site = sitesName[num]
            for person in 0..<personsName.count{
                item.stats[personsName[person]] = Int(arc4random()) % 100
            }
            item.total = true
            result.append(item)
        }
        delegat.didCompliteRequestOnData(data: SiteDataArray(data: result), date1: date1, date2: date2, dataProvider: self)
        
    }
    
}
