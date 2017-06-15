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
        for _ in 0..<count{
            let item: SiteData = SiteData()
            item.date = Date()
            item.site = sitesName[Int(arc4random()) % count]
            for person in 0..<personsName.count{
                item.stats[personsName[person]] = Int(arc4random()) % 100
            }
            item.total = false
            result.append(item)
        }
        delegat.didCompliteRequestOnData(data: result, date: Date(), dataProvider: self)
    }
    
    override func getDataOnDate(date: Date){
        var result: [SiteData] = []
        let count = sitesName.count
        for _ in 0..<count{
            let item: SiteData = SiteData()
            item.date = date
            item.site = sitesName[Int(arc4random()) % count]
            for person in 0..<personsName.count{
                item.stats[personsName[person]] = Int(arc4random()) % 100
            }
            item.total = true
            result.append(item)
        }
        delegat.didCompliteRequestTotal(data: result, dataProvider: self)
    }
    
}
