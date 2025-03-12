//
//  ContentView.swift
//  lab02
//
//  Created by student on 11/03/2025.
//

import SwiftUI

struct ContentView: View {
    @State private var liczbaWcisniec = 0
    
    @State private var wybranyRozmiar = "R15"
    @State private var wybranyProducent = "Michelin"
    @State private var iloscOpon = ""
    @State private var komunikat = ""
    @State private var pokazWynik = false
    @State private var bladWprowadzenia = false
    
    let rozmiaryOpon = ["R15", "R16", "R17", "R18"]
    let producenciOpon = ["Michelin", "Continental", "Pirelli", "Goodyear"]
    let dostepnoscOpon = [
        ("R15", "Michelin", 10),
        ("R15", "Continental", 6),
        ("R15", "Pirelli", 8),
        ("R15", "Goodyear", 12),
        ("R16", "Michelin", 7),
        ("R16", "Continental", 5),
        ("R16", "Pirelli", 9),
        ("R16", "Goodyear", 14),
        ("R17", "Michelin", 15),
        ("R17", "Continental", 13),
        ("R17", "Pirelli", 11),
        ("R17", "Goodyear", 8),
        ("R18", "Michelin", 6),
        ("R18", "Continental", 9),
        ("R18", "Pirelli", 6),
        ("R18", "Goodyear", 7)
    ]
    
    var body: some View {
        // zadanie 3.1.
        //        VStack {
        //            Text("Wcisnij przycisk!")
        //            Button(action: {
        //                liczbaWcisniec += 1
        //            }) {
        //                Text("Wcisnij")
        //                    .foregroundColor(.green)
        //            }
        //            .background(Color.gray)
        //            .cornerRadius(10)
        //            Text("Wcisnieto \(liczbaWcisniec) razy!")
        //        }
        //        .padding()
        
        // zadanie 3.2.
        VStack {
            Text("Wybierz opony:")
                .font(.largeTitle)
                .bold()
                .padding(.top)
                    
            HStack {
                Text("Rozmiar opony:")
                    .frame(width: 118, alignment: .leading)
                Picker("Rozmiar", selection: $wybranyRozmiar){
                    ForEach(rozmiaryOpon, id: \.self) { rozmiar in
                        Text(rozmiar)
                    }
                }
                .pickerStyle(MenuPickerStyle())
            }
        HStack {
            Text("Producent:")
                .frame(width: 84, alignment: .leading)
            Picker("Producent", selection: $wybranyProducent){
                ForEach(producenciOpon, id: \.self) {producent in
                    Text(producent)
                }
            }
            .pickerStyle(MenuPickerStyle())
        }
        HStack {
            Text("Liczba opon")
                .frame(width: 93, alignment: .leading)
            TextField("Podaj liczbę", text: $iloscOpon)
                .keyboardType(.numberPad)
                .textFieldStyle(RoundedBorderTextFieldStyle())
        }
        Button(action: {
            sprawdzDostepnosc()
        }) {
            Text("Sprawdź dostępność")
                .foregroundColor(.white)
                .padding()
                .background(Color.cyan)
                .cornerRadius(50)
                .fixedSize()
        }
        .padding()
                    
        if pokazWynik {
            Text(komunikat)
                .foregroundColor(bladWprowadzenia || komunikat.lowercased().contains("nie") ? .red : .green)
                .padding()
                .background(Color.black)
                .cornerRadius(20)
        }
    }
    .padding()
}
            
func sprawdzDostepnosc() {
    guard let zadanaIlosc = Int(iloscOpon) else {
        komunikat = "Wprowadź prawidłową liczbę!"
        bladWprowadzenia = true
        pokazWynik = true
        return
    }
    for pozycja in dostepnoscOpon {
        if pozycja.0 == wybranyRozmiar && pozycja.1 == wybranyProducent {
            if zadanaIlosc <= pozycja.2 {
                komunikat = "Wybrana liczba opon jest dostępna"
            } else {
                komunikat = "Wybrana liczba opon nie jest dostępna"
            }
            pokazWynik = true
            return
        }
    }
    komunikat = "Nie znaleziono opon o podanych parametrach w bazie danych"
    pokazWynik = true
}
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
