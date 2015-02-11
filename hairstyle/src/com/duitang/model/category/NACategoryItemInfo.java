package com.duitang.model.category;

import java.io.Serializable;

public class NACategoryItemInfo implements Serializable
{ 
	private static final long serialVersionUID = 1L;

private String iconUrl;

  private String name;

  private String target;

  public String getIconUrl()
  {
    return this.iconUrl;
  }

  public String getName()
  {
    return this.name;
  }

  public String getTarget()
  {
    return this.target;
  }

  public void setIconUrl(String paramString)
  {
    this.iconUrl = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setTarget(String paramString)
  {
    this.target = paramString;
  }
}
