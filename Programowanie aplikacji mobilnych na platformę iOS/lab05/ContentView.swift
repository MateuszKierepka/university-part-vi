//
//  ContentView.swift
//  lab05
//
//  Created by student on 26/03/2025.
//

import SwiftUI

struct ContentView: View {
    @ObservedObject var MyViewModel: MyViewModel
    @State var wybraneObrazki: [ImageCategory: String] = [:]

    var body: some View {
        NavigationStack {
            VStack {
                ForEach(ImageCategory.allCases, id: \.self) {
                    category in
                    if let imageName = wybraneObrazki[category] {
                        Image(imageName)
                            .resizable()
                            .scaledToFit()
                            .frame(height: 150)
                            .padding()
                    }
                }
                Spacer()
                NavigationLink("Wylosuj element", destination: NextView(MyViewModel: MyViewModel, wybraneObrazki: $wybraneObrazki))
                    .padding()
            }
            .navigationTitle("Wybrane elementy")
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(MyViewModel: MyViewModel())
    }
}

//struct ContentView: View {
//    @ObservedObject var ViewModel : MyViewModel
//    var body: some View {
//        NavigationStack {
//            VStack {
//                Image("f2")
//                    .scaledToFit()
//
//                Text(ViewModel.item)
//                    .font(.largeTitle)
//                    .foregroundColor(.red)
//
//                NavigationLink("idz dalej",
//                               destination: NextView(viewModel: ViewModel))
//                    .font(.largeTitle)
//            }
//            .padding()
//            .navigationTitle("Main")
//        }
//    }
//}
//
//#Preview {
//    ContentView(ViewModel: MyViewModel())
//}
//
