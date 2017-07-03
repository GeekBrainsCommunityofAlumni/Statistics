//
//  TotalSiteStaticCharVC.swift
//  GBCAStatistics
//
//  Created by Dmytro Shcherbachenko on 03.07.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit
import Charts

class TotalSiteStaticCharVC: UIViewController, ChartViewDelegate {
//    var siteDataArray = SiteDataArray()
    var array: [Info] = []
    @IBOutlet var pieChartView: PieChartView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.setChartData()
        pieChartView.delegate = self
        // Do any additional setup after loading the view.
    }
    
    func setChartData(){
        var values = [PieChartDataEntry]()
        for item in array {
//        for i in 0..<siteDataArray.ranks.count {
            values.append(PieChartDataEntry(value: Double(item.count), label: item.name))
//            values.append(ChartDataEntry(x: Double(i), y: Double(siteDataArray.ranks[i].count)))
        }
        let set = PieChartDataSet(values: values, label: "")
        var colors: [NSUIColor] = []
        colors.append(contentsOf: ChartColorTemplates.vordiplom())
//       ChartColorTemplates.vordiplom , ChartColorTemplates.joyful(), ChartColorTemplates.colorful(), ChartColorTemplates.liberty()]
        set.colors = colors
        var dataSets = [PieChartDataSet]()
        dataSets.append(set)
        let data = PieChartData(dataSets: dataSets)
        data.setValueTextColor(UIColor.black)
        self.pieChartView.data = data
    }
}
