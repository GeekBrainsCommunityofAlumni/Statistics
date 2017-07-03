//
//  RangeSiteStaticWithDateChart.swift
//  GBCAStatistics
//
//  Created by Dmytro Shcherbachenko on 03.07.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit
import Charts

class RangeSiteStaticWithDateChartVC: UIViewController, ChartViewDelegate {
    var personName: String!
    var siteName: String!
    var infoWithDate: [InfoWithDate] = []
    
    @IBOutlet weak var lineChartView: LineChartView!

    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.setChartData(infoWithDate: infoWithDate)
        self.lineChartView.delegate = self
        self.navigationItem.title = siteName
        self.lineChartView.chartDescription?.text = ""
    }
    
    func setChartData(infoWithDate: [InfoWithDate]){
        var datesInString: [String] = []
//        let range = DateRange(beginDate: ((infoWithDate.first)?.date)!, endDate: ((infoWithDate.last)?.date)!)
//        for currentDate in range {
//            datesInString.append(currentDate.toStringShort()!)
//        }
        self.lineChartView.xAxis.valueFormatter = IndexAxisValueFormatter(values: datesInString)
        
        var values = [ChartDataEntry]()
        for i in 0..<infoWithDate.count {
            values.append(ChartDataEntry(x: Double(i), y: Double(infoWithDate[i].count)))
        }
        let set = LineChartDataSet(values: values, label: personName)
        set.axisDependency = .left // Line will correlate with left axis values
        set.setColor(UIColor.red.withAlphaComponent(0.5)) // our line's opacity is 50%
        set.setCircleColor(UIColor.red) // our circle will be dark red
        set.lineWidth = 2.0
        set.circleRadius = 6.0 // the radius of the node circle
        set.fillAlpha = 65 / 255.0
        set.fillColor = UIColor.red
        set.highlightColor = UIColor.white
        set.drawCircleHoleEnabled = true
        var dataSets = [LineChartDataSet]()
        dataSets.append(set)
        let data = LineChartData(dataSets: dataSets)
        data.setValueTextColor(UIColor.black)
        self.lineChartView.data = data
    }
}

extension Date {
    func toStringShort() -> String?{
        let formater = DateFormatter()
        formater.dateFormat = "dd.MM"
        return formater.string(from: self)
}
}
