//
//  RangeRootTVC.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 25.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit

class RangeRootTVC: UITableViewController, DataManagerProtocol, UITextFieldDelegate {
    var dm = DataManager.initWithNetworkManager()
    var siteDataArray = SiteDataArray()
    
    @IBOutlet weak var dateEndSelectCell: UITableViewCell!
    @IBOutlet weak var dateBeginSelectCell: UITableViewCell!
    @IBOutlet weak var dateEndTextField: UITextField!
    @IBOutlet weak var dateBeginTextField: UITextField!
    @IBOutlet weak var selectSiteButton: UIButton!
    @IBOutlet weak var selectedSiteTextField: UITextField!
    @IBOutlet weak var dateBeginDatePicker: UIDatePicker!
    @IBOutlet weak var dateEndDatePicker: UIDatePicker!
    @IBOutlet weak var showButton: UIButton!
    
    @IBAction func showButtonPush(_ sender: Any) {
        hideDatePicker()
    }
    override func viewDidLoad() {
        dm.delegat = self
        dm.getTotalData()
        selectedSiteTextField.delegate = self
        dateBeginTextField.delegate = self
        dateEndTextField.delegate = self
        dateBeginDatePicker.addTarget(self, action: #selector(dateBeginChanged(_:)), for: .valueChanged)
        dateEndDatePicker.addTarget(self, action: #selector(dateEndChanged(_:)), for: .valueChanged)
        selectSiteButton.addTarget(self, action: #selector(hideDatePicker), for: .touchUpInside)
        hideDatePicker()
        dateBeginTextField.text = dateBeginDatePicker.date.toString()
        dateEndTextField.text = dateBeginDatePicker.date.toString()
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(hideDatePicker))
        tableView.addGestureRecognizer(tapGesture)

    }
    
    func dateBeginChanged(_ sender: UIDatePicker) {
        dateBeginTextField.text = dateBeginDatePicker.date.toString()
    }
    
    func dateEndChanged(_ sender: UIDatePicker) {
        dateEndTextField.text = dateEndDatePicker.date.toString()
    }
    func didCompliteRequestOnRange(data: SiteDataArray, dateBegin: Date, dateEnd: Date) {
    }
    
    func didCompliteRequestTotal(data: SiteDataArray) {
        siteDataArray = data
        if selectedSiteTextField.text == "" && siteDataArray.sites.count > 0{
            selectedSiteTextField.text = siteDataArray.sites.first?.name
        }
    }
    
    @IBAction func unwindToRangeRootTVC(segue:UIStoryboardSegue){
        let sourceTVC = segue.source as! RangeSelectSiteTVC
        selectedSiteTextField.text = sourceTVC.selectedSite
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        tableView.reloadData()
        if segue.identifier == "SelectSite" {
            let destenationTVC = segue.destination as! RangeSelectSiteTVC
            destenationTVC.siteDataArray = self.siteDataArray
        }
        if segue.identifier == "showRangeStatic" {
            let destinationTVC = segue.destination as! RangeSiteStaticTVC
            destinationTVC.siteName = selectedSiteTextField.text
            destinationTVC.dateBegin = dateBeginTextField.text?.toDate()
            destinationTVC.dateEnd = dateEndTextField.text?.toDate()
        }
    }
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        let cell = super.tableView(tableView, cellForRowAt: indexPath)
        if cell.isHidden {
            return 0
        } else {
            return super.tableView(tableView, heightForRowAt: indexPath)
        }
    }
    
    func hideDatePicker(){
        dateBeginSelectCell.isHidden = true
        dateEndSelectCell.isHidden = true
        tableView.reloadData()
    }
    
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        hideDatePicker()
        if textField == selectedSiteTextField {
            tableView.reloadData()
            selectSiteButton.sendActions(for: .touchUpInside)
        }
        if textField == dateBeginTextField {
            dateBeginSelectCell.isHidden = false
            tableView.reloadData()
        }
        if textField == dateEndTextField {
            dateEndSelectCell.isHidden = false
            tableView.reloadData()
        }
        return false
    }
}

extension String {
    func toDate() -> Date?{
        let formatter = DateFormatter()
        formatter.dateFormat = "dd.MM.yyyy"
        return formatter.date(from: self)
    }
}

extension Date{
    func toString() -> String?{
        let formater = DateFormatter()
        formater.dateFormat = "dd.MM.yyyy"
        return formater.string(from: self)
    }
}

