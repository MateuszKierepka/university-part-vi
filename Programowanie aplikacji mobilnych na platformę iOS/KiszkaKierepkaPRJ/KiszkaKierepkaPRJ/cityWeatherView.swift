//
//  cityWeatherView.swift
//  KiszkaKierepkaPRJ
//
//  Created by student on 22/05/2025.
//

import SwiftUI

struct cityWeatherView: View {
    var weather: Weather

    var body: some View {
        VStack(spacing: 24) {
            if let date = weather.date {
                Text(date, style: .date)
                    .font(.title)
                    .fontWeight(.bold)
                    .padding()
            }
            
            let (iconName, iconColor) = weatherIconAndColor(weather.temperature)
            Image(systemName: iconName)
                .resizable()
                .scaledToFit()
                .frame(width: 80, height: 80)
                .foregroundColor(iconColor)
            
            Text("\(weather.temperature)°C")
                .font(.largeTitle)
            
            VStack(alignment: .leading, spacing: 16) {
                HStack {
                    Label("Wilgotność", systemImage: "humidity")
                    Spacer()
                    Text(String(format: "%.0f%%", weather.humiditity * 100))
                }
                HStack {
                    Label("Ciśnienie", systemImage: "gauge")
                    Spacer()
                    Text("\(weather.pressure) hPa")
                }
                HStack {
                    Label("Wiatr", systemImage: "wind")
                    Spacer()
                    Text("\(weather.wind) km/h")
                }
                HStack {
                    Label("Wschód słońca", systemImage: "sunrise.fill")
                    Spacer()
                    Text(formatTime(weather.sunrise))
                }
                HStack {
                    Label("Zachód słońca", systemImage: "sunset.fill")
                    Spacer()
                    Text(formatTime(weather.sunset))
                }
            }
            .font(.headline)
            .padding()
            .background(Color(.systemGray6))
            .cornerRadius(12)
            
            Spacer(minLength: 24)
        }
        .padding(.horizontal)
    }
        
    private func formatTime(_ timestamp: Int16) -> String {
        let hours = Int(timestamp) / 60
        let minutes = Int(timestamp) % 60
        return String(format: "%02d:%02d", hours, minutes)
    }
    
    private func weatherIconAndColor(_ temperature: Int16) -> (String, Color) {
        if temperature < 20 {
            return ("cloud.rain", .blue)
        } else if temperature < 22 {
            return ("cloud", .gray)
        } else if temperature < 25 {
            return ("cloud.sun", .yellow)
        } else {
            return ("sun.max", .yellow)
        }
    }
}
