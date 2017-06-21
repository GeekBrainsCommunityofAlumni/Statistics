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
    
    var dm = DataManager.initWithFakeManager() // служит для загрузки списка всех сайтов
    
    var dmDate = DataManager.initWithFakeManager() //служит уже для загрузки данных по дате
    
    
    var choosenSite: String = ""
//    var choosenDate1: Date = Date()
//    var choosenDate2: Date = Date()
    
    
    
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
    
    
    var dataEvery: [SiteData] = []{
        didSet{
            self.sitesEvery = self.uniqueSiteEvery(dataEvery: dataEvery)
        }
    }
        
    var sitesEvery: [String] = []
    
    func uniqueSiteEvery(dataEvery: [SiteData]) -> [String]{
        var arrayOfSiteName: [String] = []
        for item in dataEvery {
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
        self.dataEvery = data
        
    }
    
    func didCompliteRequestTotal(data: [SiteData]){
        self.data = data
    }
    
    
    func DataRange() {
        dmDate.delegat = self
        dmDate.getDataOnDate(date1: pickerDate1.date, date2: pickerDate2.date)
    }
    

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "ShowStat"{
            self.DataRange()
            
            for i in sitesEvery{
                
                if self.choosenSite == i
                {
                    let destinatinVC: TableViewControllerDayStatDV = segue.destination as! TableViewControllerDayStatDV
                    destinatinVC.choosenSite = i
                    destinatinVC.choosenDate1 = self.pickerDate1.date
                    destinatinVC.choosenDate2 = self.pickerDate2.date
                    
                    let site = self.dataEvery.filter({ (item) -> Bool in
                        if item.site == destinatinVC.choosenSite{
                            return true
                        } else {
                            return false
                        }
                    }).first
                    destinatinVC.persons = (site?.personArray())!

                }
            }
            
            
        }
       
    }


}
