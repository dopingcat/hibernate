package chap05.paging;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import chap05.file.FileReaderCvs;
import chap05.vo.BoardArticle;
import util.CommonDao;

public class PagingTest {
	CommonDao<BoardArticle> boardDao = new CommonDao<>(BoardArticle.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void deleteTest() throws Exception {
		boardDao.insert(new BoardArticle("Vwan", "Hello", new Date()));
		assertEquals(1, boardDao.count());

		boardDao.deleteAll();
		assertEquals(0, boardDao.count());
	}

	public void pagingTest() throws NumberFormatException, IOException, ParseException {
		// insert dbsample.cvs
		List<BoardArticle> list = FileReaderCvs.getArticles();
		for(BoardArticle boardArticle : list) {
			boardDao.insert(boardArticle);
		}

		List<BoardArticle> pagedList1 = (List<BoardArticle>) boardDao.getPagedList(1, 10);
		System.out.println("* * * * * * * * * 1st Page * * * * * * * * * *");
		for(BoardArticle boardArticle : pagedList1) {
			System.out.println(boardArticle);
		}
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println();

		List<BoardArticle> pagedList2 = (List<BoardArticle>) boardDao.getPagedList(2, 10);
		System.out.println("* * * * * * * * * 2nd Page * * * * * * * * * *");
		for(BoardArticle boardArticle : pagedList2) {
			System.out.println(boardArticle);
		}
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * *");
	}
}