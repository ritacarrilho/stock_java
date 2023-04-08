import org.json.simple.JSONObject;

// Car: marque, le. model, l'âge, la carburation et l'état du véhicule.
public class Voiture {
    private String brand;
    private String model;
    private Integer age;
    private String carburation;
    private Integer condition;

    public Voiture(String brand, String model, Integer age, String carburation, Integer condition) {
        this.brand = brand;
        this.model = model;
        this.age = age;
        this.carburation = carburation;
        this.condition = condition;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCarburation() {
        return carburation;
    }

    public void setCarburation(String carburation) {
        this.carburation = carburation;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    // Convert voiture obj in json
    public JSONObject voitureToJson(){
        JSONObject voitureJson = new JSONObject();
        voitureJson.put("brand", this.getBrand());
        voitureJson.put("model", this.getModel());
        voitureJson.put("age", this.getAge());
        voitureJson.put("carburation", this.getCarburation());
        voitureJson.put("condition", this.getCondition());

        return voitureJson;
    }
}
