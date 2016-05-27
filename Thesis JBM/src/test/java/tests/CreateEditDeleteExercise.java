package tests;

import globalTesting.GlobalTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Exam;
import domain.Exercise;

import services.ExamService;
import services.ExerciseService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CreateEditDeleteExercise extends GlobalTest {

	@Autowired
	ExerciseService exerciseService;

	@Autowired
	ExamService examService;

	// @Before
	// public void setUp() {
	// PopulateDatabase.main(null);
	// }

	@Test
	public void testCreateExerciseAdmin() {

		authenticate("admin");

		int sizeBefore = exerciseService.findAll().size();

		Exercise exercise = exerciseService.create();
		exercise.setName("prueba");
		exercise.setText("a");
		exercise.setExam(examService.findOne(7));
		exerciseService.save(exercise);

		int sizeAfter = exerciseService.findAll().size();

		Assert.isTrue(sizeAfter > sizeBefore);
	}

	@Test
	public void testCreateExerciseCustomer() {

		authenticate("customer");

		int sizeBefore = exerciseService.findAll().size();

		Exercise exercise = exerciseService.create();
		exercise.setName("pruebacustomer");
		exercise.setText("a");
		exercise.setExam(examService.findOne(7));

		
		exerciseService.save(exercise);

		int sizeAfter = exerciseService.findAll().size();

		Assert.isTrue(sizeAfter > sizeBefore);
	}

	@Test
	public void testEditExercise() {

		authenticate("admin");
		Exercise exercise = exerciseService.findOne(8);
		exercise.setName("aeraera");
		exerciseService.save(exercise);
		Exercise again = exerciseService.findOne(8);
		Assert.isTrue(again.getName().equals("aeraera"));

	}

}