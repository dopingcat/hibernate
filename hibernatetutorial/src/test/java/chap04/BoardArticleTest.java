package chap04;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import chap01.vo.Member;
import chap04.vo.BoardArticle;
import chap04.vo.Comments;
import util.CommonDao;

public class BoardArticleTest {
	// Const for Test
	private static final String HELLO = "Hello";
	private static final String VWAN = "Vwan";

	CommonDao<BoardArticle> boardDao = new CommonDao<>(BoardArticle.class);
	CommonDao<Member> memberDao = new CommonDao<>(Member.class);
	CommonDao<Comments> commentsDao = new CommonDao<>(Comments.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		BoardArticle boardArticleVo = new BoardArticle(VWAN, HELLO, new Date());
		boardDao.insert(boardArticleVo);

		BoardArticle getBoardArticle = boardDao.selectById(1);
		assertEquals(VWAN, getBoardArticle.getUserId());

		commentsDao.insert(new Comments("Vwan", "Hello Comments"));
		assertEquals("Hello Comments", commentsDao.selectById(1).getMessage());
	}

}
