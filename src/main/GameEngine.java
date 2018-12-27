package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GameEngine {

	//Balises template 
	private static final String BALISEANNEE35 = "%annee35%";
	private static final String BALISEANNEE34 = "%annee34%";
	private static final String BALISEANNEE33 = "%annee33%";
	private static final String BALISEANNEE32 = "%annee32%";
	private static final String BALISEANNEE31 = "%annee31%";
	private static final String BALISEANNEE30 = "%annee30%";
	private static final String BALISEANNEE29 = "%annee29%";
	private static final String BALISEANNEE28 = "%annee28%";
	private static final String BALISEANNEE27 = "%annee27%";
	private static final String BALISEANNEE26 = "%annee26%";
	private static final String BALISEANNEE25 = "%annee25%";
	private static final String BALISEANNEE24 = "%annee24%";
	private static final String BALISEANNEE23 = "%annee23%";
	private static final String BALISEANNEE22 = "%annee22%";
	private static final String BALISEANNEE21 = "%annee21%";
	private static final String BALISEANNEE20 = "%annee20%";
	private static final String BALISEANNEE19 = "%annee19%";
	private static final String BALISEANNEE18 = "%annee18%";
	private static final String BALISEANNEE17 = "%annee17%";
	private static final String BALISEANNEE16 = "%annee16%";
	private static final String BALISEANNEE15 = "%annee15%";
	private static final String BALISEANNEE14 = "%annee14%";
	private static final String BALISEANNEE13 = "%annee13%";
	private static final String BALISEANNEE12 = "%annee12%";
	private static final String BALISEANNEE11 = "%annee11%";
	private static final String BALISEANNEE10 = "%annee10%";
	private static final String BALISEANNEE9 = "%annee9%";
	private static final String BALISEANNEE8 = "%annee8%";
	private static final String BALISEANNEE7 = "%annee7%";
	private static final String BALISEANNEE6 = "%annee6%";
	private static final String BALISEANNEE5 = "%annee5%";
	private static final String BALISEANNEE4 = "%annee4%";
	private static final String BALISEANNEE3 = "%annee3%";
	private static final String BALISEANNEE2 = "%annee2%";
	private static final String BALISEANNEE1 = "%annee1%";
	
	
	private static final String BALISEMOIS35 = "%mois35%";
	private static final String BALISEMOIS34 = "%mois34%";
	private static final String BALISEMOIS33 = "%mois33%";
	private static final String BALISEMOIS32 = "%mois32%";
	private static final String BALISEMOIS31 = "%mois31%";
	private static final String BALISEMOIS30 = "%mois30%";
	private static final String BALISEMOIS29 = "%mois29%";
	private static final String BALISEMOIS28 = "%mois28%";
	private static final String BALISEMOIS27 = "%mois27%";
	private static final String BALISEMOIS26 = "%mois26%";
	private static final String BALISEMOIS25 = "%mois25%";
	private static final String BALISEMOIS24 = "%mois24%";
	private static final String BALISEMOIS23 = "%mois23%";
	private static final String BALISEMOIS22 = "%mois22%";
	private static final String BALISEMOIS21 = "%mois21%";
	private static final String BALISEMOIS20 = "%mois20%";
	private static final String BALISEMOIS19 = "%mois19%";
	private static final String BALISEMOIS18 = "%mois18%";
	private static final String BALISEMOIS17 = "%mois17%";
	private static final String BALISEMOIS16 = "%mois16%";
	private static final String BALISEMOIS15 = "%mois15%";
	private static final String BALISEMOIS14 = "%mois14%";
	private static final String BALISEMOIS13 = "%mois13%";
	private static final String BALISEMOIS12 = "%mois12%";
	private static final String BALISEMOIS11 = "%mois11%";
	private static final String BALISEMOIS10 = "%mois10%";
	private static final String BALISEMOIS9 = "%mois9%";
	private static final String BALISEMOIS8 = "%mois8%";
	private static final String BALISEMOIS7 = "%mois7%";
	private static final String BALISEMOIS6 = "%mois6%";
	private static final String BALISEMOIS5 = "%mois5%";
	private static final String BALISEMOIS4 = "%mois4%";
	private static final String BALISEMOIS3 = "%mois3%";
	private static final String BALISEMOIS2 = "%mois2%";
	private static final String BALISEMOIS1 = "%mois1%";
	/*private static final String BAOBABBALISE = "%baobab";
	private static final String PLATANEBALISE = "%platane";
	private static final String BONSAIBALISE = "%bonsai";
	*/
	//regex utilise pour filter les lignes qui nous interessent dans dataFile
	private static final String REGEXFILTER = "^[^,]+,[^,]+,[^,]+,(Impressions|Photocopies),[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,[^,]+(\")";
	
	private List<ServicePlayer> servicesScore;  //liste des services à classer par ordre décroissant selon leur score
	private List<ServicePlayer>  servicesScoreCumule;   //liste des services à classer par ordre décroissant selon leur score cumule
	private String nomServiceFichier; //nom du service utilise pour le nom du fichier
	private String nomServiceAffichage; //nom du service utilise pour l'affichage: avec espace et caracteres speciaux
	private double dataMonthTemp; //donnee du mois temporaire (que impression ou que photocopie)
	private double dataMonth;  //donnee du mois reelle
	private String nomServiceFichierTemp; 
	private String nomServiceAffichageTemp; 	
	private String fileName_lecture; //fichier a lire : template
	private String fileName_ecriture; //fichier a remplir : resultats
	private String dataFile; //fichier contenant les donnees: rapport watchdoc 

	//constructeur
	public GameEngine() {
		fileName_lecture ="template.txt";
		fileName_ecriture = "resultats.txt";
		dataFile = "M6_Statement.csv";
		servicesScore = new ArrayList<ServicePlayer>();
		servicesScoreCumule = new ArrayList<ServicePlayer>();
		nomServiceFichier = null;
		nomServiceAffichage = null;
		nomServiceFichierTemp = " ";
		nomServiceAffichageTemp = " ";
		dataMonthTemp = 0;
		dataMonth = 0;
	}

	public void storeData() throws FileNotFoundException, IOException {

		ServicePlayer serviceTemp = null;
		File fichierTemp=new File(dataFile);
		if(!fichierTemp.exists()) System.exit(0);
		//stocker le fichier dataFile dans un stream
		try(BufferedReader buffer = new BufferedReader(new FileReader(dataFile))){
			List<String> fichier = buffer.lines().collect(Collectors.toList()); 
			Pattern p = Pattern.compile(REGEXFILTER);
			//filtrer les lignes qui nous interessent : donnees photocopies et impressions
			List<String> results = fichier.stream().filter(s -> p.matcher(s).find()).collect(Collectors.toList());
			for (String result : results){
				//recuperer le nom du service 
				nomServiceAffichage = result.split(",")[0];

				if(nomServiceAffichage.compareTo("M6") == 0) nomServiceAffichage = "Direction "+result.split(",")[1];
				nomServiceFichier = nomServiceAffichage.replaceAll("[^\\w]","");
				dataMonthTemp = Integer.parseInt(result.split(",")[4]);

				if(nomServiceFichier.compareTo(nomServiceFichierTemp) != 0){
					//recuperer donnees impressions et photocopies
					if(nomServiceFichierTemp != " "){
						serviceTemp = new ServicePlayer(dataMonth, nomServiceFichierTemp, nomServiceAffichageTemp);
						servicesScore.add(serviceTemp);
						serviceTemp.getDataService();
					}
					dataMonth = dataMonthTemp;					
				}

				else{
					dataMonth = dataMonth + dataMonthTemp;
				}

				nomServiceFichierTemp = nomServiceFichier; 
				nomServiceAffichageTemp=nomServiceAffichage;


			}
			//creer une nouvelle instance de service 
			serviceTemp = new ServicePlayer(dataMonth, nomServiceFichier, nomServiceAffichage);
			servicesScore.add(serviceTemp);
			serviceTemp.getDataService();
			//copier la premiere liste dans la deuxieme
			servicesScoreCumule.addAll(servicesScore);
		} catch (IOException e) {
			e.printStackTrace();
		}
		GregorianCalendar calendar = new GregorianCalendar(); 
		calendar.setTime(new Date()); 
		File f = new File(dataFile);
		File f2 = new File("M6_Statement"+ (calendar.get(GregorianCalendar.MONTH) + 1)+".csv");
		f.renameTo(f2);

	}

	public void writeData(){
		//tri de servicesScore, ordre decroissant, selon le score
		Collections.sort(servicesScore, Collections.reverseOrder(new Comparator<ServicePlayer>() {

			@Override

			public int compare(ServicePlayer serviceTemp1, ServicePlayer serviceTemp2) {

				return serviceTemp1.getScore().compareTo(serviceTemp2.getScore());

			}	

		}));


		//tri de servicesScoreCumule, ordre decroissant, selon le score cumule
		Collections.sort(servicesScoreCumule, Collections.reverseOrder(new Comparator<ServicePlayer>() {

			@Override

			public int compare(ServicePlayer serviceTemp1, ServicePlayer serviceTemp2) {

				return serviceTemp1.getScoreCumule().compareTo(serviceTemp2.getScoreCumule());

			}	

		}));

		 String fichier= ""; 
		String test; 
	/*	ArrayList <String> bonsai = new ArrayList<String>();
		ArrayList <String> platane = new ArrayList<String>();
		ArrayList <String> baobab = new ArrayList<String>();*/
		//creer une arraylist par division
		//for(int i=0; i<servicesScoreCumule.size(); i++){
		//	if (servicesScoreCumule.get(i).getDivision()=="Bonsai") bonsai.add(servicesScoreCumule.get(i).getNomServiceAffichage()+ " " +servicesScoreCumule.get(i).getScore().intValue());
		//	else if (servicesScoreCumule.get(i).getDivision()=="Platane") platane.add(servicesScoreCumule.get(i).getNomServiceAffichage()+" " +servicesScoreCumule.get(i).getScore().intValue());
		//	else if (servicesScoreCumule.get(i).getDivision()=="Baobab") baobab.add(servicesScoreCumule.get(i).getNomServiceAffichage()+" "+servicesScoreCumule.get(i).getScore().intValue());
		//} */
		
		//stockage du template dans un string
		try(BufferedReader buffer = new BufferedReader(new FileReader(fileName_lecture))){
			while((test = buffer.readLine())!= null){
				fichier+= " " +test; 
			}
			//remplacer les balises par les donnees reelles
			fichier = fichier.replace(BALISEMOIS1,servicesScore.get(0).getNomServiceAffichage() + " " + servicesScore.get(0).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS2, servicesScore.get(1).getNomServiceAffichage() + " " + servicesScore.get(1).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS3, servicesScore.get(2).getNomServiceAffichage() + " " + servicesScore.get(2).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS4,servicesScore.get(3).getNomServiceAffichage() + " " + servicesScore.get(3).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS5,servicesScore.get(4).getNomServiceAffichage() + " " + servicesScore.get(4).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS6,servicesScore.get(5).getNomServiceAffichage() + " " + servicesScore.get(5).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS7,servicesScore.get(6).getNomServiceAffichage() + " " + servicesScore.get(6).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS8,servicesScore.get(7).getNomServiceAffichage() + " " + servicesScore.get(7).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS9,servicesScore.get(8).getNomServiceAffichage() + " " + servicesScore.get(8).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS10,servicesScore.get(9).getNomServiceAffichage() + " " + servicesScore.get(9).getScore().intValue());
			
			
			fichier = fichier.replace(BALISEMOIS11,servicesScore.get(10).getNomServiceAffichage() + " " + servicesScore.get(10).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS12,servicesScore.get(11).getNomServiceAffichage() + " " + servicesScore.get(11).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS13,servicesScore.get(12).getNomServiceAffichage() + " " + servicesScore.get(12).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS14,servicesScore.get(13).getNomServiceAffichage() + " " + servicesScore.get(13).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS15,servicesScore.get(14).getNomServiceAffichage() + " " + servicesScore.get(14).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS16,servicesScore.get(15).getNomServiceAffichage() + " " + servicesScore.get(15).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS17,servicesScore.get(16).getNomServiceAffichage() + " " + servicesScore.get(16).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS18,servicesScore.get(17).getNomServiceAffichage() + " " + servicesScore.get(17).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS19,servicesScore.get(18).getNomServiceAffichage() + " " + servicesScore.get(18).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS20,servicesScore.get(19).getNomServiceAffichage() + " " + servicesScore.get(19).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS21,servicesScore.get(20).getNomServiceAffichage() + " " + servicesScore.get(20).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS22,servicesScore.get(21).getNomServiceAffichage() + " " + servicesScore.get(21).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS23,servicesScore.get(22).getNomServiceAffichage() + " " + servicesScore.get(22).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS24,servicesScore.get(23).getNomServiceAffichage() + " " + servicesScore.get(23).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS25,servicesScore.get(24).getNomServiceAffichage() + " " + servicesScore.get(24).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS26,servicesScore.get(25).getNomServiceAffichage() + " " + servicesScore.get(25).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS27,servicesScore.get(26).getNomServiceAffichage() + " " + servicesScore.get(26).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS28,servicesScore.get(27).getNomServiceAffichage() + " " + servicesScore.get(27).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS29,servicesScore.get(28).getNomServiceAffichage() + " " + servicesScore.get(28).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS30,servicesScore.get(29).getNomServiceAffichage() + " " + servicesScore.get(29).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS31,servicesScore.get(30).getNomServiceAffichage() + " " + servicesScore.get(30).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS32,servicesScore.get(31).getNomServiceAffichage() + " " + servicesScore.get(31).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS33,servicesScore.get(32).getNomServiceAffichage() + " " + servicesScore.get(32).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS34,servicesScore.get(33).getNomServiceAffichage() + " " + servicesScore.get(33).getScore().intValue());
			fichier = fichier.replace(BALISEMOIS35,servicesScore.get(34).getNomServiceAffichage() + " " + servicesScore.get(34).getScore().intValue());
			
			fichier = fichier.replace(BALISEANNEE1, servicesScoreCumule.get(0).getNomServiceAffichage() + " " + servicesScoreCumule.get(0).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE2, servicesScoreCumule.get(1).getNomServiceAffichage() + " " + servicesScoreCumule.get(1).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE3,servicesScoreCumule.get(2).getNomServiceAffichage() + " " + servicesScoreCumule.get(2).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE4,servicesScoreCumule.get(3).getNomServiceAffichage() + " " + servicesScoreCumule.get(3).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE5,servicesScoreCumule.get(4).getNomServiceAffichage() + " " + servicesScoreCumule.get(4).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE6,servicesScoreCumule.get(5).getNomServiceAffichage() + " " + servicesScoreCumule.get(5).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE7,servicesScoreCumule.get(6).getNomServiceAffichage() + " " + servicesScoreCumule.get(6).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE8,servicesScoreCumule.get(7).getNomServiceAffichage() + " " + servicesScoreCumule.get(7).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE9,servicesScoreCumule.get(8).getNomServiceAffichage() + " " + servicesScoreCumule.get(8).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE10,servicesScoreCumule.get(9).getNomServiceAffichage() + " " + servicesScoreCumule.get(9).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE11,servicesScoreCumule.get(10).getNomServiceAffichage() + " " + servicesScoreCumule.get(10).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE12,servicesScoreCumule.get(11).getNomServiceAffichage() + " " + servicesScoreCumule.get(11).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE13,servicesScoreCumule.get(12).getNomServiceAffichage() + " " + servicesScoreCumule.get(12).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE14,servicesScoreCumule.get(13).getNomServiceAffichage() + " " + servicesScoreCumule.get(13).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE15,servicesScoreCumule.get(14).getNomServiceAffichage() + " " + servicesScoreCumule.get(14).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE16,servicesScoreCumule.get(15).getNomServiceAffichage() + " " + servicesScoreCumule.get(15).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE17,servicesScoreCumule.get(16).getNomServiceAffichage() + " " + servicesScoreCumule.get(16).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE18,servicesScoreCumule.get(17).getNomServiceAffichage() + " " + servicesScoreCumule.get(17).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE19,servicesScoreCumule.get(18).getNomServiceAffichage() + " " + servicesScoreCumule.get(18).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE20,servicesScoreCumule.get(19).getNomServiceAffichage() + " " + servicesScoreCumule.get(19).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE21,servicesScoreCumule.get(20).getNomServiceAffichage() + " " + servicesScoreCumule.get(20).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE22,servicesScoreCumule.get(21).getNomServiceAffichage() + " " + servicesScoreCumule.get(21).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE23,servicesScoreCumule.get(22).getNomServiceAffichage() + " " + servicesScoreCumule.get(22).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE24,servicesScoreCumule.get(23).getNomServiceAffichage() + " " + servicesScoreCumule.get(23).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE25,servicesScoreCumule.get(24).getNomServiceAffichage() + " " + servicesScoreCumule.get(24).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE26,servicesScoreCumule.get(25).getNomServiceAffichage() + " " + servicesScoreCumule.get(25).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE27,servicesScoreCumule.get(26).getNomServiceAffichage() + " " + servicesScoreCumule.get(26).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE28,servicesScoreCumule.get(27).getNomServiceAffichage() + " " + servicesScoreCumule.get(27).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE29,servicesScoreCumule.get(28).getNomServiceAffichage() + " " + servicesScoreCumule.get(28).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE30,servicesScoreCumule.get(29).getNomServiceAffichage() + " " + servicesScoreCumule.get(29).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE31,servicesScoreCumule.get(30).getNomServiceAffichage() + " " + servicesScoreCumule.get(30).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE32,servicesScoreCumule.get(31).getNomServiceAffichage() + " " + servicesScoreCumule.get(31).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE33,servicesScoreCumule.get(32).getNomServiceAffichage() + " " + servicesScoreCumule.get(32).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE34,servicesScoreCumule.get(33).getNomServiceAffichage() + " " + servicesScoreCumule.get(33).getScoreCumule().intValue());
			fichier = fichier.replace(BALISEANNEE35,servicesScoreCumule.get(34).getNomServiceAffichage() + " " + servicesScoreCumule.get(34).getScoreCumule().intValue());
			
			/*for(int i=0; i<Math.max(bonsai.size(), Math.max(platane.size(), baobab.size())); i++){
			if (i < bonsai.size()) {
					fichier = fichier.replace(BONSAIBALISE+i+"%",bonsai.get(i));
				}
				else { 
					fichier = fichier.replace(BONSAIBALISE+i+"%"," ");
				}
				if (i < platane.size()) {
					fichier = fichier.replace(PLATANEBALISE+i+"%", platane.get(i));
				}
				else {
					fichier = fichier.replace(PLATANEBALISE+i+"%", " ");
				}
				if (i<baobab.size()) {
					fichier = fichier.replace(BAOBABBALISE+i+"%", baobab.get(i));
				}
				else {
					fichier = fichier.replace(BAOBABBALISE+i+"%", " ");
				}
			}
*/
		} catch (IOException e) {
			e.printStackTrace();
		}

		//ecrire le template modifie dans le fichier resultat
		try (BufferedWriter buffer = new BufferedWriter(new FileWriter(fileName_ecriture,false))) {
			buffer.write(fichier.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
