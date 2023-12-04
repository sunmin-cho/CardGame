package algorithm_term_project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class CardSolve{
	class Point {
		int row, col, cnt;

		Point(int r, int c, int t) {
			row = r;
			col = c;
			cnt = t;
		}
	}

	static final int INF = 987654321;
	public static int[][] input_board;
	int[][] Board;
	static final int[][] D = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

	int bfs(Point src, Point dst) {
		boolean[][] visited = new boolean[4][4];
		Queue<Point> q = new LinkedList<>();
		q.add(src);
		while (!q.isEmpty()) {
			Point curr = q.poll();
			if (curr.row == dst.row && curr.col == dst.col) {
				return curr.cnt;
			}
			for (int i = 0; i < 4; i++) {
				int nr = curr.row + D[i][0], nc = curr.col + D[i][1];
				if (nr < 0 || nr > 3 || nc < 0 || nc > 3)
					continue;
				if (!visited[nr][nc]) {
					visited[nr][nc] = true;
					q.add(new Point(nr, nc, curr.cnt + 1));
				}
				for (int j = 0; j < 2; j++) {
					if (Board[nr][nc] != 0)
						break;
					if (nr + D[i][0] < 0 || nr + D[i][0] > 3 || nc + D[i][1] < 0 || nc + D[i][1] > 3)
						break;
					nr += D[i][0];
					nc += D[i][1];
				}
				if (!visited[nr][nc]) {
					visited[nr][nc] = true;
					q.add(new Point(nr, nc, curr.cnt + 1));
				}
			}
		}
		return INF;
	}

	int permutate(Point src) {
		int ret = INF;

		for (int num = 1; num <= 6; num++) {
			List<Point> card = new ArrayList<>();
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (Board[i][j] == num) {
						card.add(new Point(i, j, 0));
					}
				}
			}
			if (card.isEmpty())
				continue;

			int one = bfs(src, card.get(0)) + bfs(card.get(0), card.get(1)) + 2;
			int two = bfs(src, card.get(1)) + bfs(card.get(1), card.get(0)) + 2;

			for (int i = 0; i < 2; i++) {
				Board[card.get(i).row][card.get(i).col] = 0;
			}

			ret = Math.min(ret, one + permutate(card.get(1)));
			ret = Math.min(ret, two + permutate(card.get(0)));

			for (int i = 0; i < 2; i++) {
				Board[card.get(i).row][card.get(i).col] = num;
			}
		}
		if (ret == INF)
			return 0;
		return ret;
	}

	public int CardSolve(int[][] board, int r, int c) {
		Board = board;
		return permutate(new Point(r, c, 0));
	}

	int[][] randomarraygenerator() {
		int[][] array = new int[4][4];
		Random random = new Random();
		int time = random.nextInt(6) + 1;
		int[] random_array = new int[time];

		for (int i = 0; i < time; i++) {
			random_array[i] = random.nextInt(6) + 1;

			for (int j = 0; j < i; j++) {
				while (random_array[j] == random_array[i]) {
					random_array[i] = random.nextInt(6) + 1;
					j = 0;
				}
			}
		}

		System.out.println("Random Array: " + Arrays.toString(random_array));

		for (int i = 0; i < time; i++) {
			int row = random.nextInt(4);
			int col = random.nextInt(4);
			do {
				row = random.nextInt(4);
				col = random.nextInt(4);
			} while (array[row][col]!=0);
			array[row][col] = random_array[i];
			System.out.println("ROW:\"" + row + "\"COL:\"" + col+"\"" + "ENTERD:" + random_array[i]);
			int row2, col2;
			do {
				row2 = random.nextInt(4);
				col2 = random.nextInt(4);
			} while ((row2 == row && col2 == col) || (array[row2][col2]!=0));
			array[row2][col2] = random_array[i];
			System.out.println("ROW2:\"" + row2 + "\"COL2:\"" + col2+"\""+"ENTERD:" + random_array[i]);
		}

		return array;
	}

	int randomnumbergenerator() {
		return new Random().nextInt(4);
	}

	public static void main(String[] args) {
		CardSolve solve = new CardSolve();
		int[][] input_board = solve.randomarraygenerator();
		int r = solve.randomnumbergenerator();
		int c = solve.randomnumbergenerator();
		for (int i = 0; i < input_board.length; i++) {
			for (int j = 0; j < input_board[i].length; j++) {
				System.out.print(input_board[i][j] + " ");
			}
			System.out.println();
		}

		int result = solve.CardSolve(input_board, r, c);
		System.out.println(result);

		CardGame game = new CardGame();
		game.setBoardValues(input_board);
		game.startlocation(r, c);
		game.resetVal(result);
	}
}
