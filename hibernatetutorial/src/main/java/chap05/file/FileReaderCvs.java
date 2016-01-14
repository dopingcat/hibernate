package chap05.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import chap05.vo.BoardArticle;

public class FileReaderCvs {
	public static List<BoardArticle> getArticles() throws NumberFormatException, IOException, ParseException {
		BoardArticle boardArticle = null;
		StringTokenizer tokenizer;
		SimpleDateFormat transFormat = new SimpleDateFormat("MM/DD/yyyy");

		int id;
		String userId;
		String content;
		Date writeDate;

		BufferedReader br = new BufferedReader(new FileReader("src/main/resources/dbsample.csv"));
		ArrayList<BoardArticle> list = new ArrayList<>();
		String str;

		while((str = br.readLine()) != null) {
			tokenizer = new StringTokenizer(str, ",");

			while(tokenizer.hasMoreElements()) {
				id = Integer.parseInt(tokenizer.nextToken());
				userId = tokenizer.nextToken();
				content = tokenizer.nextToken();
				writeDate = transFormat.parse(tokenizer.nextToken());
				boardArticle = new BoardArticle(id, userId, content, writeDate);
				list.add(boardArticle);
			}
		}
		br.close();
		return list;
	}
}
