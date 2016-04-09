package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import utilities.internal.DatabaseUtil;
import domain.Answer;
import domain.Exam;
import domain.Exercise;
import domain.Question;
import domain.Statistic;

public class FileToObjectsConverter {
	
	public static String prettyFormat(String input, int indent) {
        try
        {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            // This statement works with JDK 6
            transformerFactory.setAttribute("indent-number", indent);
             
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        }
        catch (Throwable e)
        {
            // You'll come here if you are using JDK 1.5
            // you are getting an the following exeption
            // java.lang.IllegalArgumentException: Not supported: indent-number
            // Use this code (Set the output property in transformer.
            try
            {
                Source xmlInput = new StreamSource(new StringReader(input));
                StringWriter stringWriter = new StringWriter();
                StreamResult xmlOutput = new StreamResult(stringWriter);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(indent));
                transformer.transform(xmlInput, xmlOutput);
                return xmlOutput.getWriter().toString();
            }
            catch(Throwable t)
            {
                return input;
            }
        }
    }
 
    public static String prettyFormat(String input) {
        return prettyFormat(input, 2);
    }

	public static void main(String[] args) {
		DatabaseUtil databaseUtil;

		databaseUtil = null;

		BufferedReader br = null;

		Properties prop = new Properties();
		InputStream input = null;

		try {
			System.out.printf("PopulateDatabase 1.3%n");
			System.out.printf("--------------------%n%n");

			System.out.printf("Initialising persistence context `%s'...%n",
					DatabaseConfig.PersistenceUnit);
			databaseUtil = new DatabaseUtil();

			System.out.printf("Creating database `%s' (%s)...%n",
					databaseUtil.getDatabaseName(),
					databaseUtil.getDatabaseDialectName());
			databaseUtil.recreateDatabase();

			String sCurrentLine;

			System.out.println("Accessing Properties File...");

			try {

				input = FileToObjectsConverter.class.getClassLoader()
						.getResourceAsStream("config.properties");

				// load a properties file
				prop.load(input);

			} catch (IOException ex) {
				ex.printStackTrace();
			}

			Path file = Paths.get(prop.getProperty("pathToExam"));
			BasicFileAttributes attr = Files.readAttributes(file,
					BasicFileAttributes.class);
			Date creation = new Date(attr.creationTime().toMillis());

			br = new BufferedReader(new FileReader(
					prop.getProperty("pathToExam")));

			Exam e = new Exam();
			e.setName(file.getFileName().toString().replace(".tst", ""));
			e.setDate(creation);
			System.out.println(e.getName());
			String xmlToExam = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p:examen barajaEjercicios=\"false\" barajaPreguntas=\"false\" barajaRespuestas=\"true\" numEnunciados=\"9\" ordenEjercicios=\"10,3,5,ej1,2,4,6,7,8,9\" tipoNumeracionEjercicios=\"ordinal_literal\" tipoNumeracionPreguntas=\"decimal\" tipoNumeracionRespuestas=\"caracteres_minusculas\" xmlns:p=\"barajador_examen.modelo.jaxb.modeloexamen\" xmlns:xsi=\"ht//www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"barajador_examen.modelo.jaxb.modeloexamen modeloExamen.xsd \"> <cabecera> <infoUniversidad> <nombre>Universidad Politécnica de Madrid</nombre> <siglas>UPM</siglas> </infoUniversidad> <infoFacultad> <nombre>Escuela Técnica Superior de Ingenieros Informáticos</nombre> <siglas>ETSIINF</siglas> </infoFacultad> <infoDepartamento> <nombre>Departamento de Lenguajes y Sistemas Informáticos e Ingeniería del Software</nombre> <siglas>DLSIIS</siglas> </infoDepartamento> <infoAsignatura> <nombre>Programación II</nombre> <tipo>Troncal</tipo> </infoAsignatura> <tipoExamen>Itinerario Flexible</tipoExamen> <fechaExamen> <fechaSeparadores tipo=\"separadores\">"
					+ e.getDate()
					+ "</fechaSeparadores> </fechaExamen> <tituloExamen> <nombreUniversidad>false</nombreUniversidad> <nombreFacultad>false</nombreFacultad> <nombreDepartamento>true</nombreDepartamento> <siglasUniversidad>true</siglasUniversidad> <siglasFacultad>true</siglasFacultad> <siglasDepartamento>false</siglasDepartamento> </tituloExamen> <subtituloExamen> <nombreAsignatura>true</nombreAsignatura> <siglasAsignatura>false</siglasAsignatura> <tipoAsignatura>false</tipoAsignatura> <tipoExamen>true</tipoExamen> <fechaExamen>true</fechaExamen> </subtituloExamen> <textoCaratulaExamen> <prefacioDelExamen> <elemento nombreElemento=\"Realización\"><![CDATA[El test se realizará en la hoja de respuesta. Es \textbf{importante} que no olvidéis rellenar vuestros datos personales y el código clave de vuestro enunciado. Se pueden utilizar hojas aparte en sucio. ]]> </elemento> <elemento nombreElemento=\"Duración\"> </elemento> <elemento nombreElemento=\"Puntuación\"> </elemento> <elemento nombreElemento=\"Calificaciones\"> </elemento> <elemento nombreElemento=\"Revisión\"> </elemento> </prefacioDelExamen> </textoCaratulaExamen> </cabecera> <ejercicios>";
			while ((sCurrentLine = br.readLine()) != null) {
				if (sCurrentLine.contains("EJERCICIO")
						&& !sCurrentLine.contains("INCLUIR")) {
					Exercise exercise = new Exercise();
					// Date
					e.getExercises().add(exercise);
					exercise.getExams().add(e);
					// Setting exercise name
					exercise.setName(sCurrentLine.substring(
							sCurrentLine.indexOf("E"),
							sCurrentLine.indexOf("<") - 1));
					// Next line for weight (For the question of that exercise)
					sCurrentLine = br.readLine();
					// sCurrentLine = sCurrentLine.replaceAll("[^\\d.]", "");
					Double currentWeight = null;
					while (currentWeight == null) {
						try {
							sCurrentLine = sCurrentLine.replaceAll("[^\\d.]",
									"");
							currentWeight = Double.valueOf(sCurrentLine);
						} catch (Exception ex) {
							sCurrentLine = br.readLine();
						}
					}
					// Next line is ENUNCIADO (text)
					sCurrentLine = br.readLine();
					// Next start the text of the exercise:
					Boolean textFinish = false;
					exercise.setText("");
					while ((sCurrentLine = br.readLine()) != null
							&& !textFinish) {
						// String variable to avoid modifying currentLine
						String copy = sCurrentLine;
						if (copy.replaceAll("\\s+", "").equals(":")) {
							textFinish = true;
						} else {
							exercise.setText(exercise.getText().concat(
									"\n" + sCurrentLine));
						}
					}
					// Text finished, now question
					Question question = new Question();
					exercise.getQuestions().add(question);
					question.getExercises().add(exercise);
					// Search for the first line "PREGUNTA 1XX"
					while (!(sCurrentLine.contains("PREGUNTA") && !sCurrentLine
							.contains("ESCOGE"))) {
						sCurrentLine = br.readLine();
					}

					question.setName(sCurrentLine.substring(
							sCurrentLine.indexOf("P"),
							sCurrentLine.indexOf("<") - 1));

					// Skipping the ENUNCIADO_TF line
					sCurrentLine = br.readLine();
					Boolean textQuestionFinish = false;
					question.setWeight(currentWeight);
					question.setText("");
					while ((sCurrentLine = br.readLine()) != null
							&& !textQuestionFinish) {
						// String variable to avoid modifying currentLine
						String copy = sCurrentLine;
						if (copy.replaceAll("\\s+", "").equals("::")) {
							textQuestionFinish = true;
						} else {
							question.setText(question.getText().concat(
									"\n" + sCurrentLine));
						}
					}
					// Question finished, now answers
					Collection<Answer> answers = new ArrayList<Answer>();
					// Now we look for the RESPUESTA line
					while (!sCurrentLine.contains("RESPUESTA")) {
						sCurrentLine = br.readLine();
					}
					// Now we loop each answer
					Boolean answerFinish = false;
					while (!answerFinish) {
						sCurrentLine = br.readLine();
						String copy = sCurrentLine;
						if (copy.replaceAll("\\s+", "").equals("::")) {
							answerFinish = true;
						}

						else {
							Answer a = new Answer();
							Pattern p = Pattern.compile("\\p{L}");
							Matcher m = p.matcher(copy);
							if (m.find()) {
								Integer indexStart = m.start();
								copy = copy.substring(indexStart);
								// System.out.println(copy);
								String[] splitted = copy.split("\\s+");
								a.setText("");
								for (int i = 0; i < splitted.length; i++) {
									if (i == 0) {
										a.setName(splitted[i]);
									} else if (i == 1) {
										if (splitted[i].toLowerCase().equals(
												"true")) {
											a.setIsCorrect(true);
										} else if (splitted[i].toLowerCase()
												.equals("false")) {
											a.setIsCorrect(false);
										}
									} else if (i == 2) {
										a.setText(a.getText() + splitted[i]);
									} else {
										a.setText(a.getText() + " "
												+ splitted[i]);
									}
								}
								// Setting the penalty to 0.0 because no info
								// about penalty is given in the tsts
								a.setPenalty(0.0);
								answers.add(a);

							}

						}
					}
					// question.setAnswers(answers);
					// Statistic s = new Statistic();
					// s.setPercentage(0.0);
					// question.setStatistic(s);
					// s.setQuestion(question);
					// question.setDifficulty("");
					// question.setXml("");
					// e.setXml("");
					// e.setDifficulty("");
					// for (Answer a : answers) {
					// a.setQuestion(question);
					// }
					//
					// databaseUtil.openTransaction();
					//
					// databaseUtil.persist(question);
					// databaseUtil.persist(s);
					// for (Answer a : answers) {
					// databaseUtil.persist(a);
					// }
					// databaseUtil.persist(e);
					// databaseUtil.persist(exercise);
					//
					// databaseUtil.commitTransaction();

					BufferedReader brStatistic = new BufferedReader(
							new FileReader(prop.getProperty("pathToExam")
									.replace(".tst", ".sts")));

					Statistic s = new Statistic();
					while ((sCurrentLine = brStatistic.readLine()) != null) {
						// We reached percentages
						if (sCurrentLine.contains("Valores porcentuales")) {
							String copy = sCurrentLine;
							String namecode = question.getName().substring(9);
							//

							while (copy != null && sCurrentLine != null) {
								// We have here the line of the question we are
								// doing
								// TODO
								if (copy.length() > 3) {
									copy = copy.replaceAll("\\s+", "")
											.substring(0, 3);
									if (copy.equals(namecode)) {
										// We are in the line of the question
										// now
										String nospaces = sCurrentLine;
										nospaces = nospaces.replaceAll("\\s+",
												"");
										nospaces = nospaces.replaceAll(
												namecode, "");
										// Now we separate the percentage using
										// the . instead of substring(0,3) in
										// case we have elements like 4.2
										String[] percentsplit = nospaces
												.split("\\.");
										// We build the string for the
										// percentage
										String percentage = percentsplit[0]
												+ "."
												+ percentsplit[1].substring(0,
														1);

										s.setPercentage(new Double(percentage));
										s.setQuestion(question);
										// System.out.println(s.getPercentage());
									}

									else if (s.getPercentage() == null
											&& s.getQuestion() == null) {
										s.setPercentage(100.0);
										s.setQuestion(question);
									}

								}

								sCurrentLine = brStatistic.readLine();
								// System.out.println(sCurrentLine);
								copy = sCurrentLine;
							}
						}
					}

					question.setAnswers(answers);
					question.setStatistic(s);
					question.setDifficulty(s.getPercentage().toString());
					xmlToExam = xmlToExam.concat("<ejercicio etiqueta=\""
							+ exercise.getName() + "\"> <enunciado><![CDATA["
							+ exercise.getText()
							+ "]]></enunciado> <preguntas>");
					String xmlToQuestion = " <pregunta peso=\""
							+ question.getWeight()
							+ "\" codigo=\""
							+ question.getName()
							+ "\"> <texto>"
							+ question.getText()
							+ "</texto><respuestasUnica numColumnas=\"1\" numSoluciones=\"unica\" pesoRespCorrecta=\"2\" pesoRespIncorrecta=\""
							+ question.getWeightfail() + "\">";

					e.setDifficulty("");
					for (Answer a : answers) {
						xmlToQuestion = xmlToQuestion
								.concat("<respuesta correcta=\""
										+ a.getIsCorrect() + "\"><texto>"
										+ a.getText() + "</texto></respuesta>");
						a.setQuestion(question);
					}
					// Concat XML endTags
					xmlToQuestion = xmlToQuestion
							.concat("</respuestasUnica></pregunta>");

					// Añadir comprobacion si es ultimo para añadir al final
					// /ejercicios y /examen
					xmlToExam = xmlToExam
							.concat(xmlToQuestion
									+ "</preguntas><metadatos></metadatos></ejercicio>");

					question.setXml(FileToObjectsConverter.prettyFormat(xmlToQuestion));

					e.setXml(xmlToExam);
					// DATABASE INSERT
					databaseUtil.openTransaction();

					databaseUtil.persist(question);
					for (Answer a : answers) {
						databaseUtil.persist(a);
					}
					databaseUtil.persist(e);
					databaseUtil.persist(exercise);

					databaseUtil.commitTransaction();
				}

			}

			e.setXml(e.getXml().concat(" </ejercicios></p:examen>"));
			e.setXml(FileToObjectsConverter.prettyFormat(e.getXml()));
			databaseUtil.openTransaction();
			databaseUtil.persist(e);
			databaseUtil.commitTransaction();

		} catch (Throwable oops) {
			System.out.flush();
			System.err.printf("%n%s%n", oops.getLocalizedMessage());
			oops.printStackTrace(System.err);
		} finally {
			if (databaseUtil != null)
				databaseUtil.close();
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

}
