import yaml

from deserialize_json import DeserializeJson
from serialize_json import SerializeJson
from convert_json_to_yaml import ConvertJsonToYaml

tempconffile = open("Assets/basic_config.yaml", encoding="utf8")
confdata = yaml.load(tempconffile, Loader=yaml.FullLoader)

print("hey, it's me - Python!")
# newDeserializator = DeserializeJson("Assets/data.json")
# newDeserializator.somestats()
# newDeserializator = DeserializeJson(
#     confdata["paths"]["source_folder"] + confdata["paths"]["json_source_file"]
# )
# newDeserializator.somestats()

# SerializeJson.run(newDeserializator, "Assets/data_mod.json")
# SerializeJson.run(
#     newDeserializator,
#     confdata["paths"]["source_folder"] + confdata["paths"]["json_destination_file"],
# )

# ConvertJsonToYaml.run(newDeserializator, "Assets/data_mod.yaml")
# ConvertJsonToYaml.run2("Assets/data_mod.json", "Assets/data_ymod.yaml")
# ConvertJsonToYaml.run(
#     newDeserializator,
#     confdata["paths"]["source_folder"] + confdata["paths"]["yaml_destination_file"],
# )
# w pythonie nie ma przeciążania funkcji, wiec zrobilem drugą funkcje run
# ConvertJsonToYaml.run2(
#     confdata["paths"]["source_folder"] + confdata["paths"]["json_destination_file"],
#     confdata["paths"]["source_folder"] + confdata["paths"]["yaml_destination_file2"],
# )

# zadanie 2.6.4.
newDeserializator = DeserializeJson(
    confdata["paths"]["source_folder"] + confdata["paths"]["json_source_file"]
)
for operation in confdata["operations"]:
    print("wykonuje operacje: " + operation)
    if operation == "deserialization":
        newDeserializator.somestats()

    elif operation == "serialization":
        SerializeJson.run(
            newDeserializator,
            confdata["paths"]["source_folder"]
            + confdata["paths"]["json_destination_file"],
        )

    elif operation == "json-to-yaml":
        if confdata["source"]["type"] == "file":
            print("zrodlo to plik")
            ConvertJsonToYaml.run2(
                confdata["paths"]["source_folder"]
                + confdata["paths"]["json_source_file"],
                confdata["paths"]["source_folder"]
                + confdata["paths"]["yaml_destination_file2"],
            )
        elif confdata["source"]["type"] == "object":
            print("zrodlo to obiekt")
            ConvertJsonToYaml.run(
                newDeserializator,
                confdata["paths"]["source_folder"]
                + confdata["paths"]["yaml_destination_file"],
            )
        else:
            print("nieznane zrodlo serializacji")
    else:
        print("nieznana operacja")
