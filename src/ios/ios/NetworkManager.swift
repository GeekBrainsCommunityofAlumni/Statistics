//
//  NetworkManager.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 14.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON


internal enum QueryType{
    case total, onDate
}
fileprivate struct Site {
    var id: Int!
    var name: String!
}
fileprivate struct Person {
    var id: Int!
    var name: String!
}
fileprivate struct Rank {
    var siteID: Int!
    var personID: Int!
    var rank: Int!
    var date: Date?
}
// протокол для взаимодействия NetworkProcess с NetworkManager
// выполняется передача результата запроса от NetworkProcess к NetworkManager
protocol NetworkProcessProtocol{
    func didCompliteTotalRankProcess(siteData: [SiteData])
    func didCompliteOnDateRankProcess(siteData: [SiteData], date1: Date, date2: Date)
}
// выполняет запросы к серверу, объеденяет результаты запроса в таблицу
class NetworkProcess {
    var baseURL: String
    // очередь для сохранения результатов сетевых запросов с разных потоков
    private var queue = DispatchQueue(label: "Network.manager")
    //-------------------------------------------------------------------------
    // флаги процессов. стартуют следующий за ними процесс
    // первый процесс тартует после выполнения функции start, остальные по флагам
    private var sitesDownloaded = false{
        didSet {
            if self.sitesDownloaded == true{
                // сайты загружены. запускаем загрузку персон
                self.getAllPerson()
            }
        }
    }
    private var personsDownloaded = false{
        didSet {
            if self.personsDownloaded == true {
                // персоны загружены. определяем тип запроса (общий или ежедневный)
                if self.queryType == .total {
                    // запускаем загрузку общей статистики
                    self.getTotalRank()
                } else {
                    // запускаем загрузку ежедневной статистики
                    self.getOnDateRank()
                }
            }
        }
    }
    // ранги за дату и общий получаются с помощью большого числа запросов. после
    // старта сетевого запроса эта переменная увеличивается на 1. после окончания работы уменьшается на 1
    // в тот момент когда будет значение 0 - все процессы закончили работать
    private var rankCounter: Int? {
        didSet{
            if self.rankCounter == 0 {
                if self.queryType == .total {
                    // запускаем сборку из таблиц
                    self.compileTotalRankInfo()
                } else {
                    // запускаем сборку из таблиц
                    self.compileOnDateRankInfo()
                }
            }
        }
    }
    // инкримент счетчика с другого потока (из сетевых запросов)
    private func rankCounterIncrement(){
        queue.sync {
            if rankCounter == nil {
                rankCounter = 1
            } else {
                rankCounter = rankCounter! + 1
            }
        }
    }
    // декремент счетчика с другого потока (из сетевых запросов)
    private func rankCounterDecrement(){
        queue.sync {
            rankCounter = rankCounter! - 1
        }
    }
    //-------------------------------------------------------------------------
    // временные переменные
    private var sites: [Site] = []
    private var persons: [Person] = []
    private var ranks: [Rank]?
    // сохраняет ранг с другого потока (из сетевых запросов)
    private func rankAppend(rank: Rank){
        queue.sync {
            ranks?.append(rank)
        }
    }
    private var queryType: QueryType
    private var dateBegin: Date?
    private var dateEnd: Date?
    var delegat: NetworkProcessProtocol
    // стартует работу процесса
    public func start(){
        getAllSites()
    }
    
    init(queryType: QueryType, delegat: NetworkProcessProtocol, baseURL: String, date1: Date?, date2: Date?) {
        self.baseURL = baseURL
        self.queryType = queryType
        self.delegat = delegat
        if queryType == .onDate {
            self.dateBegin = date1
            self.dateEnd = date2
        }
    }
    // инит для общей статистики
    convenience init(delegat: NetworkProcessProtocol, baseURL: String) {
        self.init(queryType: .total, delegat: delegat, baseURL: baseURL, date1: nil, date2: nil)
    }
    // инит для статистики по дням
    convenience init(delegat: NetworkProcessProtocol, baseURL: String, date1: Date?, date2: Date?) {
        self.init(queryType: .onDate, delegat: delegat, baseURL: baseURL, date1: date1, date2: date2)
    }
    // загрузка сайтов
    private func getAllSites(){
        let url = baseURL + "/site"
        Alamofire.request(url).responseJSON { (response) in
            if let error = response.error{
                print("Network error \(error)")
            } else {
                switch response.response!.statusCode{
                case 200:
                    if let data = response.data{
                        var sitesUpdate: [Site] = []
                        let json = JSON(data)
                        if let jsonArray = json.array{
                            // перебираем строки
                            for item in jsonArray{
                                var site = Site()
                                site.id = item["id"].intValue
                                site.name = item["name"].stringValue
                                // добавляем во временный массив
                                sitesUpdate.append(site)
                            }
                            self.sites = sitesUpdate
                            // устанавливаем флаг загрузки сайтов
                            self.sitesDownloaded = true
                        }
                    } else {
                        print("Error. Data is nil")
                    }
                case 400:
                    print("Error. Bad request")
                case 404:
                    print("Error. Data not found")
                    
                default: break
                }
            }
        }
    }
    
    
    // загрузка персон
    private func getAllPerson(){
        let url = baseURL + "/person"
        Alamofire.request(url).responseJSON { (response) in
            if let error = response.error{
                print("Network error \(error)")
            } else {
                switch response.response!.statusCode{
                case 200:
                    if let data = response.data{
                        var personUpdate: [Person] = []
                        let json = JSON(data)
                        if let jsonArray = json.array{
                            for item in jsonArray{
                                var person = Person()
                                person.id = item["id"].intValue
                                person.name = item["name"].stringValue
                                personUpdate.append(person)
                            }
                            self.persons = personUpdate
                            // устанавливаем флаг загрузки персон
                            self.personsDownloaded = true
                        }
                    } else {
                        print("Error. Data is nil")
                    }
                    
                case 400:
                    print("Error. Bad request")
                case 404:
                    print("Error. Data not found")
                    
                default: break
                }
            }
        }
    }
    // получаем общий ранг
    private func getTotalRank(){
        for site in self.sites{
            for person in self.persons{
                // увеличиваем счетчик url запросов на единицу
                self.rankCounterIncrement()
                // формируем url для запроса
                let url = baseURL + "/stat/\(site.id)/\(person.id)"
                Alamofire.request(url).responseJSON { (response) in
                    if let error = response.error{
                        print("Network error \(error)")
                    } else {
                        switch response.response!.statusCode{
                        case 200:
                            if let data = response.data{
                                let json = JSON(data)
                                // получаем из json данные в структуру
                                var newTotalRank = Rank()
                                newTotalRank.siteID = site.id
                                newTotalRank.personID = person.id
                                newTotalRank.rank = json["rank"].intValue
                                // сохраняем данные в массиве
                                self.rankAppend(rank: newTotalRank)
                                // уменьшаем счетчик url запросов
                                self.rankCounterDecrement()
                            } else {
                                print("Error. Data is nil")
                            }
                            
                        case 400:
                            print("Error. Bad request")
                        case 404:
                            print("Error. Data not found")
                            
                        default: break
                        }
                    }
                }
            }
        }
    }
    // получаем ранг за период
    private func getOnDateRank(){
        guard let dBegin = dateBegin else {
            print("getOnDateRank. dateBegin is nil")
            return
        }
        guard let dEnd = dateEnd else {
            print("getOnDateRank. dateEnd is nil")
            return
        }
        for site in self.sites{
            for person in self.persons{
                for currentDate in DateRange(beginDate: dBegin, endDate: dEnd){
                    // увеличиваем счетчик url запросов на единицу
                    rankCounterIncrement()
                    let dateInString = currentDate.toString()!
                    // формируем url для запроса
                    let url = baseURL + "/stat/\(site.id)/\(person.id)/\(dateInString)"
                    Alamofire.request(url).responseJSON { (response) in
                        if let error = response.error{
                            print("Network error \(error)")
                        } else {
                            switch response.response!.statusCode{
                            case 200:
                                if let data = response.data{
                                    let json = JSON(data)
                                    // получаем из json данные в структуру
                                    var newOnDateRank = Rank()
                                    newOnDateRank.siteID = site.id
                                    newOnDateRank.personID = person.id
                                    newOnDateRank.rank = json["rank"].intValue
                                    let dateString = json["date"].stringValue
                                    newOnDateRank.date = dateString.toDate()!
                                    // сохраняем данные в массиве
                                    self.rankAppend(rank: newOnDateRank)
                                    // уменьшаем счетчик url запросов
                                    self.rankCounterDecrement()
                                } else {
                                    print("Error. Data is nil")
                                }
                                
                            case 400:
                                print("Error. Bad request")
                            case 404:
                                print("Error. Data not found")
                                
                            default: break
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    private func compileTotalRankInfo(){
        // проверяем наличие данных рангов
        guard let ranksArray = self.ranks else {
            print("compileDataError. totalRank is nil")
            return
        }
        // инициализируем массив - результат компиляции
        var compiledRank: [SiteData] = []
        // перебираем сайты из списка сайтов
        for site in self.sites{
            // отфильтровываем ранги по текущему сайту
            let siteArray = ranksArray.filter({ (rank) -> Bool in rank.siteID == site.id })
            // заполняем структуру
            let siteData = SiteData()
            siteData.site = site.name
            // перебираем персон для текущего сайта
            for person in self.persons{
                // получаем массив рангов из одной, текущей персоны
                let personRank = siteArray.filter({ (p) -> Bool in p.personID == person.id })
                // заполняем словарь текущей персоной
                siteData.stats[person.name] = personRank[0].rank
            }
            // добавляем сайт в результирующий массив
            compiledRank.append(siteData)
        }
        // передаем в менеджер результаты работы процесса
        delegat.didCompliteTotalRankProcess(siteData: compiledRank)
    }
    private func compileOnDateRankInfo(){
        // проверяем наличие данных рангов
        guard let ranksArray = self.ranks else {
            print("compileDataError. totalRank is nil")
            return
        }
        // инициализируем массив - результат компиляции
        var compiledRank: [SiteData] = []
        // перебираем сайты из списка сайтов
        for site in self.sites{
            // отфильтровываем ранги по текущему сайту
            let siteArray = ranksArray.filter({ (rank) -> Bool in rank.siteID == site.id })
            // перебираем персон для текущего сайта
            for person in self.persons{
                // получаем массив рангов из одной, текущей персоны
                let personRank = siteArray.filter({ (p) -> Bool in p.personID == person.id })
                for onDate in personRank {
                    // заполняем словарь текущей персоной за одну дату
                    let siteData = SiteData()
                    siteData.site = site.name
                    siteData.stats[person.name] = onDate.rank
                    siteData.date = onDate.date!
                    // добавляем информации в результирующий массив
                    compiledRank.append(siteData)
                }
            }
            
        }
        // передаем в менеджер результаты работы процесса
        delegat.didCompliteOnDateRankProcess(siteData: compiledRank, date1: dateBegin!, date2: dateEnd!)
    }
}
// Получает запросы от DataManager, ставит в очередь. запускает в порядке FIFO
class NetworkManager:DataProvider, NetworkProcessProtocol {
    var baseURL: String = "http://127.0.0.1:8080"
    // очередь процессов
    private var processes: [NetworkProcess] = [] {
        didSet (value){
            if value.count == 0 && processes.count == 1 {
                // добавлено первый процесс. стартуем его
                processes.first?.start()
                
            }
            if value.count < processes.count && processes.count > 0{
                // было удалено задание. начинаем новый процесс
                processes.first?.start()
            }
        }
    }
    // ставим в очередь задание получения суммарных данных
    public override func getSumaryData() {
        let process = NetworkProcess(delegat: self, baseURL: baseURL)
        processes.append(process)
    }
    // ставим в очередь задание получения данных за период
    public override func getDataOnDate(date1: Date, date2: Date) {
        let process = NetworkProcess(delegat: self, baseURL: baseURL, date1: date1, date2: date2)
        processes.append(process)
    }
    // удаляем первый в очереди процесс (когда он закончил работу), зачищаем временные переменные
    private func removeNetworkProcess(){
        processes.removeFirst()
    }
    // получаем от процесса данные, передаем их на уровень выше и удаляем процесс.
    internal func didCompliteTotalRankProcess(siteData: [SiteData]){
        self.removeNetworkProcess()
        delegat.didCompliteRequestTotal(data: SiteDataArray(data: siteData), dataProvider: self)
    }
    // получаем от процесса данные, передаем их на уровень выше и удаляем процесс.
    internal func didCompliteOnDateRankProcess(siteData: [SiteData], date1: Date, date2: Date) {
        self.removeNetworkProcess()
        delegat.didCompliteRequestOnData(data: SiteDataArray(data: siteData), date1: date1, date2: date2, dataProvider: self)
    }
    
}


extension String {
    // преобразование строки в дату
    func toDate() -> Date?{
        let formatter = DateFormatter()
        formatter.dateFormat = "dd.MM.yyyy"
        return formatter.date(from: self)
    }
}

extension Date{
    // преобразование даты в строку
    func toString() -> String?{
        let formater = DateFormatter()
        formater.dateFormat = "dd.MM.yyyy"
        return formater.string(from: self)
    }
}














