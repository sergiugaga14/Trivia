/**
 * Created by idaut on 03/18/16.
 */

 function checkUsername()
 {
     var username = document.getElementById("username").value;
     var label = document.getElementById("lblUsername");
 
     if(username.indexOf(" ") !== -1)
     {
         label.innerHTML = "No spaces allowed in the username";
         return false;
     }
     else
     {
         var flag = true;
         for(var i = 0; i < username.length; i++)
         {
             var code = username.charCodeAt(i);
 
             if (!(code > 47 && code < 58) && // numeric (0-9)
                 !(code > 64 && code < 91) && // upper alpha (A-Z)
                 !(code > 96 && code < 123)) // lower alpha (a-z)
             {
                 label.innerHTML = "Only latin letters and numbers allowed";
                 flag = false;
             }
             else
             {
                 label.innerHTML = "";
                 flag = true;
             }
         }
         return flag;
 
     }
 }
 
 function checkPass()
 {
     var pass = document.getElementById("pass").value;
     var label = document.getElementById("lblPass");
 
     if(pass.length < 8 && pass.length > 0)
     {
         label.innerHTML = "Password should be at least 8 characters long"
         return false;
     }
     else
     {
         label.innerHTML = "";
         return true;
     }
 }
 
 function confirmPass()
 {
     var originalPass = document.getElementById("pass").value;
     var passtwo = document.getElementById("passtwo").value;
     var label = document.getElementById("lblPasstwo");
 
     if(passtwo.length > 0 && passtwo !== originalPass)
     {
         label.innerHTML = "Passwords don't match";
         return false;
     }
     else
     {
         label.innerHTML = "";
         return true;
     }
 }
 
 function checkEmail()
 {
     var email = document.getElementById("email").value;
     var label = document.getElementById("lblEmail");
 
     if(email.indexOf(" ") !== -1)
     {
         label.innerHTML = "No spaces allowed in the email";
         return false;
     }
     else if(email.indexOf('@') == -1)
     {
         label.innerHTML = "'@' is required in email address";
         return false;
     }
     else if(email.indexOf('.') == -1 || (email.indexOf('.') - email.indexOf('@') < 2) || ((email.length - email.indexOf('.'))<3 ))
     {
         label.innerHTML = "Domain is required (ex: asd.com)";
         return false;
     }
     else
     {
         label.innerHTML = "";
         return true;
     }
 }
 
 function checkEmpty()
 {
     var fields = document.getElementsByClassName("fields");
     var labels = document.getElementsByClassName("labels");
     var flag = true;
 
     for(var i = 0; i<fields.length; i++)
     {
         if(fields[i].value.length < 1 || fields[i].value.length == "")
         {
             fields[i].style.backgroundColor = "#FFA758";
             labels[i].innerHTML = "Field is required";
             if(flag != false)
             flag = false;
         }
     }
 
     var finalFlag = flag && checkUsername() && checkPass() && confirmPass() && checkEmail();
     return finalFlag;
 
 }
 
 function resetError()
 {
     var labels = document.getElementsByClassName("labels");
     var fields = document.getElementsByClassName("fields");
 
     for(var i = 0; i < fields.length; i++)
     {
         if(this.id === fields[i].id)
         {
             labels[i].innerHTML = "";
         }
     }
     this.style.backgroundColor = "white";
 }
 
 //self executing below
 
 (function(){
 
     var mainForm = document.getElementById("mainForm");
     mainForm.onsubmit = function(){return checkEmpty()};
     
     var username = document.getElementById("username");
     username.oninput = checkUsername;
     username.onfocus = resetError;
 
     var pass = document.getElementById("pass");
     pass.onchange = function(){checkPass(); confirmPass();};
     pass.onfocus = resetError;
 
     var passtwo = document.getElementById("passtwo");
     passtwo.onchange = confirmPass;
     passtwo.onfocus = resetError;
 
     var email = document.getElementById("email");
     email.onchange = checkEmail;
     email.onfocus = resetError;
 
 })();