# -*- coding: utf-8 -*-
"""
deserialize json
"""
import json


class DeserializeJson:
    # konstruktor
    def __init__(self, filename):
        print("let's deserialize something")
        tempdata = open(filename, encoding="utf8")
        self.data = json.load(tempdata)

    # przykładowe statystyki
    def somestats(self):
        example_stat = 0
        for dep in self.data:
            if dep['typ_JST'] == 'GM' and dep['Województwo'] == 'dolnośląskie':
                example_stat += 1
        print('liczba urzędów miejskich w województwie dolnośląskim: ' + ' ' + str(example_stat))

        slownik = {}
        for dep in self.data:
            woj = dep["Województwo"].strip()
            if woj not in slownik:
                slownik[woj] = []
            slownik[woj].append(dep['typ_JST'])

        for woj in slownik:
            print("wojewodztwo: " + woj)

            urzedy = slownik[woj]
            urzedy_set = set(urzedy)

            for urz in urzedy_set:
                counter = 0
                for urz_el in urzedy:
                    if urz_el == urz:
                        counter += 1
                print("\t" + urz + ": " + str(counter))
