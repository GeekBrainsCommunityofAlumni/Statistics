//
//  StatDVCell.swift
//  ios
//
//  Created by Dmytro Shcherbachenko on 19.06.17.
//  Copyright © 2017 GB. All rights reserved.
//

import UIKit

class StatDVCell: UITableViewCell {

    @IBOutlet weak var countLabel: UILabel!
    @IBOutlet weak var titleLabel: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
