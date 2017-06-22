//
//  TableViewController.swift
//  ios
//
//  Created by Михаил Агеев macbook on 12.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit

class TableViewController: UITableViewController, DataManagerProtocol {
    var dm = DataManager.initWithFakeManager()
//    var dm = DataManager.initWithNetworkManager()
    var data = SiteDataArray(){
        didSet{
            self.sites = data.siteNameArray()
        }
    }
    
    var sites: [String] = []{
        didSet{
            tableView.reloadData()
        }
    }
    
//    func uniqueSite(data: [SiteData]) -> [String]{
//        var arrayOfSiteName: [String] = []
//        for item in data {
//            arrayOfSiteName.append(item.site)
//        }
//        arrayOfSiteName = Array(Set(arrayOfSiteName))
//        return arrayOfSiteName
//    }
    func didCompliteRequestOnData(data: SiteDataArray, date1: Date, date2: Date){
    }
    
    func didCompliteRequestTotal(data: SiteDataArray){
            self.data = data
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        dm.delegat = self
        dm.getSumaryData()
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return sites.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath)

        cell.textLabel?.text = sites[indexPath.row]

        return cell
    }
 

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "statDV" {
            let destenationVC = segue.destination as! TableViewControllerAllStatDV
            if let selectedItem = tableView.indexPathForSelectedRow{
                destenationVC.siteName = sites[selectedItem.row]
                let siteInfo = self.data.filterBySite(siteName: destenationVC.siteName)
                destenationVC.persons = (siteInfo.first?.personArray())!
            }
        }
    }
 

}

