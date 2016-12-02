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
	
	public void union(int xf, int yf, int xp, int yp)
	{
		Case daddy = tableauPeres_[xp][yp];
		Case son = tableauPeres_[xf][yf];
		if (!(son.equals(daddy))) 
		{
			if (daddy.getCol() == "bleu")
			{
				scoreJ2_ += son.getNbEtoiles();
			}
			else if (daddy.getCol() == "rouge")
			{
				scoreJ1_ += son.getNbEtoiles();
			}
			daddy.setNbEtoiles(daddy.getNbEtoiles()+son.getNbEtoiles());
			tableauPeres_[xp][yp] = tableauPeres_[xf][yf]; //son=daddy
		}
	}
	
	public int getNbEtoiles(int x, int y, String col)
	{
		System.out.println("La composante dont la case ["+x+", "+y+"] fait parti et de couleur "+col+" contient "+tableauPeres_[x][y].getNbEtoiles()+" *");
		return tableauPeres_[x][y].getNbEtoiles();
	}	
	
	public void afficheScores(String col)
	{
		if (col == "bleu")
		{
			System.out.println(scoreJ2_);
		}
		else if (col == "rouge")
		{
			System.out.println(scoreJ1_);
		}
		else
		{
			System.out.println("Couleur fausse");
		}
		
	}

	public initialiser()
	{
		int max = 11;
		tableauPeres_ = new Case[max][max];
		int i, j = 0;
		for(i=0; i<max-1; ++i)
		{
			for(j=0; j<(max-1); ++j)
			{
				tableauPeres_[i][j] = new Case(i, j, 0);
				
				}
			}
	}
	
}
