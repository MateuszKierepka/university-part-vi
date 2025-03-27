//
//  ImageData.swift
//  lab05
//
//  Created by student on 26/03/2025.
//

import Foundation

class ImageData: ObservableObject {
    @Published var flowers: [String] = []
    @Published var animals: [String] = []
    @Published var cars: [String] = []

    init() {
        flowers = ["f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "f10", "f11"]
        animals = ["a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8"]
        cars = ["c1", "c2", "c3", "c4", "c5", "c6"]
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

    func getImageNumbersForCategories() -> [(ImageCategory, Int)] {
        return [
            (.flower, flowers.count),
            (.animal, animals.count),
            (.car, cars.count)
        ]
    }
}
