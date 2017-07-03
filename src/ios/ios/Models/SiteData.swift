//
//  SiteData.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 25.06.17.
//  Copyright © 2017 GB. All rights reserved.
//
import UIKit
//  Additional class for present info about persons total rank and total site rank
struct Info {
    var name: String
    var count: Int
    
    init(name:String, count:Int) {
        self.name = name
        self.count = count
    }
}
//  Functions for work with class Info
func + (left: [Info], right: Info) -> [Info]{
    var newP: [Info] = left
    let findPerson = newP.filter { (person) -> Bool in person.name == right.name}
    if findPerson.isEmpty {
        newP.append(right)
    } else {
        newP = newP.map({ (personInfo) -> Info in
            var newPersonInfo = personInfo
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
//  Additional class for present info about rank persons with date
struct InfoWithDate {
    var name: String
    var count: Int
    var date: Date!
    
    init(name: String, count: Int, date: Date) {
        self.name = name
        self.count = count
        self.date = date
    }
}
//  Base information about site and rank persons
class SiteData {
    var site: String = ""
    var stats: [String: Int] = [:] {
        didSet{
            ranks = updateRanks()
        }
    }

    var date: Date = Date()
    var total: Bool = false
    var ranks: [Info] = []
    
    func updateRanks() -> [Info] {
        var newRanks: [Info] = []
        for item in stats {
            let newRank = Info.init(name: item.key, count: item.value)
            newRanks.append(newRank)
        }
        return newRanks
    }
}
//  Extension for testing class SiteData. In project not use
extension Info:Equatable{
    static func == (left: Info, right: Info) -> Bool {
        return left.name == right.name && left.count == right.count
    }
}

