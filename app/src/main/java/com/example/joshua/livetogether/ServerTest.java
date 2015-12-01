// /**
//  *	Unit tests for all controller functions
//  */
// public class ServerTest
// {
// 	public static void main (String[] args)
// 	{
// 		// Test user signin and register
// 		User use = ServerCom.register("testUser", "testPass", "7144943046");
// 		String uid;
// 		if (use == null)
// 			uid = ServerCom.signIn("testUser", "testPass");
// 		else
// 			uid = use.getUID();
// 		System.out.println(uid);

// 		// Test creating or joining apartment
// 		String aid = ServerCom.createApartment(uid, "testApt");
// 		if (aid == null)
// 			aid = ServerCom.setApartmentID(uid, "testApt");

// 		// Test get apt id
// 		System.out.println(ServerCom.getApartmentID(uid));

// 		// Test adding / removing repeating tasks
// 		ServerCom.addTask(aid, "task1", 30, true);
// 		System.out.println(ServerCom.removeTask(aid, "task1", "testUser"));
// 		// Expect true because it is repeating

// 		// Test adding / removing single-time tasks
// 		ServerCom.addTask(aid, "task2", 30, false);
// 		System.out.println(ServerCom.removeTask(aid, "task2", "testUser"));
// 		// Expect false because it is not repeating

// 		// Get and print tasks
// 		Task[] taskList = ServerCom.getTasks(aid);
// 		System.out.println("TASKS:");
// 		for (Task indiv : taskList)
// 		{
// 			System.out.println(indiv.getDescription());
// 			System.out.println(indiv.getAssignee());
// 		}
// 	}

// }