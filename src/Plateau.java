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
		if (tableauPeres_[x][y].getX() != x && tableauPeres_[x][y].getY() != y && tableauPeres_[x][y].getCol() != "blanc")
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
	public void afficheComposante(int x, int y, String col)
	{

		int limite = getLongueur()-1;

		for (int i = 0; i <= limite; ++i)
		{
			for (int j=0; j <= limite; ++j)
			{
				if (existeCheminCases(tableauPeres_[x][y], tableauPeres_[i][j], col))
				{
					System.out.println("La case ["+x+", "+y+"] contient la composante suivante :");
					System.out.println(i+":"+j);
				}
			}
		}
			

	}
	//Fin question 2)

	//Pour la question 3) existeCheminCases
	public boolean rechercheMemePere(Case case1, Case case2)
	{
		return tableauPeres_[case1.getX()][case1.getY()] == tableauPeres_[case2.getX()][case2.getY()];
	}	
	
	public boolean existeCheminCases(Case case1, Case case2, String col)
	{
		return rechercheMemePere(case1, case2) && case1.getCol() == case2.getCol();
	}
	//Fin question 3)

	//Pour la question 4) relierCasesMin
	public int relierCasesMin(int x, int y, int z, int t, String col)
	{
		boolean visite[][];
		boolean tousVisite = false;
		Case predecesseur[][];
		int poids[][];
		int min;
		Case courante = new Case(0,0,0);
		String couleur = "blanc";
		predecesseur = new Case[longueur_][longueur_];
		poids = new int[longueur_][longueur_];
		visite = new boolean[longueur_][longueur_];

		for (int i = 0; i <= longueur_-1; ++i)
		{
			for(int j = 0; j <= longueur_-1; ++j)
			{
				poids[i][j] = Integer.MAX_VALUE;
				if (tableauPeres_[i][j].getCol() != col && tableauPeres_[i][j].getCol() != couleur)
				{
					visite[i][j] = true;
					poids[i][j] = -1;
					predecesseur[i][j] = tableauPeres_[i][j];
				}
				else
				{
					visite[i][j] = false;
					predecesseur[i][j] = null;
				}
			}
		}
		predecesseur[x][y] = tableauPeres_[x][y];
		poids[x][y] = 0;

		if(poids[z][t] > poids[x][y])
		{
			poids[z][t] = poids[x][y]+1;
			predecesseur[z][t] = tableauPeres_[x][y];
		}

		Case sommet = new Case(0,0,0);
		min = Integer.MAX_VALUE;
		for(int i = 0; i <= longueur_-1; ++i) 
		{ 
			for(int j = 0; j <= longueur_-1; ++j) 
			{		
				if(!visite[i][j] && poids[i][j] < min) 
				{
					min = poids[i][j];
					sommet = tableauPeres_[i][j];
					
				}
			}
		}
		
		int i = z;
		int j = t;
		int cpt = 0;

		courante = tableauPeres_[i][j];

		while ((i !=x) || (j != y))
		{
			if (courante.getCol() == col)
			{
				++cpt;
			}

			courante = predecesseur[i][j];
			i = courante.getX();
			j = courante.getY();

		}

		return poids[z][t] - cpt;
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
			if(getLesVoisins(x, y, col).getX() == x && getLesVoisins(x, y, col).getY() == y)
			{
				return false;
			}
			else
			{
				return (getLesVoisins(x, y, col).getCol() == tableauPeres_[x][y].getCol());
			}
		/*}*/
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
		int choix;
		int i = 0;
		int x = 0;
		int y = 0;
		int x2 = 0;
		int y2 = 0;
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
			System.out.println("1-Jouer");
			System.out.println("2-Afficher une composante");
			System.out.println("3-Vérifier si une case relie une composante");
			System.out.println("4-Regarder s'il existe un chemin entre deux cases d'une couleur donnée");
			System.out.println("5-Afficher le nombre minimum de cases entre deux cases données (x,y) et (z,t)");
			System.out.println("6-Quitter");
			choix = clavier.nextInt();
			afficher(i);
			switch (choix)
			{
				case 1:
					
					System.out.println("### Ajout d'une case pour jouer ###");
					System.out.println("Quel est la valeur de x ?");
					x = clavier.nextInt();
					System.out.println("Quel est la valeur de y ?");
					y = clavier.nextInt();
					compressionChemin(x, y);
					result = tableauPeres_[x][y].colorerCase(couleur);
					//System.out.println(existeCheminCases(getLesVoisins(x, y, couleur), tableauPeres_[x][y], couleur));
					if (getNbEtoiles(x, y, couleur) < getNbEtoiles(getLesVoisins(x, y, couleur).getX(), getLesVoisins(x, y, couleur).getY(), couleur))
					{
						union(getLesVoisins(x, y, couleur).getX(), getLesVoisins(x, y, couleur).getY(), x, y);
					}
					else
					{
						union(x, y, getLesVoisins(x, y, couleur).getX(), getLesVoisins(x, y, couleur).getY());
					}
					preparerScore(x, y, couleur);
					afficheScores(couleur);
					nombresEtoiles(x, y, couleur);
					++i;
					break;
				case 2:
					System.out.println("### Afficher une composante ###");
					System.out.println("Quel est la valeur de x ?");
					x = clavier.nextInt();
					System.out.println("Quel est la valeur de y ?");
					y = clavier.nextInt();
					compressionChemin(x, y);
					compressionChemin(x2, y2);
					afficheComposante(x, y, couleur);
					break;
				case 3:
					System.out.println("### Tester si une case relie une composante ###");
					System.out.println("Quel est la valeur de x ?");
					x = clavier.nextInt();
					System.out.println("Quel est la valeur de y ?");
					y = clavier.nextInt();
					compressionChemin(x, y);
					if(!relieComposantes(x, y, couleur))
					{
						System.out.println("Cette case ne relie aucune composante.");
					}
					else
					{
						System.out.println("Cette case relie une ou plusieurs composante(s).");
					}
					break;
				case 4:
					System.out.println("### Tester s'il existe un chemin entre deux cases données ###");
					System.out.println("Quel est la valeur de x de la première case ?");
					x = clavier.nextInt();
					System.out.println("Quel est la valeur de y de la première case ?");
					y = clavier.nextInt();
					System.out.println("Quel est la valeur de x de la deuxième case ?");
					x2 = clavier.nextInt();
					System.out.println("Quel est la valeur de y de la deuxième case ?");
					y2 = clavier.nextInt();
					compressionChemin(x, y);
					compressionChemin(x2, y2);
					if (existeCheminCases(tableauPeres_[x][y], tableauPeres_[x2][y2], couleur))
					{
						System.out.println("Il existe un chemin entre les deux cases.");
					}
					else
					{
						System.out.println("Il n'existe pas de chemin entre les deux cases.");
					}
					
					break;
				case 5:
					System.out.println("### Afficher nombre minimum de cases qui relie deux cases ###");
					System.out.println("Quel est la valeur de x de la première case ?");
					x = clavier.nextInt();
					System.out.println("Quel est la valeur de y de la première case ?");
					y = clavier.nextInt();
					System.out.println("Quel est la valeur de x de la deuxième case ?");
					x2 = clavier.nextInt();
					System.out.println("Quel est la valeur de y de la deuxième case ?");
					y2 = clavier.nextInt();
					compressionChemin(x, y);
					compressionChemin(x2, y2);
					System.out.println(relierCasesMin(x, y, x2, y2, couleur));
					break;
				case 6:
					fin = true;
					break;
			}
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
