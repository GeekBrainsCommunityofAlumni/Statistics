//
//  SiteDataArray.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 22.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

class SiteDataArray {
    var array: [SiteData] = []
    init(data: [SiteData]) {
        self.array = data
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
    func siteNameArray() -> [String] {
        var arrayOfSiteName: [String] = []
        for item in array {
            arrayOfSiteName.append(item.site)
        }
        arrayOfSiteName = Array(Set(arrayOfSiteName))
        return arrayOfSiteName
    }
    
//    func personNameArray() -> [String]{
//        var arrayOfPersonName: [String] = []
//        for item in array {
//            arrayOfPersonName.append(item.site)
//        }
//        arrayOfPersonName = Array(Set(arrayOfPersonName))
//        return arrayOfPersonName
//    }
    
    func filterBySite(siteName: String) -> SiteDataArray{
        let filteredArray = array.filter { (siteData) -> Bool in
            if siteData.site == siteName {
                return true
            } else {
                return false
            }
        }
        let result = SiteDataArray()
        result.array = filteredArray
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
}
