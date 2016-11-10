class Plateau
{
	private Case tableauPeres_[][];
	private int longueur_;
	private int scoreJ1_;
	private int scoreJ2_;
	
	public Plateau(int longueur)
	{
		scoreJ1_ = 0;
		scoreJ2_ = 0;
		longueur_ = longueur;
	}
	
	public int getLongueur()
	{
		return longueur_;
	}
	
	public int getScoreJ1()
	{
		return scoreJ1_;
	}
	
	public int getScoreJ2()
	{
		return scoreJ2_;
	}
	
	public void setLongueur(int longueur)
	{
		longueur_ = longueur;
	}
	
	public void setScoreJ1(int score)
	{
		scoreJ1_ = score;
	}
	
	public void setScoreJ2(int score)
	{
		scoreJ2_ = score;
	}
	
	public boolean rechercheMemePere(Case case1, Case case2)
	{
		// compression de chemin à faire
		return tableauPeres_[case1.getX()][case1.getY()] == tableauPeres_[case2.getX()][case2.getY()];
	}	
	
	public boolean existeCheminCases(Case case1, Case case2, String col)
	{
		return rechercheMemePere(case1, case2) && case1.getCol() == case2.getCol();
	}
	
	public void afficherComposante(Case case1, Case case2, String col)
	{
		int limite = getLongueur()-1;
		if (limite > case2.getX() || limite > case2.getY())
		{
			if (existeCheminCases(case1, case2, col))
			{
				System.out.println(case2.getX()+" : "+case2.getY());
			}
			case2.incX();
			afficherComposante(case1, case2, col);
			case2.incY();
			afficherComposante(case1, case2, col);
			
		}
	}
	
		
	
}
