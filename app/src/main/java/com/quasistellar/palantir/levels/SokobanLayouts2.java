/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.quasistellar.palantir.levels;

public class SokobanLayouts2 {
	
	//32X32
	private static final int W = Terrain.WALL;
	private static final int T = Terrain.SHRUB;
	private static final int Z = Terrain.HIGH_GRASS;
	private static final int D = Terrain.DOOR;
	private static final int L = Terrain.LOCKED_DOOR;
	private static final int _ = Terrain.EMPTY; //for readability

	//private static final int T = Terrain.INACTIVE_TRAP;

	private static final int E = Terrain.EMPTY;
	private static final int K = Terrain.EXIT;
	private static final int M = Terrain.ENTRANCE;
	//private static final int X = Terrain.EXIT;

	//private static final int M = Terrain.WALL_DECO;
	//private static final int P = Terrain.PEDESTAL;
	
	private static final int A = Terrain.SOKOBAN_SHEEP;
	private static final int X = Terrain.CORNER_SOKOBAN_SHEEP;
	private static final int C = Terrain.SWITCH_SOKOBAN_SHEEP;
	private static final int B = Terrain.BLACK_SOKOBAN_SHEEP;
	private static final int H = Terrain.SOKOBAN_HEAP;
    private static final int I = Terrain.SOKOBAN_ITEM_REVEAL;
    private static final int F = Terrain.FLEECING_TRAP;
    private static final int U = Terrain.STATUE;
    private static final int G = Terrain.CHANGE_SHEEP_TRAP;
    private static final int S = Terrain.SECRET_DOOR;
    private static final int R = Terrain.PORT_WELL;
    private static final int V = Terrain.SOKOBAN_PORT_SWITCH;


	public static final int[] SOKOBAN_PERL = {
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, _, _, M, _, _, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, _, _, _, _, _, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, U, F, U, F, _, W, U, U, A, U, U, W, _, _, _, _, _, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, H, U, F, U, _, S, _, U, _, U, _, S, _, _, _, _, X, S, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, U, U, U, _, _, W, _, U, F, U, _, W, _, _, F, F, F, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, _, X, _, _, X, W, S, W, D, W, S, W, _, _, F, F, F, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, U, _, _, _, _, W, _, U, _, U, _, W, C, G, F, F, H, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, H, U, I, U, _, W, W, W, W, W, W, W, _, U, _, U, _, W, W, W, W, W, W, W, I, F, F, F, B, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, U, F, U, F, U, W, _, _, F, F, F, S, _, U, _, U, _, S, F, F, F, _, U, W, U, U, A, U, B, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, _, U, F, U, _, W, _, _, W, U, U, W, U, U, _, U, U, W, U, U, U, _, U, W, _, _, _, _, B, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, _, X, _, X, _, W, _, _, _, _, _, S, _, _, _, _, _, S, _, X, _, U, _, W, _, _, _, _, _, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, _, _, X, _, _, W, _, G, G, G, _, W, W, W, D, W, W, W, X, X, _, _, _, W, _, _, _, _, _, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, D, W, W, W, W, W, C, _, C, _, C, W, _, _, _, _, _, W, _, _, _, _, _, W, W, W, W, W, D, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, F, U, B, _, _, W, W, W, W, W, W, W, _, _, _, _, _, W, W, W, W, W, W, W, _, B, _, F, F, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, F, U, B, _, _, W, U, U, _, C, _, W, _, _, G, _, _, W, C, _, _, U, F, W, _, _, _, _, F, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, F, _, _, A, _, D, F, F, G, G, _, D, _, _, G, _, _, D, _, G, G, _, U, D, _, _, _, _, F, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, F, U, B, _, _, W, U, U, _, C, _, W, _, _, G, _, _, W, _, _, _, _, U, W, _, _, _, _, F, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, F, U, B, _, _, W, W, W, W, W, W, W, _, _, C, _, _, W, W, W, W, W, W, W, _, B, _, F, F, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, D, W, W, W, W, W, C, C, _, _, _, L, _, _, _, _, _, L, _, _, _, _, _, W, W, W, W, W, D, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, _, _, _, _, _, W, _, _, _, _, _, W, W, W, S, W, W, W, _, _, B, X, _, W, _, A, _, _, _, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, A, B, _, _, A, W, _, _, G, _, _, W, _, _, _, _, _, W, _, _, _, _, _, W, _, _, _, A, _, S, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, _, _, _, _, _, W, _, _, _, _, _, W, _, U, F, U, _, W, _, F, F, F, _, W, _, G, U, U, F, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, F, U, _, U, F, W, _, F, F, F, _, W, _, U, H, U, _, W, F, F, F, F, F, W, _, U, U, F, X, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, _, U, _, U, I, W, D, W, L, W, W, W, _, U, U, U, _, W, W, W, L, W, W, W, _, U, I, U, H, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, _, _, _, _, _, W, _, _, _, _, _, W, _, _, _, _, _, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, _, _, _, W, _, _, _, _, _, W, W, W, W, W, W, W, _, _, _, _, _, W, _, _, _, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, _, V, _, S, _, _, V, _, _, W, _, W, _, W, _, W, _, _, R, _, _, W, _, R, _, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, _, _, _, W, _, _, _, _, _, W, _, W, L, W, _, W, _, _, _, _, _, W, _, _, _, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, _, _, _, _, _, W, _, _, _, _, _, W, _, _, _, _, _, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, _, _, _, _, _, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, _, _, K, _, _, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W
	};
		
	public static final int[] SOKOBAN_VAULT_LEVEL = {
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 
		W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 
		W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 
		W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 
		W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 
		W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	W, 	W, 
		W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 
		W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 
		W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 
		W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 
		W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 
		W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 
		W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 
		W, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 
		W, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 
		W, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 
		W, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W
	};
	
	public static final int[] DRAGON_CAVE = {
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	_, 	_, 	_, 	_, 	_, 	W, 	_, 	_, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	H, 	W, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	H, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	H, 	H, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	H, 	H, 	H, 	H, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	H, 	H, 	H, 	H, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	_, 	H, 	H, 	_, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	_, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	H, 	_, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	W, 
		W, 	W, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	_, 	H, 	_, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	S, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	_, 	_, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	_, 	W, 	W, 
		W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	_, 	W, 	W, 
		W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	H, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	W, 	W, 	_, 	W, 	W, 	W, 	_, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	W, 	W, 	W, 	_, 	W, 	W, 	_, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	H, 	_, 	_, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	H, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	H, 	_, 	_, 	_, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	_, 	_, 	H, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	_, 	_, 	_, 	_, 	_, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W		
	};
	
}
