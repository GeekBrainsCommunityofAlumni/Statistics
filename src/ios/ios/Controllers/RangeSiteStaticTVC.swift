//
//  RangeSiteStaticTVC.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 25.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit

class RangeSiteStaticTVC: UITableViewController, DataManagerProtocol {
    var siteName: String!
    var dateBegin: Date!
    var dateEnd: Date!
    var siteDataArray = SiteDataArray()
    var dm = DataManager.initWithNetworkManager()
    
    override func viewDidLoad() {
        dm.delegat = self
        dm.getOnRangeData(dateBegin: dateBegin, dateEnd: dateEnd)
    }
    
    func didCompliteRequestOnRange(data: SiteDataArray, dateBegin: Date, dateEnd: Date){
        siteDataArray = data.filterBySite(siteName: siteName)
        tableView.reloadData()
    }
    
    func didCompliteRequestTotal(data: SiteDataArray){
        
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return siteDataArray.ranks.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "RangeSiteStaticCell", for: indexPath) as! RangeSiteStaticTVCell
        cell.nameLabel.text = siteDataArray.ranks[indexPath.row].name
        cell.countLabel.text = String(siteDataArray.ranks[indexPath.row].count)
        return cell
    }
    
}
