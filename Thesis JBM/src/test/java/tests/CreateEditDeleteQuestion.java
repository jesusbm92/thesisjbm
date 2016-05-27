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

import domain.Exercise;
import domain.Question;
import domain.Statistic;

import repositories.QuestionRepository;
import services.ExerciseService;
import services.QuestionService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CreateEditDeleteQuestion extends GlobalTest {

	@Autowired
	QuestionService questionService;

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	ExerciseService exerciseService;

	// @Before
	// public void setUp() {
	// PopulateDatabase.main(null);
	// }

	@Test
	public void testCreateQuestionAdmin() {

		authenticate("admin");

		int sizeBefore = questionService.findAll().size();

		Question question = questionService.create();
		question.setName("prueba");
		question.setText("a");
		question.setWeight(0.0);
		Statistic statistic = new Statistic();
		statistic.setPercentage(0.0);
		statistic.setQuestion(question);
		question.setStatistic(statistic);
		question.setXml("");
		question.setDifficulty("");
		question.setExercise(exerciseService.findOne(8));
		questionService.save(question);

		int sizeAfter = questionService.findAll().size();

		Assert.isTrue(sizeAfter > sizeBefore);
	}

	@Test
	public void testCreateQuestionCustomer() {

		authenticate("customer");

		int sizeBefore = questionService.findAll().size();

		Question question = questionService.create();
		question.setName("pruebacustomer");
		question.setText("a");
		question.setWeight(0.0);
		Statistic statistic = new Statistic();
		statistic.setPercentage(0.0);
		statistic.setQuestion(question);
		question.setStatistic(statistic);
		question.setXml("");
		question.setDifficulty("");
		question.setExercise(exerciseService.findOne(8));

		questionService.save(question);

		int sizeAfter = questionService.findAll().size();

		Assert.isTrue(sizeAfter > sizeBefore);
	}

	@Test
	public void testEditQuestion() {

		authenticate("admin");
		Question question = questionService.findOne(9);
		question.setName("aeraera");
		questionService.save(question);
		Question again = questionService.findOne(9);
		Assert.isTrue(again.getName().equals("aeraera"));

	}

}