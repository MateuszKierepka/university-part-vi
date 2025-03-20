# -*- coding: utf-8 -*-
"""
json to yaml converter
"""
import yaml
import json


class ConvertJsonToYaml:
    @staticmethod
    def run(deserializeddata, destinationfilelocaiton):
        print("let's convert something")
        with open(destinationfilelocaiton, "w", encoding="utf8") as f:
            yaml.dump(deserializeddata, f, allow_unicode=True)
            print("it is done")

    @staticmethod
    def run2(jsonfilelocation, destinationfilelocation):
        print("let's convert json to yaml")
        with open(jsonfilelocation, "r", encoding="utf8") as f2:
            data = json.load(f2)
        with open(destinationfilelocation, "w", encoding="utf8") as f:
            yaml.dump(data, f, allow_unicode=True)
            print("it is done!")
