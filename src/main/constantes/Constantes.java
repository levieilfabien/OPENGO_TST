package main.constantes;

import outils.PropertiesOutil;

/**
 * Ensemble des constantes manipulées par les tests IZIVENTES et les IHMS.
 * Certaines variables pointent directement sur le fichier properties, celui ci doit être dans les resources.
 * @author levieilfa
 *
 */
public class Constantes {
	
	//////////////////////////////////////////////////// INFORMATIONS TECHNIQUES ////////////////////////////////////////////////////////////////
	public static final String EMPLACEMENT_FIREFOX = PropertiesOutil.getInfoConstante("EMPLACEMENT_FIREFOX");
	public static final String EMPLACEMENT_PROFIL = PropertiesOutil.getInfoConstante("EMPLACEMENT_PROFILE");
	
	
	//////////////////////////////////////////////////// INFORMATIONS POUR LES TESTS ////////////////////////////////////////////////////////////
	
	public static final int CAS_CE = 1;
	public static final int CAS_BPCEIOM = 2;
	public static final int CAS_BP = 3;
	
//	public static final String REPERTOIRE_TEST = PropertiesOutil.getInfoConstante("REPERTOIRE_TEST");
//	public static final String REPERTOIRE_TEST2 = PropertiesOutil.getInfoConstante("REPERTOIRE_TEST2");

	/**
	 * URL d'accès direct à la synthèse
	 */
	public static final String URL_SYNTHESE = PropertiesOutil.getInfoEnvConstante("SYNTHESE");
	/**
	 * Login de l'utilisateur UNITED à utiliser.
	 */
	public static final String LOGIN_UNITED = PropertiesOutil.getInfoEnvConstante("LOGIN_UNITED");
	/**
	 * Mot de passe associé à l'utilisateur UNITED à utiliser.
	 */
	public static final String PASSWORD_UNITED = PropertiesOutil.getInfoEnvConstante("PASSWORD_UNITED");
	/**
	 * Profil utilisateur à utiliser pour l'affichage de la synthèse.
	 */
	public static final String PROFIL_UTILISATEUR = PropertiesOutil.getInfoEnvConstante("PROFIL");
	/**
	 * Titre de la page de synthèse.
	 */
	public static final String TITRE_PAGE_SYNTHESE = "TEMP";

	////////////////////////////////////////////////////INFORMATIONS POUR LES PREFERENCES ////////////////////////////////////////////////////////////
	public static final String PREF_FIREFOX_REPERTOIRE_TELECHARGEMENT = "browser.download.dir";
}
