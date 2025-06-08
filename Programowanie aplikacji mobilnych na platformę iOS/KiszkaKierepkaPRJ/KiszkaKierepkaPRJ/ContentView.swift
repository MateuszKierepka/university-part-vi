//
//  ContentView.swift
//  KiszkaKierepkaPRJ
//
//  Created by student on 22/05/2025.
//

import SwiftUI
import CoreData

struct ContentView: View {
    @State private var searchText: String = ""
    
    @Environment(\.managedObjectContext) private var viewContext
    
    @FetchRequest(
        sortDescriptors: [NSSortDescriptor(keyPath: \City.cityName, ascending: true)],
        animation: .default)
    private var cities: FetchedResults<City>
    
    var filteredCities: [City] {
        if searchText.isEmpty {
            return Array(cities)
        } else {
            let search = searchText.lowercased()
            return cities.filter { city in
                (city.cityName ?? "").lowercased().contains(search)
            }
        }
    }
    
    var favouriteCities: [City] {
        filteredCities.filter { $0.isFavourite }
    }
    
    var otherCities: [City] {
        filteredCities.filter { !$0.isFavourite }
    }
    
    var body: some View {
        NavigationView {
            VStack {
                TextField("Szukaj miasta...", text: $searchText)
                    .padding(8)
                    .background(Color(.systemGray6))
                    .cornerRadius(8)
                    .padding([.horizontal, .top])
                    .onChange(of: searchText) { oldValue, newValue in
                        let filtered = newValue.filter { !$0.isNumber }
                        if filtered != newValue {
                            searchText = filtered
                        }
                    }
                List {
                    if !favouriteCities.isEmpty {
                        Section(header: Text("Ulubione")) {
                            ForEach(favouriteCities) { city in
                                HStack {
                                    NavigationLink(destination: cityForecastView(city: city)) {
                                        Text(city.cityName ?? "")
                                    }
                                    Spacer()
                                    Image(systemName: "star.fill")
                                        .foregroundColor(.yellow)
                                        .onTapGesture {
                                            toggleFavourite(city)
                                        }
                                }
                            }
                            .onDelete(perform: removeFromFavourites)
                        }
                    }
                    if !otherCities.isEmpty {
                        Section(header: Text("Wszystkie miasta")) {
                            ForEach(otherCities) { city in
                                HStack {
                                    NavigationLink(destination: cityForecastView(city: city)) {
                                        Text(city.cityName ?? "")
                                    }
                                    Spacer()
                                    Image(systemName: "star")
                                        .foregroundColor(.gray)
                                        .onTapGesture {
                                            toggleFavourite(city)
                                        }
                                }
                            }
                        }
                    }
                }
                .listStyle(.plain)
            }
            .navigationTitle("Weather")
            .onAppear {
                if cities.isEmpty {
                    propagateData()
                }
            }
        }
    }
    
    private func propagateData() {
        let berlinWeatherData: [(temp: Int16, humidity: Double, pressure: Int16, wind: Int16, sunrise: Int16, sunset: Int16)] = [
            (22, 0.5, 1012, 10, 320, 1200),
            (24, 0.45, 1010, 15, 322, 1205),
            (23, 0.52, 1011, 12, 321, 1203),
            (25, 0.4, 1008, 17, 323, 1208),
            (21, 0.55, 1013, 9, 319, 1198)
        ]
        let warsawWeatherData: [(temp: Int16, humidity: Double, pressure: Int16, wind: Int16, sunrise: Int16, sunset: Int16)] = [
            (20, 0.6, 1015, 8, 315, 1210),
            (21, 0.58, 1014, 10, 316, 1212),
            (22, 0.53, 1012, 11, 317, 1215),
            (23, 0.5, 1013, 13, 318, 1218),
            (19, 0.62, 1016, 7, 314, 1207)
        ]
        let lublinWeatherData: [(temp: Int16, humidity: Double, pressure: Int16, wind: Int16, sunrise: Int16, sunset: Int16)] = [
            (18, 0.65, 1014, 9, 318, 1210),
            (19, 0.6, 1013, 11, 319, 1214),
            (20, 0.58, 1011, 12, 320, 1217),
            (21, 0.55, 1012, 10, 321, 1219),
            (17, 0.68, 1015, 8, 317, 1209)
        ]
        
        var berlinWeathers: [Weather] = []
        for i in 0..<5 {
            let weather = Weather(context: viewContext)
            weather.date = Calendar.current.date(byAdding: .day, value: i, to: Date())
            weather.temperature = berlinWeatherData[i].temp
            weather.humiditity = berlinWeatherData[i].humidity
            weather.pressure = berlinWeatherData[i].pressure
            weather.wind = berlinWeatherData[i].wind
            weather.sunrise = berlinWeatherData[i].sunrise
            weather.sunset = berlinWeatherData[i].sunset
            weather.timestamp = Int16(100 + i)
            do {
                try viewContext.save()
            } catch {
                let nsError = error as NSError
                fatalError("Unresolved error \(nsError), \(nsError.userInfo)")
            }
            berlinWeathers.append(weather)
        }
        
        var warsawWeathers: [Weather] = []
        for i in 0..<5 {
            let weather = Weather(context: viewContext)
            weather.date = Calendar.current.date(byAdding: .day, value: i, to: Date())
            weather.temperature = warsawWeatherData[i].temp
            weather.humiditity = warsawWeatherData[i].humidity
            weather.pressure = warsawWeatherData[i].pressure
            weather.wind = warsawWeatherData[i].wind
            weather.sunrise = warsawWeatherData[i].sunrise
            weather.sunset = warsawWeatherData[i].sunset
            weather.timestamp = Int16(200 + i)
            do {
                try viewContext.save()
            } catch {
                let nsError = error as NSError
                fatalError("Unresolved error \(nsError), \(nsError.userInfo)")
            }
            warsawWeathers.append(weather)
        }
        
        var lublinWeathers: [Weather] = []
        for i in 0..<5 {
            let weather = Weather(context: viewContext)
            weather.date = Calendar.current.date(byAdding: .day, value: i, to: Date())
            weather.temperature = lublinWeatherData[i].temp
            weather.humiditity = lublinWeatherData[i].humidity
            weather.pressure = lublinWeatherData[i].pressure
            weather.wind = lublinWeatherData[i].wind
            weather.sunrise = lublinWeatherData[i].sunrise
            weather.sunset = lublinWeatherData[i].sunset
            weather.timestamp = Int16(300 + i)
            do {
                try viewContext.save()
            } catch {
                let nsError = error as NSError
                fatalError("Unresolved error \(nsError), \(nsError.userInfo)")
            }
            lublinWeathers.append(weather)
        }
        
        let berlin = City(context: viewContext)
        berlin.cityName = "Berlin"
        berlin.country = "Deutschland"
        berlin.latitude = "52.520008"
        berlin.longtitude = "13.404954"
        for weather in berlinWeathers {
            berlin.addToRelationship(weather)
        }
        
        let warsaw = City(context: viewContext)
        warsaw.cityName = "Warszawa"
        warsaw.country = "Polska"
        warsaw.latitude = "52.229676"
        warsaw.longtitude = "21.012229"
        for weather in warsawWeathers {
            warsaw.addToRelationship(weather)
        }
        
        let lublin = City(context: viewContext)
        lublin.cityName = "Lublin"
        lublin.country = "Polska"
        lublin.latitude = "51.246452"
        lublin.longtitude = "22.568445"
        for weather in lublinWeathers {
            lublin.addToRelationship(weather)
        }
        
        do {
            try viewContext.save()
        } catch {
            let nsError = error as NSError
            fatalError("Unresolved error \(nsError), \(nsError.userInfo)")
        }
    }
    
    private func toggleFavourite(_ city: City) {
        city.isFavourite.toggle()
        do {
            try viewContext.save()
        } catch {
            let nsError = error as NSError
            fatalError("Unresolved error \(nsError), \(nsError.userInfo)")
        }
    }
    
    private func removeFromFavourites(at offsets: IndexSet) {
        for index in offsets {
            let city = favouriteCities[index]
            city.isFavourite = false
        }
        do {
            try viewContext.save()
        } catch {
            let nsError = error as NSError
            fatalError("Unresolved error \(nsError), \(nsError.userInfo)")
        }
    }
}
