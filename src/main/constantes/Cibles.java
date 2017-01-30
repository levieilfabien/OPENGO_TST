package main.constantes;

import beans.CibleBean;
import constantes.Clefs;

public class Cibles {
	//////////////////////////////////////////////////////CIBLE A USAGE UNIQUE ///////////////////////////////////////////////////////////////
	/**
	 * Zone de saisie de l'identifiant de l'utilisateur
	 */
	public static final CibleBean SAISIE_LOGIN =  new CibleBean(Clefs.NAME, "user");
	
	/**
	 * Zone de saisie du mot de passe de l'utilisateur
	 */
	public static final CibleBean SAISIE_MDP =  new CibleBean(Clefs.NAME, "mdp");
	
	/**
	 * Bouton de validation des identifiants d'acc�s � Izigate
	 */
	public static final CibleBean BOUTON_VALIDATION_LOGIN =  new CibleBean(Clefs.XPATH, "html/body/table[4]/tbody/tr/td[1]/input");
	
	/**
	 * Zone de saisie du num�ro FFI pour la consultation d'un dossier
	 */
	public static final CibleBean SAISIE_FFI_CONSULT = new CibleBean(Clefs.NAME, "s1_sioc_id");
	
	/**
	 * Onglet de s�lection du distributeur CE
	 */
	public static final CibleBean BOUTON_ONGLET_CE = new CibleBean(Clefs.XPATH, "html/body/table[2]/tbody/tr/td/table/tbody/tr/td[1]");
	
	/**
	 * Onglet de s�lection du distributeur BPOP
	 */
	public static final CibleBean BOUTON_ONGLET_BPOP = new CibleBean(Clefs.XPATH, "html/body/table[2]/tbody/tr/td/table/tbody/tr/td[3]");
	
	/**
	 * Onglet de s�lection du distributeur BPCE IOM
	 */
	public static final CibleBean BOUTON_ONGLET_BPCEIOM = new CibleBean(Clefs.XPATH, "html/body/table[2]/tbody/tr/td/table/tbody/tr/td[5]");
	
	/**
	 * Bouton d'acc�s � la page de m�rissement d'un dossier
	 */
	public static final CibleBean BOUTON_MURISSEMENT = new CibleBean(Clefs.XPATH, "html/body/table[3]/tbody/tr/td/table/tbody/tr/td[5]");
	
	/**
	 * Zone de saisie du num�ro FFI pour le m�rissement d'un dossier
	 */
	public static final CibleBean SAISIE_FFI_MURISSEMENT = new CibleBean(Clefs.NAME, "s2_sioc_id");
	
	/**
	 * Zone de saisie de la date d'instructio d'un dossier pour la recherche d'un dossier
	 */
	public static final CibleBean SAISIE_DATE_MURISSEMENT = new CibleBean(Clefs.NAME, "s2_date");
	
	/**
	 * Bouton d'envoi de recherche de dossier
	 */
	public static final CibleBean BOUTON_ENVOI_RECHERCHE = new CibleBean(Clefs.VALEUR, "Envoi");
}
