# -*- coding: utf-8 -*-
"""
serialize json
"""
import json


class SerializeJson:
    # metoda statyczna
    @staticmethod
    def run(deserializeddata, filelocation):
        print("let's serialize something")
        lst = []
        # TODO – do modyfikacji
        for dep in deserializeddata.data:
            lst.append({
                "Kod_TERYT": dep['Kod_TERYT'],
                "Województwo": dep['Województwo'],
                "Powiat": dep['Powiat'],
                "typ_JST": dep['typ_JST'],
                "nazwa_urzędu_JST": dep['nazwa_urzędu_JST'],
                "miejscowość": dep['miejscowość'],
                "telefon_z_numerem_kierunkowym": str(dep['telefon kierunkowy']) + " " + dep['telefon']
            })
            jsontemp = {"departaments": lst}
            with open(filelocation, 'w', encoding='utf-8') as f:
                json.dump(jsontemp, f, ensure_ascii=False)
