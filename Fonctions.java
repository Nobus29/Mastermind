class Fonctions {
	
	//  JEU
	
	//obtenir un nombre aleatoire
	static int aleatoire(){
		int min = 0; int max = 7;
		return min + (int)(Math.random()*((max-min)+1));
	}
	
	// tirage au sort des couleurs
	static char[] tirageCouleur(){
    	char[] combinaison = new char[4];
        // on remplit le tableau combinaison avec 4 couleurs différentes
    	for(int i=0; i<combinaison.length; i++)
    		Procedures.choixCouleur(combinaison,i);
   	    return combinaison;
	}
	
	// retourne le nombre d'occurrences x dans le tableau t
	static int testTab(char[] t,char x ){
		    int nb = 0;
			for(int i=0; i<t.length; i++) 
				if(t[i] == x) nb++;
			return nb;
	}
	
   // on vérifie la présence des éléments du tableau t1 dans le tableau t2;
   static boolean controlLettre(char[] t1, char[] t2){
   	   boolean res=true;
   	   for(int i=0; i<t1.length; i++) 
   	   	   if(testTab(t2, t1[i]) != 1) res = false;
       return res;
   }
  
  static boolean verificationSaisie(String s){
  	  	// transformation de la chaine de caractères en un tableau
		char[] t = s.toCharArray();
		// on enléve les espaces
		Procedures.proposition = cleanTab(t,(t.length-testTab(t,' ')),' ');
		// vérification de la proposition
		if(Procedures.proposition.length==4) return verificationTab(Procedures.proposition);
		else {
		  	  Procedures.erreurSaisie(4);
		      return false;
		}	
  }
  
  // vérifie qu'une chaine s soit de la taille x
  static boolean verificationTaille(String s, int x){
  	  if(s.length() == x) return verificationSaisie(s);
  	  else {
  	  	  Procedures.erreurSaisie(1);
  	  	  return false;
  	  }
  }
  
  static boolean verificationTab(char[] t){
  	  // vérification que les couleurs tapées soient bien dans les couleurs attendues
  	  boolean valid = verificationCouleurs(t,Variables.couleurs,2);
  	  // vérification que les couleurs tapées soient uniques
  	  if(valid) valid = verificationCouleurs(t,t,3);
  	  return valid;
  }
  
  static boolean verificationCouleurs(char[] t1, char[] t2, int error){
  	  boolean valid = controlLettre(t1,t2);
  	  if(!valid) Procedures.erreurSaisie(error);
  	  return valid;
  }
	
  //rempli un tableau t2 de taille x avec les caractères du tableau t1 en enlevant les caractères y
  static char[] cleanTab(char[] t1, int x, char y){
  	  char[] t2 = new char[x];
  	  int i=0; int j=0;
  	  while(i<x){
  	  	  if(t1[j]!=y){
  	  	  	  t2[i] = t1[j];
  	  	  	  i++;
  	  	  }
  	  	  j++;
  	  }    
  	  return t2;
  }
  
  // transforme un tableau en une chaine de caractères
  static String arrayToString(char[] y){
  	  String x = "";
  	  for(int i=0; i<y.length; i++)
  	  	  x += y[i];
  	  return x;
  }
  
  
  static char reponse(String q){
  	    Terminal.ecrireString(q);
		return Character.toLowerCase(Terminal.lireChar());
  }
  
  static boolean question(String q){
  	  char res; boolean ok = false;
  	  do{
  	  	  res = reponse(q);
  	  	  if(res == 'o' || res == 'n') ok = true;
  	  	  else Terminal.ecrireStringln("Vous devez taper 'o' ou 'n' !");
  	  }while(!ok);
  	  if(res == 'o') return true;
  	  else return false;
  }
		
  // retourne la saisi du joueur
  static String askCombinaison(){
    	Terminal.ecrireString("Essai n°" + (Procedures.nbEssai+1) + " ? ");
		//traitement de la saisie:  on met tout en majuscule est on enléve les espaces en début et fin de chaine
		String essai = ((Terminal.lireString()).toUpperCase()).trim();
		return essai;
  }
    
  // détermine la question à poser
  static String questionDebut(){
    	String texte;
    	if(Variables.nbParties > 0)
    		texte = "Désirez-vous refaire une partie (o/n) ? ";
    	else texte = "Désirez-vous faire une partie (o/n) ? ";
    	return texte;
  }
  
  // ajouter un s à la fin d'un mot
  static String s(int x){
  	  if(x>1) return "s"; else return ""; 
  }
}
