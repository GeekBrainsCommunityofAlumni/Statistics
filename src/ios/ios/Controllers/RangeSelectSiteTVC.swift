//
//  RangeSelectSiteTVC.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 25.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit

class RangeSelectSiteTVC: UITableViewController {
    var siteDataArray = SiteDataArray()
    var selectedSite: String = ""
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return siteDataArray.sites.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "siteName", for: indexPath) as! RangeSelectSiteTVCell
        cell.siteNameLabel.text = siteDataArray.sites[indexPath.row].name
        return cell
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        selectedSite = siteDataArray.sites[indexPath.row].name
        performSegue(withIdentifier: "unwindToRangeRootTVCWithSegue", sender: self)
    }

}
