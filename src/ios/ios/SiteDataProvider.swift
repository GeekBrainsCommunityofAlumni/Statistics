//
//  SiteDataProvider.swift
//  ios
//
//  Created by Михаил Агеев macbook on 14.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit
import SwiftyJSON

class SiteDataProvider: NSObject, URLSessionDelegate {
    
    //var siteArray: [String]
    
    let dataLink = ""  //адрес сервера
    private static let myQeueue: OperationQueue = OperationQueue()
    private static let session: URLSession = URLSession(configuration: .default , delegate: self as? URLSessionDelegate, delegateQueue: OperationQueue.main)
    
    var delegate: SiteDataProviderDelegate
    
    init(withDelegate someDelegate: SiteDataProviderDelegate) {
        
        // данная иницилизация позаимственна с курсов обучения
        self.delegate = someDelegate
        //self.siteArray = ["Ex1"]
        let tempED: TableViewControllerDayStat = someDelegate as! TableViewControllerDayStat
        let tempAD: TableViewController = someDelegate as! TableViewController
    }
    
    func getSiteWith(index: Int ){
        let url = URL(string: dataLink)!
        let dataTask = SiteDataProvider.session.dataTask(with: url) {
            (data, response, error) in
            if let err = error{
                print ("Mistakes: \(err)")
            }
            else{
                do{
                    if data == nil {
                        print ("Downloadin ok, but data is nil.")
                    }
                    else{
                        let json = JSON(data!)
                        var site: SiteData = SiteData()
                        
                        site.site = json["SiteName"].stringValue
                        //site.stats = json["Name"].stringValue["Number"]
                        
                        SiteDataProvider.myQeueue.addOperation {
                            self.delegate.requestDidCompleteSite(data: site, forIndex: index)
                        }
                        
                    }
                }
                catch let error as NSError {
                        print ("Error: \(error)")
                    }
                }
            
            }
         dataTask.resume()
        }
    
}
