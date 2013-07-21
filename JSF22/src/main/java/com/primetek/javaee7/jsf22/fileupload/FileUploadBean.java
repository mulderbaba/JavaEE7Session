/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primetek.javaee7.jsf22.fileupload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

/**
 *
 * @author mertcaliskan
 */
@Named(value = "fileUploadBean")
@RequestScoped
public class FileUploadBean {

    
    
  private Part file;
  private String fileContent;
 
  public void upload() {
    try {
      fileContent = new Scanner(file.getInputStream()).useDelimiter("\\A").next();
    } 
    catch (IOException e) {
        System.err.print(e);
      // Error handling
    }
  }
 
  public Part getFile() {
    return file;
  }
 
  public void setFile(Part file) {
    this.file = file;
  }
  
    public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        Part file = (Part)value;

        if (file.getSize() > 1024) {
          msgs.add(new FacesMessage("file too big"));
        }

        //if (!"text/plain".equals(file.getContentType())) {
        //  msgs.add(new FacesMessage("not a text file"));
        //}

        if (!msgs.isEmpty()) {
          throw new ValidatorException(msgs);
        }
    }
}