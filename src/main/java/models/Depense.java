package models;

public class Depense extends BaseModel{
    private Integer id_prevision;
    private Double montant;

    public Depense() {
        super();
    }

    public Depense(Integer id_prevision, Double montant){
        super();
        this.setId_prevision(id_prevision);
        this.setMontant(montant);
    }

    public Integer getId_prevision() {
        return id_prevision;
    }

    public void setId_prevision(Integer id_prevision) {
        this.id_prevision = id_prevision;
    }

    public Double getMontant() {
        return montant;
    }
    public void setMontant(Double montant) {
        this.montant = montant;
    }

    
}
