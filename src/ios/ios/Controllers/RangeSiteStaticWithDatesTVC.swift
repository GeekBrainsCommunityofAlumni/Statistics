//
//  RangeSiteStaticWithDatesTVC.swift
//  GBCA Statistics
//
//  Created by Dmytro Shcherbachenko on 26.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit

class RangeSiteStaticWithDatesTVC: UITableViewController {
    var personName: String!
    var siteName: String!
    var infoWithDate: [InfoWithDate] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        navigationItem.title = personName //siteName + " " + personName
        tableView.reloadData()
    }

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return infoWithDate.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "dateWithCount", for: indexPath) as! RangeSiteStaticWithDatesCell
        cell.dateLabel.text = infoWithDate[indexPath.row].date.toString()
        cell.countLabel.text = String(infoWithDate[indexPath.row].count)
        return cell
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "RangeSiteStaticWithDateChartVC" {
            let destinationTVC = segue.destination as! RangeSiteStaticWithDateChartVC
            destinationTVC.siteName = siteName
            destinationTVC.personName = personName
            destinationTVC.infoWithDate = infoWithDate
        }
    }
}
