//
//  ContentView.swift
//  lab01
//
//  Created by student on 27/02/2025.
//

import SwiftUI

struct ContentView: View {
    @State var dzien: String = ""
    @State var dzienwybor: String = ""
    @State var poprawny = false
    
    let dniTygodnia: [String] = [
        "poniedziałek", "wtorek", "środa", "czwartek", "piątek", "sobota", "niedziela"
    ]
    var body: some View {
        VStack(alignment: .center) {
            Text("Podaj wybrany dzien tygodnia:").foregroundColor(.green).rotation3DEffect(.degrees(45), axis: (x: 1, y: 0, z: 0))
            TextField("Wpisz dzien tygodnia", text: $dzien).multilineTextAlignment(.center)
            Button(action:{
                if dniTygodnia.contains(dzien.lowercased()) {
                    poprawny = true
                    dzienwybor = dzien
                } else{
                    poprawny = false
                }
            }, label: {
                Text("Zatwierdz wybor")
            }).background(Color.yellow)
            
            if !poprawny {
                Text("zly dzien tygodnia")
                    .foregroundColor(.red)
            }
            if poprawny && !dzienwybor.isEmpty {
                Text("Wybrano: \(dzienwybor)")
            }
        }
        .padding()
    }
}

#Preview {
    ContentView()
}