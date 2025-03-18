//
//  Header2View.swift
//  lab03
//
//  Created by student on 18/03/2025.
//

import SwiftUI

struct Header2View: View {
    var body: some View {
        VStack {
            Text("Geometria dla uczniów")
                .font(.largeTitle)
                .fontWeight(.bold)
                .padding()
            Text("Poznaj kształty i kolory poprzez interaktywną aplikację")
                .foregroundColor(.gray)
                .padding(.bottom, 20)
        }
    }
}

struct Header2View_Previews: PreviewProvider {
    static var previews: some View {
        Header2View()
    }
}
