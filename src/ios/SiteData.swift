//
//  SiteData.swift
//  
//
//  Created by Михаил Агеев macbook on 14.06.17.
//
//

import UIKit

class SiteData {
    
    static let defaultSiteName = "Default Name"
    static let defaultStatic: [String: Int] = ["Putin": 100, "Medvedev": 500]
    
    var site: String = defaultSiteName
    var stats: [String: Int] = defaultStatic
    
    var date: Date
    var total: Bool
}


protocol SiteDataProviderDelegate {
    func requestDidCompleteSite(data: SiteData, forIndex index: Int)
}
