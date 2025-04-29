<?php
class Cities{
    private $citiesTable = "city";
    public $id;
    public $name;
    public $countryCode;
    public $district;
    public $population;
    
    public function __construct($db){
        $this->conn = $db;
    }

    function read(){
        if($this->id) {
            $stmt = $this->conn->prepare("SELECT * FROM ".$this->citiesTable." WHERE ID = ?");
            $stmt->bind_param("i", $this->id);
        } else {
            $stmt = $this->conn->prepare("SELECT * FROM ".$this->citiesTable);
        }
        $stmt->execute();
        $result = $stmt->get_result();
        return $result;
    }

    function create(){
        $stmt = $this->conn->prepare("INSERT INTO ".$this->citiesTable." (Name, CountryCode, District, Population) VALUES(?, ?, ?, ?)");
        $this->name = htmlspecialchars(strip_tags($this->name));
        $this->countryCode = htmlspecialchars(strip_tags($this->countryCode));
        $this->district = htmlspecialchars(strip_tags($this->district));
        $this->population = htmlspecialchars(strip_tags($this->population));
        $stmt->bind_param("sssi", $this->name, $this->countryCode, $this->district, $this->population);
        if($stmt->execute()){
            return true;
        }
        return false;
    }

    function update(){
        $stmt = $this->conn->prepare("UPDATE ".$this->citiesTable." SET Name = ?, CountryCode = ?, District = ?, Population = ? WHERE ID = ?");
        $this->name = htmlspecialchars(strip_tags($this->name));
        $this->countryCode = htmlspecialchars(strip_tags($this->countryCode));
        $this->district = htmlspecialchars(strip_tags($this->district));
        $this->population = htmlspecialchars(strip_tags($this->population));
        $this->id = htmlspecialchars(strip_tags($this->id));
        $stmt->bind_param("sssii", $this->name, $this->countryCode, $this->district, $this->population, $this->id);
        if($stmt->execute()){
            return true;
        }
        return false;
    }

    function delete(){
        $stmt = $this->conn->prepare("DELETE FROM ".$this->citiesTable." WHERE ID = ?");
        $this->id = htmlspecialchars(strip_tags($this->id));
        $stmt->bind_param("i", $this->id);
        if($stmt->execute()){
            return true;
        }
        return false;
    }
}
