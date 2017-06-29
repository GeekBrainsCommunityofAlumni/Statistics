//
//  SiteDataTest.swift
//  GBCA Statistics
//
//  Created by Dmytro Shcherbachenko on 29.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import XCTest
@testable import GBCAStatistics
class SiteDataTest: XCTestCase {
    var siteData: SiteData!
    override func setUp() {
        super.setUp()
        siteData = SiteData()
        siteData.site = "le.ru"
        siteData.date = Date()
        siteData.total = false
        siteData.stats["pu"] = 5
        siteData.stats["me"] = 6
        siteData.stats["na"] = 7
        siteData.stats["zh"] = 8
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        siteData = nil
        super.tearDown()
    }
    func testUpdateRanks(){
        var ranksResult: [Info] = []
        ranksResult.append(Info.init(name: "pu", count: 5))
        ranksResult.append(Info.init(name: "me", count: 6))
        ranksResult.append(Info.init(name: "na", count: 7))
        ranksResult.append(Info.init(name: "zh", count: 8))
        XCTAssertEqual(siteData.ranks, ranksResult, "yes")
    }
//    func testExample() {
//        // This is an example of a functional test case.
//        // Use XCTAssert and related functions to verify your tests produce the correct results.
//    }
    
    func testPerformanceExample() {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
    
}
