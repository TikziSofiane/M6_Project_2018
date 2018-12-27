package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServicePlayer {

	private String fileName;
	private String filePath;
	private String nomServiceFichier; //nom du service qui apparait dans le nom du fichier
	private String nomServiceAffichage; //nom du services qui apparait sur le RSE : avec espaces et caracteres speciaux
	private List<Double> donnees; 
	private Double donneePapierMois; //somme impression et photocopie du service pour le mois  
	private Double score, scoreCumule;
	private String division;

	//constructeur
	public ServicePlayer(double donneeMois, String nomServiceFichier, String nomServiceAffichage) {
	
		filePath = "Donnees/";  //nom du dossier contenant tous les fichiers contenant les donnees
		fileName = filePath + "donnee" + nomServiceFichier + ".txt"; //nom du fichier, un fichier par service
		this.nomServiceFichier = nomServiceFichier; 
		this.nomServiceAffichage = nomServiceAffichage;
		donnees = new ArrayList<Double>();
		this.donneePapierMois = donneeMois;
		score = 0.0;
		scoreCumule = 0.0;

	}
		   
	//Getters and setters
	public Double getScoreCumule() {
		return scoreCumule;
	}
	
	public Double getScore() {
		return score;
	}
	
	public String getDivision() {
		return division;
	}

	public String getNomServiceFichier() {
		return nomServiceFichier;
	}

	
	public String getNomServiceAffichage() {
		return nomServiceAffichage;
	}
	
	public String getFileName() {
		return fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public List<Double> getDonnees() {
		return donnees;
	}

	public Double getDonneePapierMois() {
		return donneePapierMois;
	}

	public void getDataService() throws IOException{
		double moyenne = 0;
		File directory = new File(filePath);
		if(!directory.exists()){
			directory.mkdirs();
		}
		//si le service n'existait pas le mois precedent, creer un fichier et le remplir avec la donnee du mois 
		if(!new File(fileName).exists())
		{
			try{
				FileWriter writer= new FileWriter(fileName);
				BufferedWriter bw = new BufferedWriter(writer);
				for(int i=0 ; i<4 ; i++){
					bw.write(Double.toString(0));
					bw.newLine();
				}
				for(int j=0 ; j<12 ; j++){
					bw.write(Double.toString(donneePapierMois));
					bw.newLine();
				}
			    bw.close();
				}catch(IOException e){
		    		e.printStackTrace();
				}
		}
		
		//lire le fichier du service correspondant et le stocker dans un stream
		try(BufferedReader buffer = new BufferedReader(new FileReader(fileName))){
			 List<String> fichier = buffer.lines().collect(Collectors.toList()); 
			 //stocker les donnees du fichier dans une arraylist
			 donnees=fichier.stream().map(Double::valueOf).collect(Collectors.toList());
			 //calculer la moyenne sur les 12 derniers mois
	    	 moyenne = donnees.stream().skip(4).mapToDouble(Double :: valueOf).average().getAsDouble();
	    	 //attribuer une division au service en fonction de la moyenne
	    	 if(moyenne<100) division = "Bonsai";
	    	 else if(moyenne>=100 && moyenne<1000) division = "Platane";
	    	 else division = "Baobab";
	    	 //modifier la moyenne dans l'arraylist
	    	 donnees.set(2,moyenne);
	    	 deleteData();
	    	 //rajouter la nouvelle donnee du mois de l'arraylist
	    	 donnees.add(donneePapierMois);
	    	 donnees.set(1, calculScore()); 
	    	 scoreCumule = score + donnees.get(0);
	    	 donnees.set(0, donnees.get(0)+donnees.get(1));	 
	    	 ecriture_fichier(); 
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	 //supprimer la donnee du mois le plus ancien de l'arraylist
	private void deleteData(){
		donnees.remove(4); 
	}
	
	
	private Double calculScore(){
		double bonus =0;
		double economie = 0;
		//calcul de l'economie du mois en fonction de la moyenne de 12 derniers mois
		economie = (donnees.get(2) - donnees.get(15))*100/donnees.get(2);
		//calcul du score en fonction de l'economie, algo differents
		if(economie <0) {
			donnees.set(3,0.0);
			score = -Math.log(-economie);
		}
		else if(economie==0){
			if(donnees.get(3)>2) bonus =10;
			score = -Math.log(-economie+1) + bonus;
		}
		else{
			donnees.set(3, donnees.get(3)+1);
			if(donnees.get(2)>2) bonus =10;
			score = Math.log(economie)*10 + bonus;
		} 
		return score; 
	}
	
	 //tout re ecrire dans le fichier du service
	private void ecriture_fichier(){
		try{
			FileWriter writer= new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(writer);
			for(int i=0 ; i<donnees.size() ; i++){
				bw.write(Double.toString(Math.round(donnees.get(i))));
				bw.newLine();
			}
		    bw.close();
			}catch(IOException e){
	    		e.printStackTrace();
			}
	}


}
