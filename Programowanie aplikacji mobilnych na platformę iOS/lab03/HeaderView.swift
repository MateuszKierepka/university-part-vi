//
//  HeaderView.swift
//  lab03
//
//  Created by student on 18/03/2025.
//

import SwiftUI

struct HeaderView: View {
    var body: some View {
        VStack {
            Text("KANTOR ONLINE")
                .font(.largeTitle)
                .fontWeight(.bold)
                .padding()
            Text("tutaj reklama jakas")
                .foregroundColor(.gray)
            Text("Godziny otwarcia: 9:00 - 18:00")
                .foregroundColor(.gray)
            Text("Kontakt: +48 123 456 789")
                .foregroundColor(.gray)
                .padding(.bottom, 20)
        }
    }
}

struct HeaderView_Previews: PreviewProvider {
    static var previews: some View {
        HeaderView()
    }
}
