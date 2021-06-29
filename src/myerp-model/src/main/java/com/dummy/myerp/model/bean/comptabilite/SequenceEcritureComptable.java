package com.dummy.myerp.model.bean.comptabilite;


/**
 * Bean représentant une séquence pour les références d'écriture comptable
 */
public class SequenceEcritureComptable {

    // ==================== Attributs ====================
    /** Le code du journal */
    private String codeJournal;
    /** L'année */
    private Integer annee;
    /** La dernière valeur utilisée */
    private Integer derniereValeur;

    // ==================== Constructeurs ====================
    /**
     * Constructeur
     */
    public SequenceEcritureComptable() {
    }

    /**
     * Constructeur
     *
     * @param pCodeJournal -
     * @param pAnnee -
     * @param pDerniereValeur -
     */
    public SequenceEcritureComptable(String pCodeJournal, Integer pAnnee, Integer pDerniereValeur) {
        codeJournal = pCodeJournal;
        annee = pAnnee;
        derniereValeur = pDerniereValeur;
    }


    // ==================== Getters/Setters ====================

    public String getCodeJournal() { return codeJournal; }
    public void setCodeJournal(String codeJournal) { this.codeJournal = codeJournal; }
    public Integer getAnnee() {
        return annee;
    }
    public void setAnnee(Integer pAnnee) {
        annee = pAnnee;
    }
    public Integer getDerniereValeur() {
        return derniereValeur;
    }
    public void setDerniereValeur(Integer pDerniereValeur) {
        derniereValeur = pDerniereValeur;
    }


    // ==================== Méthodes ====================
    @Override
    public String toString() {
        final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
        final String vSEP = ", ";
        vStB.append("{")
                .append("codeJournal='").append(codeJournal).append('\'')
                .append(vSEP).append("annee=").append(annee)
                .append(vSEP).append("derniereValeur=").append(derniereValeur)
                .append("}");
        return vStB.toString();
    }
}
