//
//  FakeManager.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 14.06.17.
//  Copyright © 2017 GB. All rights reserved.
//
import UIKit
struct DateRange: Sequence {
    var beginDate: Date
    var endDate:Date
    
    init(beginDate: Date, endDate: Date) {
        self.beginDate = beginDate
        self.endDate = endDate
    }
    
    func makeIterator() -> DateRangeIterator {
        return DateRangeIterator(dateRange: self)
    }
    
    struct DateRangeIterator: IteratorProtocol {
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
            guard Calendar.current.compare(dateRange.endDate, to: nextDate, toGranularity: Calendar.Component.day) == .orderedDescending else {
                return nil
            }
            counter += 1
            return nextDate
        }
    }
}
//  Generate fake data
class FakeGenerator {
    static let sitesName: [String] = ["Forestbook", "ForestNews", "ForestTymes"]
    static let personsName: [String] = ["Red hat", "Gray woolf", "Grandma", "Logger"]
    
    static let shared: FakeGenerator = {
        let instance = FakeGenerator()
        instance.siteDataArray = SiteDataArray(data: generateFakeData())
        return instance
    }()
    var siteDataArray: SiteDataArray!
    
    static func generateFakeData() -> [SiteData] {
        var dateRange: DateRange {
            let dateBegin = Calendar.current.date(byAdding: Calendar.Component.month, value: -4, to: Date())!
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
}
// Class for test
class FakeManager:DataProvider {
    var siteDataArray: SiteDataArray!
    
    override init() {
        super.init()
        self.type = .source
        self.siteDataArray = FakeGenerator.shared.siteDataArray!
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
        delegat.didCompliteRequestTotal(data: SiteDataArray(data: siteDataTotal), dataProvider: self)
    }

    override func getOnRangeData(dateBegin: Date, dateEnd: Date) {
        var siteOnRangeData: [SiteData] = []
        let range = DateRange(beginDate: dateBegin, endDate: dateEnd)
        for currentDate in range {
            for currentData in siteDataArray {
                if Calendar.current.compare(currentData.date, to: currentDate, toGranularity: Calendar.Component.day) == .orderedSame {
                    siteOnRangeData.append(currentData)
                }
            }
        }
        delegat.didCompliteRequestOnRange(data: SiteDataArray(data: siteOnRangeData), dateBegin: dateBegin, dateEnd: dateEnd, dataProvider: self)
    }
    
}
