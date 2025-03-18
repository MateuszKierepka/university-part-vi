//
//  DrawingView.swift
//  lab03
//
//  Created by student on 18/03/2025.
//

import SwiftUI

struct DrawingView: View {
    @State private var selectedShape = "Koło"
    @State private var selectedColor = Color.red
    @State private var frameWidth: Double = 150
    @State private var frameHeight: Double = 150
    @State private var addBorder: Bool = false
    
    let shapes = ["Koło", "Prostokąt", "Trójkąt"]
    let colors: [(String, Color)] = [("Czerwony", .red), ("Zielony", .green), ("Niebieski", .blue), ("Żółty", .yellow)]
    
    var body: some View {
        VStack {
            Text("Wybierz kształt:")
            Picker("Kształt", selection: $selectedShape) {
                ForEach(shapes, id: \ .self) { shape in
                    Text(shape)
                }
            }
            .pickerStyle(SegmentedPickerStyle())
            .padding()
            
            Text("Wybierz kolor:")
            HStack {
                ForEach(colors, id: \ .0) { color in
                    Button(action: { selectedColor = color.1 }) {
                        Circle()
                            .fill(color.1)
                            .frame(width: 30, height: 30)
                            .overlay(Circle().stroke(Color.black, lineWidth: selectedColor == color.1 ? 3 : 0))
                    }
                }
            }
            .padding()
            
            VStack {
                Text("Wymiary ramki:")
                Slider(value: $frameWidth, in: 50...300, step: 10) {
                    Text("Szerokość")
                }
                Text("Szerokość: \(Int(frameWidth)) px")
                
                Slider(value: $frameHeight, in: 50...300, step: 10) {
                    Text("Wysokość")
                }
                Text("Wysokość: \(Int(frameHeight)) px")
            }
            .padding()
            
            Toggle("Dodaj ramkę", isOn: $addBorder)
                .padding()
            
            Spacer()
            
            ZStack {
                if selectedShape == "Koło" {
                    Circle()
                        .fill(selectedColor)
                        .frame(width: frameWidth, height: frameHeight)
                        .overlay(addBorder ? Circle().stroke(Color.black, lineWidth: 4) : nil)
                } else if selectedShape == "Prostokąt" {
                    Rectangle()
                        .fill(selectedColor)
                        .frame(width: frameWidth, height: frameHeight)
                        .overlay(addBorder ? Rectangle().stroke(Color.black, lineWidth: 4) : nil)
                } else if selectedShape == "Trójkąt" {
                    Triangle()
                        .fill(selectedColor)
                        .frame(width: frameWidth, height: frameHeight)
                        .overlay(addBorder ? Triangle().stroke(Color.black, lineWidth: 4) : nil)
                }
            }
            .padding()
        }
    }
}

struct Triangle: Shape {
    func path(in rect: CGRect) -> Path {
        var path = Path()
        path.move(to: CGPoint(x: rect.midX, y: rect.minY))
        path.addLine(to: CGPoint(x: rect.maxX, y: rect.maxY))
        path.addLine(to: CGPoint(x: rect.minX, y: rect.maxY))
        path.closeSubpath()
        return path
    }
}

struct DrawingView_Previews: PreviewProvider {
    static var previews: some View {
        DrawingView()
    }
}
