package net.laboulangerie.api.models;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class MmoModel implements Serializable {
    private int palier = 0;
    private List<TalentModel> talents = Collections.emptyList();

    public int getPalier() {
        return palier;
    }

    public void setPalier(int palier) {
        this.palier = palier;
    }

    public List<TalentModel> getTalents() {
        return talents;
    }

    public void setTalents(List<TalentModel> talents) {
        this.talents = talents;
    }
}
