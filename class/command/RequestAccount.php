<?php

  /**
   * RequestAccount
   *
   * Sends an email with information from a form to request account creation
   *
   * @author James Corsi
   */

  class RequestAccount extends Command
  {
    public function execute()
    {
      $wagsEmail = "wags@cs.appstate.edu";
      $personalEmail = "ransomts@email.appstate.edu";
      $username = $_REQUEST['username'];
      $fullname = $_REQUEST['name'];
      $email = $_REQUEST['email'];
      $school = $_REQUEST['school'];  
      $comments = $_REQUEST['comments'];
      $message = "New teacher account request:\nUsername: $username\nFull Name: $fullname\nEmail: $email\nSchool: $school\nComments: $comments";
      
      if ($username == null || $fullname == null || $email == null || $school == null)
	{
	  JSON::error('nullfield');
	}

      else if (!filter_var($email, FILTER_VALIDATE_EMAIL))
	{
	  JSON::warn('email');
	}

      else if (User::isUsername($username))
	{
	  JSON::warn('userexists');
	}

      else
	{
	  JSON::success('success');
      mail(
	   $wagsEmail,
	   "New Account Request for $fullname",
	   $message );
	   
      mail(
	   $personalEmail,
	   "New Account Request for $fullname",
	   $message );
      }
    }
  }
?>