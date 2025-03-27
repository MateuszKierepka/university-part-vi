//
//  ImagePickerView.swift
//  lab05
//
//  Created by student on 26/03/2025.
//

import SwiftUI

struct ImagePickerView: View {
    @ObservedObject var imageData = ImageData()
    @Binding var selectedImages: [ImageCategory: String]
    @Binding var displayOrder: [ImageCategory]
    @State private var selectedCategory: ImageCategory? = nil
    @State private var selectedImage: String?

    var body: some View {
        VStack {
            if let imageName = selectedImage {
                Image(imageName)
                    .resizable()
                    .scaledToFit()
                    .frame(maxWidth: 300, maxHeight: 200)
                    .padding()
            }

            Picker("Wybierz kategorię", selection: $selectedCategory) {
                Text("Wybierz kategorię").tag(ImageCategory?.none)
                ForEach(ImageCategory.allCases, id: \.self) { category in
                    Text(category.rawValue).tag(category as ImageCategory?)
                }
            }
            .pickerStyle(MenuPickerStyle())
            .padding()

            Button(action: {
                if let category = selectedCategory {
                    let images: [String]
                    switch category {
                    case .flower:
                        images = imageData.flowers
                    case .animal:
                        images = imageData.animals
                    case .car:
                        images = imageData.cars
                    }
                    if let randomImage = images.randomElement() {
                        selectedImage = randomImage
                        selectedImages[category] = randomImage
                        if !displayOrder.contains(category) {
                            displayOrder.append(category)
                        }
                    }
                }
            }) {
                Text("Wylosuj obrazek dla kategorii")
                    .padding()
                    .foregroundColor(.blue)
            }
        }
        .navigationTitle("Losowanie")
        .navigationBarBackButtonHidden(false)
    }
}

struct ImagePickerView_Previews: PreviewProvider {
    static var previews: some View {
        ImagePickerView(selectedImages: .constant([:]), displayOrder: .constant([]))
    }
}
