//
//  cityForecastView.swift
//  KiszkaKierepkaPRJ
//
//  Created by student on 29/05/2025.
//

import SwiftUI

struct cityForecastView: View {
    @State var selectedWeather: Weather? = nil
    var city: City
    let today = Calendar.current.startOfDay(for: Date())
    
    var body: some View {
        VStack {
            List {
                if let weatherSet = city.relationship as? Set<Weather> {
                    let endDate = Calendar.current.date(byAdding: .day, value: 4, to: today)!
                    let filteredWeather = weatherSet.filter { weather in
                        guard let weatherDate = weather.date else { return false }
                        let weatherDay = Calendar.current.startOfDay(for: weatherDate)
                        return weatherDay >= today && weatherDay <= endDate
                    }
                    let sortedWeather = filteredWeather.sorted {
                        ($0.date ?? Date.distantPast) < ($1.date ?? Date.distantPast)
                    }
                    
                    ForEach(Array(sortedWeather.enumerated()), id: \.element) { idx, weatherForDay in
                        HStack {
                            Text(dayLabel(weatherForDay.date, idx))
                                .frame(width: 60, alignment: .leading)
                                .font(.headline)
                            
                            let (iconName, iconColor) = weatherIconAndColor(weatherForDay.temperature)
                            Image(systemName: iconName)
                                .foregroundColor(iconColor)
                            
                            Spacer()

                            Text("\(weatherForDay.temperature)°C")
                                .font(.headline)
                                .frame(width: 50, alignment: .trailing)
                        }
                        .contentShape(Rectangle())
                        .onTapGesture {
                            selectedWeather = weatherForDay
                        }
                    }
                } else {
                    Text("No weather data")
                }
            }
            .sheet(item: $selectedWeather) { weather in
                cityWeatherView(weather: weather)
            }
        }
        .navigationTitle(city.cityName ?? "City Forecast")
    }
    
    func dayLabel(_ date: Date?, _ index: Int) -> String {
        guard let date = date else {
            return ""
        }
        if Calendar.current.isDateInToday(date) || index == 0 {
            return "Today"
        } else {
            let formatter = DateFormatter()
            formatter.locale = Locale.current
            formatter.dateFormat = "EEE"
            return formatter.string(from: date)
        }
    }

    func weatherIconAndColor(_ temperature: Int16) -> (String, Color) {
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
