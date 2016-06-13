import java.util.ArrayList;
class Procedures{
	
	//   VARIABLES
	
    // nombre de coups joués
	public static int nbEssai = 0;
	// arrêt de la partie
	public static boolean stop = false;
	// partie gagnée
	public static boolean win = false;
	//initialisation de la variable de fin de jeu
	public static boolean finJeu = false;
	// combinaison de couleurs proposée par le joueur
	public static char[] proposition = new char[4];
	// combinaison à trouver
	public static char[] combinaison = new char[4];
	// enregistrement de la partie
	public static ArrayList<String> coups = new ArrayList<String>();
    
	
    //   INTRODUCTION
    
	static void reglement(){
		if(Fonctions.question("Désirez-vous lire le réglement (o/n) ? ")) texteReglement();
		else sautLigne(1);
	}
   
	
	// JEU
	
	static void jouer(){
			if(!finJeu){ 
				if(Fonctions.question(Fonctions.questionDebut())){
					// si oui, on joue une partie
					reinitialisationPartie();
					tiragesCombinaison();
					infoDebutJeu();
					partie();
					finPartie();
				}
				// réponse non, on termine le jeu
				else finJeu = true;
				// on rapelle la Procedure
	            jouer();
			}
	}
	
    // Reinitialisation de la partie
	static void reinitialisationPartie(){
		if(stop) stop = false;
		nbEssai = 0;
		coups.clear();
		if(win) win = false;
	}
	//   Tirage au sort de la combinaison
    static void tiragesCombinaison(){
    	combinaison = Fonctions.tirageCouleur();
    	// Affiche de la solution
    	//afficherTab(combinaison);
    }
	// information de début de jeu
	static void infoDebutJeu(){
		/*  si c'est le debut de la première partie, 
		on affiche les couleurs possibles,le mode de saisie des couleurs */
    	if(Variables.nbParties==0 && nbEssai==0){
    		choixCouleur();
    		modSaisie();
    		modReponse();
    	}
	} 
	
	// déroulement d'une partie
	static void partie(){
		sautLigne(2);
		// on incremente et on affiche le n° de la partie 
    	Terminal.ecrireString("PARTIE N°" + (Variables.nbParties +=1));
		// on joue
    	jeu();
	}
	
    // on recupère la combinaison tapée par le joueur
    static void jeu(){
    	if(!stop && !win && nbEssai<12){
			// saisie de la combinaison
			combinaison();
			// resultat
			if(!stop) resultat();
			// on rapelle la procedure
			jeu();
    	}	
    }
    
   
    // Fin de partie
    static void finPartie(){
    	// le joueur a mis fin à la partie
    	if(stop) Terminal.ecrireStringln("Vous avez mis fin à cette partie.");
    	// le joueur à gagné
    	else if(win) gagne();
    	// le joueur a perdu
    	else perdu();
    	// voir la partie jouée
    	if(coups.size() > 0) revoirPartie();
    	// rejouer
    	jouer();	
    }
    
    
    // STATISTIQUES
    
    static void stat(){
    	sautLigne(2);
    	Terminal.ecrireStringln("Vos statistiques");
    	Terminal.ecrireStringln("****************");
    	sautLigne(1);
    	Terminal.ecrireStringln(" - Nombre de parties jouées : " + Variables.nbParties);
    	Terminal.ecrireStringln(" - Nombre de parties gagnées : " + Variables.score);
    	Terminal.ecrireStringln(" - Poucentage de réussite : " + (Variables.score*100/Variables.nbParties + "%."));
    	if(Variables.score>1)
    		Terminal.ecrireString(" - Meilleur score : solution trouvée en " + Variables.nbCoups + " coup"  + Fonctions.s(Variables.nbCoups) + ".");
    	sautLigne(3);
    }


// --------------------------------- traitement de la saisie----------------------------
    
	// Validation de la saisie de la combinaison
	static void combinaison(){
		boolean valid = false;
		//controle de la saisie
		do{
			sautLigne(2);
			// on demande la combinaison
			String essai = Fonctions.askCombinaison();
			//si le joueur tape 'stop'=>fin de partie
			if(essai.equals("STOP")) {
				    if(Fonctions.question("Désirez-vous vraiment quitter cette partie (o/n) ? "))
				    	stop = valid = true;	
			}
			// revoir les couleurs possibles
			else if(essai.equals("COULEURS")) choixCouleur();
		    // on traite la saisie
			else valid = Fonctions.verificationTaille(essai,7);
		}while(!valid);
		// tout est bon on va afficher le resultat
	}                                      

	
	
//----------------------Affichage du résultat-----------------------------------	

	static void resultat(){
		//on affiche le résultat
		afficherResultat(proposition);
		//on incrémente le nombre d'essais
		nbEssai++;
  	    //on enregistre le coup dans le tableau de sauvegarde de la partie
  	    coups.add(Fonctions.arrayToString(proposition));
	}
	
	static void afficherResultat(char[] proposition){
		Terminal.ecrireString("Resultat :  ");
		// couleurs bien placées
		int place = 0;
		for(int i=0; i<proposition.length; i++) 
			if(proposition[i] == combinaison[i]) place++;
		place(place);
		if(place == 4) win = true;
		// bonnes couleurs
		int bonne = 0;
		for(int i=0; i<combinaison.length; i++)
			bonne += Fonctions.testTab(proposition, combinaison[i]);
  	   bon(bonne-place);
  	   // couleurs fausses
  	   non(combinaison.length-bonne);
  	   sautLigne(1);
	}
	
	// vérifie si le joueur a battu son record
	static void bestRecord(){
		if(Variables.nbCoups > nbEssai){
    			Terminal.ecrireString("Bravo nouveau record, vous avez trouvez la solution en : " + nbEssai + " coup" + Fonctions.s(nbEssai) + ".");
    			sautLigne(1);
    			if(Variables.nbCoups < 12) {
    				Terminal.ecrireString("Votre précédent record était de : " + Variables.nbCoups + " coup" + Fonctions.s(Variables.nbCoups) + ".");
    			}
    			//on met à jour le record nbCoups
    			Variables.nbCoups = nbEssai;
    			sautLigne(1);
    	}	
	}
	
	// revoir la partie
	static void revoirPartie(){
		//voir la partie joué
		if(Fonctions.question("Voulez-vous revoir la partie (o/n) ? "))
			Procedures.afficherPartie();
	}	
		
    static void afficherPartie(){
    	sautLigne(2);
    	Terminal.ecrireString("Combinaison à trouver :");
    	espace(2);
    	afficherTab(combinaison);
    	sautLigne(1);
    	for(int i=0; i<coups.size(); i++){
    		espace(15);
    		Terminal.ecrireString("coup n°" + (i+1));
    		if(i+1<10) espace(2); else espace(1);
    		char[] x = (coups.get(i)).toCharArray();
    		afficherTab(x);
    		espace(2);
    	    afficherResultat(x);
    	}
    	sautLigne(2);
    }	
//-------------------------------Affichage des textes-----------------------------	
	
	//           TEXTE
	
	static void titre(){
		String titre ="    +-------------------------+\n" +
		              "    |                         |\n" +
		              "    |       MASTERMIND        |**\n" +
		              "    |                         |**\n" +
		              "    +-------------------------+**\n" +
		              "      ***************************\n" +
		              "      ***************************";              
    	sautLigne(3);
		Terminal.ecrireString(titre);
		sautLigne(2);
    }
    
    static void fin(){
    	String bye =" +-+-+-+ +-+-+-+ +-+-+-+-+\n"+
                    " |S|e|e| |y|o|u| |s|o|o|n|\n"+               
                    " +-+-+-+ +-+-+-+ +-+-+-+-+\n";
        sautLigne(2);
        Terminal.ecrireString(bye);
        sautLigne(3);
    }
    
    static void gagne(){
    	String gagne =" ######      ###     ######   ##    ## ########\n"+                        
                      " ##    ##    ## ##   ##    ##  ###   ## ##     \n"+                         
                      " ##         ##   ##  ##        ####  ## ##     \n"+                         
                      " ##   #### ##     ## ##   #### ## ## ## ###### \n"+                         
                      " ##    ##  ######### ##    ##  ##  #### ##     \n"+                         
                      " ##    ##  ##     ## ##    ##  ##   ### ##      \n"+                        
                      " ######   ##     ##  ######   ##    ## ######## ";
       sautLigne(2);
       Terminal.ecrireString(gagne);
       sautLigne(3);
       Terminal.ecrireString("Bravo vous avez gagné!!!");
       sautLigne(2);
       //on incrémente le score
       Variables.score++;
       //record battu?
       bestRecord();	
    }
    
    static void perdu(){
    	
    	String perdu =" ######  ####### ######  ######  #     #\n"+
                      " #     # #       #     # #     # #     #\n"+
                      " #     # #       #     # #     # #     #\n"+
                      " ######  #####   ######  #     # #     #\n"+
                      " #       #       #   #   #     # #     #\n"+
                      " #       #       #    #  #     # #     #\n"+
                      " #       ####### #     # ######   ##### ";
       sautLigne(2);
       Terminal.ecrireString(perdu);
       sautLigne(3);
       Terminal.ecrireString("Désolé... Vous avez perdu...");
       sautLigne(2);
    }
    
    static void texteReglement(){
    	String reglement = "Réglement\n" +
    	                   "---------\n\n" +
    	                   "Le programme choisit aléatoirement une combinaison de quatres couleurs différentes, choisies parmi 8 couleurs :\n"+
    	                   "Rouge - Jaune - Vert - Bleu - Orange - Blanc - Violet - Fuschia\n\n" +
    	                   "Vous devez deviner cette combinaison, c'est-à-dire les couleurs et leurs positions, en douze coups maximum.\n" +
    	                   "A chaque tour le programme vous indique:\n" +
    	                   "   1. le nombre de bonnes couleurs\n" +
    	                   "   2. le nombre de couleurs bien placées\n\n" +
    	                   "Affichage du résutat pour chaque essai:\n" +
    	                   "   Couleur bien placée : [*]\n" +
    	                   "   Couleur bien présente mais mal placée : [-]\n" +
    	                   "   Couleur pas présente : [ ]\n";
    	sautLigne(2);
        Terminal.ecrireString(reglement);
        sautLigne(2);
    }
    
    static void choixCouleur(){
    	sautLigne(1);
    	Terminal.ecrireStringln("Couleurs possibles:");
    	sautLigne(1);
    	Terminal.ecrireString("R pour rouge, J pour jaune, V pour vert, B pour bleu, O pour orange, W pour blanc, P pour violet, F pour fuchsia ");
    	sautLigne(1);
    }
    
    static void modSaisie(){
    	sautLigne(1);
    	Terminal.ecrireStringln("Pour jouer, tapez sur une même ligne 4 couleurs distinctes, séparées par un espace.");
    	Terminal.ecrireString("Exemple : J R V P");
    	sautLigne(2);
    }
    
    static void modReponse(){
    	Terminal.ecrireString("La réponse à votre proposition s'affichera ainsi:");
    	sautLigne(2);
    	espace(8);place(2);bon(1);non(1);
    	sautLigne(2);
    	place(2);
    	Terminal.ecrireString(" deux couleurs sont biens placées.");
    	sautLigne(1);
    	bon(1);
    	Terminal.ecrireString(" une autre couleur est juste mais mal placée.");
    	sautLigne(1);
    	non(1); 
    	Terminal.ecrireString(" une couleur est fausse.");
    	sautLigne(2);
    	Terminal.ecrireString("Pour arrêter la partie tapez 'stop'.");
    	sautLigne(2);
    	Terminal.ecrireString("Pour revoir les couleurs possibles tapez 'couleurs'.");
    }
    
    
    static void erreurSaisie(int x){
    	if(x == 1) Terminal.ecrireStringln("Votre combinaison est trop longue ou trop courte.");
    	if(x == 2) Terminal.ecrireStringln("Vous avez fait une erreur de saisie dans les couleurs.");
    	if(x == 3) Terminal.ecrireStringln("Votre combinaison contient plusieurs fois la même couleur.");
    	if(x == 4) Terminal.ecrireStringln("Votre combinaison doit contenir quatre couleurs.");
    }
   
    static void place(int x){for(int i=0; i<x; i++) Terminal.ecrireString("[*]");}
    static void bon(int x){for(int i=0; i<x; i++) Terminal.ecrireString("[-]");}
    static void non(int x){for(int i=0; i<x; i++) Terminal.ecrireString("[ ]");}
    	
    
    
    // procedures diverses
    
    static void sautLigne(int ligne){
    	for(int i=1; i<=ligne; i++) Terminal.sautDeLigne();
    }
    static void espace(int espace){
    	for(int i=0; i<espace; i++) Terminal.ecrireString(" ");
    }
    static void afficherTab(char[] t){
    	for(int i=0; i<t.length; i++)
    		Terminal.ecrireString("[" + t[i] + "]");
    }
    
    // choix couleur
	static void choixCouleur(char[] combinaison, int i){
		char x = Variables.couleurs[Fonctions.aleatoire()];
    	// si la couleur x n'est pas déjà présente dans le tableau combinaison on l'ajoute
    	if(Fonctions.testTab(combinaison,x)==0) combinaison[i] = x;
    	// si non on recommence
    	else choixCouleur(combinaison,i);
	}
}