/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CmbServicios {
	private int id;
	private String name;	

	public CmbServicios(int id, String name){
		this.id = id;
		this.name = name;
	}

	public int getId(){
		return id;
	}

	@Override
	public String toString(){
		return name;
	}
}
