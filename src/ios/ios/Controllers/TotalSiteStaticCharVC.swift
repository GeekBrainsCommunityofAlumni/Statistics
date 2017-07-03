//
//  TotalSiteStaticCharVC.swift
//  GBCAStatistics
//
//  Created by Dmytro Shcherbachenko on 03.07.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit
import Charts

class TotalSiteStaticCharVC: UIViewController {
    var array: [Info] = []
    @IBOutlet var pieChartView: PieChartView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.setChartData()
    }
    
    func setChartData() {
        var values = [PieChartDataEntry]()
        for item in array {
            values.append(PieChartDataEntry(value: Double(item.count), label: item.name))
        }
        let set = PieChartDataSet(values: values, label: "")
        var colors: [NSUIColor] = []
        colors.append(contentsOf: ChartColorTemplates.vordiplom())
        set.colors = colors
        var dataSets = [PieChartDataSet]()
        dataSets.append(set)
        let data = PieChartData(dataSets: dataSets)
        data.setValueTextColor(UIColor.black)
        self.pieChartView.data = data
    }
}
