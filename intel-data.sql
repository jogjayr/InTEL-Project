-- phpMyAdmin SQL Dump
-- version 2.11.11.3
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 09, 2011 at 12:33 PM
-- Server version: 5.0.77
-- PHP Version: 5.3.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `intel`
--

--
-- Dumping data for table `app_assignment_type`
--

INSERT INTO `app_assignment_type` (`id`, `type`, `is_active`, `created_on`, `updated_on`) VALUES
(1, 'practice', 1, 1234794582, 1234794582),
(2, 'extra credit', 1, 1234794582, 1234794582),
(3, 'assignment', 1, 1234794582, 1234794582);

--
-- Dumping data for table `app_problem`
--

INSERT INTO `app_problem` (`id`, `name`, `description`, `instructor_description`, `image`, `java_jar_name`, `java_class_name`, `type`, `extra`, `width`, `height`, `is_active`, `ordseq`) VALUES
(1, 'Archer', 'Free Body Diagram Exercise: Archer', 'Asks the student to create free body diagrams for the bow, the bowstring, and both together.', 'archer.png', 'Archer.jar', 'archer.ArcherExercise', 'java', '', 1100, 768, 1, 2),
(2, 'SeeSaw', 'Free Body Diagram Exercise: See Saw', 'This simple exercise asks the student to find the FBD of the seesaw.', 'seesaw.png', 'SeeSaw.jar', 'seesaw.SeeSawExercise', 'java', '', 1100, 768, 1, 2),
(3, 'Pisa Tower', 'Free Body Diagram Exercise: Tower of Pisa', 'This simple exercise asks the student to find the FBD of the pisa tower. This does not to solve for the reactions at the base.', 'pisa.jpg', 'PisaProblem.jar', 'example02.SimpleTowerExercise', 'java', '', 1100, 768, 1, 2),
(4, 'Purse Problem', 'Frame Exercise: Arm and Purse', 'This asks the user to solve for the tension in the bicep, the reactions at the  elbow, and the reactions at the shoulder.', 'arm-purse.png', 'PurseProblem.jar', 'example01.PurseExerciseGraded', 'java', '', 1100, 768, 1, 6),
(5, 'Keyboard', 'Frame Exercise: Keyboard', 'This frame exercise asks the user to find the tension or compression in the crossbar PQ. The student should do this by solving the diagram for the whole frame then one of the side bars.', 'keyboard.png', 'Keyboard.jar', 'keyboard.KeyboardExercise', 'java', '', 1100, 768, 1, 6),
(6, 'Bicycle', 'Frame Exercise: Bicycle', 'This is a fairly challenging frame exercise. The student is asked to solve for all the 2FMs. The easiest way to do this is to solve the whole frame then the joints D, C, and E, in that order.', 'bicycle.png', 'Bicycle.jar', 'bicycle.BicycleExercise', 'java', '', 1100, 768, 1, 6),
(7, 'Awning', 'Distributed Load Exercise: Awning', 'This is a simple distributed load exercise, where the student finds the resultant of the force due to the snow on the awning, and solves for the reactions at the base.', 'awning.png', 'Awning.jar', 'awning.AwningExercise', 'java', '', 1100, 768, 1, 4),
(8, 'Bookshelf', 'Distributed Load Exercise: Bookshelf', 'The bookshelf is a slightly more complicated distributed load exercise in which the user must find the resultant of three groups of books, and then find the reactions at at both ends of the shelf.', 'bookshelf.png', 'Bookshelf.jar', 'bookshelf.BookshelfExercise', 'java', '', 1100, 768, 1, 4),
(9, 'Levee (part 1)', 'Distributed Load Exercise: Levee (part 1)', 'The Levee is a tricky distributed load problem. The trick is that the student is not given the value for the maximum of the force/distance from to the water. This can be calculated as 62.4*depth. So for this problem, the resultant magnitude is 10*10*62.4/2.', 'levee-10ft.png', 'Levee.jar', 'levee.LeveeExerciseLow', 'java', '', 1100, 768, 1, 4),
(10, 'Levee (part 2)', 'Distributed Load Exercise: Levee (part 2)', 'The Levee is a tricky distributed load problem. The trick is that the student is not given the value for the maximum of the force/distance from to the water. This can be calculated as 62.4*depth. So for this problem, the resultant magnitude is 12*12*62.4/2.\r\n\r\nThis differs from the previous problem by increasing the water level from 10 ft to 12 ft.', 'levee-12ft.png', 'Levee.jar', 'levee.LeveeExercise', 'java', '', 1100, 768, 1, 4),
(11, 'Learning Connectors', 'Introduction: Three short matching games to link statics connection types to images.', 'This is a practice exercise which helps students distinguish between pins, rollers, and fixed supports. \r\n\r\nThis should only be a practice problem, because the student''s score will not be recorded!', 'connectors.png', 'Matchingas3_v2', 'none', 'flash', '', 550, 400, 1, 1),
(12, 'merry-go-round manipulable', 'Introduction: Set the force on a merry-go-round to achieve maximum fun.', 'This simple exercises helps teach how the angle of a force affects the resulting moment. \r\n\r\nThis should only be a practice problem, because the student''s score will not be recorded!', 'merry.png', 'merrygoround3', 'merrygoround3', 'simplejava', '', 700, 700, 1, 1),
(13, 'see-saw manipulable', 'Introduction: Arrange the children on the see-saw to achieve balance.', 'This simple exercises helps teach how the distance of a force from a point affects the moment it exerts.\r\n\r\nThis should only be a practice problem, because the student''s score will not be recorded!', 'seesaw2.png', 'seesawnormal', 'seesawnormal', 'simplejava', '', 670, 400, 1, 1),
(14, 'Bridge', 'Truss Exercise: Minneapolis Bridge', 'This is a very large and intricate truss problem. It is recommended to only assign this as an extra credit problem.', 'bridge.png', 'Bridge.jar', 'bridge.Bridge1', 'java', '<p>This <a href="http://www.dot.state.mn.us/i35wbridge/drawings/BR9340%20Construction%20Plan%20(1965).pdf">PDF document</a> describes the I-35W Mississippi River bridge construction plan.</p>\r\n<p>Please be patient while the problem loads, the exercise is fairly large (5 MB)</p>', 1100, 768, 1, 5),
(15, 'Bridge (part 2)', 'Truss Exercise: Minneapolis Bridge', '', '', 'Bridge.jar', 'bridge.Bridge2', 'java', '<p>This <a href="http://www.dot.state.mn.us/i35wbridge/drawings/BR9340%20Construction%20Plan%20(1965).pdf">PDF document</a> describes the I-35W Mississippi River bridge construction plan.</p>\r\n<p>Please be patient while the problem loads, the exercise is fairly large (5 MB)</p>', 1100, 768, 0, 5),
(16, 'Bridge (part 3)', 'Truss Exercise: Minneapolis Bridge', '', '', 'Bridge.jar', 'bridge.Bridge3', 'java', '<p>This <a href="http://www.dot.state.mn.us/i35wbridge/drawings/BR9340%20Construction%20Plan%20(1965).pdf">PDF document</a> describes the I-35W Mississippi River bridge construction plan.</p>\r\n<p>Please be patient while the problem loads, the exercise is fairly large (5 MB)</p>', 1100, 768, 0, 5),
(17, 'Keyboard (Friction)', 'Friction Exercise: Keyboard', 'This is a variation on the keyboard which lacks a cross bar, but has friction with the ground at the base. This can be solved by making only a FBD of the whole structure, and then creating the two friction equations for the base supports.', 'keyboard.png', 'Keyboard.jar', 'keyboard.KeyboardFrictionExercise', 'java', '', 1100, 768, 1, 7),
(18, 'Space Station', 'Distributed Load Exercise: Find the centroid of the International Space Station', 'Asks the student to find the centroid of the ISS.', 'spacestation.jpg', 'SpaceStation.jar', 'spacestation.SpaceStationExercise', 'java', '', 1100, 768, 1, 4),
(19, 'Gambrel roof', 'Truss Exercise: Gambrel roof', 'This is a simple truss problem, and includes two ZFMs.', 'roof.png', 'SimpleTruss.jar', 'simpletruss.SimpleTruss1', 'java', '', 1100, 768, 1, 5),
(20, 'Spiderwoman', 'Friction Exercise: Spiderwoman', 'This is a simple friction exercise, where the Georgia Tech Spiderwoman is suspended by a cable and has her feet in friction with the wall. ', 'spiderwoman.png', 'Spiderwoman.jar', 'spiderwoman.SpiderwomanExercise', 'java', 'Hint: What is the equation to calculate friction? ', 1100, 768, 1, 7),
(21, 'Centergy Truss', 'Truss Exercise: Centergy Truss', 'This is an exercise for the Centergy parking deck collapse. The student should solve this by making a section.', 'centergytruss.png', 'CentergyTruss.jar', 'centergytruss.CentergyTrussExercise', 'java', '', 1100, 768, 1, 5),
(22, 'Centergy Frame', 'Frame Exercise: Centergy Frame', 'This is an exercise for the Centergy parking deck collapse. The student should solve this by making a diagram of the whole frame.', 'centergyframe.png', 'CentergyFrame.jar', 'centergyframe.CentergyFrameExercise', 'java', '', 1100, 768, 1, 6),
(23, 'CRC Roof', 'Frame Exercise: CRC Roof', 'This is a frame problem using the CRC Roof, students are asked to find the tension in one of the cables. This requires some trigonometry to solve.', 'CRC1.png', 'CRCRoof.jar', 'crcroof.CRCRoof', 'java', '', 1100, 768, 1, 6),
(24, 'Panel 3D', 'Equilibrium Exercise: Panel in 3D', 'This is a simple 3d exercise. ', 'panel1.png', 'Panel.jar', 'panel.PanelExercise', 'java', '', 1100, 768, 1, 3),
(27, 'Push up', 'Centroid Exercise: Find the reaction forces in push ups.', 'This gives the students three different push up poses and asks them to find the reaction forces in each.', 'pushup.png', 'PushUp.jar', 'pushup.PushUpExercise', 'java', '', 1100, 768, 1, 8),
(28, 'Ladder drill', 'Friction Exercise: find the force at which the ladder will slip.', 'The student must use the fiction equation at both the top and bottom of the ladder to find the point at which the ladder will just slip.', 'ladderdrill.png', 'LadderDrill.jar', 'ladderDrill.LadderDrillExercise', 'java', '', 1100, 768, 1, 7);

--
-- Dumping data for table `app_problem_arg`
--


--
-- Dumping data for table `app_problem_tag`
--

INSERT INTO `app_problem_tag` (`id`, `problem_type_id`, `problem_id`, `is_active`, `created_on`, `updated_on`) VALUES
(1, 1, 1, 1, 0, 0),
(2, 1, 2, 1, 0, 0),
(3, 1, 3, 1, 0, 0);

--
-- Dumping data for table `app_problem_type`
--

INSERT INTO `app_problem_type` (`id`, `type`, `is_active`, `created_on`, `updated_on`) VALUES
(1, 'Free Body Diagram Practice', 1, 0, 0);

--
-- Dumping data for table `app_submission_status`
--

INSERT INTO `app_submission_status` (`id`, `status`, `is_active`, `created_on`, `updated_on`) VALUES
(1, 'not started', 1, 1220465215, 1220465215),
(2, 'started', 1, 1220465215, 1220465215),
(3, 'in progress', 1, 1220465215, 1220465215),
(4, 'solved', 1, 1220465215, 1220465215);

--
-- Dumping data for table `app_user_type`
--

INSERT INTO `app_user_type` (`id`, `type`, `is_active`, `created_on`, `updated_on`) VALUES
(1, 'anonymous', 1, 1220465215, 1220465215),
(2, 'student', 1, 1220465215, 1220465215),
(3, 'instructor', 1, 1220465215, 1220465215),
(4, 'admin', 1, 1220465215, 1220465215);
