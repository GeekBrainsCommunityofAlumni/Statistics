//
//  SiteDataArray.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 22.06.17.
//  Copyright © 2017 GB. All rights reserved.
//


//  Superstucture for SiteData. Alow work with SiteData array
class SiteDataArray {
    var array: [SiteData] = []{
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
    //  Subscript when alow access to all SiteData info
    subscript (index: Int) -> SiteData{
        get {
                return array[index]
        }
        set (newValue) {
            array[index] = newValue
        }
    }
    //  Array site-rank info for all data
    var sites: [Info] = []
    //  Array person-rank info for all data
    var ranks: [Info] = []
    //  Return SiteDateArray with info only one site
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
    //  Create array for present site info in aspect date - rank
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
