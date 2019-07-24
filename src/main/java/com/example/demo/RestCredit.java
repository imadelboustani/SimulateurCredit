package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.metier.Simulateur;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@RestController

public class RestCredit {
	@Autowired
	private Simulateur simulateur;
	@RequestMapping(value = "/test", method= RequestMethod.GET)
			public double[] home() {
	//	   return  simulateur.calculCapitalTraiteAvecAssurance(500000,  300, 0.1, 0.00001, 0.03);
	       // return (int) simulateur.calculCapitalTraite(50000, 84, 0.1, 0.001);
		   // return (int) simulateur.calculEcartMensualite(50000, 84, 600, 0.01, 0.1);
		// return (int) simulateur.calculTauxInteret(50000, 700, 84, 0.1);
		 // return (int) simulateur.calculTegReference(50000, 800, 84, 300, 0.1, 0.000001);
	    return simulateur.calculMois(10000, 880, 0.1, 0.1);
	} 
	
}
