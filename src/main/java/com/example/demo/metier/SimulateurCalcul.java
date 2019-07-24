package com.example.demo.metier;

public interface SimulateurCalcul {
  public double calculTauxInteret(double mtCredit, double mensualite, int periode, double tauxTva);
  public  double calculTeg(double mtCredit, double mensualite, int periode, double fraisInclu, double interetIntercalaire, double  tauxTva, int nbrJours, int nbrMois);
  public   double calculInteretIntercalaire(double mtCredit, double mensualite, int periode, double fraisInclu, double interetIntercalaire, double  tauxTva,double tauxDeg, int nbrJours, int nbrMois);
  public double calculTegReference(double mtCredit, double mensualite, int periode, double fraisInclu,  double  tauxTva,double tauxDeg );
  public double[] calculMois(double mtCredit, double mensualite,  double  tauxTva,double tauxDeg);
  public double[] calculCapitalTraiteAvecAssurance(double mtCredit, int periode, double  tauxTva,double tauxDeg, double tauxAssurance );
  public double[] calculCapitalTraite(double mtCredit, int periode, double  tauxTva,double tauxDeg );
  public double calculEcartMensualite(double mtCredit  , int periode   , double mensualite  , double taux  , double tauxTva  );
}
