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
	
	public void compressionChemin(int x, int y)
	{
		if (tableauPeres_[x][y].getX() != x && tableauPeres_[x][y].getY() != y)
		{
			tableauPeres_[x][y] = tableauPeres_[tableauPeres_[x][y].getX()][tableauPeres_[x][y].getY()];
			compressionChemin(tableauPeres_[x][y].getX(), tableauPeres_[x][y].getY());
			System.out.println(x+":"+y);
		}
		else
		{
			System.out.println(x+":"+y);
		}
	}

	// Pour la question 3) existeCheminCases
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
	
	// Pour la question 2) afficheComposante
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
	
	

	public void union(int xf, int yf, int xp, int yp)
	{
		Case daddy = tableauPeres_[xp][yp];
		Case son = tableauPeres_[xf][yf];
		if (!(son.equals(daddy))) 
		{
			if (daddy.getCol() == "bleu")
			{
				scoreJ2_ += son.getNbEtoile();
			}
			else if (daddy.getCol() == "rouge")
			{
				scoreJ1_ += son.getNbEtoile();
			}
			daddy.setNbEtoile(daddy.getNbEtoile()+son.getNbEtoile());
			tableauPeres_[xp][yp] = tableauPeres_[xf][yf]; 
		}
	}
	
	public int getNbEtoiles(int x, int y, String col)
	{
		System.out.println("La composante dont la case ["+x+", "+y+"] fait parti et de couleur "+col+" contient "+tableauPeres_[x][y].getNbEtoile()+" *");
		return tableauPeres_[x][y].getNbEtoile();
	}
	
	public void nombresEtoiles(int x, int y, String col)
	{
		System.out.println("Pour la case de position ["+x+", "+y+"] et de couleur "+col+" le nombre d'étoiles est "+getNbEtoiles(x,y,col));
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

	// Pour la question 7) relieComposantes
	public boolean relieComposantes(int x, int y, String col)
	{
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
			return (tableauPeres_[x+1][y].getCol() == tableauPeres_[x][y].getCol())
				|| tableauPeres_[x-1][y].getCol() == tableauPeres_[x][y].getCol()
				|| tableauPeres_[x][y+1].getCol() == tableauPeres_[x][y].getCol()
				|| tableauPeres_[x+1][y+1].getCol() == tableauPeres_[x][y].getCol()
				|| tableauPeres_[x-1][y+1].getCol() == tableauPeres_[x][y].getCol())
		}
		else if (x == 0 && y == 0)
		{
			return (tableauPeres_[x+1][y].getCol() == tableauPeres_[x][y].getCol()
				|| tableauPeres_[x][y+1].getCol() == tableauPeres_[x][y].getCol()
				|| tableauPeres_[x+1][y+1].getCol() == tableauPeres_[x][y].getCol())
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

		return true;
		

	}

	public boolean relierCasesMin()
	{
		return true;
	}

	public void initialiser()
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
	}

	public void addEtoile(String couleur)
	{
		int entier1;
		int entier2;
		entier1 = randInt(0, longueur_-1);
		entier2 = randInt(0, longueur_-1);
		tableauPeres_[entier1][entier2].setNbEtoile(1);
		tableauPeres_[entier1][entier2].colorerCase(couleur);
	}

	public int randInt(int min, int max) 
	{
    	Random rand = new Random();
    	int randomNum = rand.nextInt((max - min) + 1) + min;
    	return randomNum;
	}

	public void joueDeuxHumains()
	{
		boolean fin = false;
		int i = 0;
		int x = 0;
		int y = 0;
		String couleur;
		initialiser();
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
			System.out.println("Quel est la valeur de x ?");
			x = clavier.nextInt();
			System.out.println("Quel est la valeur de y ?");
			y = clavier.nextInt();
			tableauPeres_[x][y].colorerCase(couleur);
			
			++i;
		}
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
			
		
		for(int x = 0; x < (longueur_ - 1); ++x) 
		{
			
			for(int y = 0; y < (longueur_ - 1); ++y) 
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
	
}
