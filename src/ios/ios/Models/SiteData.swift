//
//  SiteData.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 25.06.17.
//  Copyright © 2017 GB. All rights reserved.
//
import UIKit
struct Info {
    var name: String
    var count: Int
    init(name:String, count:Int) {
        self.name = name
        self.count = count
    }
}

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

