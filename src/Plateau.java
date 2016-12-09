import java.util.Random;
import java.util.Scanner;

class Plateau
{
	private String red_ = "\u001B[31m";
	private String reset_ = "\u001B[0m";
	private String blue_ = "\u001B[34m";

	private Scanner clavier =new Scanner(System.in);
	private Case tableauPeres_[][];
	private int longueur_;
	private int scoreJ1_;
	private int scoreJ2_;
	
	// Constructeur du plateau
	public Plateau(int longueur)
	{
		scoreJ1_ = 0;
		scoreJ2_ = 0;
		longueur_ = longueur;
	}
	
	// Accesseurs pour le plateau
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
	// Fin des accesseurs

	//Pour notre classe union

	//Compression de chemin pour un appel à classe (soit Case)
	public void compressionChemin(int x, int y)
	{
		if (tableauPeres_[x][y].getX() != x && tableauPeres_[x][y].getY() != y)
		{
			tableauPeres_[x][y] = tableauPeres_[tableauPeres_[x][y].getX()][tableauPeres_[x][y].getY()];
			tableauPeres_[x][y].setNbEtoile(tableauPeres_[tableauPeres_[x][y].getX()][tableauPeres_[x][y].getY()].getNbEtoile());
			compressionChemin(tableauPeres_[x][y].getX(), tableauPeres_[x][y].getY());

			System.out.println(x+":"+y);
		}
		else
		{
			System.out.println(x+":"+y);
		}
	}

	//Procédure union qui permet de faire l'union entre deux cases
	public void union(int xf, int yf, int xp, int yp)
	{
		Case daddy = tableauPeres_[xp][yp];
		Case son = tableauPeres_[xf][yf];
		if (!(son.equals(daddy))) 
		{
			daddy.setNbEtoile(daddy.getNbEtoile()+son.getNbEtoile());
			tableauPeres_[xf][yf] = tableauPeres_[xp][yp]; 
		}
	}
	//Fin class union

	//Pour la question 2) afficheComposante
	public void afficheComposante(Case case1, Case case2, String col)
	{

		int limite = getLongueur()-1;
		if (limite > case2.getX() || limite > case2.getY())
		{
			if (existeCheminCases(case1, case2, col))
			{
				System.out.println(case2.getX()+" : "+case2.getY());
			}
			case2.incX();
			afficheComposante(case1, case2, col);
			case2.incY();
			afficheComposante(case1, case2, col);
			
		}

	}
	//Fin question 2)

	//Pour la question 3) existeCheminCases
	public boolean rechercheMemePere(Case case1, Case case2)
	{
		compressionChemin(case1.getX(), case1.getY());
		compressionChemin(case2.getX(), case2.getY());
		return tableauPeres_[case1.getX()][case1.getY()] == tableauPeres_[case2.getX()][case2.getY()];
	}	
	
	public boolean existeCheminCases(Case case1, Case case2, String col)
	{
		return rechercheMemePere(case1, case2) && case1.getCol() == case2.getCol();
	}
	//Fin question 3)

	//Pour la question 4) relierCasesMin
	public boolean relierCasesMin(int x, int y, int z, int t)
	{
		for (int i = x; i < z; ++i)
		{
			for (int j=y; j < t; ++j)
			{
				
			}
		}
		return true;
	}
	//Fin question 4)
	
	//Pour la question 5) nombresEtoiles
	public int getNbEtoiles(int x, int y, String col)
	{
		//System.out.println("La composante dont la case ["+x+", "+y+"] fait parti et de couleur "+col+" contient "+tableauPeres_[x][y].getNbEtoile()+" *");
		return tableauPeres_[x][y].getNbEtoile();
	}
	
	public void nombresEtoiles(int x, int y, String col)
	{
		System.out.println("Pour la case de position ["+x+", "+y+"] et de couleur "+col+" le nombre d'étoiles est "+getNbEtoiles(x,y,col));
	}
	//Fin question 5)

	//Pour la question 6) afficheScores
	public void afficheScores(String col)
	{
		if (col == "bleu")
		{
			System.out.println("Vous avez réussi à connecter "+scoreJ2_+ " étoiles.");

		}
		else if (col == "rouge")
		{
			System.out.println("Vous avez réussi à connecter "+scoreJ1_+ " étoiles.");
		}
		else
		{
			System.out.println("Couleur fausse");
		}
		
	}
	//Fin question 6)

	// Pour la question 7) relieComposantes
	public boolean relieComposantes(int x, int y, String col)
	{
		if (getLesVoisins(x, y, col) == tableauPeres_[x][y])
		{
			return false;
		}
		else
		{
			return (getLesVoisins(x, y, col).getCol() == tableauPeres_[x][y].getCol());
		}
		/*if (x == 0)
		{
		 	return (tableauPeres_[x+1][y].getCol() == tableauPeres_[x][y].getCol()
		 		|| tableauPeres_[x][y-1].getCol() == tableauPeres_[x][y].getCol()
		 		|| tableauPeres_[x][y+1].getCol() == tableauPeres_[x][y].getCol()
		 		|| tableauPeres_[x+1][y-1].getCol() == tableauPeres_[x][y].getCol()
		 		|| tableauPeres_[x+1][y+1].getCol() == tableauPeres_[x][y].getCol());
		}
		else if (y == 0)
		{
			return (tableauPeres_[x+1][y].getCol() == tableauPeres_[x][y].getCol()
				|| tableauPeres_[x-1][y].getCol() == tableauPeres_[x][y].getCol()
				|| tableauPeres_[x][y+1].getCol() == tableauPeres_[x][y].getCol()
				|| tableauPeres_[x+1][y+1].getCol() == tableauPeres_[x][y].getCol()
				|| tableauPeres_[x-1][y+1].getCol() == tableauPeres_[x][y].getCol());
		}
		else if (x == 0 && y == 0)
		{
			return (tableauPeres_[x+1][y].getCol() == tableauPeres_[x][y].getCol()
				|| tableauPeres_[x][y+1].getCol() == tableauPeres_[x][y].getCol()
				|| tableauPeres_[x+1][y+1].getCol() == tableauPeres_[x][y].getCol());
		}
		else
		{
			return (tableauPeres_[x-1][y].getCol() == tableauPeres_[x][y].getCol()
				|| tableauPeres_[x+1][y].getCol() == tableauPeres_[x][y].getCol()
				|| tableauPeres_[x][y+1].getCol() == tableauPeres_[x][y].getCol()
		 		|| tableauPeres_[x][y-1].getCol() == tableauPeres_[x][y].getCol()
		 		|| tableauPeres_[x-1][y-1].getCol() == tableauPeres_[x][y].getCol()
		 		|| tableauPeres_[x+1][y-1].getCol() == tableauPeres_[x][y].getCol()
		 		|| tableauPeres_[x-1][y+1].getCol() == tableauPeres_[x][y].getCol()
		 		|| tableauPeres_[x+1][y+1].getCol() == tableauPeres_[x][y].getCol());
		}*/
		

	}
	//Fin question 7)



	public void relierVoisins(int x, int y, String col)
	{


	}
	
	public String xandy(Case case1)
	{
		return case1.getX()+" : "+case1.getY();
	}

	//Procédure qui permet de récupérer les voisins d'une case
	public Case getLesVoisins(int x, int y, String col)
	{
		/*for (int i = x-1; i <= x+1; ++i)
		{
			for (int j = y; j <= y+1; ++j)
			{
				if (tableauPeres_[i][j].getCol() == tableauPeres_[x][y].getCol() && tableauPeres_[i][j] != tableauPeres_[x][y])
				{
					System.out.println(tableauPeres_[i][j].getX()+" : "+tableauPeres_[i][j].getY());
				}
				if (tableauPeres_[j][i].getCol() == tableauPeres_[x][y].getCol() && tableauPeres_[i][j] != tableauPeres_[x][y])
				{
					System.out.println(tableauPeres_[i][j].getX()+" : "+tableauPeres_[i][j].getY());
				}
				
			}
		}*/

		if (x == 0 && y == 0)
		{
			if (tableauPeres_[x+1][y].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x+1][y]));
				return tableauPeres_[x+1][y];
			}
			else if (tableauPeres_[x][y+1].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x][y+1]));
				return tableauPeres_[x][y+1];
			}
			else if (tableauPeres_[x+1][y+1].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x+1][y+1]));
				return tableauPeres_[x+1][y+1];
			}
			else
			{
				return tableauPeres_[x][y];
			}
		 }
		 else if (y == 0 && x == longueur_-1)
		{
			if (tableauPeres_[x][y].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x+1][y]));
				return tableauPeres_[x][y];
			}
			else if (tableauPeres_[x-1][y].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x-1][y]));
				return tableauPeres_[x-1][y];
			}
			else if  (tableauPeres_[x][y+1].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x][y+1]));
				return tableauPeres_[x][y+1];
			}
			else if (tableauPeres_[x][y+1].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x+1][y+1]));
				return tableauPeres_[x][y+1];
			}
			else if (tableauPeres_[x-1][y+1].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x-1][y+1]));
				return tableauPeres_[x-1][y+1];
			}
			else
			{
				return tableauPeres_[x][y];
			}
		}
		else if (y == 0)
		{
			if (tableauPeres_[x+1][y].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x+1][y]));
				return tableauPeres_[x+1][y];
			}
			else if (tableauPeres_[x-1][y].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x-1][y]));
				return tableauPeres_[x-1][y];
			}
			else if  (tableauPeres_[x][y+1].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x][y+1]));
				return tableauPeres_[x][y+1];
			}
			else if (tableauPeres_[x+1][y+1].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x+1][y+1]));
				return tableauPeres_[x+1][y+1];
			}
			else if (tableauPeres_[x-1][y+1].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x-1][y+1]));
				return tableauPeres_[x-1][y+1];
			}
			else
			{
				return tableauPeres_[x][y];
			}
		}
		else if (x == 0 && y == longueur_-1)
		{
			if (tableauPeres_[x+1][y].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x+1][y]));
		 		return tableauPeres_[x+1][y];
		 	}
		 	else if (tableauPeres_[x][y-1].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x][y-1]));
		 		return tableauPeres_[x][y-1];
		 	}
		 	else if (tableauPeres_[x+1][y-1].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x+1][y-1]));
		 		return tableauPeres_[x+1][y-1];
		 	}
		 	else if (tableauPeres_[x+1][y-1].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x+1][y-1]));
		 		return tableauPeres_[x+1][y-1];
		 	}
		 	else
		 	{
		 		return tableauPeres_[x][y];
		 	}
		}
		else if (x == 0)
		{
			if (tableauPeres_[x+1][y].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x+1][y]));
		 		return tableauPeres_[x+1][y];
		 	}
		 	else if (tableauPeres_[x][y-1].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x][y-1]));
		 		return tableauPeres_[x][y-1];
		 	}
		 	else if (tableauPeres_[x][y+1].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x][y+1]));
		 		return tableauPeres_[x][y+1];
		 	}
		 	else if (tableauPeres_[x+1][y-1].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x+1][y-1]));
		 		return tableauPeres_[x+1][y-1];
		 	}
		 	else if (tableauPeres_[x+1][y-1].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x+1][y-1]));
		 		return tableauPeres_[x+1][y-1];
		 	}
		 	else if (tableauPeres_[x+1][y+1].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x+1][y+1]));
		 		return tableauPeres_[x+1][y+1];
		 	}
		 	else
		 	{
		 		return tableauPeres_[x][y];
		 	}
		}
		if (x == longueur_-1 && y == longueur_-1)
		{
			if (tableauPeres_[x-1][y].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x+1][y]));
				return tableauPeres_[x-1][y];
			}
			else if (tableauPeres_[x][y-1].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x][y+1]));
				return tableauPeres_[x][y-1];
			}
			else if (tableauPeres_[x-1][y-1].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x+1][y+1]));
				return tableauPeres_[x-1][y-1];
			}
			else
			{
				return tableauPeres_[x][y];
			}
		 }
		else if (x == longueur_-1)
		{
			if (tableauPeres_[x-1][y].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x+1][y]));
		 		return tableauPeres_[x-1][y];
		 	}
		 	else if (tableauPeres_[x][y-1].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x][y-1]));
		 		return tableauPeres_[x][y-1];
		 	}
		 	else if (tableauPeres_[x][y+1].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x][y+1]));
		 		return tableauPeres_[x][y+1];
		 	}
		 	else if (tableauPeres_[x-1][y-1].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x+1][y-1]));
		 		return tableauPeres_[x-1][y-1];
		 	}
		 	else if (tableauPeres_[x-1][y+1].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x+1][y+1]));
		 		return tableauPeres_[x-1][y+1];
		 	}

		 	else
		 	{
		 		return tableauPeres_[x][y];
		 	}
		}
		else if (y == longueur_-1)
		{
			if (tableauPeres_[x+1][y].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x+1][y]));
				return tableauPeres_[x+1][y];
			}
			else if (tableauPeres_[x-1][y].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x-1][y]));
				return tableauPeres_[x-1][y];
			}
			else if  (tableauPeres_[x][y-1].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x][y+1]));
				return tableauPeres_[x][y-1];
			}
			else if (tableauPeres_[x+1][y-1].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x+1][y+1]));
				return tableauPeres_[x+1][y-1];
			}
			else if (tableauPeres_[x-1][y-1].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x-1][y+1]));
				return tableauPeres_[x-1][y-1];
			}
			else
			{
				return tableauPeres_[x][y];
			}
		}
		else
		{
			if (tableauPeres_[x-1][y].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x-1][y]));
				return tableauPeres_[x-1][y];
			}
			else if (tableauPeres_[x+1][y].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x+1][y]));
				return tableauPeres_[x+1][y];
			}
			else if (tableauPeres_[x][y+1].getCol() == tableauPeres_[x][y].getCol())
			{
				//System.out.println(xandy(tableauPeres_[x][y+1]));
				return tableauPeres_[x][y+1];
			}
		 	else if (tableauPeres_[x][y-1].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x][y-1]));
		 		return tableauPeres_[x][y-1];
		 	}
		 	else if (tableauPeres_[x-1][y-1].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x-1][y-1]));
		 		return tableauPeres_[x-1][y-1];
		 	}
		 	else if (tableauPeres_[x+1][y-1].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x+1][y-1]));
		 		return tableauPeres_[x+1][y-1];
		 	}
		 	else if (tableauPeres_[x-1][y+1].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x-1][y+1]));
		 		return tableauPeres_[x-1][y+1];
		 	}
		 	else if (tableauPeres_[x+1][y+1].getCol() == tableauPeres_[x][y].getCol())
		 	{
		 		//System.out.println(xandy(tableauPeres_[x+1][y+1]));
		 		return tableauPeres_[x+1][y+1];
		 	}
		 	else
		 	{
		 		return tableauPeres_[x][y];
		 	}
		}
	}
	//Fin getLesVoisins

	//Pour le jeu entre deux humains
	public int initialiser()
	{

		int nombreE;
		int k = 0;
		//int max = randInt(2,10);
		tableauPeres_ = new Case[longueur_][longueur_];
		int i, j = 0;
		for(i=0; i<longueur_; ++i)
		{
			for(j=0; j<longueur_; ++j)
			{
				tableauPeres_[i][j] = new Case(i, j, 0);
				
			}
		}
		System.out.println("Combien d'étoiles souhaitez vous pour chaque joueur ?");
		nombreE = clavier.nextInt();
		while (k < nombreE)
		{
			addEtoile("bleu");
			addEtoile("rouge");
			++k;
		}
		return nombreE;
	}

	public void preparerScore(int x, int y, String col)
	{

		if (col == "bleu" && tableauPeres_[x][y].getNbEtoile() > scoreJ2_)
		{
			scoreJ2_ = tableauPeres_[x][y].getNbEtoile();
		}
		else if (col == "rouge" && tableauPeres_[x][y].getNbEtoile() > scoreJ1_)
		{
			scoreJ1_ = tableauPeres_[x][y].getNbEtoile();
		}
		/*else if (col == "bleu" && tableauPeres_[x][y].getNbEtoile() == scoreJ2_)
		{
			scoreJ2_ += tableauPeres_[x][y].getNbEtoile();
		}
		else if (col == "rouge" && tableauPeres_[x][y].getNbEtoile() == scoreJ1_)
		{
			scoreJ1_ += tableauPeres_[x][y].getNbEtoile();
		}*/
	}

	public void afficher(int tour)
	{
		String res = new String();
		if (tour % 2 == 1)
		{
			res += " *** joueur ROUGE *** \n\n";
		}
		else if (tour % 2 == 0)
		{
			res += " *** joueur BLEU *** \n\n";
		}
			
		
		for(int x = 0; x < (longueur_); ++x) 
		{
			
			for(int y = 0; y < (longueur_); ++y) 
			{
				if (tableauPeres_[x][y].getNbEtoile() < 1)
				{
					if(tableauPeres_[x][y].getCol() == "blanc") res += " ⬜ ";
					if(tableauPeres_[x][y].getCol() == "rouge") res +=  red_+" ⬜ "+reset_;
					if(tableauPeres_[x][y].getCol() == "bleu")	res += blue_+" ⬜ "+reset_;
					// pour les étoiles ⬚
				}
				else
				{
					if(tableauPeres_[x][y].getCol() == "blanc") res += " * ";
					if(tableauPeres_[x][y].getCol() == "rouge") res +=  red_+" * "+reset_;
					if(tableauPeres_[x][y].getCol() == "bleu")	res += blue_+" * "+reset_;
				}

			}
			res += "\n";
			
		}	
		System.out.println(res);
	}

	public void addEtoile(String couleur)
	{
		int entier1;
		int entier2;
		entier1 = randInt(0, longueur_-1);
		entier2 = randInt(0, longueur_-1);
		tableauPeres_[entier1][entier2].colorerCase(couleur);
		tableauPeres_[entier1][entier2].setNbEtoile(1);
		System.out.println(entier1+" : "+entier2);

	}

	public int randInt(int min, int max) 
	{
    	Random rand = new Random();
    	int randomNum = rand.nextInt((max - min) + 1) + min;
    	return randomNum;
	}
	//Fin jeu entre deux humains

	//Pour la question 8) joueDeuxHumains
	public void joueDeuxHumains()
	{
		boolean fin = false;
		boolean result = false;
		int etoiles;
		int i = 0;
		int x = 0;
		int y = 0;
		String couleur;
		etoiles = initialiser();
		while (!fin)
		{
			if (i % 2 == 0)
			{
				couleur = "bleu";
			}
			else
			{
				couleur = "rouge";
			}
			afficher(i);
			afficheScores(couleur);
			System.out.println("Quel est la valeur de x ?");
			x = clavier.nextInt();
			System.out.println("Quel est la valeur de y ?");
			y = clavier.nextInt();
			result = tableauPeres_[x][y].colorerCase(couleur);
			System.out.println(relieComposantes(x, y, couleur));
			
			System.out.println(existeCheminCases(getLesVoisins(x, y, couleur), tableauPeres_[x][y], couleur));
			if (getNbEtoiles(x, y, couleur) < getNbEtoiles(getLesVoisins(x, y, couleur).getX(), getLesVoisins(x, y, couleur).getY(), couleur))
			{
				union(getLesVoisins(x, y, couleur).getX(), getLesVoisins(x, y, couleur).getY(), x, y);
			}
			else
			{
				union(x, y, getLesVoisins(x, y, couleur).getX(), getLesVoisins(x, y, couleur).getY());
			}
			preparerScore(x, y, couleur);
			++i;
			if (scoreJ2_ == etoiles)
			{
				System.out.println("Joueur bleu a gagné !");
				fin = true;
			}
			else if (scoreJ1_ == etoiles)
			{
				System.out.println("Joueur rouge a gagné !");
				fin = true;
			}
		}
	}
	//Fin question 8)


	
}
