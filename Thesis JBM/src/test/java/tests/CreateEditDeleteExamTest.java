package tests;

import globalTesting.GlobalTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Exam;

import services.ExamService;
import utilities.PopulateDatabase;


@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CreateEditDeleteExamTest extends GlobalTest {

	@Autowired
	ExamService examService;
	

//	@Before
//	public void setUp() {
//		PopulateDatabase.main(null);
//	}

	@Test
	public void testCreateExamAdmin() {

		authenticate("admin");

		int sizeBefore = examService.findAll().size();

		Exam exam = examService.create();
		exam.setName("prueba");
		exam.setXml("");
		exam.setDifficulty("");
		exam.setDate(new Date());
		examService.save(exam);

		int sizeAfter = examService.findAll().size();

		Assert.isTrue(sizeAfter > sizeBefore);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateExamCustomer() {

		authenticate("customer1");

		int sizeBefore = examService.findAll().size();

		Exam exam = examService.create();
		exam.setName("prueba");
		exam.setXml("");
		exam.setDifficulty("");
		exam.setDate(new Date());

		examService.save(exam);

		int sizeAfter = examService.findAll().size();

		Assert.isTrue(sizeAfter > sizeBefore);
	}
	
	@Test
	public void testEditExam() {

		authenticate("admin");
		Exam exam = examService.findOne(7);
		exam.setName("aeraera");
		examService.save(exam);
		Exam again = examService.findOne(7);
		Assert.isTrue(again.getName().equals("aeraera"));

	}


	@Test
	public void testRemoveExam() {

		authenticate("admin");

		int sizeBefore = examService.findAll().size();

		Exam exam = examService.findOne(163841);
		examService.delete(exam);

		int sizeAfter = examService.findAll().size();

		Assert.isTrue(sizeAfter < sizeBefore);

	}
}