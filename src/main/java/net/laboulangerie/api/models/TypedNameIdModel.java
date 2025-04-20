package net.laboulangerie.api.models;

public class TypedNameIdModel extends NameIdModel {
    private String type = null;

    public TypedNameIdModel(String name, String id, String type) {
        super(name, id);
        this.type = type;
    }

    public TypedNameIdModel(NameIdModel model, String type) {
        super(model.getName(), model.getId());
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
