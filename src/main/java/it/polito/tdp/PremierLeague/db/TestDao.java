package it.polito.tdp.PremierLeague.db;

public class TestDao {

	public static void main(String[] args) {
		TestDao testDao = new TestDao();
		testDao.run();
	}
	
	public void run() {
		PremierLeagueDAO dao = new PremierLeagueDAO();
		System.out.println("\nPlayers:");
//		System.out.println(dao.listAllPlayers());
		System.out.println("\nActions:");
		System.out.println(dao.listAllActions());
	}

}
