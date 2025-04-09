//
//  MyViewModel.swift
//  lab05
//
//  Created by student on 27/3/25.
//

import Foundation

class MyViewModel : ObservableObject {
    @Published var flowers: [String] = []
    @Published var animals: [String] = []
    @Published var cars: [String] = []
    
    init() {
        for i in 1...11 {
            flowers.append("f\(i)")
        }
        for i in 1...6 {
            cars.append("c\(i)")
        }
        for i in 1...8 {
            animals.append("a\(i)")
        }
    }
    
    func getImageNameForCategory(category: ImageCategory, andIndex index: Int) -> String? {
        switch category {
            case .flower:
                return index < flowers.count ? flowers[index] : nil
            case .animal:
                return index < animals.count ? animals[index] : nil
            case .car:
                return index < cars.count ? cars[index] : nil
        }
    }
    
    func getImageNumbersForCategories() -> [(ImageCategory,Int)] {
        return [
            (.flower, flowers.count),
            (.animal, animals.count),
            (.car, cars.count)
        ]
    }
}

//init(){
//    for i in 1...7{
//        tab.append(String("f\(i)"))
//    }
//}
//changeItem{
//    self.item =  String("f\(Int.random(in: 1...7))")
//    return self.item
//}
