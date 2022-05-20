package com.axelor.student.webservice;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.axelor.student.db.Student;
import com.axelor.student.db.repo.StudentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;



@Path("/student")
public class StudentService {
	
	@Inject 
	StudentRepository studentRepo;
	
	JsonNodeFactory jsonFactory = JsonNodeFactory.instance;
	
	@Path("/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JsonNode getStudents() {
		ObjectNode mainNode = jsonFactory.objectNode();
		
		List<Student> studentList = studentRepo.all().fetch();
		
		ArrayNode arrayNode = jsonFactory.arrayNode();
		
		for(Student s : studentList) {
			ObjectNode studentNode = jsonFactory.objectNode();
			
			studentNode.put("fullName", s.getFullname());
			studentNode.putPOJO("0", s);
			
			arrayNode.add(studentNode);
		}
		mainNode.put("status", 0);
		mainNode.set("data", arrayNode);
		
		return mainNode;
	}
}
