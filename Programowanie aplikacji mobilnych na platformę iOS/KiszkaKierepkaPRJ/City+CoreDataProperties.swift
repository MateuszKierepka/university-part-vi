//
//  City+CoreDataProperties.swift
//  KiszkaKierepkaPRJ
//
//  Created by student on 22/05/2025.
//
//

import Foundation
import CoreData


extension City {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<City> {
        return NSFetchRequest<City>(entityName: "City")
    }

    @NSManaged public var cityName: String?
    @NSManaged public var country: String?
    @NSManaged public var latitude: String?
    @NSManaged public var longtitude: String?
    @NSManaged public var isFavourite: Bool
    @NSManaged public var relationship: NSSet?

}

// MARK: Generated accessors for relationship
extension City {

    @objc(addRelationshipObject:)
    @NSManaged public func addToRelationship(_ value: Weather)

    @objc(removeRelationshipObject:)
    @NSManaged public func removeFromRelationship(_ value: Weather)

    @objc(addRelationship:)
    @NSManaged public func addToRelationship(_ values: NSSet)

    @objc(removeRelationship:)
    @NSManaged public func removeFromRelationship(_ values: NSSet)

}

extension City : Identifiable {

}
