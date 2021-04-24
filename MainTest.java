package vue;

import java.util.ArrayList;
import java.util.List;

public class MainTest {

	// Struct of  matrix cell coordinates
	static class Cell {
		int x;
		int y;
	}

	public static void main(String[] args) {
		// Data
		int[][] matrice = {
				{13  , 24, 31, 19, 40, 29},
				{18, 25, 30, 15,43 , 22},
				{20  , 20   , 27, 25, 34, 33},
				{23, 26 , 28, 18, 37, 30}
		};

		// Showing initial formatted matrix in console
		showMatrice(matrice);

		// Creating a list to store matrix and reuse the previous data
		List<int[][]> matriceList = new ArrayList<>();
		matriceList.add(deepCopyintMatrice(matrice));
		int matriceId = 0;

		// Initializing matrix with initial data
		int[][] oldMatrice;
		int[][] newMatrice;
		oldMatrice = matriceList.get(matriceId);

		// Iterate while "entrance variable" is available
		while(getEntranteCell(oldMatrice) != null) {

			// Initializing a new matrix with the oldest to keep the format
			newMatrice = deepCopyintMatrice(oldMatrice);

			// Get "Entrante variable" and "Pivot"
			Cell entrante = getEntranteCell(oldMatrice);
			Cell pivot = getPivotCell(oldMatrice, entrante);

			// Replace value in cell by 0 for the "Pivot" column
			for (int j = 0; j < newMatrice.length; j++) {
				newMatrice[j][pivot.x] = 0;
			}

			// Calculate the new values for the "Pivot" row
			for (int i = 0; i < newMatrice[pivot.y].length; i++) {
				newMatrice[pivot.y][i] = oldMatrice[pivot.y][i] / oldMatrice[pivot.y][pivot.x];
			}

			// Calculating the all other values of the matrix
			for (int j = 0; j < newMatrice.length; j++) {
				for (int i = 0; i < newMatrice[j].length; i++) {
					if (i != pivot.x && pivot.y != j) {
						newMatrice[j][i] = oldMatrice[j][i] - oldMatrice[pivot.y][i] * oldMatrice[j][pivot.x] / oldMatrice[pivot.y][pivot.x];
					}
				}
			}

			showMatrice(newMatrice);

			// Incrementing the matrix
			matriceId++;
			matriceList.add(deepCopyintMatrice(newMatrice));
			oldMatrice = matriceList.get(matriceId);
		}

	}

	// Return the "Entrance" cell position
	public static Cell getEntranteCell(int[][] matrice) {
		int lastRow    = matrice.length - 1;
		int lastColumn = matrice[0].length - 1;
		int[] array  = matrice[lastRow];
		int maxValue = array[0];
		Cell entrante  = new Cell();
		entrante.y     = lastRow;

		for (int i = 1; i < array.length; i++) {
			if (array[i] > maxValue && i < lastColumn) {
				maxValue   = array[i];
				entrante.x = i;
			}
		}
		if (maxValue > 0f) {
			return entrante;
		} else {
			return null;
		}

	}

	// Return the "Pivot" cell position
	private static Cell getPivotCell(int[][] matrice, Cell entrante) {
		int lastColumn = matrice[0].length - 1;
		int minValue = matrice[0][lastColumn];
		Cell pivot     = new Cell();
		pivot.x        = entrante.x;

		for (int j = 1; j < matrice.length; j++) {

			int calculedValue = matrice[j][lastColumn]/matrice[j][entrante.x];
			if ( calculedValue < minValue && calculedValue > 0) {
				minValue = calculedValue;
				pivot.y  = j;
			}
		}
		return pivot;
	}

	// Show formatted matrix in console
	private static void showMatrice(int[][] matrice){
		for (int j = 0; j < matrice.length; j++) {
			for (int i = 0; i < matrice[j].length; i++) {

				System.out.print( matrice[j][i] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}

	// Copying the matrix and drop the reference of nested array
	public static int[][] deepCopyintMatrice(int[][] matrice) {
		if (matrice == null)
			return null;
		int[][] result = new int[matrice.length][];
		for (int r = 0; r < matrice.length; r++) {
			result[r] = matrice[r].clone();
		}
		return result;
	}
}