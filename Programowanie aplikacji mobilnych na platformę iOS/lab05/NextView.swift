import SwiftUI

struct NextView: View {
    @ObservedObject var MyViewModel: MyViewModel
    @State private var wybranaKategoria: ImageCategory = .flower
    @State private var wybranyObrazek: String?
    @Binding var wybraneObrazki: [ImageCategory: String]
    
    var body: some View {
        VStack {
            if let imageName = wybranyObrazek {
                Image(imageName)
                    .resizable()
                    .scaledToFit()
                    .frame(height: 200)
                    .padding()
            }
            
            Picker("Wybierz kategorię", selection: $wybranaKategoria) {
                ForEach(ImageCategory.allCases, id: \.self) {
                    category in
                    Text(category.rawValue).tag(category)
                }
            }
            .padding()
            
            Button(action: {
                let count = MyViewModel.getImageNumbersForCategories().first { $0.0 == wybranaKategoria }?.1 ?? 0
                if count > 0 {
                    let rnd = Int.random(in: 0..<count)
                    wybranyObrazek = MyViewModel.getImageNameForCategory(category: wybranaKategoria, andIndex: rnd)
                    
                    if let obrazek = wybranyObrazek {
                        wybraneObrazki[wybranaKategoria] = obrazek
                    }
                }
            }) {
                Text("Wylosuj obrazek dla kategorii")
                    .foregroundColor(.blue)
                    .padding()
                    .cornerRadius(10)
            }
            .padding()
        }
        .navigationTitle("Losowanie")
    }
}

struct NextView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationStack {
            NextView(MyViewModel: MyViewModel(), wybraneObrazki: .constant([:]))
        }
    }
}

//import SwiftUI

//struct NextView: View {
//    @State var tests: String = ""
//    @ObservedObject var viewModel : MyViewModel
//    var body: some View {
//        NavigationStack {
//            VStack{
//                Text(/*@START_MENU_TOKEN@*/"Hello, World!"/*@END_MENU_TOKEN@*/)
//                TextField("Podaj cos", text: $tests)
//
//                Text(viewModel.item)
//                    .font(.largeTitle)
//                    .foregroundColor(.red)
//
//                Button("Zmien") {
//                    viewModel.changeItem(napis: tests)
//                }
//
//                NavigationLink("jeszcze dalej", destination: NextNextView())
//                    .font(.largeTitle)
//            }
//            .padding()
//        }
//    }
//}
//
//#Preview {
//    NextView(viewModel: MyViewModel())
//}
