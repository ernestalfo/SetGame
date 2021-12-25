package ernest.testgame.canvas.setgame;

import java.util.Random;

public class TMonedaJusta {
	private Random MiAzar;
	private boolean AlAzar = true;
	private int jugador;
	public TMonedaJusta (long seed)
	{
		 MiAzar = new Random(seed);
	}
	public int LanzarMoneda()
	{
		if(AlAzar)
		{
			jugador = MiAzar.nextInt(2) + 1;
			AlAzar = false;
		}
		else
		{
			if(jugador == 1)
				jugador = 2;
			else
				jugador = 1;
			AlAzar = true;
		}
		return jugador;
	}//public int LanzarMoneda()
}//public class TMonedaJusta
