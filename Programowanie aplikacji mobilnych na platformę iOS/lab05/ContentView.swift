//
//  ContentView.swift
//  lab05
//
//  Created by student on 26/03/2025.
//

import SwiftUI

struct ContentView: View {
    @ObservedObject var imageData = ImageData()
    @State private var selectedImages: [ImageCategory: String] = [:]
    @State private var displayOrder: [ImageCategory] = []

    var body: some View {
        NavigationView {
            VStack {
                ForEach(displayOrder.reversed(), id: \.self) { category in
                    if let imageName = selectedImages[category] {
                        Image(imageName)
                            .resizable()
                            .scaledToFit()
                            .frame(maxWidth: 300, maxHeight: 200)
                            .padding()
                    }
                }
                Spacer()
                NavigationLink(destination: ImagePickerView(selectedImages: $selectedImages, displayOrder: $displayOrder)) {
                    Text("Wylosuj element")
                        .foregroundColor(.blue)
                        .padding()
                }
            }
            .navigationTitle("Wybrane elementy")
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
