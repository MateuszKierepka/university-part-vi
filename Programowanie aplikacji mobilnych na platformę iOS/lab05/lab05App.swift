//
//  lab05App.swift
//  lab05
//
//  Created by student on 26/03/2025.
//

import SwiftUI

@main
struct lab05App: App {
    @StateObject var vm = MyViewModel()
    var body: some Scene {
        WindowGroup {
            ContentView(MyViewModel: vm)
        }
    }
}
