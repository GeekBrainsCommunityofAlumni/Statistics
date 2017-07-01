//
//  FakeManager.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 14.06.17.
//  Copyright © 2017 GB. All rights reserved.
//
import UIKit
struct DateRange: Sequence{
    var beginDate: Date
    var endDate:Date
    
    init(beginDate: Date, endDate: Date) {
        self.beginDate = beginDate
        self.endDate = endDate
    }
    
    func makeIterator() -> DateRangeIterator {
        return DateRangeIterator(dateRange: self)
    }
    
    struct DateRangeIterator: IteratorProtocol{
        let dateRange: DateRange
        let calendar = Calendar(identifier: .gregorian)
        var counter = 0
        
        init(dateRange: DateRange ) {
            self.dateRange = dateRange
        }
        
        mutating func next() -> Date? {
            guard let nextDate = calendar.date(byAdding: Calendar.Component.day, value: counter, to: dateRange.beginDate) else {
                return nil
            }
            guard dateRange.endDate.compare(nextDate) == .orderedDescending else {
                return nil
            }
            counter += 1
            return nextDate
        }
    }
}

// Class for test
class FakeManager:DataProvider{
    let sitesName: [String] = ["Forestbook", "ForestNews", "ForestTymes"]
    let personsName: [String] = ["Red hat", "Gray woolf", "Grandma", "Logger"]
    var siteDataArray: SiteDataArray!
    
    func generateFakeData() -> [SiteData]{
        var dateRange: DateRange {
            let dateBegin = Calendar.current.date(byAdding: Calendar.Component.month, value: -2, to: Date())!
            let dateEnd = Date()
            let range = DateRange(beginDate: dateBegin, endDate: dateEnd)
            return range
        }
        var sitesData: [SiteData] = []
        for currentDate in dateRange {
            for currentSite in sitesName {
                let siteData = SiteData ()
                siteData.site = currentSite
                siteData.total = false
                siteData.date = currentDate
                for currentPerson in personsName {
                    if Int(arc4random()) % 100 > 30 {
                        siteData.stats[currentPerson] = Int(arc4random()) % 100 + 1
                    }
                }
                if Array(siteData.stats).count > 0 {
                    sitesData.append(siteData)
                }
            }
        }
        return sitesData
    }
    
    override init() {
        super.init()
        self.type = .source
        self.siteDataArray = SiteDataArray.init(data: self.generateFakeData())
    }
    
    override func getTotalData() {
        var siteDataTotal: [SiteData] = []
        for currentSite in siteDataArray.sites{
            let temporaryDataArray = siteDataArray.filterBySite(siteName: currentSite.name)
            let temporarySiteData = SiteData()
            temporarySiteData.site = currentSite.name
            temporarySiteData.date = Date()
            temporarySiteData.total = true
            for currentRank in temporaryDataArray.ranks {
                temporarySiteData.stats[currentRank.name] = currentRank.count
            }
            siteDataTotal.append(temporarySiteData)
        }
        print("end")
        delegat.didCompliteRequestTotal(data: SiteDataArray(data: siteDataTotal), dataProvider: self)

//        var result: [SiteData] = []
//        let count = sitesName.count
//        for num in 0..<count{
//            let item: SiteData = SiteData()
//            item.date = Date()
//            item.site = sitesName[num]
//            for person in 0..<personsName.count{
//                item.stats[personsName[person]] = Int(arc4random()) % 100
//            }
//            item.total = false
//            result.append(item)
//        }
//        delegat.didCompliteRequestTotal(data: SiteDataArray(data: result), dataProvider: self)
    }

    override func getOnRangeData(dateBegin: Date, dateEnd: Date) {
        var siteOnRangeData: [SiteData] = []
        var range = DateRange(beginDate: dateBegin, endDate: dateEnd)
        for currentDate in range {
            for currentData in siteDataArray.array {
                if Calendar.current.compare(currentData.date, to: currentDate, toGranularity: Calendar.Component.day) == .orderedSame {
//                if currentData.date.compare(currentDate) == ComparisonResult.orderedSame {
                    siteOnRangeData.append(currentData)
                }
            }
        }
        delegat.didCompliteRequestOnRange(data: SiteDataArray(data: siteOnRangeData), dateBegin: dateBegin, dateEnd: dateEnd, dataProvider: self)
//        var result: [SiteData] = []
//        let count = sitesName.count
//        for num in 0..<count{
//            let item: SiteData = SiteData()
//            item.date = dateBegin
//            item.site = sitesName[num]
//            for person in 0..<personsName.count{
//                item.stats[personsName[person]] = Int(arc4random()) % 100
//            }
//            item.total = true
//            result.append(item)
//        }
//        delegat.didCompliteRequestOnRange(data: SiteDataArray(data: result), dateBegin: dateBegin, dateEnd: dateEnd, dataProvider: self)
        
    }
    
}
