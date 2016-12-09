class Case
{
	private int x_;
	private int y_;
	private String col_;
	private int nbEtoile_;
	private boolean estInitial_;
	
	public Case(int x, int y, int nbEtoile)
	{
		x_ = x;
		y_ = y;
		col_ = "blanc";
		nbEtoile_ = nbEtoile;
		setEstInitial(false);
	}
	
	public int getY()
	{
		return y_;
	}

	public boolean getEstInitial()
	{
		return estInitial_;
	}
	
	public int getX()
	{
		return x_;
	}
	
	public int getNbEtoile()
	{
		return nbEtoile_;
	}
	
	public String getCol()
	{
		return col_;
	}
	
	public void setX(int x)
	{
		x_ = x;
	}
	
	public void setY(int y)
	{
		y_ = y;
	}
	
	public void setCol(String col)
	{
		col_ = col;
	}
	
	public void setNbEtoile(int nbEtoile)
	{
		nbEtoile_ = nbEtoile;
	}

	public void setEstInitial(boolean initial)
	{
		estInitial_ = initial;
	}
	
	public void incX()
	{
		++x_;
	}
	
	public void incY()
	{
		++y_;
	}
	
	//Pour la question 1) colorerCase()
	public boolean colorerCase(String col)
	{
		if(col_ == "blanc")
		{
			col_ = col;
			return true;
		}
		else
		{
			System.out.println("Cette case est déjà en couleur.");
			return false;
		}
	}
}
	
