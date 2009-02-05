<?php
	
	function connect($server, $username, $password, $dbname) {
		$db_link = mysql_connect($server, $username, $password)
		or die("Could not connect to MySQL server!");
		mysql_select_db($dbname, $db_link)
		or die("Could not select database!");
		return $db_link;
	}
	
	function disconnect($db_link) {
		mysql_close($db_link);
	}
	
  /**
   * @param query string The MySQL query to be executed. Should be either UPDATE, INSERT, or DELETE.
   * @param db_link resource The database to use. Should be the global $db.
   * @return null
   */
	function query($query, $db_link) {
		$result = mysql_query($query, $db_link)
		or die(mysql_error());
		return $result;
	}

  /**
   * @param query string The MySQL query to be executed. Should be a SELECT query.
   * @param db_link resource The database to use. Should be the global $db.
   * @return array Returns an associative array.
   */
	function aquery($query, $db_link) {
		$result = mysql_query($query, $db_link)
		or die(mysql_error());
		$qarray = array();
		while($row = mysql_fetch_assoc($result)) {
			$qarray[]=$row;
		}
		return $qarray;
	}
	
	function colNames($result) {
		$fields = mysql_num_fields($result);
		for($count=0;$count<$fields;$count++) {
			$field = mysql_fetch_field($result, $count);
			$fieldArr[$count] = $field->name;
		}
		return $fieldArr;
	}
	
	function lastInsertId($db_link) {
		return mysql_insert_id($db_link);
	}
	
	function update($table_name, $row, $db_link) {
		//requires a row with an id column as the primary key
		//updates the row in the table
		
		$id = $row['id'];
		$query = "UPDATE {$table_name} SET ";
		$i = 0;
		$maxI = count($row);
		foreach($row as $col_name => $col_val) {
			$query .= $col_name;
			$query .= "='" . $col_val . "'";
			if ($i < $maxI - 1) {
				$query .= ", ";
			}
			$i++;
		}
		$query .= " WHERE id='{$id}'";
		query($query, $db_link);
	}
	
	function insert($table_name, $row, $db_link) {
		//inserts the row in the table
		
		$id = $row['id'];
		$query = "INSERT INTO {$table_name} ( ";
		$i = 0;
		$maxI = count($row);
		foreach($row as $col_name => $col_val) {
			$query .= $col_name;
			if ($i < $maxI - 1) {
				$query .= ", ";
			}
			$i++;
		}
		$query .= " ) VALUES ( ";
		$i = 0;
		foreach($row as $col_name => $col_val) {
			$query .= "'" . $col_val . "'";
			if ($i < $maxI - 1) {
				$query .= ", ";
			}
			$i++;
		}
		$query .= " ) ";
		query($query, $db_link);
	}

?>
