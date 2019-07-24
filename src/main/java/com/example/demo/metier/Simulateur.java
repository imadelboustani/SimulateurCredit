package com.example.demo.metier;

import org.springframework.stereotype.Service;

@Service
public class Simulateur implements SimulateurCalcul {
	private double mtCredit;
	private double mensualite;
	private int periode;
	private double tauxInteret;
	private double teg;
	private double tegReference;
	private double tauxTva;
	private double  fractionAssure;
	
	public Simulateur() {
		
	}
	public double getMtCredit() {
		return mtCredit;
	}
	public void setMtCredit(double mtCredit) {
		this.mtCredit = mtCredit;
	}
	public double getMensualite() {
		return mensualite;
	}
	public void setMensualite(double mensualite) {
		this.mensualite = mensualite;
	}
	public int getPeriode() {
		return periode;
	}
	public void setPeriode(int periode) {
		this.periode = periode;
	}
	public double getTauxInteret() {
		return tauxInteret;
	}
	public void setTauxInteret(double tauxInteret) {
		this.tauxInteret = tauxInteret;
	}
	public double getTeg() {
		return teg;
	}
	public void setTeg(double teg) {
		this.teg = teg;
	}
	public double getTegReference() {
		return tegReference;
	}
	public void setTegReference(double tegReference) {
		this.tegReference = tegReference;
	}
	public double getTauxTva() {
		return tauxTva;
	}
	public void setTauxTva(double tauxTva) {
		this.tauxTva = tauxTva;
	}
	@Override
	public double calculTauxInteret(double mtCredit, double mensualite, int periode, double tauxTva) {
		double epsilon= 0.0000000001, fractile=1, result=1, taux1=0, taux2= 1.50,taux=0;
		
	double mensMin = mtCredit/periode; System.out.println("mensMin " + mensMin);
		double mensMax = ((mtCredit*taux2)/12)/(1-Math.pow(1+(taux2/12), -periode)); System.out.println("mensMax " + mensMax);
	if( mensualite <mensMin || mensualite > mensMax )
		 return -2;
	 double result1 = calculEcartMensualite(mtCredit, periode, mensualite, taux1, tauxTva);System.out.println("result1 " + result1);
	 double result2 = calculEcartMensualite(mtCredit, periode, mensualite, taux2, tauxTva);System.out.println("result2 " + result2);
	 if(result1 * result2>0) {
		 return -1;
	 }
	 else {
		 while(fractile> epsilon) {
			
			double xresult=result ;
              taux=(taux1+taux2)/2 ;
              System.out.println("taux1 " + taux1 + " taux2 "+ taux2 + " taux "+ taux);
              result= calculEcartMensualite(mtCredit, periode, mensualite, taux, tauxTva);
             if  (result1*result<0 ) {
                     taux2=taux ;
                      result2=result ;
             }

             if (result2*result <0)   {
                     taux1=taux ; 
                     result1=result ;
             }
             
           
	 fractile =   Math.abs(result1- result2) ; 

		 }
		 return taux*100;
	 }
		 

	}
	@Override
	public double calculTeg(double mtCredit, double mensualite, int periode, double fraisInclu,
			double interetIntercalaire, double tauxTva, int nbrJours, int nbrMois) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double calculInteretIntercalaire(double mtCredit, double mensualite, int periode, double fraisInclu,
			double interetIntercalaire, double tauxTva, double tauxDeg, int nbrJours, int nbrMois) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double calculTegReference(double mtCredit, double mensualite, int periode, double fraisInclu, double tauxTva,
			double tauxDeg) {
	fraisInclu =  (double) fraisInclu > 165 ?  fraisInclu -165 :  0;
	return calculTauxInteret(mtCredit - fraisInclu, mensualite, periode, tauxTva);
		 
	}
	@Override
	public double[] calculMois(double mtCredit, double mensualite, double tauxTva, double tauxDeg) {
	 double flg_ret, nbr_mens_as_float =0 ,traiteTotal=0,  mensualiteSeuil, val1;
	 int nbrMens=0;
	 
	 if (mtCredit <= 0 ||  mensualite <= 0)  
		 return new double[]{-1  , mtCredit , nbrMens , tauxDeg, tauxTva , mensualite, traiteTotal };
	 if (mtCredit <=mensualite)  
		 return new double[]{-2  , mtCredit , nbrMens , tauxDeg, tauxTva , mensualite, traiteTotal };
	 if ( Math.abs(tauxDeg) <  0.000009) {  
				 nbr_mens_as_float = mtCredit/mensualite ;
				 nbrMens   = (int) Math.round(nbr_mens_as_float) ;
				if  (nbrMens == 0) nbrMens = 1;
				 mensualite = mtCredit/nbrMens ;
				 traiteTotal = mtCredit ;
				return   new double[] {0  , mtCredit , nbrMens , tauxDeg, tauxTva , mensualite, traiteTotal }  ;
	 }
	  mensualiteSeuil = (mtCredit*tauxDeg/12)*(1+tauxTva) ;
	 

	 if ( mensualite <=  mensualiteSeuil+0.01) {   
	 	 traiteTotal 	= 0 ;
	 	 nbrMens 		= 0 ;
	 	return new double[] { -10   , mtCredit , nbrMens , tauxDeg, tauxTva , mensualite, traiteTotal };
	 }
	  val1=1-mtCredit/mensualite*tauxDeg/12*(1+tauxTva) ;

	 val1 = 1-mensualiteSeuil/mensualite ;

	 if (val1 <= 0.00001) {  
	 	 traiteTotal 	= 0 ;
	 	nbrMens 		= 0 ;
	 	return  new double[] {-3  , mtCredit , nbrMens , tauxDeg, tauxTva , mensualite, traiteTotal };
	 }
	  nbr_mens_as_float = -(Math.log(val1))/Math.log( (1+tauxDeg/12*(1+tauxTva) ));
	 nbrMens   =(int) Math.round(nbr_mens_as_float  );

	 if (nbrMens > 1500) { 
	  traiteTotal 	= 0 ;
	 	nbrMens =  0 ;
	 	return  new double[] {-4  , mtCredit , nbrMens , tauxDeg, tauxTva , mensualite, traiteTotal }   ;
	 }
	 if(nbrMens == 0) nbrMens = 1;

	 double[] tmp = calculCapitalTraite(mtCredit, nbrMens, tauxTva, tauxDeg);

	 return new double[] { 0 , mtCredit , nbrMens , tauxDeg, tauxTva , tmp[1], tmp[0] } ;

		
	}
	
	@Override
	public double[] calculCapitalTraiteAvecAssurance(double mtCredit, int periode,  double tauxTva, double tauxDeg,
			double tauxAssurance) {
		double mensualite1, traite;
		double fractionAss = (tauxAssurance/100) *mtCredit;
		if(Math.abs(tauxDeg)<= 0.00001) {
	      mensualite1 = mtCredit/periode + fractionAss ;
		  traite = mensualite1 * periode ;
		}
		  else {
			  tauxDeg = tauxDeg*(1+ tauxTva);
		      double tmp = Math.pow(1+ (tauxDeg/12), periode);
		      traite = ((mtCredit*periode*tauxDeg)/12) * (tmp/(tmp-1));
		      mensualite1 = traite/periode;
		      mensualite1 = mensualite1+ fractionAss;
		      traite = mensualite1*periode;
		  }
		return new double[] {traite, mensualite1, fractionAss };
	}
	@Override
	public double[] calculCapitalTraite(double mtCredit,  int periode, double tauxTva, double tauxDeg) {
           
	double[] tmp = calculCapitalTraiteAvecAssurance(mtCredit, periode, tauxTva, tauxDeg,0);
		return new double[] {tmp[0], tmp[1]};
	}
	@Override
	public double calculEcartMensualite(double mtCredit, int periode, double mensualite, double taux, double tauxTva) {
		// TODO Auto-generated method stub
		  double[] mensualite1 = calculCapitalTraite(mtCredit, periode, tauxTva, taux);
		    return mensualite1[1] -mensualite;
	}
	
	
	

}
