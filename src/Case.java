class Case
{
	private int x_;
	private int y_;
	private String col_;
	private int nbEtoile_;
	
	public Case(int x, int y)
	{
		x_ = x;
		y_ = y;
		col_ = "blanc";
		aEtoile_ = false;
	}
	
	public int getY()
	{
		return y_;
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
	
	public void incX()
	{
		++x_;
	}
	
	public void incY()
	{
		++y_;
	}
	
	public void colorerCase(String col)
	{
		if(col_ = "blanc")
		{
			col_ = col;
		}
		else
		{
			System.out.println("Cette case est déjà en couleur.");
		}
	}
}
	