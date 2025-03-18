//
//  ExchangeView.swift
//  lab03
//
//  Created by student on 18/03/2025.
//

import SwiftUI

struct ExchangeView: View {
    @State private var selectedBuyCurrency = "USD"
    @State private var selectedPayCurrency = "PLN"
    @State private var amountToBuy: String = ""
    
    let currencies = ["USD", "EUR", "PLN","GBP", "JPY"]
    
    let exchangeRates: [String: [String: Double]] = [
        "USD": ["PLN": 4, "EUR": 0.85, "GBP": 0.75, "JPY": 110],
        "EUR": ["PLN": 4.2, "USD": 1.18, "GBP": 0.88, "JPY": 130],
        "PLN": ["USD": 0.25, "EUR": 0.21, "GBP": 0.19, "JPY": 28],
        "GBP": ["PLN": 5.2, "USD": 1.34, "EUR": 1.14, "JPY": 150],
        "JPY": ["PLN": 0.04, "USD": 0.009, "EUR": 0.007, "GBP": 0.007]
    ]
    
    var convertedAmount: Double {
        if let amount = Double(amountToBuy), let rate = exchangeRates[selectedBuyCurrency]?[selectedPayCurrency] {
            return amount * rate
        }
        return 0
    }
    
    var body: some View {
        VStack {
            VStack(alignment: .leading) {
                Text("Wybierz walutę do kupienia:")
                Picker("", selection: $selectedBuyCurrency) {
                    ForEach(currencies.filter { $0 != selectedPayCurrency }, id: \ .self) {
                        currency in
                        Text(currency)
                    }
                }
                .pickerStyle(MenuPickerStyle())
                
                Text("Wybierz walutę do zapłaty:")
                Picker("", selection: $selectedPayCurrency) {
                    ForEach(currencies.filter { $0 != selectedBuyCurrency }, id: \ .self) {
                        currency in
                        Text(currency)
                    }
                }
                .pickerStyle(MenuPickerStyle())
            }
            TextField("Ilość kupowanej waluty", text: $amountToBuy)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .keyboardType(.decimalPad)
                .padding()
            Text("Kwota do zapłaty: \(convertedAmount, specifier: "%.2f") \(selectedPayCurrency)")
                .font(.title2)
                .bold()
                .padding()
            
            Spacer()
        }
        .padding()
    }
}

struct ExchangeView_Previews: PreviewProvider {
    static var previews: some View {
        ExchangeView()
    }
}
