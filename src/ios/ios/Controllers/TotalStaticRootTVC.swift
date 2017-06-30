//
//  TotalStaticRootTVC.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 25.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit

class TotalStaticRootTVC: UITableViewController, DataManagerProtocol {
    var dm = DataManager.initWithNetworkManager()
    var data = SiteDataArray()
    func didCompliteRequestOnRange(data: SiteDataArray, dateBegin: Date, dateEnd: Date) {
    }
    
    func didCompliteRequestTotal(data: SiteDataArray){
        self.data = data
        tableView.reloadData()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        dm.delegat = self
        dm.getTotalData()
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data.sites.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "TotalStaticCell", for: indexPath) as! TotalStaticRootTVCell
        cell.nameLabel.text = data.sites[indexPath.row].name
        cell.countLabel.text = String(data.sites[indexPath.row].count)
        return cell
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "statDV" {
            let destenationVC = segue.destination as! TotalSiteStaticTVC
            if let selectedItem = tableView.indexPathForSelectedRow{
                destenationVC.siteDataArray = data.filterBySite(siteName: data.sites[selectedItem.row].name)
            }
        }
    }
}


