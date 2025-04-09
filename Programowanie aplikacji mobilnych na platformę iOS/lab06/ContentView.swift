//
//  ContentView.swift
//  lab06
//
//  Created by student on 09/04/2025.
//

import SwiftUI

// przykład 1
//struct ContentView: View {
//    @State var changeColor: Bool = true
//    var body: some View {
//        NavigationView {
//            VStack(spacing: 25) {
//                Text("Hello, world!")
//                    .foregroundColor(changeColor ? .yellow : .green)
//                    .navigationTitle(Text("Pierwszy widok"))
//                NavigationLink(
//                    destination: SecondView(change: $changeColor),
//                    label: {
//                        Text("Drugi widok")
//                            .fontWeight(.bold)
//                    })
//            }
//        }
//        .padding()
//    }
//}

//struct ContentView_Previews: PreviewProvider {
//    static var previews: some View {
//        ContentView()
//    }
//}

// przykład 2
//struct ContentView: View {
//    @State private var showSecond: Bool = false
//    var book = Book (tytul: "Potop", autor: "Henryk Sienkiewicz")
//    var body: some View {
//        Button(action: {
//            self.showSecond.toggle()
//        }, label: {
//            Text("Pokaz")
//        })
//        .sheet(isPresented: $showSecond) {
//            SecondView(book: book)
//        }
//    }
//}
//
//struct ContentView_Previews: PreviewProvider {
//    static var previews: some View {
//        ContentView()
//    }
//}

//struct ContentView: View {
//    @State var changeColor: Bool = true
//    var body: some View {
//        NavigationView {
//            VStack(spacing: 25) {
//                Text("Hello, world!")
//                    .foregroundColor(changeColor ? .yellow : .green)
//                    .navigationTitle(Text("Pierwszy widok"))
//                NavigationLink(
//                    destination: SecondView(change: $changeColor),
//                    label: {
//                        Text("Drugi widok")
//                            .fontWeight(.bold)
//                    })
//            }
//        }
//        .padding()
//    }
//}

// zadanie 6.1.
//struct ContentView: View {
//    @State var selectedColor: Color = .black
//    var body: some View {
//        NavigationView {
//            VStack(spacing: 25) {
//                Text("Hello, world!")
//                    .foregroundColor(selectedColor)
//                    .navigationTitle(Text("Pierwszy widok"))
//                NavigationLink(
//                    destination: SecondView(selectedColor: $selectedColor),
//                    label: {
//                        Text("Drugi widok")
//                            .fontWeight(.bold)
//                    })
//            }
//        }
//        .padding()
//    }
//}
//
//struct ContentView_Previews: PreviewProvider {
//    static var previews: some View {
//        ContentView()
//    }
//}

// zadanie 6.3.
struct ContentView: View {
    @State private var selectedNazwisko: String = ""
    
    let books = [
        Book(imie: "Remigiusz", nazwisko: "Mroz", tytul: "Przepaść", ilosc: "10"),
        Book(imie: "Remigiusz", nazwisko: "Mroz", tytul: "Behawiorysta", ilosc: "7"),
        Book(imie: "Olga", nazwisko: "Tokarczuk", tytul: "Księgi Jakubowe", ilosc: "5")
    ]
    
    var filteredBooks: [Book] {
        if selectedNazwisko.isEmpty {
            return []
        }
        return books.filter { $0.nazwisko.lowercased().contains(selectedNazwisko.lowercased()) }
    }
    
    var body: some View {
        NavigationView {
            VStack(spacing: 25) {
                NavigationLink(
                    destination: SecondView(nazwisko: $selectedNazwisko),
                    label: {
                        Text("Wybierz autora")
                    }
                )
                VStack {
                    Text("Dostępne książki autora\(selectedNazwisko.isEmpty ? ":" : " \(selectedNazwisko):")")
                        .foregroundColor(.purple)
                    if !filteredBooks.isEmpty {
                        ForEach(filteredBooks, id: \.tytul) { book in
                            Text("\(book.imie) \(book.nazwisko) \(book.tytul) - \(book.ilosc) szt.")
                        }
                    }
                }.padding()
            }
            .navigationTitle("Autor")
            .padding()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
