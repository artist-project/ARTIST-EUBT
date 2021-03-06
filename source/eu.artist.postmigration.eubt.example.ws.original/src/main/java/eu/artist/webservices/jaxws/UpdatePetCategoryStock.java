/*******************************************************************************
 * Copyright (c) 2014 Vienna University of Technology.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Patrick Neubauer (Vienna University of Technology) - initial API and implementation
 *
 * Initially developed in the context of ARTIST EU project www.artist-project.eu
 *******************************************************************************/

package eu.artist.webservices.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "updatePetCategoryStock", namespace = "http://webservices.artist.eu/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updatePetCategoryStock", namespace = "http://webservices.artist.eu/", propOrder = {
    "categoryid",
    "instock"
})
public class UpdatePetCategoryStock {

    @XmlElement(name = "categoryid", namespace = "")
    private String categoryid;
    @XmlElement(name = "instock", namespace = "")
    private String instock;   

    /**
     * 
     * @return
     *     returns String
     */
    public String getCategoryId() {
        return this.categoryid;
    }

    /**
     * 
     * @param categoryid
     *     the value for the categoryid property
     */
    public void setCategoryId(String categoryid) {
        this.categoryid = categoryid;
    }

    /**
     * 
     * @return
     *     returns String
     *     
     */
    public String getInStock() {
        return this.instock;
    }

    /**
     * 
     * @param instock
     *     the value for the instock property
     */
    public void setInStock(String instock) {
        this.instock = instock;
    }
    
}
