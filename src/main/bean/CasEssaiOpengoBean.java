package main.bean;

import beans.CasEssaiBean;

/**
 * Classe d'extension des cas d'essai classique pour IZIGATE
 * @author levieilfa
 *
 */
public class CasEssaiOpengoBean extends CasEssaiBean {

	/**
	 * Id de sérialisation par défaut.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Le numéro IUN du dossier saisi
	 */
	private String numeroIUN = "";
	
	/**
	 * distributeur
	 */
	private int distributeur;

	
	public int getDistributeur() {
		return distributeur;
	}

	public void setDistributeur(int distributeur) {
		this.distributeur = distributeur;
	}

	public CasEssaiOpengoBean() {
		super();
	}
	
	public CasEssaiOpengoBean(CasEssaiOpengoBean reference, String prefixe, Integer idTestPlan) {
		super();
		setAlm(reference.getAlm());
		setNomCasEssai(prefixe + reference.getTime());
		setIdUniqueTestLab(reference.getIdUniqueTestLab());
		setIdUniqueTestPlan(idTestPlan);
		setRepertoireTelechargement(reference.getRepertoireTelechargement());
		// Par défaut on ne connais pas l'état final de cette étape, elle sera déduite de la sommes de ses steps.
		setEtatFinal(null);
		reference.getTests().add(this);
	}

	public String getNumeroIUN() {
		return numeroIUN;
	}

	public void setNumeroIUN(String numeroIUN) {
		this.numeroIUN = numeroIUN;
	}

}

