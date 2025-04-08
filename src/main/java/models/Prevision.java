package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import connection.MySQLConnection;

public class Prevision extends BaseModel {

    private String libelle;
    private Double montant;
    private Date date;
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Prevision() {
        super();
    }

    public Prevision(Integer id,String libelle, Double montant) {
        super(id);
        this.setLibelle(libelle);
        this.setMontant(montant);
    }

    public Prevision(String libelle, Double montant,Date date) {
        super();
        this.setLibelle(libelle);
        this.setMontant(montant);
        this.setDate(date);
    }

    public Double getReste() throws Exception{    
        return this.getMontant()-this.getAllDepense();
    }

    public Double getAllDepense() throws Exception{
        Double result = Double.valueOf(0);
        String query = "SELECT sum(montant) as montant FROM depense WHERE id_prevision=?";
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = MySQLConnection.getConnection();
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, this.getId());
            rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getDouble("montant");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rs.close();
            stmt.close();
            connection.close();
        }
        return result;
    }

    public static List<Prevision> findByDate(String dateDebut, String dateFin) throws Exception {
        String debut =  !dateDebut.isEmpty() ?dateDebut:"null";
        String fin = !dateFin.isEmpty()?dateFin:"null";
        List<Prevision> result = new ArrayList<>();
        String query = "SELECT * FROM prevision WHERE ("+debut+" IS NULL OR '"+debut+"' <= date) AND ("+fin+" IS NULL OR '"+fin+"' >= date)";
        System.out.println(query);
        try (Connection connection = MySQLConnection.getConnection();PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(mapResultSetToEntity(rs, Prevision.class));
                }
                rs.close();
            }
        }
        return result;
    }
    

}