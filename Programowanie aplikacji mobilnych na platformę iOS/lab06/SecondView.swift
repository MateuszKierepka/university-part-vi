//
//  SecondView.swift
//  lab06
//
//  Created by student on 09/04/2025.
//

import SwiftUI

// przyklad 1
//struct SecondView: View {
//    @Binding var change: Bool
//    var body: some View {
//        Button(action: {
//            change.toggle()
//        }, label: {
//            Text("Zmiana koloru")
//        })
//        .navigationTitle("Wybor zmiany")
//    }
//}
//
//struct SecondView_Previews: PreviewProvider {
//    static var previews: some View {
//        SecondView(change: .constant(true))
//    }
//}

// przyklad 2
//struct SecondView: View {
//    var book: Book
//    var body: some View {
//        VStack {
//            Text(book.tytul)
//            Text(book.autor)
//        }
//    }
//}
//
//struct SecondView_Previews: PreviewProvider {
//    static let book2 = Book(tytul: "Pan Tadeusz", autor: "Adam Mickiewicz")
//    static var previews: some View {
//        SecondView(book: book2)
//    }
//}

// zadanie 6.1.
//struct SecondView: View {
//    let colors: [Color] = [.yellow, .green, .red, .blue]
//    let colorNames = ["Żółty", "Zielony", "Czerwony", "Niebieski"]
//
//    @Binding var selectedColor: Color
//    var body: some View {
//        Picker("Wybierz kolor", selection: $selectedColor) {
//            ForEach(0..<colors.count, id: \.self) { index in
//                Text(colorNames[index]).tag(colors[index])
//            }
//        }
//        .navigationTitle("Wybor zmiany")
//    }
//}
//
//struct SecondView_Previews: PreviewProvider {
//    static var previews: some View {
//        SecondView(selectedColor: .constant(.black))
//    }
//}

// zadanie 6.3.
struct SecondView: View {
    @Binding var nazwisko: String

    var body: some View {
        VStack {
            TextField("Wpisz nazwisko autora", text: $nazwisko)
                .padding()
        }
    }
}

struct SecondView_Previews: PreviewProvider {
    static var previews: some View {
        SecondView(nazwisko: .constant(""))
    }
}
