//
//  SiteData.swift
//  
//
//  Created by Михаил Агеев macbook on 14.06.17.
//
//

import UIKit
struct PersonInfo {
    var name: String
    var count: Int
    init(name:String, count:Int) {
        self.name = name
        self.count = count
    }
}

class SiteData {
    
    static let defaultSiteName = "Default Name"
    static let defaultStatic: [String: Int] = ["Putin": 100, "Medvedev": 500]
    
    var site: String = defaultSiteName
    var stats: [String: Int] = defaultStatic
    
    var date: Date = Date()
    var total: Bool = false
}

extension SiteData{
    func personArray() -> [PersonInfo]?{
        var persons: [PersonInfo] = []
        for (key,value) in stats{
            let newPerson = PersonInfo.init(name: key, count: value)
            persons.append(newPerson)
        }
        return persons
    }
}

protocol SiteDataProviderDelegate {
    func requestDidCompleteSite(data: SiteData, forIndex index: Int)
}
