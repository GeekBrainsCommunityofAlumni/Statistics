//
//  Utils.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 19.06.17.
//  Copyright © 2017 GB. All rights reserved.
//
import UIKit

struct DateRange: Sequence{
    var beginDate: Date
    var endDate:Date
    
    init(beginDate: Date, endDate: Date) {
        self.beginDate = beginDate
        self.endDate = endDate
    }
    
    func makeIterator() -> DateRangeIterator {
        return DateRangeIterator(dateRange: self)
    }
    
    struct DateRangeIterator: IteratorProtocol{
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
            guard dateRange.endDate.compare(nextDate) == .orderedDescending else {
                return nil
            }
            counter += 1
            return nextDate
        }
    }
}
