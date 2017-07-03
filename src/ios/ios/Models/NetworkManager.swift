//
//  NetworkManager.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 14.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON


internal enum QueryType{
    case total, onDate
}

fileprivate struct Site {
    var id: Int!
    var name: String!
}

fileprivate struct Person {
    var id: Int!
    var name: String!
}

fileprivate struct Rank {
    var siteID: Int!
    var personID: Int!
    var rank: Int!
    var date: Date?
}

protocol NetworkProcessDelegat {
    func didCompliteTotalRankProcess(siteData: [SiteData])
    func didCompliteOnRangRankProcess(siteData: [SiteData], dateBegin: Date, dateEnd: Date)
}

// Working with REST. NetworkProcess get information in REST and return structure how useed in programm
class NetworkProcess {
    var baseURL: String
    private var queue = DispatchQueue(label: "Network.manager")
    //  Flags for different process
    private var sitesDownloaded = false {
        didSet {
            if self.sitesDownloaded == true {
                self.getAllPerson()
            }
        }
    }
    
    private var personsDownloaded = false {
        didSet {
            if self.personsDownloaded == true {
                if self.queryType == .total {
                    self.getTotalRank()
                } else {
                    self.getOnRangRank()
                }
            }
        }
    }
    
    //  Counter for network operation. When have value 0 - all network operation is comlited
    private var rankCounter: Int? {
        didSet{
            if self.rankCounter == 0 {
                if self.queryType == .total {
                    self.compileTotalRankInfo()
                } else {
                    self.compileOnRangRankInfo()
                }
            }
        }
    }
    
    private func rankCounterIncrement() {
        queue.sync {
            if rankCounter == nil {
                rankCounter = 1
            } else {
                rankCounter = rankCounter! + 1
            }
        }
    }
    
    private func rankCounterDecrement() {
        queue.sync {
            rankCounter = rankCounter! - 1
        }
    }
    
    private var sites: [Site] = []
    private var persons: [Person] = []
    private var ranks: [Rank] = []
    
     private func rankAppend(rank: Rank) {
        queue.sync {
            ranks.append(rank)
        }
    }
    
    private var queryType: QueryType
    private var dateBegin: Date?
    private var dateEnd: Date?
    var delegat: NetworkProcessDelegat
    
     public func start() {
        getAllSites()
    }
    
    init(queryType: QueryType, delegat: NetworkProcessDelegat, baseURL: String, date1: Date?, date2: Date?) {
        self.baseURL = baseURL
        self.queryType = queryType
        self.delegat = delegat
        if queryType == .onDate {
            self.dateBegin = date1
            self.dateEnd = date2
        }
    }
    
    convenience init(delegat: NetworkProcessDelegat, baseURL: String) {
        self.init(queryType: .total, delegat: delegat, baseURL: baseURL, date1: nil, date2: nil)
    }
    
    convenience init(delegat: NetworkProcessDelegat, baseURL: String, date1: Date?, date2: Date?) {
        self.init(queryType: .onDate, delegat: delegat, baseURL: baseURL, date1: date1, date2: date2)
    }
    
    private func getAllSites() {
        let url = baseURL + "/site"
        Alamofire.request(url).responseJSON { (response) in
            if let error = response.error{
                print("Network error \(error)")
            } else {
                switch response.response!.statusCode {
                case 200:
                    if let data = response.data{
                        var sitesUpdate: [Site] = []
                        let json = JSON(data)
                        if let jsonArray = json.array {
                            for item in jsonArray {
                                var site = Site()
                                site.id = item["id"].intValue
                                site.name = item["name"].stringValue
                                sitesUpdate.append(site)
                            }
                            self.sites = sitesUpdate
                            self.sitesDownloaded = true
                        }
                    } else {
                        print("Error. Data is nil")
                    }
                case 400:
                    print("Error. Bad request")
                case 404:
                    print("Error. Data not found")
                    
                default: break
                }
            }
        }
    }
    
    private func getAllPerson() {
        let url = baseURL + "/person"
        Alamofire.request(url).responseJSON { (response) in
            if let error = response.error{
                print("Network error \(error)")
            } else {
                switch response.response!.statusCode{
                case 200:
                    if let data = response.data {
                        var personUpdate: [Person] = []
                        let json = JSON(data)
                        if let jsonArray = json.array {
                            for item in jsonArray {
                                var person = Person()
                                person.id = item["id"].intValue
                                person.name = item["name"].stringValue
                                personUpdate.append(person)
                            }
                            self.persons = personUpdate
                            self.personsDownloaded = true
                        }
                    } else {
                        print("Error. Data is nil")
                    }
                    
                case 400:
                    print("Error. Bad request")
                case 404:
                    print("Error. Data not found")
                    
                default: break
                }
            }
        }
    }
    
    private func getTotalRank() {
        for site in self.sites {
            self.rankCounterIncrement()
            let url = baseURL + "/stat/\(site.id!)"
            Alamofire.request(url).responseJSON { (response) in
                if let error = response.error {
                    print("Network error \(error)")
                } else {
                    switch response.response!.statusCode {
                    case 200:
                        if let data = response.data {
                            let json = JSON(data)
                            for itemJson in json.arrayValue {
                                var newTotalRank = Rank()
                                newTotalRank.siteID = site.id
                                newTotalRank.personID = (itemJson["person"].dictionaryValue)["id"]?.intValue
                                newTotalRank.rank = itemJson["rank"].intValue
                                self.rankAppend(rank: newTotalRank)
                            }
                        } else {
                            print("Error. Data is nil")
                        }
                    case 400:
                        print("Error. Bad request")
                    case 404:
                        print("Error. Data not found")
                        
                    default: break
                    }
                }
                self.rankCounterDecrement()
            }
        }
    }
    
    private func getOnRangRank() {
        guard let dBegin = self.dateBegin?.toStringBack() else {
            print("getOnDateRank. dateBegin is nil")
            return
        }
        guard let dEnd = self.dateEnd?.toStringBack() else {
            print("getOnDateRank. dateEnd is nil")
            return
        }
        for site in self.sites {
            for person in self.persons {
                rankCounterIncrement()
                let url = baseURL + "/stat/\(site.id!)/\(person.id!)/\(dBegin)/\(dEnd)"
                Alamofire.request(url).responseJSON { (response) in
                    if let error = response.error {
                        print("Network error \(error)")
                    } else {
                        switch response.response!.statusCode {
                        case 200:
                            if let data = response.data {
                                let json = JSON(data)
                                for itemJson in json.arrayValue {
                                    var newOnDateRank = Rank()
                                    newOnDateRank.siteID = site.id
                                    newOnDateRank.personID = person.id
                                    newOnDateRank.rank = itemJson["rank"].intValue
                                    let dateString = (itemJson["page"].dictionaryValue)["founddatetime"]?.stringValue
                                    newOnDateRank.date = dateString!.toDateBack()!
                                    self.rankAppend(rank: newOnDateRank)
                                }
                            } else {
                                print("Error. Data is nil")
                            }
                            
                        case 400:
                            print("Error. Bad request")
                        case 404:
                            print("Error. Data not found")
                            
                        default: break
                        }
                    }
                    self.rankCounterDecrement()
                }
            }
        }
    }
    
    private func compileTotalRankInfo() {
        guard self.ranks.count > 0 else {
            print("compileDataError. totalRank is nil")
            return
        }
        let queue = DispatchQueue.global(qos: .userInitiated)
        queue.async {
            var compiledRank: [SiteData] = []
            for site in self.sites {
                let siteArray = self.ranks.filter({ (rank) -> Bool in rank.siteID == site.id })
                let siteData = SiteData()
                siteData.site = site.name
                for person in self.persons {
                    let personRank = siteArray.filter({ (p) -> Bool in p.personID == person.id })
                    if personRank.isEmpty {
                        siteData.stats[person.name] = 0
                    } else {
                        siteData.stats[person.name] = personRank[0].rank
                    }
                }
                compiledRank.append(siteData)
            }
            DispatchQueue.main.async {
                self.delegat.didCompliteTotalRankProcess(siteData: compiledRank)
            }
        }
    }
    
    private func compileOnRangRankInfo() {
        guard self.ranks.count > 0 else {
            print("compileDataError. totalRank is nil")
            return
        }
        let queue = DispatchQueue.global(qos: .userInitiated)
        queue.async {
            var compiledRank: [SiteData] = []
            for site in self.sites {
                let siteArray = self.ranks.filter({ (rank) -> Bool in rank.siteID == site.id })
                var dateArray: [Date] = []
                for item in siteArray {
                    dateArray.append(item.date!)
                }
                dateArray = Array(Set(dateArray))
                for onDate in dateArray {
                    let siteData = SiteData()
                    siteData.site = site.name
                    siteData.date = onDate
                    for person in self.persons {
                        let personRank = siteArray.filter({ (p) -> Bool in p.personID == person.id && p.date == onDate })
                        if personRank.count > 0 {
                            siteData.stats[person.name] = (personRank.first)?.rank
                        }
                    }
                    if siteData.stats.count > 0 {
                        compiledRank.append(siteData)
                    }
                }
            }
            DispatchQueue.main.async {
                self.delegat.didCompliteOnRangRankProcess(siteData: compiledRank, dateBegin: self.dateBegin!, dateEnd: self.dateEnd!)
            }
        }
    }
}

//  Get information from network over REST. Work with query as FIFO
class NetworkManager:DataProvider {
    var baseURL: String = "http://94.130.27.143:8080/api"

    private var processes: [NetworkProcess] = [] {
        didSet (value) {
            if value.count == 0 && processes.count == 1 {
                processes.first?.start()
            } else if value.count < processes.count && processes.count > 0 {
                processes.first?.start()
            }
        }
    }
    
    override init() {
        super.init()
        self.type = .source
    }
    
    public override func getTotalData() {
        let process = NetworkProcess(delegat: self, baseURL: baseURL)
        processes.append(process)
    }
    
    public override func getOnRangeData(dateBegin: Date, dateEnd: Date) {
        let process = NetworkProcess(delegat: self, baseURL: baseURL, date1: dateBegin, date2: dateEnd)
        processes.append(process)
    }
    
    fileprivate func removeNetworkProcess() {
        processes.removeFirst()
    }
}

extension NetworkManager: NetworkProcessDelegat {
    internal func didCompliteTotalRankProcess(siteData: [SiteData]) {
        self.removeNetworkProcess()
        delegat.didCompliteRequestTotal(data: SiteDataArray(data: siteData), dataProvider: self)
    }
    
    internal func didCompliteOnRangRankProcess(siteData: [SiteData], dateBegin: Date, dateEnd: Date) {
        self.removeNetworkProcess()
        delegat.didCompliteRequestOnRange(data: SiteDataArray(data: siteData), dateBegin: dateBegin, dateEnd: dateEnd, dataProvider: self)
    }
}

//  Convert string to date with format useed in REST
extension String {
    func toDateBack() -> Date? {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        return formatter.date(from: self)
    }
}

extension Date {
    func toStringBack() -> String? {
        let formater = DateFormatter()
        formater.dateFormat = "yyyy-MM-dd"
        return formater.string(from: self)
    }
}











