package com.sample.wishlistDemo.api.generated;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Generated dto.
 */
@javax.annotation.Generated(value = "hybris", date = "Mon Mar 06 00:25:08 EST 2017")
@XmlRootElement
@JsonAutoDetect(isGetterVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE,
		creatorVisibility = Visibility.NONE, fieldVisibility = Visibility.NONE)
public class WishlistItem
{

	@com.fasterxml.jackson.annotation.JsonProperty(value="product")
	@javax.validation.constraints.Pattern(regexp="^.+")
	@javax.validation.constraints.NotNull
	private java.lang.String _product;

	@com.fasterxml.jackson.annotation.JsonProperty(value="amount")
	@javax.validation.constraints.DecimalMin(value="1")
	@javax.validation.constraints.NotNull
	private java.lang.Integer _amount;

	@com.fasterxml.jackson.annotation.JsonProperty(value="note")
	private java.lang.String _note;

	@com.fasterxml.jackson.annotation.JsonProperty(value="createdAt")
	private java.util.Date _createdAt;
	
	public java.lang.String getProduct()
	{
		return _product;
	}
	
	public java.lang.Integer getAmount()
	{
		return _amount;
	}
	
	public java.lang.String getNote()
	{
		return _note;
	}
	
	public java.util.Date getCreatedAt()
	{
		return _createdAt;
	}

	public void setProduct(final java.lang.String _product)
	{
		this._product = _product;
	}

	public void setAmount(final java.lang.Integer _amount)
	{
		this._amount = _amount;
	}

	public void setNote(final java.lang.String _note)
	{
		this._note = _note;
	}

	public void setCreatedAt(final java.util.Date _createdAt)
	{
		this._createdAt = _createdAt;
	}

}
