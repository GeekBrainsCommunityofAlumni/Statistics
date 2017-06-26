//
//  TotalSiteStaticTVC.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 25.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit

class TotalSiteStaticTVC: UITableViewController {
    var siteDataArray = SiteDataArray()
    
    @IBOutlet weak var siteNameLabel: UINavigationItem!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        siteNameLabel.title = (siteDataArray.sites.first)?.name
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return siteDataArray.ranks.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "StatDVCell", for: indexPath) as! TotalSiteStaticTVCell
        cell.nameLabel.text = siteDataArray.ranks[indexPath.row].name
        cell.countLabel.text = String(siteDataArray.ranks[indexPath.row].count)
        return cell
    }
}
