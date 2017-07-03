//
//  TotalStaticRootTVC.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 25.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit

class TotalStaticRootTVC: UITableViewController {
//    var dm = DataManager.initWithNetworkManager()
    var dm = DataManager.initWithFakeManager()
    var siteDataArray = SiteDataArray()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        dm.delegat = self
        dm.getTotalData()
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return siteDataArray.sites.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "TotalStaticCell", for: indexPath) as! TotalStaticRootTVCell
        cell.nameLabel.text = siteDataArray.sites[indexPath.row].name
        cell.countLabel.text = String(siteDataArray.sites[indexPath.row].count)
        return cell
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "statDV" {
            let destenationVC = segue.destination as! TotalSiteStaticTVC
            if let selectedItem = tableView.indexPathForSelectedRow{
                destenationVC.siteDataArray = siteDataArray.filterBySite(siteName: siteDataArray.sites[selectedItem.row].name)
            }
        }
        if segue.identifier == "TotalSiteStaticCharVC2" {
            let destenationVC = segue.destination as! TotalSiteStaticCharVC
            destenationVC.array = siteDataArray.sites
        }
    }
}

extension TotalStaticRootTVC: DataManagerDelegat {
    func didCompliteRequestOnRange(data: SiteDataArray, dateBegin: Date, dateEnd: Date) {
    }
    
    func didCompliteRequestTotal(data: SiteDataArray) {
        self.siteDataArray = data
        tableView.reloadData()
    }
}
