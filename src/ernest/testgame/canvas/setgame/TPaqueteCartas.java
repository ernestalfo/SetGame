package ernest.testgame.canvas.setgame;

import java.util.Random;

import android.graphics.Color;

public class TPaqueteCartas {
	//atributos:
	private int ctd_cartas;
	private TCard [] cartas;
	private Random MiAzar; //Agregado en la nueva VERSION 0_6
	
	//Metodos:
	//Constructor:
	public TPaqueteCartas(){
		MiAzar = new Random();
		ctd_cartas = 81;
		cartas = new TCard[81];
		int index=0;
		for(int i=0; i<3 ; i++)
			for (int j=0; j<3; j++)
				for(int k=0; k<3; k++)
					for(int l=0; l<3; l++)
				{
				 cartas[index]=new TCard();
				 if(l == 0) 
					 //cartas[index].Colour = Color.GREEN;
					 cartas[index].Colour = Color.parseColor("#ff15cc00");
				 else if(l == 1)
					 //cartas[index].Colour = Color.RED;
					 cartas[index].Colour = Color.parseColor("#ffcc1500");
				 else
					 cartas[index].Colour = Color.BLUE;
				 
				 cartas[index].cantidadFig = i;
				 cartas[index].rell = j;
				 cartas[index].tipo = k; 
				 cartas[index].color = l;
				 index++; 
				} 
	}//Constructor de TPaqueteCartas
	public TPaqueteCartas(long seed){
		MiAzar = new Random(seed);
		ctd_cartas = 81;
		cartas = new TCard[81];
		int index=0;
		for(int i=0; i<3 ; i++)
			for (int j=0; j<3; j++)
				for(int k=0; k<3; k++)
					for(int l=0; l<3; l++)
				{
				 cartas[index]=new TCard();
				 if(l == 0) 
					 //cartas[index].Colour = Color.GREEN;
					 cartas[index].Colour = Color.parseColor("#ff15cc00");
				 else if(l == 1)
					 cartas[index].Colour = Color.RED;
				 else
					 cartas[index].Colour = Color.BLUE;
				 
				 cartas[index].cantidadFig = i;
				 cartas[index].rell = j;
				 cartas[index].tipo = k; 
				 cartas[index].color = l;
				 index++; 
				} 
	}//Constructor de TPaqueteCartas
	//Entregar una carta al "azar" del paquete.
	public TCard Entregar(){
		if(ctd_cartas > 0){
			int indexAzar;
			TCard seleccionada;
		
			//System.currentTimeMillis()
			//indexAzar = (int)(ctd_cartas*Math.random());
			indexAzar = MiAzar.nextInt(ctd_cartas);
			seleccionada = cartas[indexAzar];
		
			if(indexAzar != ctd_cartas-1)
				cartas[indexAzar] = cartas[ctd_cartas-1];
			
			ctd_cartas--;
			return seleccionada;
		}
		//else //esta demAs pero para probar...
		 return null;
	}//Fin de TCard Entregar()
	
	//Recibir una carta.
	public boolean Recibir(TCard cartaRecibida){
		if(cartaRecibida != null)
			if(ctd_cartas < 81){
				cartas[ctd_cartas] = cartaRecibida;
				ctd_cartas++;
				return true;
			}
		return false;
	}//Fin de boolean Recibir(TCard cartaRecibida)
	public int GetQtyCards(){
		return ctd_cartas;
	}
	public void Reset(){
		ctd_cartas = 81;
		cartas = new TCard[81];
		int index=0;
		for(int i=0; i<3 ; i++)
			for (int j=0; j<3; j++)
				for(int k=0; k<3; k++)
					for(int l=0; l<3; l++)
				{
				 cartas[index]=new TCard();
				 if(l == 0) 
					 //cartas[index].Colour = Color.GREEN;
					 cartas[index].Colour = Color.parseColor("#ff15cc00");
				 else if(l == 1)
					 cartas[index].Colour = Color.RED;
				 else
					 cartas[index].Colour = Color.BLUE;
				 
				 cartas[index].cantidadFig = i;
				 cartas[index].rell = j;
				 cartas[index].tipo = k; 
				 cartas[index].color = l;
				 index++; 
				} 	
	}
	

}//Hasta aqui la declaracion de TPaqueteCartas
