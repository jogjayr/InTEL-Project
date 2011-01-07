-- phpMyAdmin SQL Dump
-- version 2.11.11
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 07, 2011 at 11:33 AM
-- Server version: 5.0.77
-- PHP Version: 5.1.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `intel_dev`
--

-- --------------------------------------------------------

--
-- Table structure for table `app_assignment`
--

CREATE TABLE IF NOT EXISTS `app_assignment` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `problem_id` int(10) unsigned NOT NULL,
  `class_id` int(10) unsigned NOT NULL,
  `assignment_type_id` int(10) NOT NULL,
  `open_date` bigint(20) NOT NULL default '0',
  `close_date` bigint(20) NOT NULL default '0',
  `description` text,
  `is_active` tinyint(3) NOT NULL default '1',
  `created_on` bigint(20) NOT NULL default '0',
  `updated_on` bigint(20) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=163 ;

-- --------------------------------------------------------

--
-- Table structure for table `app_assignment_type`
--

CREATE TABLE IF NOT EXISTS `app_assignment_type` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `type` varchar(40) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `created_on` bigint(20) unsigned NOT NULL,
  `updated_on` bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

-- --------------------------------------------------------

--
-- Table structure for table `app_class`
--

CREATE TABLE IF NOT EXISTS `app_class` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `owner_user_id` int(10) unsigned NOT NULL,
  `teacher` varchar(60) NOT NULL,
  `description` text,
  `is_active` tinyint(3) NOT NULL default '1',
  `created_on` bigint(20) unsigned NOT NULL default '0',
  `updated_on` bigint(20) unsigned NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=23 ;

-- --------------------------------------------------------

--
-- Table structure for table `app_feedback`
--

CREATE TABLE IF NOT EXISTS `app_feedback` (
  `id` int(10) NOT NULL auto_increment,
  `user_id` int(10) NOT NULL,
  `timestamp` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `contents` text NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=25 ;

-- --------------------------------------------------------

--
-- Table structure for table `app_problem`
--

CREATE TABLE IF NOT EXISTS `app_problem` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(40) NOT NULL,
  `description` text,
  `instructor_description` text NOT NULL,
  `image` varchar(30) NOT NULL,
  `java_jar_name` varchar(60) NOT NULL,
  `java_class_name` varchar(60) NOT NULL,
  `type` varchar(10) NOT NULL default 'java',
  `extra` text NOT NULL,
  `width` int(11) unsigned NOT NULL,
  `height` int(11) unsigned NOT NULL,
  `is_active` tinyint(3) NOT NULL default '1',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=24 ;

-- --------------------------------------------------------

--
-- Table structure for table `app_problem_arg`
--

CREATE TABLE IF NOT EXISTS `app_problem_arg` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `problem_id` int(10) unsigned NOT NULL,
  `argument` varchar(40) NOT NULL,
  `is_active` tinyint(3) NOT NULL default '1',
  `created_on` bigint(20) unsigned NOT NULL default '0',
  `updated_on` bigint(20) unsigned NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `app_problem_tag`
--

CREATE TABLE IF NOT EXISTS `app_problem_tag` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `problem_type_id` int(10) unsigned NOT NULL,
  `problem_id` int(10) unsigned NOT NULL,
  `is_active` tinyint(3) NOT NULL default '1',
  `created_on` bigint(20) unsigned NOT NULL default '0',
  `updated_on` bigint(20) unsigned NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

-- --------------------------------------------------------

--
-- Table structure for table `app_problem_type`
--

CREATE TABLE IF NOT EXISTS `app_problem_type` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `type` varchar(40) NOT NULL,
  `is_active` tinyint(3) NOT NULL default '1',
  `created_on` bigint(20) unsigned NOT NULL default '0',
  `updated_on` bigint(20) unsigned NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Table structure for table `app_problem_usage_log`
--

CREATE TABLE IF NOT EXISTS `app_problem_usage_log` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `session_id` int(10) NOT NULL,
  `java_class` varchar(100) default NULL,
  `java_method` varchar(40) default NULL,
  `level` varchar(10) NOT NULL,
  `message` longtext,
  `created_on` datetime NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2556992 ;

-- --------------------------------------------------------

--
-- Table structure for table `app_problem_usage_sessions`
--

CREATE TABLE IF NOT EXISTS `app_problem_usage_sessions` (
  `id` int(10) NOT NULL auto_increment,
  `user_id` int(10) NOT NULL,
  `problem_id` int(10) NOT NULL,
  `java_session_id` varchar(40) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3328 ;

-- --------------------------------------------------------

--
-- Table structure for table `app_submission_status`
--

CREATE TABLE IF NOT EXISTS `app_submission_status` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `status` varchar(40) NOT NULL,
  `is_active` tinyint(3) NOT NULL default '1',
  `created_on` bigint(20) unsigned NOT NULL default '0',
  `updated_on` bigint(20) unsigned NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Table structure for table `app_user`
--

CREATE TABLE IF NOT EXISTS `app_user` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `uuid` varchar(32) NOT NULL,
  `email` varchar(60) NOT NULL,
  `password` varchar(32) NOT NULL,
  `password_salt` varchar(20) NOT NULL,
  `reset_password_code` varchar(40) default NULL,
  `reg_confirmation_code` varchar(40) default NULL,
  `first_name` varchar(40) NOT NULL,
  `last_name` varchar(40) NOT NULL,
  `user_type_id` int(10) unsigned NOT NULL,
  `is_active` tinyint(3) NOT NULL default '1',
  `created_on` bigint(20) unsigned NOT NULL default '0',
  `updated_on` bigint(20) unsigned NOT NULL default '0',
  `last_login` bigint(20) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=634 ;

-- --------------------------------------------------------

--
-- Table structure for table `app_user_assignment`
--

CREATE TABLE IF NOT EXISTS `app_user_assignment` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `user_id` int(10) unsigned NOT NULL,
  `assignment_id` int(10) unsigned NOT NULL,
  `submission_status_id` int(10) unsigned NOT NULL,
  `state` text NOT NULL,
  `is_active` tinyint(3) NOT NULL default '1',
  `created_on` bigint(20) unsigned NOT NULL default '0',
  `updated_on` bigint(20) unsigned NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8741 ;

-- --------------------------------------------------------

--
-- Table structure for table `app_user_class`
--

CREATE TABLE IF NOT EXISTS `app_user_class` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `user_id` int(10) unsigned NOT NULL,
  `class_id` int(10) unsigned NOT NULL,
  `is_active` tinyint(3) NOT NULL default '1',
  `created_on` bigint(20) unsigned NOT NULL default '0',
  `updated_on` bigint(20) unsigned NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=624 ;

-- --------------------------------------------------------

--
-- Table structure for table `app_user_type`
--

CREATE TABLE IF NOT EXISTS `app_user_type` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `type` varchar(40) NOT NULL,
  `is_active` tinyint(3) NOT NULL default '1',
  `created_on` bigint(20) unsigned NOT NULL default '0',
  `updated_on` bigint(20) unsigned NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;
