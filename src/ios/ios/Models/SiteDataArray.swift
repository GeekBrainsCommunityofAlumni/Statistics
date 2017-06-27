//
//  SiteDataArray.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 22.06.17.
//  Copyright © 2017 GB. All rights reserved.
//
// функции для операций с массивами рангов
func + (left: [Info], right: Info) -> [Info]{
    var newP: [Info] = left
    let findPerson = newP.filter { (person) -> Bool in person.name == right.name}
    if findPerson.isEmpty {
        newP.append(right)
    } else {
        newP = newP.map({ (personInfo) -> Info in
            let newPersonInfo = personInfo
            if personInfo.name == right.name {
                newPersonInfo.count = newPersonInfo.count + right.count
            }
            return newPersonInfo
        })
    }
    return newP
}

func + (left: [Info], right: [Info]) -> [Info]{
    var newP: [Info] = left
    for item in right {
        newP = newP + item
    }
    return newP
}

class SiteDataArray {
    private var array: [SiteData] = []{
        didSet {
            ranks = updateRanks()
            sites = updateSites()
        }
    }
    
    init(data: [SiteData]) {
        self.array = data
        self.ranks = self.updateRanks()
        self.sites = self.updateSites()
    }
    
    convenience init(){
        self.init(data: [])
    }
    
    subscript (index: Int) -> SiteData{
        get {
                return array[index]
        }
        set (newValue) {
            array[index] = newValue
        }
    }
    
    var sites: [Info] = []
    var ranks: [Info] = []
    
    func filterBySite(siteName: String) -> SiteDataArray{
        let filteredArray = array.filter { (siteData) -> Bool in
            if siteData.site == siteName {
                siteData.ranks = siteData.updateRanks()
                return true
            } else {
                return false
            }
        }
        let result = SiteDataArray(data: filteredArray)
        return result
    }
    
    func filterBySiteAndPerson(siteName: String, personName: String) -> [InfoWithDate] {
        var result: [InfoWithDate]
        let temp = self.filterBySite(siteName: siteName)
        result = temp.array.map { (siteData) -> InfoWithDate in
            let personRank = siteData.stats[personName]
            var infoWithDate: InfoWithDate
            if let rank = personRank {
                infoWithDate = InfoWithDate.init(name: personName, count: rank, date: siteData.date)
            } else {
                infoWithDate = InfoWithDate.init(name: "nil", count: 0, date: siteData.date)
            }
            return infoWithDate
        }
        result = result.filter{ (infoWithDate) in infoWithDate.name != "nil"}.sorted(by: { (info1, info2) -> Bool in
            info1.date < info2.date })
        return result
    }
    
    var isEmpty: Bool {
        get {
            return array.isEmpty
        }
    }
    
    var count: Int{
        get {
            return array.count
        }
    }
    
    var first: SiteData? {
        get {
            if array.count > 0 {
                return array.first
            } else {
                return nil
            }
        }
    }
    
    private func updateSites() -> [Info] {
        let allSitesLines = self.array.map { (siteData) -> String in
            return siteData.site
        }
        
        let uniqueSiteName = Array(Set(allSitesLines))
        var newSitesInfo: [Info] = []
        for item in uniqueSiteName {
            let newSiteInfoCount = self.array.filter({ (siteData) in siteData.site == item}).reduce(0) { (sum, siteData) -> Int in
                var sumCount = 0
                for item in siteData.ranks {
                    sumCount = sumCount + item.count
                }
                return sumCount
            }
            let newSiteInfo = Info.init(name: item, count: newSiteInfoCount)
            newSitesInfo.append(newSiteInfo)
        }
        return newSitesInfo
    }
    
    private func updateRanks() -> [Info] {
        var newRanks: [Info] = []
        for item in array{
            newRanks = newRanks + item.ranks
        }
        return newRanks
    }
}
