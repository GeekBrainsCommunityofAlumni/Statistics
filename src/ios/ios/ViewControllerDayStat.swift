//
//  ViewControllerDayStat.swift
//  ios
//
//  Created by Михаил Агеев macbook on 21.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit

class ViewControllerDayStat: UIViewController, DataManagerProtocol, UIPickerViewDelegate, UIPickerViewDataSource  {

    @IBOutlet weak var pickerSite: UIPickerView!
    @IBOutlet weak var pickerDate1: UIDatePicker!
    @IBOutlet weak var pickerDate2: UIDatePicker!
    
    var dm = DataManager.initWithFakeManager()
    
    
    var choosenSite: String = ""
    var choosenDate1: Date = Date()
    var choosenDate2: Date = Date()
    
    
    
    var data: [SiteData] = []{
        didSet{
            self.sites = self.uniqueSite(data: data)
        }
    }
    
    var sites: [String] = []{
        didSet{
            pickerSite.reloadInputViews()
        }
    }
    
    func uniqueSite(data: [SiteData]) -> [String]{
        var arrayOfSiteName: [String] = []
        for item in data {
            arrayOfSiteName.append(item.site)
        }
        arrayOfSiteName = Array(Set(arrayOfSiteName))
        return arrayOfSiteName
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        dm.delegat = self
        dm.getSumaryData()
        pickerSite.delegate = self
        pickerSite.dataSource = self
        self.choosenSite = self.sites[0]
    }

    // ВЫБОР САЙТА
    //количество секций
    func numberOfComponents(in pickerView: UIPickerView) -> Int{
        return 1
    }
    //количество полей в крутилке
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int{
        //количество сайтов
        return sites.count
        //return arraySite.count
    }
    
    
    //метод который реализует взаимодействие при выборе
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int){
        // по идеи должны сохранять выбор, чтобы в последствии его передать
        choosenSite = sites[row]
        
    }
    //название полей крутилки :)
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String?{
        // вывод названия сайтов
        return sites[row]
    }
    // ВЫБОР САЙТА ЗАВЕРШЕН
    
    func didCompliteRequestOnData(data: [SiteData], date1: Date, date2: Date){
        //self.data = data
    }
    
    func didCompliteRequestTotal(data: [SiteData]){
        self.data = data
    }
    

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "ShowStat"{
            let destinatinVC: TableViewControllerDayStatDV = segue.destination as! TableViewControllerDayStatDV
            destinatinVC.choosenSite = self.choosenSite
            destinatinVC.choosenDate1 = self.pickerDate1.date
            destinatinVC.choosenDate2 = self.pickerDate2.date
        }
        else{
            
        }
    }


}
