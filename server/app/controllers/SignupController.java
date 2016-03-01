package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;

import data.Accounts;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.validation.Constraints;

public class SignupController extends Controller {

	public Result signup() {
		Form<SignUp> signUpForm = Form.form(SignUp.class).bindFromRequest();

		 if ( signUpForm.hasErrors()) {
		   return badRequest(signUpForm.errorsAsJson());
		 }
		 SignUp newUser =  signUpForm.get();
		 
		 if (Accounts.getInstance().accountExists(newUser.email)) {
			 return badRequest(buildJsonResponse("error", "User exists"));
		 } else {
			 Accounts.getInstance().addAccount(newUser.email, newUser.password);
			   session().clear();
			   session("username", newUser.email);
			   return ok(buildJsonResponse("success", "User created successfully"));
		 }
		}

		public static class UserForm {
		 @Constraints.Required
		 @Constraints.Email
		 public String email;
		}

		public static class SignUp extends UserForm {
		 @Constraints.Required
		 @Constraints.MinLength(6)
		 public String password;
		}

		private static ObjectNode buildJsonResponse(String type, String message) {
		  ObjectNode wrapper = Json.newObject();
		  ObjectNode msg = Json.newObject();
		  msg.put("message", message);
		  wrapper.set(type, msg);
		  
		  return wrapper;
		}
	
}
