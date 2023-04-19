package com.rabbit.assignment3.producer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@RestController
public class EduStatProducer {

	private String topic1Prefix = "Cost_";
	private String topic2Prefix = "Top5-Expensive_";
	private String topic3Prefix = "Top5-Economic_";
	private String topic4Prefix = "Top5-HighestGrow_";
	private String topic5Prefix = "AverageExpense_";

	private String delimiter = "_";

	private static final String MESSAGE = "message";
	private static final String TOPIC = "topic";

	@Autowired
	private AmqpTemplate amqpTemplate;

	@GetMapping("/")
	public String handleRequest(@RequestParam String query) {
		System.out.println("handleRequest : " + query);

		Map<String, String> content = getMessage(query);

//		amqpTemplate.convertAndSend("exchange", "edustat", query);
		if (content != null)
			amqpTemplate.convertAndSend(content.get(TOPIC), content.get(MESSAGE));
		else
			return "Error: check query format";
		return "Message sent to rabbitmq";
	}

	private Map<String, String> getMessage(String query) {
		MongoDatabase database = dbConfig();
		Map<String, String> map = new HashMap<>();
		if (query.startsWith(topic1Prefix)) {
			map.put(TOPIC, "topic1");
			map.put(MESSAGE, executeQueryOne(database, query));
			return map;

		} else if (query.startsWith(topic2Prefix)) {
			map.put(TOPIC, "topic2");
			map.put(MESSAGE, executeQueryTwo(database, query));
			return map;
		} else if (query.startsWith(topic3Prefix)) {
			map.put(TOPIC, "topic3");
			map.put(MESSAGE, executeQueryThree(database, query));
			return map;
		} else if (query.startsWith(topic4Prefix)) {
			map.put(TOPIC, "topic4");
			map.put(MESSAGE, executeQueryFour(database, query));
			return map;

		} else if (query.startsWith(topic5Prefix)) {
			map.put(TOPIC, "topic5");
			map.put(MESSAGE, executeQueryFive(database, query));
			return map;

		}
		return null;
	}

	private String executeQueryFive(MongoDatabase database, String query) {
		String s[];
		s = query.substring(topic5Prefix.length()).split(delimiter);
		// AverageExpense_Year_Type_Length
		
		String year = s[0];
		String type = s[1];
		String length = s[2];
		MongoCollection<Document> dataset = database.getCollection("EduCostStatQueryFive");
		Document searchQuery = new Document();
		searchQuery.put("year", year);
		searchQuery.put("type", type);
		searchQuery.put("length", length);
		FindIterable<Document> searchResult = dataset.find(searchQuery);
		ArrayList<String> docs = searchResult.map(Document::toJson).into(new ArrayList<>());
		return String.join(", ", docs);
	}

	private String executeQueryFour(MongoDatabase database, String query) {
		String s[];
		s = query.substring(topic4Prefix.length()).split(delimiter);
		// Top5-HighestGrow_Year_Type_Length
		
		String year = s[0];
		String type = s[1];
		String length = s[2];
		MongoCollection<Document> dataset = database.getCollection("EduCostStatQueryFour");
		Document searchQuery = new Document();
		searchQuery.put("year", year);
		searchQuery.put("type", type);
		searchQuery.put("length", length);
		FindIterable<Document> searchResult = dataset.find(searchQuery);
		ArrayList<String> docs = searchResult.map(Document::toJson).into(new ArrayList<>());
		return String.join(", ", docs);
	}

	private String executeQueryThree(MongoDatabase database, String query) {
		String s[];
		s = query.substring(topic3Prefix.length()).split(delimiter);
		// Top5-Economic_Year_Type_Length
		
		String year = s[0];
		String type = s[1];
		String length = s[2];
		MongoCollection<Document> dataset = database.getCollection("EduCostStatQueryThree");
		Document searchQuery = new Document();
		searchQuery.put("year", year);
		searchQuery.put("type", type);
		searchQuery.put("length", length);
		FindIterable<Document> searchResult = dataset.find(searchQuery);
		ArrayList<String> docs = searchResult.map(Document::toJson).into(new ArrayList<>());
		return String.join(", ", docs);
	}

	private String executeQueryTwo(MongoDatabase database, String query) {
		String s[];
		s = query.substring(topic2Prefix.length()).split(delimiter);
		// Top5-Expensive_Year_Type_Length
		
		String year = s[0];
		String type = s[1];
		String length = s[2];
		MongoCollection<Document> dataset = database.getCollection("EduCostStatQueryTwo");
		Document searchQuery = new Document();
		searchQuery.put("year", year);
		searchQuery.put("type", type);
		searchQuery.put("length", length);
		FindIterable<Document> searchResult = dataset.find(searchQuery);
		ArrayList<String> docs = searchResult.map(Document::toJson).into(new ArrayList<>());
		return String.join(", ", docs);
	}

	private String executeQueryOne(MongoDatabase database, String query) {
		String s[];
		s = query.substring(topic1Prefix.length()).split(delimiter);
		// Cost_Year_State_Type_Length_Expense
		
		String year = s[0];
		String state = s[1];
		String type = s[2];
		String length = s[3];
		String expense = s[4];
		MongoCollection<Document> dataset = database.getCollection("EduCostStatQueryOne");
		Document searchQuery = new Document();
		searchQuery.put("year", year);
		searchQuery.put("state", state);
		searchQuery.put("type", type);
		searchQuery.put("length", length);
		searchQuery.put("expense", expense);
		FindIterable<Document> searchResult = dataset.find(searchQuery);
		ArrayList<String> docs = searchResult.map(Document::toJson).into(new ArrayList<>());
		return String.join(", ", docs);
	}

	private MongoDatabase dbConfig() {
		String uri = "mongodb+srv://ragapriya14:password1234@dssprojectdatacluster.uoo0adu.mongodb.net/?retryWrites=true&w=majority";
		ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(uri))
				.serverApi(serverApi).build();
		MongoClient mongoClient = MongoClients.create(settings);
		MongoDatabase database = mongoClient.getDatabase("EduCostStat");
		return database;

	}

}
