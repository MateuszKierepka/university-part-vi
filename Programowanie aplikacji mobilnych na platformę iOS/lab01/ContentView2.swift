//
//  ContentView.swift
//  lab01
//
//  Created by student on 27/02/2025.
//

import SwiftUI

struct ContentView: View {
    @State var liczba1: String = ""
    @State var liczba2: String = ""
    @State private var wynik: String = ""
    @State private var poprawne = false

    var body: some View {
        VStack() {
            Text("Podaj dwie liczby rzeczywiste:")
            TextField("Wpisz pierwsza liczbe", text: $liczba1).multilineTextAlignment(.center)
            TextField("Wpisz druga liczbe", text: $liczba2).multilineTextAlignment(.center)
            Button(action: {
                if let liczba1Float = Float(liczba1), let liczba2Float = Float(liczba2) {
                    if liczba2Float != 0 {
                        wynik = "Iloraz: \(liczba1Float / liczba2Float)"
                        poprawne = true
                    } else {
                        wynik = "Nie mozna dzielic przez 0"
                        poprawne = false
                    }
                } else {
                    wynik = "Prosze podac poprawne liczby"
                    poprawne = false
                }
            }, label:{
                Text("Oblicz iloraz")
            })
            Text(wynik)
        }
        .padding()
    }
}

#Preview {
    ContentView()
}