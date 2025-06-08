//
//  Weather+CoreDataProperties.swift
//  KiszkaKierepkaPRJ
//
//  Created by student on 22/05/2025.
//
//

import Foundation
import CoreData


extension Weather {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<Weather> {
        return NSFetchRequest<Weather>(entityName: "Weather")
    }

    @NSManaged public var date: Date?
    @NSManaged public var humiditity: Double
    @NSManaged public var pressure: Int16
    @NSManaged public var sunrise: Int16
    @NSManaged public var sunset: Int16
    @NSManaged public var temperature: Int16
    @NSManaged public var timestamp: Int16
    @NSManaged public var wind: Int16
    @NSManaged public var relationship: NSSet?

}

// MARK: Generated accessors for relationship
extension Weather {

    @objc(addRelationshipObject:)
    @NSManaged public func addToRelationship(_ value: City)

    @objc(removeRelationshipObject:)
    @NSManaged public func removeFromRelationship(_ value: City)

    @objc(addRelationship:)
    @NSManaged public func addToRelationship(_ values: NSSet)

    @objc(removeRelationship:)
    @NSManaged public func removeFromRelationship(_ values: NSSet)

}

extension Weather : Identifiable {

}
