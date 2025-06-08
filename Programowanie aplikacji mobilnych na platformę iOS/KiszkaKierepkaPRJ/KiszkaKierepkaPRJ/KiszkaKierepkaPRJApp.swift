//
//  KiszkaKierepkaPRJApp.swift
//  KiszkaKierepkaPRJ
//
//  Created by student on 22/05/2025.
//

import SwiftUI

@main
struct KiszkaKierepkaPRJApp: App {
    let persistenceController = PersistenceController.shared

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
}
